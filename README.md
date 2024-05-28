What to Eat
Environment
Set in env.json includes:

key: The api key of Google Map
environment: DEV or PRODUCT
Data
distance
url
address
Rating
Project Structure
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
                - DataReader.java
            - Env.java
            - Main.java
    - resources
How it works
