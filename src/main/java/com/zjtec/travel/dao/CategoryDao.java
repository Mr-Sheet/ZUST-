package com.zjtec.travel.dao;

import com.zjtec.travel.domain.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询所有分类
     * @return
     */
    List<Category> findAll();
    @Select("select * from tab_category where cid=#{cid}")
    Category findById(int cid);
}
