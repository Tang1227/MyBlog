package com.cn.blog.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_tag")
@Data
@ToString
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "标签不能为空")
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();
}
