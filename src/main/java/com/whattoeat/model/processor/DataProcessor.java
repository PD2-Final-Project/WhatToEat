package com.whattoeat.model.processor;

import com.whattoeat.Env;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Jess
 * <p>
 *     To give the recommended stores by sorted the data got from
 *     {@link com.whattoeat.model.api.DataParser}.
 * </p>
 * */
public class DataProcessor {
    private JSONObject searchResult;
    private double priceLevelWeight = 7.50;
    private double distanceWeight = 7.69;
    private double ratingWeight = 6.15;
    private Mood mood;

    /**
     * @param searchResult is the JSONArray of the search results.
     * */
    public DataProcessor(JSONObject searchResult) {
        this.searchResult = searchResult;
    }

    /**
     * To standardize the distance value from 0 to 5.
     * @param terms is the key in the details object.
     * The method will standardize the value of the key in the details object.
     * */
    private void setStandardizedDetailsValues(String terms){
        int scale = 5;
        JSONArray storeContent = this.searchResult.getJSONArray("storeContent");
        double[] values = new double[storeContent.length()];
        for(int j = 0 ; j < storeContent.length() ; j++) {
            JSONObject details = storeContent.getJSONObject(j).getJSONObject("details");
            values[j] = details.getDouble(terms);
        }
        DataStandardization ds = new DataStandardization(values);
        ds.setScale(scale);
        double[] standardizedValues = ds.getStandardizedValues();
        for(int j = 0 ; j < standardizedValues.length ; j++) {
            storeContent.getJSONObject(j).getJSONObject("details").put(terms, standardizedValues[j]);
        }
    }
    /**
     * Weight the search results then determine which is higher ranking.
     * @param storeContent is a JSONArray of each searching.
     * @return {@link JSONArray} the sorted and weighted results
     * */
    private JSONArray weightedSearchResultCalculator(JSONArray storeContent){
        JSONArray weightedStoreContent = new JSONArray();
        ArrayList<JSONObject> storeDetails = new ArrayList<JSONObject>();
        for(int i = 0 ; i < storeContent.length() ; i++) {
            storeDetails.add(storeContent.getJSONObject(i));
        }
        Collections.sort(storeDetails, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                o1 = o1.getJSONObject("details");
                o2 = o2.getJSONObject("details");
                double priceLevel1 = (4 - o1.getInt("priceLevel"))*1.25;
                double priceLevel2 = (4 - o2.getInt("priceLevel"))*1.25;
                double distance1 = 5 - o1.getInt("distance");
                double distance2 = 5 - o2.getInt("distance");
                double rating1 = o1.getDouble("rating");
                double rating2 = o2.getDouble("rating");

                double weight1 = priceLevel1 * DataProcessor.this.priceLevelWeight +
                                distance1 * DataProcessor.this.distanceWeight +
                                rating1 * DataProcessor.this.ratingWeight;
                double weight2 = priceLevel2 * DataProcessor.this.priceLevelWeight +
                        distance2 * DataProcessor.this.distanceWeight +
                        rating2 * DataProcessor.this.ratingWeight;
                return Double.compare(weight2, weight1);
            }
        });
        for(int i = 0 ; i < storeDetails.size() ; i++) {
            weightedStoreContent.put(storeDetails.get(i));
        }
        return weightedStoreContent;
    }

    /**
     *  To get the weighted search results.
     *  @return {@link JSONArray} of the weighted search results.
     * */
    public JSONObject getWeightedSearchResult() {
        this.setStandardizedDetailsValues("distance");
        JSONObject weightedSearchResult = new JSONObject();
        weightedSearchResult.put("location", searchResult.getString("location"));
        weightedSearchResult.put("keyword", searchResult.getString("keyword"));
        weightedSearchResult.put("radius", searchResult.getString("radius"));
        weightedSearchResult.put("mood", this.mood.toString());
        // To process each stores in the storeContent array and store it to weightedSearchResult
        JSONArray storeContent = searchResult.getJSONArray("storeContent");
        weightedSearchResult.put("storeContent", weightedSearchResultCalculator(storeContent));
        return weightedSearchResult;
    }

    /**
     * To set the mood of the user, and change the weighting depending on the mood.
     * @param mood The mood defined in {@link com.whattoeat.model.processor.Mood}
     * */
    public void setMood(Mood mood) {
        this.mood = mood;
        if(mood == Mood.GOOD){
            this.priceLevelWeight *= 0.3;
            this.ratingWeight *= 2;
            this.distanceWeight = 0.2;
        } else if(mood == Mood.BAD) {
            this.priceLevelWeight *= 0.7;
            this.ratingWeight *= 1.3;
            this.distanceWeight *= 1.4;
        }
    }

    /**
     * To make the json file contains the url of photos.
     * @param photoWidth The width of photos
     * @param photoHeight The height of photos
     * */
    public void setPhoto(int photoWidth, int photoHeight) {
        JSONArray storeContent = this.searchResult.getJSONArray("storeContent");
        for(int i = 0 ; i < storeContent.length() ; i++) {
            JSONArray photos = storeContent.getJSONObject(i).getJSONObject("details").getJSONArray("photos");
            for(int j = 0 ; j < photos.length() ; j++) {
                String photoID = photos.getString(j);
                String photoURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=" +
                        photoWidth + "&maxheight=" + photoHeight + "&photo_reference=" + photoID +
                        "&key=" + Env.getApiKey();
                photos.put(j, photoURL);
            }
        }
    }
}
