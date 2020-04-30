package automation.restassured.domain.pets;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Root
{
    private String id;

    private Category category;

    private String name;

    private List<String> photoUrls;
    private List<Tags> tags;

    private String status;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setCategory(Category category){
        this.category = category;
    }
    public Category getCategory(){
        return this.category;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPhotoUrls(List<String> photoUrls){
        this.photoUrls = photoUrls;
    }
    public List<String> getPhotoUrls(){
        return this.photoUrls;
    }
    public void setTags(List<Tags> tags){
        this.tags = tags;
    }
    public List<Tags> getTags(){
        return this.tags;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Root)) return false;
        Root root = (Root) o;
        return getId() == root.getId() &&
            getCategory().equals(root.getCategory()) &&
            getName().equals(root.getName()) &&
            getPhotoUrls().equals(root.getPhotoUrls()) &&
            getTags().equals(root.getTags()) &&
            getStatus().equals(root.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategory(), getName(), getPhotoUrls(), getTags(), getStatus());
    }

    @Override
    public String toString() {
        return "Root{" +
            "id=" + id +
            ", category=" + category +
            ", name='" + name + '\'' +
            ", photoUrls=" + photoUrls +
            ", tags=" + tags +
            ", status='" + status + '\'' +
            '}';
    }



}
