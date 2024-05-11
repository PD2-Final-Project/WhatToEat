package com.whattoeat.model.api;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class DataParser {
    private final GeoApiContext context;
    private final String apiKey;
    private GeocodingResult selfLocationDetails = null;
    private PriceLevel minPreferPrice = null;
    private PriceLevel maxPreferPrice = null;
    private PlaceType placeType = PlaceType.RESTAURANT;
    private String keyword = "";
    private String searchToken = null;
    private int radius = 1000;
    private int storesDataCount = 0;
    private boolean finishSearch = false;
    private JSONArray searchResult = null;


    public DataParser(String apiKey, String location) {
        this.apiKey = apiKey;
        context = new GeoApiContext.Builder()
                .apiKey(this.apiKey)
                .build();
        setSelfLocation(location);
    }

    private void reset() {
        storesDataCount = 0;
        finishSearch = false;
    }

    public void setMinPreferPrice(PriceLevel minPreferPrice) {
        this.minPreferPrice = minPreferPrice;
        reset();
    }

    public void setMaxPreferPrice(PriceLevel maxPreferPrice) {
        this.maxPreferPrice = maxPreferPrice;
        reset();
    }

    public void setPlaceType(@NotNull Places places) {
        this.placeType = PlaceType.valueOf(places.name());
    }

    public void setRadius(int radius) {
        this.radius = radius;
        reset();
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        reset();
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public int getStoresDataCount() { return this.storesDataCount; }

    public void setSelfLocation(String location) {
        GeocodingResult[] geocodingResults = parseGeocode(location);
        for (GeocodingResult geocodingResult : geocodingResults) {
            selfLocationDetails = geocodingResult;
            break;
        }
        reset();
    }

    public GeocodingResult[] parseGeocode(String location) {
        GeocodingApiRequest geocodingApiRequest = GeocodingApi.geocode(context, location);
        GeocodingResult[] geocodingResults = null;
        try {
            geocodingResults = geocodingApiRequest.await();
        } catch (ApiException | IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {

        }
        return geocodingResults;
    }

    public void close() {
        try {
            context.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchNear() {
        if (searchResult == null) {
            searchResult = searchNearBy();
        }
        searchResult = searchNearBy(searchResult);
    }

    public void searchNear(int searchTimes) {
        for (int i = 0; i < searchTimes; i++) {
            searchNear();
        }
    }

    public JSONArray getSearchResult() {
        return searchResult;
    }

    public JSONArray searchNearBy() {
        return searchNearBy(new JSONArray());
    }

    public JSONArray searchNearBy(int searchTimes) {
        if (searchTimes <= 1) {
            return searchNearBy();
        } else {
            JSONArray searchedStores = new JSONArray();
            for (int i = 0; i < searchTimes; i++) {
                searchedStores = searchNearBy(searchedStores);
                if (finishSearch) {
                    break;
                }
            }
            return searchedStores;
        }
    }

    public JSONArray searchNearBy(JSONArray searchedStores) {
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
            nearbySearchRequest.type(placeType);
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

    private void parseResults(PlacesSearchResult[] results, @NotNull JSONArray searchedStores) {
        JSONObject storeJson = null;
        JSONArray storeContent = null;
        String addressValue = selfLocationDetails.formattedAddress;
        String keywordValue = String.valueOf(keyword);
        String radiusValue = String.valueOf(radius);

        boolean findJsonObject = false;
        for (Object object: searchedStores) {
            JSONObject jsonObject = (JSONObject) object;
            String location = jsonObject.getString("location");
            String keyword = jsonObject.getString("keyword");
            String radius = jsonObject.getString("radius");
            if (location.equals(addressValue) && keyword.equals(keywordValue) && radius.equals(radiusValue)) {
                storeJson = jsonObject;
                storeContent = jsonObject.getJSONArray("storeContent");
                findJsonObject = true;
                break;
            }
        }
        if (!findJsonObject) {
            storeJson = new JSONObject();
            storeJson.put("location", addressValue);
            storeJson.put("keyword", keywordValue);
            storeJson.put("radius", radiusValue);
            storeContent = new JSONArray();
            storeJson.put("storeContent", storeContent);
        }
        for (PlacesSearchResult result : results) {
            PlaceDetails details = getPlaceDetails(result.placeId);

            // parse reviews
            JSONArray storeReviews = new JSONArray();
            parseStoreReviews(details, storeReviews);

            // parse photos
            JSONArray storePhotos = new JSONArray();
            parseStorePhotos(details, storePhotos);

            // parse open hours
            JSONObject openPeriod = new JSONObject();
            parseOpenPeriod(details, openPeriod);

            // parse distance
            long meter = getDistance(details);

            // price level
            int priceLevel = -1;
            if (!(details.priceLevel == null)) {
                priceLevel = Integer.parseInt(details.priceLevel.toString());
            }

            // put all properties to json
            String phoneNumber = details.formattedPhoneNumber == null ? "" : details.formattedPhoneNumber;
            JSONObject storeDetails = new JSONObject();
            storeDetails.put("name", result.name);
            JSONObject advanceDetails = new JSONObject();
            advanceDetails.put("rating", result.rating);
            advanceDetails.put("address", details.formattedAddress);
            advanceDetails.put("distance", meter);
            advanceDetails.put("phoneNumber", phoneNumber);
            advanceDetails.put("url", details.url.toString());
            advanceDetails.put("priceLevel", priceLevel);
            advanceDetails.put("openTimes", openPeriod);
            advanceDetails.put("photos", storePhotos);
            advanceDetails.put("reviews", storeReviews);
            storeDetails.put("details", advanceDetails);
            storeContent.put(storeDetails);
            storesDataCount++;
        }
        if (!findJsonObject) {
            searchedStores.put(storeJson);
        }
    }

    private long getDistance(@NotNull PlaceDetails details) {
        String[] origin = {selfLocationDetails.formattedAddress};
        String[] destination = {details.formattedAddress};
        DistanceMatrix matrix = countDistance(origin, destination);
        return matrix.rows[0].elements[0].distance.inMeters;
    }

    private void parseOpenPeriod(@NotNull PlaceDetails details, JSONObject openPeriod) {
        if (!(details.openingHours == null)) {
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
                openPeriod.put(day, openHoursTime);
            }
        }
    }

    private void parseStorePhotos(@NotNull PlaceDetails details, JSONArray storePhotos) {
        Photo[] photos = details.photos;
        if (!(photos == null)) {
            for (Photo photo : photos) {
                storePhotos.put(photo.photoReference);
            }
        }
    }

    private void parseStoreReviews(@NotNull PlaceDetails details, JSONArray storeReviews) {
        PlaceDetails.Review[] reviews = details.reviews;
        if (!(reviews == null)) {
            for (PlaceDetails.Review review : reviews) {
                JSONObject personReview = new JSONObject();
                personReview.put("authorName", review.authorName);
                personReview.put("text", review.text);
                personReview.put("rating", review.rating);
                storeReviews.put(personReview);
            }
        }
    }
}
