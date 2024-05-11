package com.whattoeat.model.processor;

import com.whattoeat.model.api.DataParser;
import org.json.JSONArray;

/**
 * @author Jess, learningen13@gmail.com
 * To give the recommended stores by sorted the data got from {@link com.whattoeat.model.api.DataParser}.
 * */
public class DataProcessor {
    private DataParser dataParser;
    private JSONArray sortedJSONArray;

    public DataProcessor(DataParser dataParser) {
        this.dataParser = dataParser;
    }

    public JSONArray getSortedJSONArray() {
        return sortedJSONArray;
    }

    private void sortJSONArray(){

    }
}
