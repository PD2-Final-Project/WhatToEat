package com.whattoeat;

import com.google.gson.JsonObject;
import com.whattoeat.model.StoresDataQuery;
import com.whattoeat.model.api.DataParser;


public class Main {
    public static void main(String[] args) {
        String apiKey;
        String runEnv;

        Env env = new Env("src/env.json");
        apiKey = env.getApiKey();
        runEnv = env.getRunEnv();

        if (runEnv.equals(Env.DEV)) {
            int radius = 1000;
            String keyWord = "restaurant";
            String location = "成大";
            StoresDataQuery storesDataQuery = new StoresDataQuery(location, keyWord, radius);
            String[] storeNames = storesDataQuery.storesData.getNames();
            int[] storePrice = storesDataQuery.storesData.getPriceLevels();
            int[] storeDistance = storesDataQuery.storesData.getDistances();
            double[] storeRating = storesDataQuery.storesData.getRatings();
            String[] storeUrls = storesDataQuery.storesData.getUrls();
            String[] storeAddress = storesDataQuery.storesData.getAddresses();

        } else if (runEnv.equals(Env.PRODUCT)) {
            // TODO:
        }
    }
}