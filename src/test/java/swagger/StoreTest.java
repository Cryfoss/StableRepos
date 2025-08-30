package swagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DATE;

public class StoreTest extends BaseSwaggerTest{
    @Test
    public void store(){
        //Проверям список категорий магазина, сверяемся что есть основные: available, sold, pending
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL,baseUriStore));
        JsonPath jsonPath = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/inventory")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().body().jsonPath();
        assertThat(jsonPath.getMap("$"))
                .containsKeys("available","sold","pending");

//Создаем заказ
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                .create();
        Date shipDates = Date.from(Instant.now());
        Integer id = faker.number().randomDigit();
        Integer petId = faker.number().randomDigit();
        Order order = Order.builder()
                .id(id)
                .petId(petId)
                .quantity(1)
                .shipDate(shipDates)
                .status("placed")
                .complete(true)
                .build();

        String json = gson.toJson(order);
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/order")
                .then()
                .log().all()
                .extract().body().jsonPath();
        assertThat(id).isEqualTo(order.getId());
        assertThat(petId).isEqualTo(order.getPetId());
        assertThat(1).isEqualTo(order.getQuantity());

        //Get запрос на получение заказа по id
given()
        .contentType(ContentType.JSON)
        .pathParam("orderId",id)
        .when()
        .get("/order/"+"{orderId}")
        .then()
        .log().all()
        .extract().body().jsonPath();
    }
}
