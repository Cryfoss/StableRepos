package api_forStudy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


import static io.restassured.RestAssured.given;

class ReqresTest {
private final static String URL = "https://reqres.in";
//private final static String API_KEY = "x-api-key";
//private final static String API_VALUE ="reqres-free-v1";

    @Test
    public void checkAvatarAndIdTest(){
        Specifications.installSpec(Specifications.requestSpec(URL),Specifications.responseSpec200());
        List<UserData> users = given()
                //.header(API_KEY, API_VALUE)
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data",UserData.class);
                users.forEach(a -> Assertions.assertTrue(a.getAvatar().contains(a.getId().toString())));
                Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
    }
}
