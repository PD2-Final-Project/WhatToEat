package com.whattoeat.model.processor;

import com.google.maps.model.PriceLevel;
import com.whattoeat.model.api.DataParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Jess, learningen13@gmail.com
 * To give the recommended stores by sorted the data got from {@link com.whattoeat.model.api.DataParser}.
 * */
public class DataProcessor {
    private JSONArray searchResults;
    private int priceLevel = Integer.valueOf(PriceLevel.MODERATE.toString());
    private int[] radius;
    private double rating = 3;
    private int mood = Mood.CALM.getMoodCode();
    // The value of weight scales from 0 to 10
    private int priceLevelWeight = 5;
    private int distanceWeight = 5;
    private int ratingWeight = 5;
    private int moodWeight = 5;

    public DataProcessor(JSONArray searchResults) {
        this.searchResults = searchResults;
        this.radius = new int[searchResults.length()];
        for(int i = 0 ; i < searchResults.length() ; i++) {
            this.radius[i] = searchResults.getJSONObject(i).getInt("radius");
        }
    }

    public JSONArray getWeightedSearchResults() {
        JSONArray weightedSearchResults = new JSONArray();
        // This will process each search results
        for(int i = 0 ; i < searchResults.length() ; i++) {
            // The data that won't affect the results
            JSONObject searchResult = searchResults.getJSONObject(i);
            JSONObject weightedSearchResult = new JSONObject();
            weightedSearchResult.put("location", searchResult.getString("location"));
            weightedSearchResult.put("keyword", searchResult.getString("keyword"));
            weightedSearchResult.put("radius", searchResult.getString("radius"));
            // To process each stores in the storeContent array and store it to weightedSearchResult
            JSONArray storeContent = searchResult.getJSONArray("storeContent");
            weightedSearchResult.put("storeContent", weightedSearchResultCalculator(storeContent, this.radius[i]));
            weightedSearchResults.put(weightedSearchResult);
        }
        return weightedSearchResults;
    }

    private JSONArray weightedSearchResultCalculator(JSONArray storeContent, int radius){
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
                int priceLevel1 = 6 - o1.getInt("priceLevel");
                int priceLevel2 = 6 - o2.getInt("priceLevel");
                int distance1 = o1.getInt("distance")/radius*5;
                int distance2 = o2.getInt("distance")/radius*5;
                double rating1 = o1.getDouble("rating");
                double rating2 = o2.getDouble("rating");

                double weight1 = priceLevel1 * DataProcessor.this.priceLevelWeight +
                                distance1 * DataProcessor.this.distanceWeight +
                                rating1 * DataProcessor.this.ratingWeight;
                double weight2 = priceLevel2 * DataProcessor.this.priceLevelWeight +
                        distance2 * DataProcessor.this.distanceWeight +
                        rating2 * DataProcessor.this.ratingWeight;
                if(weight1 > weight2 || weight1 == weight2) {
                    return 1;
                }
                return -1;
            }
        });
        for(int i = 0 ; i < storeDetails.size() ; i++) {
            weightedStoreContent.put(storeDetails.get(i));
        }
        return weightedStoreContent;
    }

    public void setPriceLevel(PriceLevel priceLevel) {
        this.priceLevel = Integer.parseInt(priceLevel.toString());
    }

//    public void setRadius(int radius, int index) {
//        this.radius[index] = radius;
//    }

    public void setRating(double rating) {
        this.rating = rating;
    }


}
