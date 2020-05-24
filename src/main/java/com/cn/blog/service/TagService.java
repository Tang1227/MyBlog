package com.cn.blog.service;

import com.cn.blog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {

    Tag saveTag(Tag tag);

    Tag findTagById(Long id);
    Tag updateTag(Tag tag);

    Page<Tag> ListTags(Pageable pageable);

    Tag findTagByName(String name);

    void deleteTag(Long id);

    List<Tag> listTags();

    List<Tag> findTagByIds(String ids);
}
