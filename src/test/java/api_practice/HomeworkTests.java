package api_practice;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

class HomeworkTests {
    private final static String URL = "https://jsonplaceholder.typicode.com";
    @Test
    //Простой GET запрос на 100 постов
void SimpleGetRequest(){
        //GET запрос
        Spec.instSpec(Spec.RequestSpec(URL),Spec.Response200());
        List<Posts> posts = given()
                .when()
                .get("/posts")
                .then().log().all()
                .extract().body().jsonPath().getList(".",Posts.class);
        //Проверь, что в ответе приходит 100 постов
    Assertions.assertEquals(100, posts.size());
    //Проверь, что у первого поста userId == 1
    Assertions.assertEquals(1, (int) posts.getFirst().getUserId());
    }
    @Test
    //Работа с параметрами и валидацией JSON
    void JSON(){
        //GET запрос
        Spec.instSpec(Spec.RequestSpec(URL),Spec.Response200());
        List<Parametrs> parametrs = given()
                .when()
                .get("/posts?userId=1")
                .then().log().all()
                //Валидация JSON schema
                .body(matchesJsonSchemaInClasspath("schemas/user-schema.json"))
                .extract().body().jsonPath().getList(".", Parametrs.class);

        //Убедись, что все полученные посты имеют userId == 1
       Assertions.assertTrue(parametrs.stream().allMatch(x -> x.getUserId() == 1));
       //Проверь, что в ответе минимум 5 постов
       Assertions.assertTrue(parametrs.size() >= 5);
    }
}

