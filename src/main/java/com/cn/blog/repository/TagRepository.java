package com.cn.blog.repository;

import com.cn.blog.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findTagByName(String name);
}
