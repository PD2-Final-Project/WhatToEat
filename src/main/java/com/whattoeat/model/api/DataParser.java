package com.whattoeat.model.api;

import com.google.gson.JsonObject;
import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import java.io.IOException;
import java.util.Arrays;

public class DataParser {
    private final GeoApiContext context;
    private final String apiKey;
    private GeocodingResult selfLocationDetails = null;
    private PriceLevel minPreferPrice = null;
    private PriceLevel maxPreferPrice = null;
    private String keyword = "";
    private String searchToken = null;
    private int radius = 1000;
    private int storesDataCount = 0;
    private boolean finishSearch = false;


    public DataParser(String apiKey, String location) {
        this.apiKey = apiKey;
        context = new GeoApiContext.Builder()
                .apiKey(this.apiKey)
                .build();
        setSelfLocation(location);
    }

    public void setMinPreferPrice(PriceLevel minPreferPrice) {
        this.minPreferPrice = minPreferPrice;
    }

    public void setMaxPreferPrice(PriceLevel maxPreferPrice) {
        this.maxPreferPrice = maxPreferPrice;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public int getStoresDataCount() { return this.storesDataCount; }

    public void setSelfLocation(String location) {
        GeocodingApiRequest geocodingApiRequest = GeocodingApi.geocode(context, location);
        try {
            GeocodingResult[] geocodingResults = geocodingApiRequest.await();
            for (GeocodingResult geocodingResult : geocodingResults) {
                selfLocationDetails = geocodingResult;
                break;
            }
        } catch (ApiException | IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void close() {
        try {
            context.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonObject searchNearBy() {
        return searchNearBy(new JsonObject());
    }

    public JsonObject searchNearBy(int searchTimes) {
        if (searchTimes <= 1) {
            return searchNearBy();
        } else {
            JsonObject searchedStores = new JsonObject();
            for (int i = 0; i < searchTimes; i++) {
                searchedStores = searchNearBy(searchedStores);
                if (finishSearch) {
                    break;
                }
            }
            return searchedStores;
        }
    }

    public JsonObject searchNearBy(JsonObject searchedStores) {
        if (selfLocationDetails == null) {
            System.err.println("location is null, please set the location");
            return null;
        }
        if (finishSearch) {
            return searchedStores;
        }
        LatLng location = selfLocationDetails.geometry.location;
        NearbySearchRequest nearbySearchRequest = new NearbySearchRequest(context);
        if (searchToken == null) {
            nearbySearchRequest.language("zh-TW");
            nearbySearchRequest.location(location);
            nearbySearchRequest.type(PlaceType.RESTAURANT);
            nearbySearchRequest.keyword(keyword);
            nearbySearchRequest.radius(radius);
            nearbySearchRequest.openNow(true);
            if (!(minPreferPrice == null)) {
                nearbySearchRequest.minPrice(minPreferPrice);
            }
            if (!(maxPreferPrice == null)) {
                nearbySearchRequest.maxPrice(maxPreferPrice);
            }
        } else {
            nearbySearchRequest.pageToken(searchToken);
        }
        try {
            PlacesSearchResponse response = nearbySearchRequest.await();
            searchToken = response.nextPageToken;
            if (searchToken == null) {
                finishSearch = true;
            }
            parseResults(response.results, searchedStores);
        } catch (ApiException | IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
        return searchedStores;
    }

    public PlaceDetails getPlaceDetails(String placeId) {
        PlaceDetailsRequest placeDetailsRequest = new PlaceDetailsRequest(context);
        placeDetailsRequest.placeId(placeId);
        placeDetailsRequest.language("zh-TW");
        placeDetailsRequest.fields(Arrays.stream(PlaceDetailsRequest.FieldMask.values())
                .filter(x -> x != PlaceDetailsRequest.FieldMask.SECONDARY_OPENING_HOURS)
                .toArray(PlaceDetailsRequest.FieldMask[]::new));
        PlaceDetails details = null;
        try {
            details = placeDetailsRequest.await();
        } catch (IllegalStateException | IOException | InterruptedException | ApiException e) {
            e.printStackTrace();
        } finally {

        }
        return details;
    }
    public DistanceMatrix countDistance(String[] origin, String[] destination) {
        DistanceMatrixApiRequest request = DistanceMatrixApi.getDistanceMatrix(context, origin, destination);
        DistanceMatrix matrix = null;
        try {
            matrix = request.await();
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {

        }
        return matrix;
    }
    private void parseResults(PlacesSearchResult[] results, JsonObject searchedStores) {
        JsonObject storeJson;
        String addressKey = selfLocationDetails.formattedAddress;
        String keywordKey = String.format("keyword:%s", keyword);
        String radiusKey = String.format("raduis:%d", radius);
        if (searchedStores.has(addressKey)) {
            JsonObject keywordSearch = searchedStores.getAsJsonObject(addressKey);
            if (keywordSearch.has(keywordKey)) {
                JsonObject radiusSearch = keywordSearch.getAsJsonObject(keywordKey);
                if (radiusSearch.has(radiusKey)) {
                    storeJson = radiusSearch.getAsJsonObject(radiusKey);
                } else {
                    storeJson = new JsonObject();
                    radiusSearch.add(radiusKey, storeJson);
                }
            } else {
                JsonObject radiusSearch = new JsonObject();
                storeJson = new JsonObject();
                keywordSearch.add(keywordKey, radiusSearch);
                radiusSearch.add(radiusKey, storeJson);
            }
        } else {
            storeJson = new JsonObject();
            JsonObject radiusSearch = new JsonObject();
            radiusSearch.add(String.format("raduis:%d", radius), storeJson);
            JsonObject keywordSearch = new JsonObject();
            keywordSearch.add(String.format("keyword:%s", keyword), radiusSearch);
            searchedStores.add(selfLocationDetails.formattedAddress, keywordSearch);
        }
        for (PlacesSearchResult result : results) {
            PlaceDetails details = getPlaceDetails(result.placeId);

            // parse reviews
            JsonObject storeReviews = new JsonObject();
            parseStoreReviews(details, storeReviews);

            // parse photos
            JsonObject storePhotos = new JsonObject();
            parseStorePhotos(details, storePhotos);

            // parse open hours
            JsonObject openPeriod = new JsonObject();
            parseOpenPeriod(details, openPeriod);

            // parse distance
            long meter = getDistance(details);

            // add all properties to json
            String phoneNumber = details.formattedPhoneNumber == null ? "" : details.formattedPhoneNumber;
            JsonObject storeDetails = new JsonObject();
            storeDetails.addProperty("rating", result.rating);
            storeDetails.addProperty("address", details.formattedAddress);
            storeDetails.addProperty("distance", meter);
            storeDetails.addProperty("phoneNumber", phoneNumber);
            storeDetails.addProperty("url", details.url.toString());
            storeDetails.add("openTimes", openPeriod);
            storeDetails.add("photos", storePhotos);
            storeDetails.add("reviews", storeReviews);
            storeJson.add(result.name, storeDetails);
            storesDataCount++;
        }
    }

    private long getDistance(PlaceDetails details) {
        String[] origin = {selfLocationDetails.formattedAddress};
        String[] destination = {details.formattedAddress};
        DistanceMatrix matrix = countDistance(origin, destination);
        return matrix.rows[0].elements[0].distance.inMeters;
    }

    private void parseOpenPeriod(PlaceDetails details, JsonObject openPeriod) {
        for (OpeningHours.Period period : details.openingHours.periods) {
            String openHoursTime;
            String day;
            if (!(period.close == null)) {
                openHoursTime = String.format("%s-%s", period.open.time.toString(), period.close.time.toString());
                day = period.open.day.getName();
            } else {
                openHoursTime = "24 hours";
                day = "All";
            }
            openPeriod.addProperty(day, openHoursTime);
        }
    }

    private void parseStorePhotos(PlaceDetails details, JsonObject storePhotos) {
        Photo[] photos = details.photos;
        int index = 0;
        for (Photo photo : photos) {
            storePhotos.addProperty(String.valueOf(index), photo.photoReference);
            index++;
        }
    }

    private void parseStoreReviews(PlaceDetails details, JsonObject storeReviews) {
        PlaceDetails.Review[] reviews = details.reviews;
        int index = 0;
        for (PlaceDetails.Review review : reviews) {
            JsonObject personReview = new JsonObject();
            personReview.addProperty("authorName", review.authorName);
            personReview.addProperty("text", review.text);
            personReview.addProperty("rating", review.rating);
            storeReviews.add(String.valueOf(index), personReview);
            index++;
        }
    }

}
