package com.whattoeat;

import com.whattoeat.ui.FrameController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
    public static void main(String[] args) {
        System.setProperty("log.path", Env.getLogFilePath());
        final Logger logger = LogManager.getLogger(Main.class);
        logger.info("What to eat?");
        if (Env.getENV().equals("DEV")) {
            logger.info("Running Development");
            FrameController.initialize();
        } else if (Env.getENV().equals("PRODUCT")) {
            logger.info("Running Production");
            FrameController.initialize();
        }
    }
}