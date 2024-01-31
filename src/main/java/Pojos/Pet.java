package Pojos;



import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private int id;
    private String name;

    Category category;
    private List<String> photoUrls;
    private List<Tags> tags = new ArrayList<Tags>();

    Tags tag;
    private String status;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pet(){
        super();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public List<Tags> getTags() {
        return tags;
    }


    public String getStatus() {
        return status;
    }

    public Pet(int id, String name, Category category, List<String> photoUrls, List<Tags> tags, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

}

