package com.whattoeat;

import java.io.FileReader;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.maps.errors.ApiException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.whattoeat.model.api.DataParser;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ApiException {
        // Get Google Map API key
        String apiKey = "";
        try {
            FileReader reader = new FileReader("src/env.json");
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject json = new JSONObject(tokener);
            apiKey = json.getString("key");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int radius = 1000;
        String keyWord = "restaurant";
        DataParser dataParser = new DataParser(apiKey, "成功大學");
        dataParser.setKeyword(keyWord);
        dataParser.setRadius(radius);
        JsonObject searchedStores = dataParser.searchNearBy();

        dataParser.close();
    }
}