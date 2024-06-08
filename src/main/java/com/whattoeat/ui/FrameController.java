package com.whattoeat.ui;

public class FrameController {

    public static void main(String[] args) {
        int radius = 1000;
    }

    private static void updateStoreData(Frame2 seconPage, StoreDataQuery storeDataQuery) {
        //uncompleted
        secondPage.storeNameData.setText("Next Store Name");
        secondPage.priceData.setText("Next Average Price");
        secondPage.distanceData.setText("Next Distance");
        secondPage.ratingData.setText("Next Rating");
        secondPage.uriData.setText("Next URI");
    }
}
