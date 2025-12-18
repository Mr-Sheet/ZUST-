package com.zjtec.travel.dao;

import com.zjtec.travel.domain.Seller;
import org.apache.ibatis.annotations.Select;

public interface SellerDao {
    @Select("select * from tab_seller")
    Seller select();
}
