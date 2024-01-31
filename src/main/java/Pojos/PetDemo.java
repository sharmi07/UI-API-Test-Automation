package Pojos;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PetDemo {
    private int id = 123;
    private String name = "Coco";

    Category category;
    private List<String> photoUrls = new ArrayList<>();
    private List<Tags> tagsList = new ArrayList<Tags>();

    Tags tag;
    private String status = "Available";

    Pet pet;

    public void petPojo(){
       category = new Category(
            2, "Husky"
       );

        tag = new Tags(
            1, "Cookie and Cream"
        );

        photoUrls.add("hello.com");
        tagsList.add(tag);

        pet = new Pet(
                id, name, category, photoUrls, tagsList, status
        );

    }

    public void mapRequest(Pet pet){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File("target/pet.json");
            objectMapper.writeValue(file, pet );
           // file.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]){
        PetDemo pd = new PetDemo();
        pd.petPojo();
    }
}
