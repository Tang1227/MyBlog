package com.cn.blog.service.impl;

import com.cn.blog.handle.NotFoundExpection;
import com.cn.blog.pojo.Tag;
import com.cn.blog.repository.TagRepository;
import com.cn.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag findTagById(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag updateTag(Tag tag) {
        Tag t = tagRepository.getOne(tag.getId());
        if(t==null){
            throw  new NotFoundExpection("实例不存在");
        }else {
            BeanUtils.copyProperties(tag,t);
            return tagRepository.save(t);

        }
    }

    @Override
    public Page<Tag> ListTags(Pageable pageable) {
       return tagRepository.findAll(pageable);
    }


    @Override
    public Tag findTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> listTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findTagByIds(String ids) {
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids!=null){
            String[] tag = ids.split(",");
            for(String t:tag){
                list.add(Long.parseLong(t));
            }
        }
        return tagRepository.findAllById(list);
    }


}
