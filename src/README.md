# What to Eat 

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
                    - Places.java
                - processor
                    - DataProcessor.java
                    - DataStandardization.java
                    - DataWriter.java
                    - MinMaxNormalization.java
                    - Mood.java
                - JSONValidity.java
                - StoresDataQuery
            - Env.java
            - Main.java
    - resources
```
### How it works
According to the given location, radius, keyword and mood gives out the most suitable choices for users.
