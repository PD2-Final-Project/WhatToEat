package com.whattoeat.model.processor;

import com.whattoeat.model.JSONValidity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataWriter {
    public DataWriter(String filePath, JSONObject jsonObject) {
        File file = new File(filePath);
        if(!file.exists() || JSONValidity.isValidJSONFile(filePath)) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileWriter fileWriter = new FileWriter(file);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);
                fileWriter.write(jsonArray.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            ArrayList<String> location = new ArrayList<>();
            ArrayList<String> keyword = new ArrayList<>();
            ArrayList<String> radius = new ArrayList<>();
            try {
                JSONTokener jsonTokener = new JSONTokener(file.toURI().toURL().openStream());
                JSONArray searchResults = new JSONArray(jsonTokener);
                for(int i = 0 ; i < searchResults.length() ; i++){
                    JSONObject searchResult = searchResults.getJSONObject(i);
                    location.add(searchResult.getString("location"));
                    keyword.add(searchResult.getString("keyword"));
                    radius.add(searchResult.getString("radius"));
                }
                for(int i = 0 ; i < searchResults.length() ; i++) {
                    JSONObject searchResult = searchResults.getJSONObject(i);
                    String insertLocation = searchResult.getString("location");
                    String insertKeyword = searchResult.getString("keyword");
                    String insertRadius = searchResult.getString("radius");
                    if(!location.contains(insertLocation) || !keyword.contains(insertKeyword) || !radius.contains(insertRadius)) {
                        try {
                            FileWriter fileWriter = new FileWriter(file);
                            searchResults.put(searchResult);
                            fileWriter.write(searchResults.toString());
                            fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                jsonTokener.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
