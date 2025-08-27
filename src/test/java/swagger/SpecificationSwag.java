package swagger;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class SpecificationSwag {
    public static RequestSpecification requestSpecification (String url){
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static void initialSpec(RequestSpecification requestSpecification){
        RestAssured.requestSpecification = requestSpecification;
    }

}
