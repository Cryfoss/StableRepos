package Test.Tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GsonParser {
    public Post parse() throws IOException {

        Gson gson = new Gson();
        try (FileReader fileReader = new FileReader("src/main/resources/user-schema.json")) {
            Post post = gson.fromJson(fileReader, Post.class);
        } catch (Exception e) {
            System.out.println("Parsing error " + e.toString());
        }
        return null;
    }
}
