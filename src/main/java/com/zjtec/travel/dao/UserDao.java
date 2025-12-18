package com.zjtec.travel.dao;

import com.zjtec.travel.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据访问接口
 * 功能：定义用户相关的数据库操作方法，与MyBatis的XML文件对应
 * 老师可能提问：
 * 1. MyBatis的Mapper接口是如何与XML文件关联的？
 * 2. 为什么使用@Param注解？
 * 3. 接口中的方法是如何映射到SQL语句的？
 */
public interface UserDao {
    /**
     * 按用户名查找用户信息
     * @param username 用户名
     * @return User 用户对象，如果不存在则返回null
     * 老师可能提问：
     * 1. 这个方法对应的SQL语句是什么？
     * 2. 如果用户名不存在会返回什么？
     */
    User findByUserName(String username);

    /**
     * 按用户名查找已激活用户信息
     * @param username 用户名
     * @return User 已激活的用户对象，如果不存在或未激活则返回null
     * 老师可能提问：
     * 1. 与findByUserName方法的区别是什么？
     * 2. 为什么需要单独的方法来查询已激活用户？
     */
    User findActiveUserByUserName(String username);

    /**
     * 保存用户信息
     * @param ue 用户对象
     * @return int 受影响的行数，成功返回1，失败返回0
     * 老师可能提问：
     * 1. 这个方法对应的SQL语句是插入还是更新？
     * 2. 如果保存失败会有什么影响？
     */
    int save(User ue);

    /**
     * 是否存在用户名或Email
     * @param username 用户名
     * @param email 邮箱
     * @return boolean 如果存在则返回true，否则返回false
     * 老师可能提问：
     * 1. 为什么要同时检查用户名和邮箱？
     * 2. 对应的SQL语句使用了什么条件？
     */
    boolean existUserNameOrEmail(@Param("username") String username, @Param("email") String email);

    /**
     * 激活用户
     * @param username 用户名
     * @param code 激活码
     * @return boolean 激活成功返回true，否则返回false
     * 老师可能提问：
     * 1. 激活用户的SQL语句是如何实现的？
     * 2. 为什么需要同时验证用户名和激活码？
     */
    boolean activeUser(@Param("username")String username,@Param("code")String code);
}
