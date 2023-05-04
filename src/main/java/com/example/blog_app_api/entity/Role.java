package com.example.blog_app_api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Role {

    @Id
    private int id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Users> users;
}
