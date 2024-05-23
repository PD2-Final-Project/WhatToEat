package com.whattoeat.model.api;

import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import com.whattoeat.model.api.TimeSplit;


/**
 * <p>
 * The DataParser is used to parse the data from google map. <br>
 * You should provide the google map api key and the location you want to search neary by. <br>
 * You can also assign the time span to determine whether to search again if the given search conditions are the same as before. <br>
 * The default time span is 2.0 hours. <br>
 * Example:
 * <blockquotes><pre>
 *     {@code
 *          DataParser dataParser = new DataParser("AIzaSyA...", "your location", 2.0);
 *          dataParser.setKeyword(keyWord);
 *          dataParser.setRadius(radius);
 *          dataParser.setPlaceType(Places.RESTAURANT);
 *          dataParser.searchNear();
 *          JSONObject searchedStores = dataParser.getSearchResult();
 *          dataParser.close();
 *     }
 * </pre></blockquotes>
 * @see Places
 * @see JSONObject
 * @author hding4915
 * */
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
    private JSONObject searchResult = null;

    private final TimeSplit timeSplit;

    /**
     * @param apiKey - Put the google map api key.
     * @param location - Set the location that you want to search.
     * */
    public DataParser(String apiKey, String location) {
        this(apiKey, location, 2.0);
    }

    /**
     * @param apiKey - Put the google map api key.
     * @param location - Set the location that you want to search.
     * @param timeSpan - Set the time span in hours to determine whether to search again if the given search conditions are the same as before.
     * */
    public DataParser(String apiKey, String location, double timeSpan) {
        this.apiKey = apiKey;
        context = new GeoApiContext.Builder()
                .apiKey(this.apiKey)
                .build();
        setSelfLocation(location);
        if (timeSpan <= 0) {
            timeSpan = 2.0;
        }
        this.timeSplit = new TimeSplit(timeSpan);
    }

    private void reset() {
        storesDataCount = 0;
        finishSearch = false;
    }

    /**
     * @param minPreferPrice - Set the min price for searching.
     * @see PriceLevel
     * */
    public void setMinPreferPrice(PriceLevel minPreferPrice) {
        this.minPreferPrice = minPreferPrice;
        reset();
    }

    /**
     * @param maxPreferPrice - Set the max price for searching.
     * @see PriceLevel
     * */
    public void setMaxPreferPrice(PriceLevel maxPreferPrice) {
        this.maxPreferPrice = maxPreferPrice;
        reset();
    }

    /**
     * @param places Set the place type for searching so that the searching
     *               result can be precise.
     * @see Places
     * */
    public void setPlaceType(@NotNull Places places) {
        this.placeType = PlaceType.valueOf(places.name());
    }

    /**
     * <p>
     * Specifies the distance (in meters) within which to return place results.
     * The maximum allowed radius is 50,000 meters.
     * @param radius - Set the distance in meters around the location to search.
     * */
    public void setRadius(int radius) {
        this.radius = radius;
        reset();
    }

    /**
     * <p>
     * Specifies a term to be matched against all content that you want to search for.
     * You may assign keyword like: "beef", "cafe", ...
     * @param keyword - Set the keyword for searching.
     * */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
        reset();
    }

    /**
     * @param location - Set the location for searching.
     * */
    public void setSelfLocation(String location) {
        GeocodingResult[] geocodingResults = parseGeocode(location);
        for (GeocodingResult geocodingResult : geocodingResults) {
            selfLocationDetails = geocodingResult;
            break;
        }
        reset();
    }

    /**
     * @return Get current place type. The default value is PlaceType.RESTAURANT
     * @see PlaceType
     * */
    public PlaceType getPlaceType() {
        return placeType;
    }

    /**
     * @return Get the min preference price. The default value is null.
     * @see PriceLevel
     */
    public PriceLevel getMinPreferPrice() {
        return minPreferPrice;
    }

    /**
     * @return Get the max preference price. The default value is null.
     * @see PriceLevel
     */
    public PriceLevel getMaxPreferPrice() {
        return maxPreferPrice;
    }

    /**
     * @return Get the number of the data for searching.
     * */
    public int getStoresDataCount() { return this.storesDataCount; }

    /**
     * @param location - The location you want to request.
     * @return Type of {@link GeocodingResult}[].
     *         It contains placeId, formattedAddress, geometry, ......
     *         It could be null if the given location is invalid.
     * */
    public GeocodingResult[] parseGeocode(String location) {
        GeocodingApiRequest geocodingApiRequest = GeocodingApi.geocode(context, location);
        geocodingApiRequest.language("zh-TW");
        GeocodingResult[] geocodingResults = null;
        try {
            geocodingResults = geocodingApiRequest.await();
        } catch (ApiException | IOException | InterruptedException e) {
            geocodingApiRequest.cancel();
            e.printStackTrace();
        } finally {

        }
        return geocodingResults;
    }

    /**
     * <p>
     * Close the GeoApiContext.
     * @throws RuntimeException if the {@link IOException} were caught.
     * */
    public void close() {
        try {
            context.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * <p>
     * It will search the places nearby from the location you set
     * and also parse data and store it to {@link JSONObject}. <br>
     * You can get it by calling {@link DataParser#getSearchResult()}
     * */
    public void searchNear() {
        if (searchResult == null) {
            searchResult = searchNearBy();
        }
        searchNearBy(searchResult);
    }


    /**
     * <p>
     * It will search the places nearby several times
     * by calling {@link DataParser#searchNear()} for the search times you assigned.
     * @param searchTimes - The times you want to search.
     * */
    public void searchNear(int searchTimes) {
        for (int i = 0; i < searchTimes; i++) {
            searchNear();
        }
    }

    /**
     * @return Type of {@link JSONObject}.
     *         You will get the searching result with JSONArray format.
     * */
    public JSONObject getSearchResult() {
        return searchResult;
    }


    /**
     * <p>
     * It will search the places nearby from the location you set
     * and also parse data and return a {@link JSONObject}. <br>
     * @return Type of {@link JSONObject} for the searching result.
     * */
    public JSONObject searchNearBy() {
        return searchNearBy(new JSONObject());
    }

    /**
     * <p>
     * It will search the places nearby from the location you set
     * several times and also parse data and return a {@link JSONObject}. <br>
     * @param searchTimes - The times you want to search.
     * @return Type of {@link JSONObject} for the searching result.
     * */
    public JSONObject searchNearBy(int searchTimes) {
        if (searchTimes <= 1) {
            return searchNearBy();
        } else {
            JSONObject searchedStores = new JSONObject();
            for (int i = 0; i < searchTimes; i++) {
                searchNearBy(searchedStores);
                if (finishSearch) {
                    break;
                }
            }
            return searchedStores;
        }
    }

    /**
     * <p>
     * It will search the places nearby from the location you set
     * and also parse data to the {@link JSONObject} you passed in and return it. <br>
     * @param searchedStores - The searched result you got before.
     * @return Type of {@link JSONObject} for the searching result.
     * */
    public JSONObject searchNearBy(JSONObject searchedStores) {
        if (selfLocationDetails == null) {
            throw new IllegalArgumentException(
                    "Location details is null. " +
                    "It could happen if you have not set the location yet or the given location is invalid. " +
                    "Please provide or change the location");
        }
        if (finishSearch) {
            if (checkTimeToSearch(searchedStores)) {
                reset();
            } else {
                return searchedStores;
            }
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
            nearbySearchRequest.cancel();
            e.printStackTrace();
        } finally {

        }
        return searchedStores;
    }

    /**
     * <p>
     * It will parse the details of the place by the place id.
     * @param placeId - The place id of the place.
     * @return Type of {@link PlaceDetails}.
     * */
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
            placeDetailsRequest.cancel();
            e.printStackTrace();
        } finally {

        }
        return details;
    }

    /**
     * <p>
     * Count the distance between multiple origin and destination.
     * @param origin - Multiple origin. It should be the address of the place.
     * @param destination - Multiple destination. It should be the address of the place.
     * @return Type of {@link DistanceMatrix}.
     * */
    public DistanceMatrix countDistance(String[] origin, String[] destination) {
        DistanceMatrixApiRequest request = DistanceMatrixApi.getDistanceMatrix(context, origin, destination);
        request.mode(TravelMode.WALKING);
        request.transitRoutingPreference(TransitRoutingPreference.LESS_WALKING);
        DistanceMatrix matrix = null;
        try {
            matrix = request.await();
        } catch (ApiException | InterruptedException | IOException e) {
            request.cancel();
            e.printStackTrace();
        } finally {

        }
        return matrix;
    }

    private boolean checkTimeToSearch(@NotNull JSONObject searchedStores) {
        if (searchedStores.has("searchTime")) {
            String searchTime = searchedStores.getString("searchTime");
            try {
                return timeSplit.checkTimeInterval(searchTime);
            } catch (InvalidTimeFormatException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private void parseResults(PlacesSearchResult[] results, @NotNull JSONObject searchedStores) {
        JSONArray storeContent = null;
        String addressValue = selfLocationDetails.formattedAddress;
        String keywordValue = String.valueOf(keyword);
        String radiusValue = String.valueOf(radius);
        boolean findObject = false;

        if (searchedStores.has("location")) {
            String location = searchedStores.getString("location");
            String keyword = searchedStores.getString("keyword");
            String radius = searchedStores.getString("radius");
            if (location.equals(addressValue) && keyword.equals(keywordValue) && radius.equals(radiusValue)) {
                storeContent = searchedStores.getJSONArray("storeContent");
                findObject = true;
            }
        }
        if (!findObject) {
            searchedStores.put("location", addressValue);
            searchedStores.put("keyword", keywordValue);
            searchedStores.put("radius", radiusValue);
            searchedStores.put("searchTime", timeSplit.getNowTime());
            storeContent = new JSONArray();
            searchedStores.put("storeContent", storeContent);
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
            int priceLevel = Integer.parseInt(PriceLevel.MODERATE.toString());
            if (!(details.priceLevel == null || details.priceLevel.toString().equals("Unknown"))) {
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
