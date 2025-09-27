package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Movie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)            //
    private long id;
    private String name;
    private String hero;
    private String heroin;

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getHero() {
//        return hero;
//    }
//
//    public String getHeroin() {
//        return heroin;
//    }
//
//    public void setHeroin(String heroin) {
//        this.heroin = heroin;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setHero(String hero) {
//        this.hero = hero;
//    }

    @ElementCollection
    private List<String> actors= new ArrayList<>();
}
