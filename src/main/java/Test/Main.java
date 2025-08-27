package Test;

import Test.Tools.GsonParser;
import Test.Tools.Post;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GsonParser parser = new GsonParser();
        Post post = parser.parse();
        //System.out.println("Post" + post.toString());
    }
}
