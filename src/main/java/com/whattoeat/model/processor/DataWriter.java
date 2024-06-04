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
    public DataWriter(String filePath, JSONObject newSearchResult) {
        File file = new File(filePath);
        // If the file doesn't exist or the file is not a valid JSON file,
        // then create a new file and write the jsonObject to it.
        newSearchResult.put("searchTime", System.currentTimeMillis());
        if(!file.exists() || !JSONValidity.isValidJSONFile(filePath)) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileWriter fileWriter = new FileWriter(file);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(newSearchResult);
                fileWriter.write(jsonArray.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // If the file exists and is a valid JSON file,
        // then read the file and check if the jsonObject is already in the file.
        // If it is not in the file, then write the jsonObject to the file.
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
                    if(searchResult.getString("keyword").equals(newSearchResult.getString("keyword")) &&
                        searchResult.getString("location").equals(newSearchResult.getString("location")) &&
                        searchResult.getString("radius").equals(newSearchResult.getString("radius"))
                    ) {
                        Long timeGap = Long.parseLong(searchResult.getString("searchTime")) - System.currentTimeMillis();
                        if(timeGap < (1000*60*60*2)) {
                            searchResults.put(i, newSearchResult);
                            jsonTokener.close();
                            return;
                        }
                    }
                }
                if(!location.contains(newSearchResult.getString("location")) ||
                    !keyword.contains(newSearchResult.getString("keyword")) ||
                    !radius.contains(newSearchResult.getString("radius"))
                ) {
                    FileWriter fileWriter = new FileWriter(file);
                    newSearchResult.append("searchTime", System.currentTimeMillis());
                    fileWriter.write(searchResults.toString());
                    fileWriter.close();
                }
                jsonTokener.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
