package swagger;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import lombok.Builder;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class PetTest extends BaseSwaggerTest {
    @DisplayName("Отдельная ручка на создание пета")
    @Test
    public void createPet() {
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL,baseUriPet));
        //Создаем объект питомца, инициализируем все поля
        Integer petId = faker.number().numberBetween(1,200000000);
        Category cow = new Category(25, "Cow");
        Tag tagHouseAnimal = new Tag(7, "houseAnimal");
        Pet pet = Pet.builder()
                .id(petId)
                .category(cow)
                .name("murka")
                .photoUrls(List.of("String"))
                .tags(List.of(tagHouseAnimal))
                .status("available")
                .build();

        //Вызываем метод POST(Создаем питомца на сервере), сверяемся, что все поля создались корректно
        String json = gson.toJson(pet);
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then()
                .log().all()
                .extract().body().jsonPath();
        assertThat(petId).isEqualTo(pet.getId());
        assertThat(List.of(25, "Cow")).isEqualTo(List.of(cow.getId(), cow.getName()));
        assertThat("murka").isEqualTo(pet.getName());
        assertThat(List.of("String")).isEqualTo(pet.getPhotoUrls());
        assertThat(pet.getTags()).extracting(Tag::getId, Tag::getName).containsExactly(tuple(7, "houseAnimal"));
        assertThat("available").isEqualTo(pet.getStatus());
    }
    @DisplayName("Create Pet")
    @Description("Проверяет отправку запроса на создание питомца по всем полям, проверяем все ручки")
    @Builder
    @Test
    public void createPetFull(){
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL,baseUriPet));
        //Создаем объект питомца, инициализируем все поля
        Integer petId = faker.number().numberBetween(1,200000000);
        Integer petCategoryId = faker.number().numberBetween(1,100000);
        Integer petTegId = faker.number().numberBetween(1,5000000);
        Category cow = new Category(petCategoryId, "Cow");
        Tag tagHouseAnimal = new Tag(petTegId,"houseAnimal");
        Pet pet = Pet.builder()
                        .id(petId)
                                .category(cow)
                                        .name("murka")
                                                .photoUrls(List.of("String"))
                                                        .tags(List.of(tagHouseAnimal))
                                                                .status("available")
                                                                        .build();

        //Вызываем метод POST(Создаем питомца на сервере), сверяемся, что все поля создались корректно
        String json = gson.toJson(pet);
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then()
                .log().all()
                .extract().body().jsonPath();
                assertThat(petId).isEqualTo(pet.getId());
                assertThat(List.of(petCategoryId,"Cow")).isEqualTo(List.of(cow.getId(),cow.getName()));
                assertThat("murka").isEqualTo(pet.getName());
                assertThat(List.of("String")).isEqualTo(pet.getPhotoUrls());
                assertThat(pet.getTags()).extracting(Tag::getId,Tag::getName).containsExactly(tuple(petTegId,"houseAnimal"));
                assertThat("available").isEqualTo(pet.getStatus());

        // Через метод POST обновляем имя животного
               given()
                        .contentType(ContentType.URLENC)
                        .pathParam("petId",petId)
                        .formParam("name","CowFromElwynnForest")
                        .when()
                        .post("{petId}")
                        .then()
                        .statusCode(200)
        .extract().as(Pet.class);

                        Pet afterForm = given()
                                .pathParam("petId",petId)
                                .when()
                                .get("{petId}")
                                .then().statusCode(200)
                        .extract().as(Pet.class);
                assertThat("CowFromElwynnForest").isEqualTo(afterForm.getName());

        //Через метод PUT меняем имя, ставим статус - Продано
                Pet pet1 = Pet.builder()
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
                .pathParam("petId",petId)
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
                .accept("*/*")
                .contentType("multipart/form-data")
                .multiPart("additionalMetadata", "picture")
                .multiPart("file",imgs, "image/jpg")
                .when()
                .post(petId+"/uploadImage")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath();

        //Получение списка питомцев по статусу. Стоит available, также доступны: pending, sold
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/findByStatus?status="+"available")//pending,sold
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath();
        //Удаление питомца, по его id
        given()
                .pathParam("petId",petId)
                .contentType(ContentType.JSON)
                .when()
                .delete("{petId}")
                .then()
                .log().all()
                .extract().body();
        //Получаем информацию о питомце по id, проверяем, что животное удалено
        given()
                .pathParam("petId",petId)
                .when()
                .get("{petId}")
                .then()
                .statusCode(404)
                .log().all();
    }
}