package swagger;

import java.util.List;

public class PetUtils {

    public static Pet getSampleCow(int id){
        //Integer petId = BasePetStoreTest.faker.number().numberBetween(1, 200_000_000);
        Category cowCategory = new Category(25, "Cow");
        Tag tagHouseAnimal = new Tag(7, "houseAnimal");
        return Pet.builder()
                .id(id)
                .category(cowCategory)
                .name("murka")
                .photoUrls(List.of("String"))
                .tags(List.of(tagHouseAnimal))
                .status("available")
                .build();
    }
}
