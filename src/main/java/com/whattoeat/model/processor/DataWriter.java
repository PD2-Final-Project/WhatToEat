package com.whattoeat.model.processor;

import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
    public DataWriter(String filePath, JSONArray jsonArray) {
        File file = new File(filePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(jsonArray.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {

        }
    }
}
