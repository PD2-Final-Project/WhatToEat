import com.whattoeat.model.StoresDataQuery;
import com.whattoeat.model.processor.Mood;

public class TestApi {
    public static void main(String[] args) {
        int radius = 1000;
        String keyWord = "restaurant";
        String location = "台南";
        StoresDataQuery storesDataQuery = new StoresDataQuery(location, keyWord, radius, Mood.NORMAL, 300, 400);
    }
}
