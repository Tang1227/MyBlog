package com.cn.blog.controller;

import com.cn.blog.pojo.Type;
import com.cn.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 查询所有分类
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 4,sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){
     model.addAttribute("page",typeService.ListType(pageable));
     return "admin/types";
    }

    /**
     * 分类页面
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
      model.addAttribute("type",new Type());
      return "admin/types-input";
    }

    /**
     * 添加分类
     * @param type
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types")
    public String saveTypes(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes){
        if(typeService.getTypeByName(type.getName())!=null){
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if(t==null){
            redirectAttributes.addFlashAttribute("message","添加失败");
        }else {
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 编辑
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable  Long id, Model model){
      model.addAttribute("type",typeService.findTypeById(id));
      return "admin/types-input";
    }

    @PostMapping("/types/{id}")
    public String editTypes(@Valid Type type, BindingResult result, @PathVariable Long id,RedirectAttributes redirectAttributes){
        if(typeService.getTypeByName(type.getName())!=null){
            result.rejectValue("name","nameError","不能添加重复分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if(t==null){
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else {
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
       typeService.deleteType(id);
       redirectAttributes.addFlashAttribute("message","删除成功");
       return "redirect:/admin/types";
    }

}
