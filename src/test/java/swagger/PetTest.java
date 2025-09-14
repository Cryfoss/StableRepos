package swagger;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetTest extends BasePetStoreTest {

    private Pet pet = new Pet();
    private Integer id;
    private String json;
    PetSteps petSteps = new PetSteps();

    @BeforeAll
    public void initialTests() {
        SpecificationSwag.initialSpec(SpecificationSwag.requestSpecification(URL, baseUriPet));
    }

    @BeforeEach
    public void setUpTests() {
        id = faker.number().numberBetween(1, 2_000_000);
        pet = PetUtils.getSampleCow(id);
        json = gson.toJson(pet);
    }

    //Создаем объект питомца, инициализируем все поля
    @DisplayName("Create Pet")
    @Description("Создаем животного, валидируем json")
    @Test
    public void createPet() {
        //Вызываем метод POST(Создаем питомца на сервере), сверяемся, что все поля создались корректно
        String actualJson = petSteps.createPet(json);
        assertThat(actualJson).isEqualTo(json);
    }

    @AfterEach
    void tempStorage(TestInfo info) {
        if (info.getTags().contains("noAfterEach")) return;
        PetSteps.deletePet(id);
        pet = null;
        json = null;
    }

    @DisplayName("Update Name Pet (Post)")
    @Description("Создаем животного, меняем имя, проверяем что все корректно")
    @Test
    public void updatePet() {
        System.out.println(id);
        // Через метод POST создаем животного, валидируем json
        String actualJson = petSteps.createPet(json);
        assertThat(actualJson).isEqualTo(json);

        // Получаем id, сверяемся что имя тоже что и было в создании
        Pet resultedPet = petSteps.getPet(id);
        assertThat(resultedPet.getName()).isEqualTo("murka");

        //Обновляем имя животного (Как мы можем проверить сразу что имя создалось, если нам нужно что-то помимо статус кода? Нормально ли сверятся так, по id?)
        String actualMessage = petSteps.updatePetName(id, "CowFromForest");
        assertThat((id).toString()).isEqualTo(actualMessage);

        //Get запрос с получением имени животного
        String actualName = petSteps.getPetName(id);
        assertThat(actualName).isEqualTo("CowFromForest");
    }

    @DisplayName("Update Pet (PUT)")
    @Description("Создаем животного, меняем статус, тег")
    @Test
    public void putPet() {
        // Создаем животного
        String actualJson = petSteps.createPet(json);
        assertThat(actualJson).isEqualTo(json);

        //Создаем новые данные, которые будем менять
        Tag newAnimal = new Tag(10, "Dog");
        Pet newPet = Pet.builder()
                .id(id)
                .status("sold")
                .tags(List.of(newAnimal))
                .build();
        //Меняем данные
        petSteps.putPet(newPet);

        //Get запрос с проверкой на измененные данные
        petSteps.getPet(id);
        assertAll(
                () -> assertThat(newPet.getStatus()).isEqualTo("sold"),
                () -> assertThat(newPet.getTags()).extracting(Tag::getId, Tag::getName).containsExactly(tuple(10, "Dog")));
    }

    @DisplayName("Update img Pet")
    @Description("Обновление картинки питомца")
    @Test
    public void updateImg() {
        //Создание питомца
        String actualJson = petSteps.createPet(json);
        assertThat(actualJson).isEqualTo(json);
        //Добавление картинки
        petSteps.updateImg(id);
    }

    @DisplayName("find by status")
    @Description("Поиск по статусу")
    @Test
    @org.junit.jupiter.api.Tag("noAfterEach")
    //Какую можно поставить проверку здесь, помимо статус кода?
    public void findByStatus() {
        petSteps.findByStatus("available");
        petSteps.findByStatus("sold");
        petSteps.findByStatus("pending");
    }
}