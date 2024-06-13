package com.whattoeat.model;

import com.whattoeat.Env;
import com.whattoeat.model.api.DataParser;
import com.whattoeat.model.processor.DataWriter;
import com.whattoeat.model.processor.DataProcessor;
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
    private final static String path = Env.getDataStorageFolderPath() + "queryResults.json";

    /**
     * @param location The location where the search is made.
     * @param keyword The keyword to limit the search results.
     * @param radius The radius desired to search.
     * @param mood The mood of users
     * */
    public StoresDataQuery(String location, String keyword, int radius, Mood mood, int photoWidth, int photoHeight) {
        this.searchResult = this.getSearchResult(location, keyword, radius, mood, photoWidth, photoHeight);
        this.storesData = new StoresData(this.searchResult.getJSONArray("storeContent"));
    }

    /**
     * This method is to get the search result and check whether it exits or not.
     * If it exists, then return it. If not, then make a new request then return it.
     * @param location  The location of searching
     * @param keyword  The keyword needed while searching
     * @param radius  The radius want to search
     * @param mood The mood of users
     * @param photoWidth  The width of the photo response
     * @param photoHeight  The height of the photo response
     */
    private JSONObject getSearchResult(String location, String keyword, int radius, Mood mood, int photoWidth, int photoHeight) {
        // If the data has been found inside the file, then return it;
        try {
            File file = new File(StoresDataQuery.path);
            if(!file.exists()) file.createNewFile();
            if(JSONValidity.isValidJSONFile(StoresDataQuery.path)) {
                FileReader reader = new FileReader(StoresDataQuery.path);
                JSONArray searchResults = new JSONArray(new JSONTokener(reader));
                if (!searchResults.isEmpty()) {
                    for (int i = 0; i < searchResults.length(); i++) {
                        Long timeGap = searchResults.getJSONObject(i).getLong("searchTime") - System.currentTimeMillis();
                        if (searchResults.getJSONObject(i).getString("keyword").equals(keyword) &&
                                searchResults.getJSONObject(i).getString("location").equals(location) &&
                                searchResults.getJSONObject(i).getInt("radius") == radius && timeGap < (1000*60*60*2) &&
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
        DataParser dataParser = new DataParser(Env.getApiKey(), location);
        dataParser.setKeyword(keyword);
        dataParser.setRadius(radius);
        DataProcessor processor = new DataProcessor(dataParser.searchNearBy());
        processor.setMood(mood);
        processor.setPhoto(photoWidth, photoHeight);
        DataWriter dataWriter = new DataWriter(path, processor.getWeightedSearchResult());
        return processor.getWeightedSearchResult();
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
        private String[][] photoURLs;

        public StoresData(JSONArray storesContent) {
            this.length = storesContent.length();
            this.distances = new int[length];
            this.priceLevels = new int[length];
            this.ratings = new double[length];
            this.names = new String[length];
            this.addresses = new String[length];
            this.phoneNumbers = new String[length];
            this.urls = new String[length];
            this.photoURLs = new String[length][];
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
                JSONArray photoURLsList = details.getJSONArray("photos");
                this.photoURLs[i] = new String[photoURLsList.length()];
                for(int j = 0 ; j < photoURLsList.length(); j++) {
                    this.photoURLs[i][j] = photoURLsList.getString(j);
                }
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

        public String[][] getPhotosURLs() {
            return this.photoURLs;
        }
    }
}
