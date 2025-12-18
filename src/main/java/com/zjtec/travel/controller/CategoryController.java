//列表
package com.zjtec.travel.controller;

import com.zjtec.travel.domain.Category;
import com.zjtec.travel.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

  private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value="/findAll")
  @ResponseBody
  public List<Category> findAll(){
    logger.info("开始查找旅游目录12");
    // 调用Service层方法获取所有产品目录数据
    List<Category> categoryList = categoryService.findAll();
    logger.info("查找旅游目录完成，共获取到{}条数据", categoryList.size());
    return categoryList;
  }
}
