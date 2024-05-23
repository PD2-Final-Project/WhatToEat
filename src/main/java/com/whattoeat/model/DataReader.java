package com.whattoeat.model;

import com.whattoeat.Env;
import com.whattoeat.model.api.DataParser;
import com.whattoeat.model.processor.DataProcessor;
import com.whattoeat.model.processor.DataWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Jess
 * DataReader is mean to provide the result of the given condition.
 * If the search conditions have been searched before, then returning the result.
 * Else, if the conditions haven't been searched, first making a search then returning and writing to the file.
 * */
public class DataReader {
    private JSONObject searchResult;
    private String path = "src/test/weightedOutput.json";

    /**
     * @param location The location where the search is made.
     * @param keyword The keyword to limit the search results.
     * @param radius The radius desired to search.
     * */
    public DataReader(String location, String keyword, int radius) {
        this.searchResult = this.getSearchResult(this.path, location, keyword, radius);
    }

    /**
     * @param path The path of the file to store.
     * <p> This will return the search results by given conditions </p>
     * */
    private JSONObject getSearchResult(String path, String location, String keyword, int radius) {
        try {
            JSONArray searchResults = new JSONArray(
                    new JSONTokener(new FileReader(path)));
            JSONObject searchResult = new JSONObject();
            if (!(searchResults.length() == 0)) {
                for (int i = 0; i < searchResults.length(); i++) {
                    if (searchResults.getJSONObject(i).getString("keyword").equals(keyword) &&
                            searchResults.getJSONObject(i).getString("location").equals(location)
                            && (searchResults.getJSONObject(i).getInt("radius") == radius)
                    ) {
                        searchResult = new JSONObject(searchResults.getJSONObject(i).getString("result"));
                        break;
                    }
                }
            } else {
                // TODO: This hard coded part should be changed.
                Env env = new Env("src/env.json");
                DataParser dataParser = new DataParser(env.getApiKey(), location);
                dataParser.setKeyword(keyword);
                dataParser.setRadius(radius);
                DataProcessor processor = new DataProcessor(dataParser.searchNearBy());
                // TODO: hard coded
                DataWriter dataWriter = new DataWriter("src/test/weightedOutput.json", processor.getWeightedSearchResults());
                return processor.getWeightedSearchResults();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return searchResult;
    }

    public JSONArray getStoreContent() {
        return this.searchResult.getJSONArray("storeContent");
    }
}
