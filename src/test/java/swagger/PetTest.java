package swagger;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Pet Testing")
public class PetTest {
    private final static String URL = "https://petstore.swagger.io/v2/pet/";
    private final static Gson gson = new Gson();

    @Order(1)
    @DisplayName("Create Pet")
    @Description("Проверяет отправку запроса на создание питомца по всем полям, проверяем поля name и id")
    @Test
    public void createPet(){
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL));
        Category cow = new Category(10000, "Cow");
        Tag tagHouseAnimal = new Tag(101,"houseAnimal");
        Pet pet = new Pet();
        pet.setId(10001);
        pet.setCategory(cow);
        pet.setName("murka");
        pet.setPhotoUrls(List.of("String"));
        pet.setTags(List.of(tagHouseAnimal));
        pet.setStatus("available");
        String json = gson.toJson(pet);
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then()
                .log().all()
                .extract().body().jsonPath();
                assertThat(10001).isEqualTo(pet.getId());
                assertThat("murka").isEqualTo(pet.getName());
    }
    @Order(2)
    @DisplayName("Update Pet")
    @Description("Post запрос. Обновляем данные по id питомца(только имя и статус)")
    @Test
    public void updatePet(){
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL));
        given()
                .contentType(ContentType.URLENC)
                .pathParam("petId",10000)
                .formParam("name","molodaya")
                .formParam("status","available")
                //.body(json)
                .when()
                .post("{petId}")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath();
    }
    @Order(3)
    @DisplayName("Updating data by id")
    @Description("Обновляет информацию по id питомца, при чем можно изменить любое поле, PUT запрос")
    @Test
    public void putPetById(){
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL));
        Pet pet = new Pet();
        pet.setId(10001);
        pet.setName("borka");
        String json = gson.toJson(pet);
        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .put()
                .then()
                .log().all()
                .extract().body().jsonPath();
        assertThat(10001).isEqualTo(pet.getId());
        assertThat("borka").isEqualTo(pet.getName());
    }

    @Order(4)
    @DisplayName("Get pet by id after put")
    @Description("Получаем информацию о питомце по id, через метод GET, проверяем, что через метод PUT данные обновились")
    @Test
    public void getPet(){
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL));

        Pet pet = given()
                .pathParam("petId",10001)
                .when()
                .get("{petId}")
                .then().log().all()
                .extract().body().as(Pet.class);
        //jsonPath().getList("Pet", Pet.class);
        assertThat(10001).isEqualTo(pet.getId());
        assertThat("borka").isEqualTo(pet.getName());


    }
    @Order(5)
    @DisplayName("Updating img by pet")
    @Description("Добавление картинки питомцу по id")
    @Test
    public void uploadImagePet(){
        File imgs = Paths.get("src/test/resources/cat.jpg").toFile();
        org.assertj.core.api.Assertions.assertThat(imgs).exists();
        given()
                .accept(io.restassured.http.ContentType.JSON)
                .multiPart("additionalMetadata", "picture")
                .multiPart("file",imgs, "image/jpg")
                .when()
                .post("https://petstore.swagger.io/v2/pet/"+10000+"/uploadImage")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath();
    }
    @Order(6)
    @DisplayName("Get pets by status")
    @Description("Получение списка питомцев по статусу(По умолчанию стоит available, для смены статуса перепишите название эндпоинта)")
    @Test
    public void getPetsByStatus(){
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL));
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/findByStatus?status="+"available")//pending,sold
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath();
    }
    @Order(7)
    @DisplayName("Delete pet by id")
    @Description("Удаление питомца, по его id")
    @Test
    public void deletePet(){
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL));
        given()
                .pathParam("petId","10001")
                //.header()
                .contentType(ContentType.JSON)
                .when()
                .delete("{petId}")
                .then()
                .log().all()
                .extract().body();
    }
    @Order(8)
    @DisplayName("Get pet by id after delete")
    @Description("Получаем информацию о питомце по id, проверяем, что животное удалено")
    @Test
    public void secondGetPet(){
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL));

        given()
                .pathParam("petId",10001)
                .when()
                .get("{petId}")
                .then()
                .statusCode(404)
                .log().all();
}
}