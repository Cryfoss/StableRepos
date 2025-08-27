package api_practice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

class HomeworkTests {
    private final static String URL = "https://jsonplaceholder.typicode.com";

    @Test
        //Простой GET запрос на 100 постов
    void SimpleGetRequest() {
        //GET запрос
        Spec.instSpec(Spec.requestSpec(URL), Spec.response200());
        List<Posts> posts = given()
                .when()
                .get("/posts")
                .then().log().all()
                .extract().body().jsonPath().getList(".", Posts.class);
        //Проверь, что в ответе приходит 100 постов
        assertThat(100).isEqualTo(posts.size());
        //Проверь, что у первого поста userId == 1
        assertThat(posts.getFirst().getUserId()).isEqualTo(1);
    }

    @Test
        //Работа с параметрами и валидацией JSON
    void JSON() {
        //GET запрос
        Spec.instSpec(Spec.requestSpec(URL), Spec.response200());
        List<Parametrs> parametrs = given()
                .when()
                .get("/posts?userId=1")
                .then().log().all()
                //Валидация JSON schema
                .extract().body().jsonPath().getList(".", Parametrs.class);

        //Убедись, что все полученные посты имеют userId == 1
        assertThat(parametrs)
                .extracting("userId")
                .containsOnly(1);

        //Проверь, что в ответе минимум 5 постов
        assertThat(parametrs.size()>=5).isTrue();
    }
}

