package com.whattoeat;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;


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
        LatLng location = new LatLng(22.997140, 120.216211);
        String keyWord = "restaurant";
        GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
        NearbySearchRequest request = new NearbySearchRequest(context).
                keyword(keyWord).radius(radius).location(location).language("zh-TW");
        PlacesSearchResponse response = request.await();
        for(PlacesSearchResult result : response.results) {
            System.out.println(result.name + "  " + result.rating);
        }
    }
}