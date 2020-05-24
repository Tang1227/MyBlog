package com.cn.blog.service.impl;


import com.cn.blog.handle.NotFoundExpection;
import com.cn.blog.pojo.Blog;

import com.cn.blog.pojo.Type;
import com.cn.blog.repository.BlogRepository;
import com.cn.blog.service.BlogService;
import com.cn.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){
            //表示新增
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            //表示修改
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Override
    public Blog findBlogById(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        if(b==null){
            throw new NotFoundExpection("实例不存在");
        }
        BeanUtils.copyProperties(blog,b);
        return blogRepository.save(b);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
      return blogRepository.findAll(new Specification<Blog>() {
          @Override
          public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
              List<Predicate> predicateList = new ArrayList();
              if(!StringUtils.isEmpty(blog.getTitle())){
                  predicateList.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
              }
              if(blog.getTypeId()!=null){
                  predicateList.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
              }
              if(blog.isRecommend()){
                  predicateList.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
              }
              query.where(predicateList.toArray(new Predicate[predicateList.size()]));
              return null;
          }
      }, pageable);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
