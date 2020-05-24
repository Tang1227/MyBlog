package com.cn.blog.vo;

import lombok.Data;

@Data
public class BlogQuery {
  private Long typeId;
  private String title;
  private boolean recommend;
}
