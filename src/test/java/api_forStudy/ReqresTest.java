package api_forStudy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


import static io.restassured.RestAssured.given;

class ReqresTest extends BaseTest {

    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        assertThat(users).allSatisfy(a -> assertThat(a.getAvatar().contains(a.getId().toString())).isTrue());
        assertThat(users).allSatisfy(a -> assertThat(a.getEmail().endsWith("@reqres.in")).isTrue());
    }
}
