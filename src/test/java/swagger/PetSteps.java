package swagger;

import io.restassured.http.ContentType;

import java.io.File;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PetSteps {
    Pet pet = new Pet();
    String json = BasePetStoreTest.Jsons.toJson(pet);

    public static void deleteAllPets(Integer id) {
        given()
                .pathParam("petId", id)
                .delete("/{petId}")
                .then().statusCode(200);
    }

    public String createPet(String json) {
        return given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then().log().all()
                .extract().asString();
    }

    public Pet getPetById(Integer id) {
        return given()
                .pathParam("petId", id)
                .when()
                .get("/{petId}")
                .then().log().all().statusCode(200).extract().body().as(Pet.class);
    }

    public String updatePetName(Integer id, String name) {
        return given()
                .contentType(ContentType.URLENC)
                .pathParam("petId", id)
                .formParam("name", name)
                .post("/{petId}")
                .then().log().all().statusCode(200).extract().path("message");
    }

    public String getPetByIdWithExtractName(Integer id) {
        return given()
                .pathParam("petId", id)
                .when()
                .get("/{petId}")
                .then().log().all().statusCode(200).extract().body().path("name");
    }
    public String putPet(Pet newPet){
        return given()
                .body(newPet)
                .when()
                .put()
                .then().log().all()
                .extract().asString();
    }
    public void updateImg(Integer id){
        File imgs = Paths.get("src/test/resources/cat.jpg").toFile();
        org.assertj.core.api.Assertions.assertThat(imgs).exists();
        given()
                .accept("*/*")
                .contentType("multipart/form-data")
                .multiPart("additionalMetadata", "picture")
                .multiPart("file", imgs, "image/jpg")
                .when()
                .post(id + "/uploadImage")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath();
    }
    public void findByStatus(String status){
        given()
                .when()
                .get("/findByStatus?status=" + status)
                .then().log().all()
                .statusCode(200);
    }

}