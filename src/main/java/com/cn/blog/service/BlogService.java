package com.cn.blog.service;

import com.cn.blog.pojo.Blog;
import com.cn.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog saveBlog(Blog blog);

    Blog findBlogById(Long blog);

    Blog updateBlog(Long id,Blog blog);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    void deleteBlog(Long id);
}
