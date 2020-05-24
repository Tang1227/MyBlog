package com.cn.blog.pojo;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_blog")
@Data
@ToString
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Basic(fetch =  FetchType.LAZY)
    @Lob
    private String content;  //内容
    private String flag;      //标签
    private Integer views;   //浏览次数
    private boolean appreciation;  //赞赏开启
    private boolean shareStatement;  //转载声明
    private boolean iscomment;  //允许评论
    private boolean published;  //允许发布
    private boolean recommend;// 允许赞赏
    private String description;

    @ManyToOne
    private Type type;  //类型

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();  //标签

    @Transient
    private  String tagIds;

    //获取tagIds
    private   String tagsToIds(List<Tag> tags){
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        for(Tag tag:tags){
            if(flag){
                sb.append(",");
            }else{
                flag = true;
            }
            sb.append(tag.getId());
        }
        return sb.toString();
    }


    public void init(){
        this.tagIds = tagsToIds(this.tags);
    }
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date  createTime; //创建时间
    @Temporal(TemporalType.DATE)
    private Date  updateTime; //创建时间

//    public static void main(String[] args) {
//        Tag t1 = new Tag();
//        Tag t2 = new Tag();
//        t1.setId(1);
//        t2.setId(2);
//        List<Tag> t = new ArrayList<>();
//        t.add(t1);
//        t.add(t2);
//        System.out.println(new Blog().tagsToIds(t));
//    }
}
