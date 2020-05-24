package com.cn.blog.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_type")
@Data
@ToString
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    @OneToMany(mappedBy = "type",fetch = FetchType.LAZY)
    private List<Blog> blogs = new ArrayList<>();
}
