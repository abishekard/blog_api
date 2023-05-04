package com.example.blog_app_api.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private String imageName;
    private Date addedDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Users user;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments;


}
