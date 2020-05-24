package com.cn.blog.repository;

import com.cn.blog.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type,Long> {
   Type findTypeByName(String name);
}
