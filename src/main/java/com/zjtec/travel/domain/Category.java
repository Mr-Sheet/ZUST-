package com.zjtec.travel.domain;

import java.io.Serializable;

/**
 * 分类实体类
 * 功能：映射数据库中的category表，用于封装旅游线路分类信息
 * 实现Serializable接口的原因：支持对象序列化，便于在网络传输或存储
 * 老师可能提问：
 * 1. Category类的主要作用是什么？
 * 2. Category类与Route类是什么关系？
 * 3. 为什么需要实现Serializable接口？
 */
public class Category implements Serializable {

    /**
     * 分类ID，主键，自增长
     */
    private int cid;//分类id
    
    /**
     * 分类名称
     */
    private String cname;//分类名称

    /**
     * 无参构造方法
     * 作用：用于对象的初始化和MyBatis的映射
     * 老师可能提问：
     * 1. 为什么需要无参构造方法？
     */
    public Category() {
    }

    /**
     * 有参构造方法
     * @param cid 分类ID
     * @param cname 分类名称
     * 老师可能提问：
     * 1. 什么时候使用有参构造方法？
     */
    public Category(int cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    /**
     * 重写toString方法
     * 作用：方便调试，用于打印对象信息
     */
    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                '}';
    }

    /**
     * 获取分类ID
     * @return int 分类ID
     */
    public int getCid() {
        return cid;
    }

    /**
     * 设置分类ID
     * @param cid 分类ID
     */
    public void setCid(int cid) {
        this.cid = cid;
    }

    /**
     * 获取分类名称
     * @return String 分类名称
     */
    public String getCname() {
        return cname;
    }

    /**
     * 设置分类名称
     * @param cname 分类名称
     */
    public void setCname(String cname) {
        this.cname = cname;
    }
}
