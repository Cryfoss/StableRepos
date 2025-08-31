package swagger;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest extends BaseSwaggerTest{
    @Test
    public void user(){
        //Создание одного пользователя
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL,baseUriUser));
        int id = faker.number().randomDigit();
        int status = faker.number().randomDigit();
        User user = User.builder()
                .id(id)
                .username("rogue80lvl")
                .firstName("Petr")
                .lastName("Petrov")
                .email("wow@mail.ru")
                .password("Horde1995")
                .phone("89999999999")
                .userStatus(status)
                .build();
        String json = gson.toJson(user);
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath();
        assertThat(id).isEqualTo(user.getId());
        assertThat("rogue80lvl").isEqualTo(user.getUsername());
        assertThat("Petr").isEqualTo(user.getFirstName());
        assertThat("Petrov").isEqualTo(user.getLastName());
        assertThat("wow@mail.ru").isEqualTo(user.getEmail());
        assertThat("Horde1995").isEqualTo(user.getPassword());
        assertThat("89999999999").isEqualTo(user.getPhone());
        assertThat(status).isEqualTo(user.getUserStatus());
    }
    //Создание списка пользователей (List)
    @Test
    public void getListUser() {
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL,baseUriUser));
        int id = faker.number().randomDigit();
        int status = faker.number().randomDigit();
        User user = User.builder()
                .id(id)
                .username("pal1lvl")
                .firstName("Vasia")
                .lastName("Vasiliev")
                .email("worldofwarcraft@mail.ru")
                .password("Aliance2000")
                .phone("89109999555")
                .userStatus(status)
                .build();
        User user1 = User.builder()
                .id(id)
                .username("testich")
                .firstName("test")
                .lastName("testov")
                .email("ai@mail.ru")
                .password("qwerty")
                .phone("0")
                .userStatus(status)
                .build();
        String json = gson.toJson(user);
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .log().all()
                .post("createWithList")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", User.class);
    }
}
