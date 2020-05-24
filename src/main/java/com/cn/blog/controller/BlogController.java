package com.cn.blog.controller;

import com.cn.blog.pojo.Blog;
import com.cn.blog.pojo.User;
import com.cn.blog.service.BlogService;
import com.cn.blog.service.TagService;
import com.cn.blog.service.TypeService;
import com.cn.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String BlogList="admin/blogs";
    private static final String INPUT = "admin/blogs-input";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagSerivice;
    @GetMapping("/blogs")
    public String ListBlogs(@PageableDefault(size = 4,sort = {"updateTime"},direction= Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listTypes());
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return BlogList;
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 4,sort = {"updateTime"},direction= Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    /**
     * 获取博客编辑页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
      model.addAttribute("blog",new Blog());
      setTypeAndTag(model);
      return INPUT;
    }

    public void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listTypes());
        model.addAttribute("tags",tagSerivice.listTags());
    }

    /**
     * 编辑博客
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
     public String getEdit(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog = blogService.findBlogById(id);
        blog.init();//获取ids
        model.addAttribute("blog",blog);
        return  INPUT;
     }

    /**
     * 保存博客
     * 新增/修改
     * @param blog
     * @param session
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/blogs")
    public String saveBlogs(Blog blog, HttpSession session, RedirectAttributes redirectAttributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.findTypeById(blog.getType().getId()));
        blog.setTags(tagSerivice.findTagByIds(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);
        if(b==null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 删除博客
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return  REDIRECT_LIST;

    }
}
