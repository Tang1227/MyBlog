package com.cn.blog.service.impl;


import com.cn.blog.handle.NotFoundExpection;
import com.cn.blog.pojo.Type;
import com.cn.blog.repository.TypeRepository;
import com.cn.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }


    @Override
    public Type findTypeById(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> ListType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type changeType = typeRepository.getOne(id);
        if(changeType!=null){
            BeanUtils.copyProperties(type,changeType);
            return this.typeRepository.save(changeType);
        }else {
            throw  new NotFoundExpection("实例不存在");

        }

    }

    @Transactional
    @Override
    public void deleteType(Long id) {
      typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findTypeByName(name);
    }

    @Override
    public List<Type> listTypes() {
        return typeRepository.findAll();
    }
}
