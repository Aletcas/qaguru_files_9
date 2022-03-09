package aletca.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Student {
    public String name;
    public String nick;
    public Integer hourse;
    @JsonProperty("favorite-flowers")
    public List<String> favoriteFlowers;
    public Address address;

}
