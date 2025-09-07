package swagger;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BasePetStoreTest {
    protected final static String URL = "https://petstore.swagger.io/v2";
    protected final static String baseUriPet = "/pet";
    protected final static String baseUriStore = "/store";
    protected final static String baseUriUser = "/user";
    protected final static Gson gson = new Gson();
    protected final static Faker faker = new Faker();
    protected final static List<String> toCleanup = new ArrayList<>();

    protected static String shipDate = LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME);

    public final class Jsons {
        private static final Gson GSON = new Gson();
        private Jsons() {}
        public static String toJson(Object o) { return GSON.toJson(o); }
    }

}
