package com.cn.blog.service;

import com.cn.blog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);

    Type findTypeById(Long id);

    Page<Type> ListType(Pageable pageable);

    Type updateType(Long id,Type type);

    void  deleteType(Long id);
    Type getTypeByName(String name);

    List<Type> listTypes();
}
