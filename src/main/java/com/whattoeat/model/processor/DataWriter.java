package com.whattoeat.model.processor;

import com.whattoeat.model.JSONValidity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author Jess
 * <p>
 *     DataWriter is a class that writes search results to a JSON file.
 *     If the data exists and is valid, it updates the existing result.
 *     If the data doesn't exist, it stores it in the JSON file.
 * </p>
 */
public class DataWriter {
    private final String filePath;
    private final Logger logger = LogManager.getLogger(DataWriter.class);
    /**
     * Constructor for DataWriter.
     * @param filePath        The path of the file to save.
     */
    public DataWriter(String filePath) {
        this.filePath = filePath;
    }

    public void write(JSONObject newSearchResult) {
        File file = new File(filePath);
        // If the file doesn't exist or the file is not a valid JSON file,
        // then create a new file and write the jsonObject to it.
        newSearchResult.put("searchTime", System.currentTimeMillis());
        if(!file.exists() || !JSONValidity.isValidJSONFile(filePath)) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Error creating file", e);
            }
            try {
                FileWriter fileWriter = new FileWriter(file);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(newSearchResult);
                fileWriter.write(jsonArray.toString());
                fileWriter.close();
            } catch (IOException e) {
                logger.error("Error writing to file", e);
            }
        }
        // If the file exists and is a valid JSON file,
        // then read the file and check if the jsonObject is already in the file.
        // If it is not in the file, then write the jsonObject to the file.
        else {
            JSONTokener jsonTokener = null;
            try {
                jsonTokener =  new JSONTokener(file.toURI().toURL().openStream());
            } catch (MalformedURLException e) {
                logger.error("Malformed URL", e);
            } catch (IOException e) {
                logger.error("Error writing to file", e);
                throw new RuntimeException(e);
            }
            try {
                assert jsonTokener != null;
                JSONArray searchResults = new JSONArray(jsonTokener);
                boolean isDuplicate = false;
                for(int i = 0; i < searchResults.length(); i++) {
                    JSONObject searchResult = searchResults.getJSONObject(i);
                    if(isExisting(searchResult, newSearchResult)) {
                        long timeGap = searchResult.getLong("searchTime") - newSearchResult.getLong("searchTime");
                        if(timeGap < (1000*60*60*2)) {
                            jsonTokener.close();
                            return;
                        } else {
                            isDuplicate = true;
                            searchResults.put(i, newSearchResult);
                            break;
                        }
                    }
                }
                FileWriter writer = new FileWriter(filePath);
                if(!isDuplicate){
                    searchResults.put(newSearchResult);
                    writer.write(searchResults.toString());
                }
                writer.close();
            } catch (IOException e) {
                logger.error("Error writing to file", e);
            }

        }
    }

    private boolean isExisting(JSONObject searchResult, JSONObject newSearchResult) {
        String location = searchResult.getString("location");
        String keyword = searchResult.getString("keyword");
        String radius =  searchResult.getString("radius");
        String mood = searchResult.getString("mood");
        return location.equals(newSearchResult.getString("location")) &&
                keyword.equals(newSearchResult.getString("keyword")) &&
                radius.equals(newSearchResult.getString("radius")) &&
                mood.equals(newSearchResult.getString("mood"));
    }
}
