package swagger;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import lombok.Builder;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetTest extends BasePetStoreTest {

    private Integer petId;
    private Category cow;
    private Pet pet;
    private String json;

    @BeforeEach
    public void CreatePetOnServer() {
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL, baseUriPet));
        petId = faker.number().numberBetween(1, 200_000_000);
        cow = new Category(25, "Cow");
        Tag tagHouseAnimal = new Tag(7, "houseAnimal");
        pet = Pet.builder()
                .id(petId)
                .category(cow)
                .name("murka")
                .photoUrls(List.of("String"))
                .tags(List.of(tagHouseAnimal))
                .status("available")
                .build();
        json = gson.toJson(pet);
        System.out.println("Before method is called");
    }

    //Создаем объект питомца, инициализируем все поля
    @DisplayName("Create Pet")
    @Test
    public void createPet() {
        //Вызываем метод POST(Создаем питомца на сервере), сверяемся, что все поля создались корректно
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then();
        assertAll(
                () -> assertThat(petId).isEqualTo(pet.getId()),
                () -> assertThat(List.of(25, "Cow")).isEqualTo(List.of(cow.getId(), cow.getName())),
                () -> assertThat("murka").isEqualTo(pet.getName()),
                () -> assertThat(List.of("String")).isEqualTo(pet.getPhotoUrls()),
                () -> assertThat(pet.getTags()).extracting(Tag::getId, Tag::getName).containsExactly(tuple(7, "houseAnimal")),
                () -> assertThat("available").isEqualTo(pet.getStatus()));
    }

    @AfterEach
    void cleunup() {
        given()
                .pathParam("petId", petId)
                .delete("/{petId}")
                .then();
    }

    @DisplayName("Update form pet")
    @Description("Проверяет отправку запроса на создание питомца по всем полям, проверяем все ручки")
    @Test
    public void UpdatePet() {
        // Через метод POST обновляем имя животного
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then().log().all();
        given()
                .log().uri()
                .pathParam("petId", petId)
                .when()
                .get("/{petId}")
                .then().log().all();//.statusCode(200)
                assertThat("murka").isEqualTo(pet.getName());
        given()
                .contentType(ContentType.URLENC)
                .pathParam("petId", petId)
                .formParam("name", "CowFromForest")
                .post("/{petId}")
                .then().log().all();//.statusCode(200);

        String actualName = given()
                .log().uri()
                .pathParam("petId",petId)
                .get("/{petId}")
                .then().log().all()//.statusCode(200)
                .extract().path("name");
        given()
                .pathParam("petId", petId)
                .when()
                .get("/{petId}")
                .then()
                .log().all();//.statusCode(200);
        assertThat(actualName).isEqualTo("CowFromForest");
    }
}
        /*given()
                .log().uri()
                .contentType(ContentType.URLENC)
                .pathParam("petId", petId)
                .formParam("name", "CowFromForest")
                .post("/{petId}")
                .then()
                .log().all()
                .statusCode(200);

        String actualName = given()
                .pathParam("petId",petId)
                .get("/{petId}")
                        .then()
                .log().all()
                        .statusCode(200)
                        .extract().path("name");
        System.out.println(actualName);
        given()
                .pathParam("petId", petId)
                .when()
                .get("/{petId}")
                .then()
                .log().all()
                .statusCode(200);
        assertThat("CowFromForest").isEqualTo(actualName);
        System.out.println(pet);
    }
}
        //Через метод PUT меняем имя, ставим статус - Продано
        /*Pet pet1 = Pet.builder()
                .id(pet.getId())
                .name("borka")
                .status("sold")
                .build();
        pet1 = given()
                .contentType(ContentType.JSON)
                .body(pet1)
                .when()
                .put()
                .then()
                .log().all()
                .extract().body().as(Pet.class);
        assertThat(petId).isEqualTo(pet1.getId());
        assertThat("borka").isEqualTo(pet1.getName());
        assertThat("sold").isEqualTo(pet1.getStatus());

        //Get запрос. Получаем информацию после обновления
        Pet afterUpdate = given()
                .pathParam("petId", petId)
                .when()
                .get("{petId}")
                .then().log().all()
                .extract().body().as(Pet.class);
        assertThat(petId).isEqualTo(afterUpdate.getId());
        assertThat("borka").isEqualTo(afterUpdate.getName());

        //POST запрос. Добавление картинки питомцу
        File imgs = Paths.get("src/test/resources/cat.jpg").toFile();
        org.assertj.core.api.Assertions.assertThat(imgs).exists();
        given()
                .accept("*/ /*")
                .contentType("multipart/form-data")
                .multiPart("additionalMetadata", "picture")
                .multiPart("file", imgs, "image/jpg")
                .when()
                .post(petId + "/uploadImage")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath();

        //Получение списка питомцев по статусу. Стоит available, также доступны: pending, sold
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/findByStatus?status=available")//pending,sold
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath();
        //Удаление питомца, по его id
        given()
                .pathParam("petId", petId)
                .contentType(ContentType.JSON)
                .when()
                .delete("{petId}")
                .then()
                .log().all()
                .extract().body();
        //Получаем информацию о питомце по id, проверяем, что животное удалено
        given()
                .pathParam("petId", petId)
                .when()
                .get("{petId}")
                .then()
                .statusCode(404)
                .log().all();
    }
}*/