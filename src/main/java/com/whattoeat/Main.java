package com.whattoeat;

import com.whattoeat.model.api.DataParser;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        String apiKey;
        String runEnv;

        Env env = new Env("src/env.json");
        apiKey = env.getApiKey();
        runEnv = env.getRunEnv();

        if (runEnv.equals(Env.DEV)) {
            String location = "台南";
            String keyword = "restaurant";
            int radius = 1000;
            String keyWord = "restaurant";
            DataParser dataParser = new DataParser(apiKey, "成功大學");
            dataParser.setKeyword(keyWord);
            dataParser.setRadius(radius);
            JSONObject searchResult = dataParser.getSearchResult();
            dataParser.close();
        } else if (runEnv.equals(Env.PRODUCT)) {
            // TODO:
        }
    }
}