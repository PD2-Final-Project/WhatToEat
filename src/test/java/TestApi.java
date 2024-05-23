
import com.whattoeat.Env;
import com.whattoeat.model.api.DataParser;
import com.whattoeat.model.api.Places;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class TestApi {
    public static void main(String[] args) {
        // Get Google Map API key
        String apiKey;
        String runEnv;

        Env env = new Env("src/env.json");
        apiKey = env.getApiKey();
        runEnv = env.getRunEnv();

        int radius = 1000;
        String keyWord = "restaurant";
        DataParser dataParser = new DataParser(apiKey, "台南");
        dataParser.setKeyword(keyWord);
        dataParser.setRadius(radius);
        dataParser.setPlaceType(Places.RESTAURANT);
        dataParser.searchNear();
        JSONObject searchedStores = dataParser.getSearchResult();
        System.out.println("Get " + dataParser.getStoresDataCount() + " data");

        try (FileWriter writer = new FileWriter("src/test/java/test.json")) {
            writer.write(searchedStores.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataParser.close();
    }
}
