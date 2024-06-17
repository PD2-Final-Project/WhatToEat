
# What to Eat

## Structure
- [Environment](#environment)
- [Data](#data)
- [Project Structure](#project-structure)
- [How it works](#how-it-works)
- [DataParser](#dataparser)
- [StoreDataQuery](#storedataquery)


---

### Environment
Set in environment variables includes:
- API_KEY: The api key of Google Map
- ENV: `DEV` or `PRODUCT`

### Data
- distance
- url
- address
- Rating

### Project Structure
```
- main
    - java
        - com.whattoeat
            - model
                - api
                    - DataParser.java
                    - InvaidTimeFormatException.java
                    - Places.java
                    - TimeSplit.java
                - processor
                    - DataProcessor.java
                    - DataStandardization.java
                    - DataWriter.java
                    - MinMaxNormalization.java
                    - Mood.java
                - JSONValidity.java
                - StoresDataQuery.java
            - ui
                - Frame1.java
                - Frame2.java
                - FrameController.java
                - HyperlinkLabel.java
            - Env.java
            - Main.java
    - resources
```
### How it works
According to the given location, radius, keyword and mood gives out the most suitable choices for users.

### DataParser
The DataParser is used to parse the data from google map.
- You should provide the google map api key and the location you want to search near by.
- You can also assign the time span to determine whether to search again if the given search conditions are the same as before.
- The default time span is 2.0 hours.


- Example:
    ```java
    DataParser dataParser = new DataParser("AIzaSyA...", "your location", 2.0);
    dataParser.setKeyword(keyWord);
    dataParser.setRadius(radius);
    dataParser.setPlaceType(Places.RESTAURANT);
    dataParser.searchNear();
    JSONObject searchedStores = dataParser.getSearchResult();
    dataParser.close();
    ```

  The searching ways:
    ```java
    // You can invoke this one to get the searching result
    JSONObject searchedStores = dataParser.searchNearBy();
    
    // You can also assign how many times you want to search
    JSONObject searchedStores = dataParser.searchNearBy(3);
    
    // Another way
    dataParser.searchNear();
    
    // Assign the times you want to search
    dataParser.searchNear(3);
    
    // Get all the searching result
    JSONObject searchedStores = dataParser.getSearchResult();
    ```

### StoreDataQuery
It takes 6 parameters to get the query data.
- Location: The location where query takes part
- radius: Desired radius for searching
- keyword: Like "restaurant", "cafe" and so on
- mood: The mood of the user
- photoWidth: The max width of response photos
- photoHeight: The max height of response photos
*To use the StoreDataQuery*
Use the getters defined in the inner class StoresData to get the desired data.
```java=
StoresDataQuery storesDataQuery = new StoresDataQuery("成功大學", 1000, "餐廳", Mood.NORMAL, 300, 300);
int[] distance = storesDataQuery.storesData.getDistance();
```