package com.whattoeat;

import com.google.gson.JsonObject;
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
            DataParser dataParser = new DataParser(apiKey, "成功大學");
            dataParser.setKeyword(keyWord);
            dataParser.setRadius(radius);
            JsonObject searchedStores = dataParser.searchNearBy();

            dataParser.close();
        } else if (runEnv.equals(Env.PRODUCT)) {
            // TODO:
        }
    }
}