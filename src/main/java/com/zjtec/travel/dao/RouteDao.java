package com.zjtec.travel.dao;

import com.zjtec.travel.domain.Route;
import com.zjtec.travel.domain.RouteImg;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 旅游路线数据访问接口
 * 功能：定义旅游路线相关的数据库操作方法，部分方法使用注解直接编写SQL
 * 老师可能提问：
 * 1. @Select注解的作用是什么？
 * 2. 为什么有些方法使用XML配置，有些使用注解？
 * 3. findByPage和queryDetail有什么区别？
 */
public interface RouteDao {

    /**
     * 根据分类ID查询总记录数
     * @param cid 分类ID
     * @return int 总记录数
     * 老师可能提问：
     * 1. 这个方法对应的SQL语句是什么？
     * 2. 为什么需要单独查询总记录数？
     */
    int findTotalCount(int cid);

    /**
     * 根据分类ID、起始索引和每页大小查询当前页的数据集合
     * @param cid 分类ID
     * @param start 起始索引
     * @param pageSize 每页大小
     * @return List<Route> 当前页的路线列表
     * 老师可能提问：
     * 1. 分页查询的SQL语句是如何实现的？
     * 2. 为什么使用@Param注解？
     * 3. start参数是如何计算的？
     */
    List<Route> findByPage(@Param("cid") int cid, @Param("start")int start, @Param("pageSize")int pageSize);

    /**
     * 根据路线ID查询旅游产品（关联商家信息）
     * @param rid 路线ID
     * @return Route 包含商家信息的路线对象
     * 老师可能提问：
     * 1. 关联查询是如何实现的？
     * 2. 与queryDetail方法的区别是什么？
     */
    Route findById(Integer rid);

    /**
     * 查询路线详情
     * @param rid 路线ID
     * @return Route 路线对象
     * 老师可能提问：
     * 1. @Select注解的参数是什么意思？
     * 2. 为什么使用注解而不是XML配置？
     */
    @Select("select * from tab_route where rid=#{rid}")
    Route queryDetail(Integer rid);

    /**
     * 查询路线图片列表
     * @param rid 路线ID
     * @return List<RouteImg> 路线图片列表
     * 老师可能提问：
     * 1. 路线和图片是什么关系？
     * 2. 查询到的图片如何与路线关联？
     */
    @Select("select * from tab_route_img where rid=#{rid}")
    List<RouteImg> selectImg(Integer rid);
}
