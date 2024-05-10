import com.google.gson.JsonObject;
import com.google.maps.errors.ApiException;
import com.whattoeat.model.api.DataParser;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TestApi {
    public static void main(String[] args) {
        // Get Google Map API key
        String apiKey = "";
        try {
            FileReader reader = new FileReader("src/env.json");
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject json = new JSONObject(tokener);
            apiKey = json.getString("key");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int radius = 1000;
        String keyWord = "restaurant";
        DataParser dataParser = new DataParser(apiKey, "成功大學");
        dataParser.setKeyword(keyWord);
        dataParser.setRadius(radius);
        JsonObject searchedStores = dataParser.searchNearBy(3);
        System.out.println("Get " + dataParser.getStoresDataCount() + " data");
        try (FileWriter writer = new FileWriter("src/test/java/test.json")) {
            writer.write(searchedStores.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataParser.close();
    }
}
