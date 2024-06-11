package com.whattoeat.model;

import com.whattoeat.Env;
import com.whattoeat.model.api.DataParser;
import com.whattoeat.model.processor.DataProcessor;
import com.whattoeat.model.processor.DataWriter;
import com.whattoeat.model.processor.Mood;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Jess
 * DataReader is mean to provide the result of the given condition.
 * If the search conditions have been searched before, then returning the result.
 * Else, if the conditions haven't been searched, first making a search then returning and writing to the file.
 * */
public class StoresDataQuery {
    private final JSONObject searchResult;
    public final StoresData storesData;
    private final static String path = setDataPath();

    /**
     * @param location The location where the search is made.
     * @param keyword The keyword to limit the search results.
     * @param radius The radius desired to search.
     * */
    public StoresDataQuery(String location, String keyword, int radius) {
        this.searchResult = this.getSearchResult(this.path, location, keyword, radius, Mood.NORMAL);
        this.storesData = new StoresData(this.searchResult.getJSONArray("storeContent"));
    }
    public StoresDataQuery(String location, String keyword, int radius, Mood mood) {
        this.searchResult = this.getSearchResult(this.path, location, keyword, radius, mood);
        this.storesData = new StoresData(this.searchResult.getJSONArray("storeContent"));
    }

    /**
     * @param path The path of the file to store.
     * <p> This will return the search results by given conditions </p>
     * */
    private JSONObject getSearchResult(String path, String location, String keyword, int radius, Mood mood) {
        // If the data has been found inside the file, then return it;
        try {
            File file = new File(path);
            if(!file.exists()) file.createNewFile();
            if(JSONValidity.isValidJSONFile(path)) {
                FileReader reader = new FileReader(path);
                JSONArray searchResults = new JSONArray(new JSONTokener(reader));
                if (!searchResults.isEmpty()) {
                    for (int i = 0; i < searchResults.length(); i++) {
                        Long timeGap = Long.parseLong(searchResults.getJSONObject(i).getString("searchTime")) - System.currentTimeMillis();
                        if (searchResults.getJSONObject(i).getString("keyword").equals(keyword) &&
                                searchResults.getJSONObject(i).getString("location").equals(location) &&
                                searchResults.getJSONObject(i).getInt("radius") == radius &&
                                timeGap < (1000*60*60*2) &&
                                searchResults.getJSONObject(i).getString("mood").equals(mood.toString())
                        ) {
                            reader.close();
                            return searchResults.getJSONObject(i);
                        }
                    }
            }
                reader.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        // If the data hasn't been found then create it.
        // TODO: This hard coded part should be changed.
        Env env = new Env("src/env.json");
        DataParser dataParser = new DataParser(env.getApiKey(), location);
        dataParser.setKeyword(keyword);
        dataParser.setRadius(radius);
        DataProcessor processor = new DataProcessor(dataParser.searchNearBy());
        processor.setMood(mood);
        // TODO: hard coded
        DataWriter dataWriter = new DataWriter(this.path, processor.getWeightedSearchResult());
        return processor.getWeightedSearchResult();
    }
    /**
     * Set the path to store query results.
     * @return String path
     * */
    public static String setDataPath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userHome  = System.getProperty("user.home");
        String path = "";
        if(osName.contains("win")) path = System.getenv("LOCALAPPDATA") + "\\WhatToEat\\queryResults.json";
        else if(osName.contains("mac")) path = "~/Library/Application Support/WhatToEat/queryResults.json";
        else path = "~/.WhatToEat/queryResults.json";
        try {
            File file = new File(path);
            if(!file.exists()) file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
    /**
     * This class can get the stores' data.
     * <p>Usage:
     * First new a StoresDataQuery and give the conditions.
     * To get the data by using getter like: StoreDataQuery.storesData.getter()
     * </p>
     * */
    public class StoresData {
        private int length;
        private int[] distances;
        private double[] ratings;
        private String[] names;
        private String[] addresses;
        private String[] phoneNumbers;
        private int[] priceLevels;
        private String[] urls;
        //private String[][] reviews;

        public StoresData(JSONArray storesContent) {
            this.length = storesContent.length();
            this.distances = new int[length];
            this.priceLevels = new int[length];
            this.ratings = new double[length];
            this.names = new String[length];
            this.addresses = new String[length];
            this.phoneNumbers = new String[length];
            this.urls = new String[length];
            //this.reviews = new String[this.length][];
            for(int i = 0; i < length; i++) {
                JSONObject storeContent = storesContent.getJSONObject(i);
                this.names[i] = storeContent.getString("name");
                JSONObject details = storeContent.getJSONObject("details");
                this.addresses[i] = details.getString("address");
                this.phoneNumbers[i] = details.getString("phoneNumber");
                this.distances[i] = details.getInt("distance");
                this.ratings[i] = details.getDouble("rating");
                this.priceLevels[i] = details.getInt("priceLevel");
                this.urls[i] = details.getString("url");
            }
        }

        public int getLength() {
            return length;
        }

        public int[] getDistances() {
            return this.distances;
        }

        public double[] getRatings() {
            return this.ratings;
        }

        public String[] getNames() {
            return this.names;
        }

        public String[] getAddresses() {
            return this.addresses;
        }

        public String[] getPhoneNumbers() {
            return this.phoneNumbers;
        }

        public int[] getPriceLevels() {
            return this.priceLevels;
        }

        public String[] getUrls() {
            return this.urls;
        }
    }
}
