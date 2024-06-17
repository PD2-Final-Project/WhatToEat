# What to Eat 

- Structure
- 
- [Environment](#environment)
- [Data](#data)

<a href="#test">env</a>

---

<span id="#test"> Test </span>

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
