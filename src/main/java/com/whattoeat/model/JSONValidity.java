package com.whattoeat.model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONValidity {
    public static boolean isValidJSONFile(String path) {
        try(FileReader reader = new FileReader(path)) {
            JSONArray jsonArray = new JSONArray(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
