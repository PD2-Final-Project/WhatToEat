package com.whattoeat;

import com.google.gson.JsonObject;
import com.whattoeat.model.StoresDataQuery;
import com.whattoeat.model.api.DataParser;
import com.whattoeat.ui.FrameController;
import com.whattoeat.Env;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        if (Env.getENV().equals("DEV")) {

            FrameController.initialize();


        } else if (Env.getENV().equals("PRO")) {
            // TODO:
        }
    }
}