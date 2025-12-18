package com.zjtec.travel.domain;

import java.io.Serializable;

/**
 * 用户实体类
 * 功能：映射数据库中的user表，用于封装用户信息
 * 实现Serializable接口的原因：支持对象序列化，便于在网络传输或存储
 * 老师可能提问：
 * 1. 为什么要实现Serializable接口？
 * 2. 用户实体类与数据库表的映射关系是什么？
 * 3. salt字段的作用是什么？
 */
public class User implements Serializable {
    /**
     * 用户ID，主键，自增长
     */
    private int uid;//用户id
    
    /**
     * 用户名，账号，唯一标识，用于登录
     */
    private String username;//用户名，账号
    
    /**
     * 密码，使用SHA256算法加密，存储密文
     */
    private String password;//密码
    
    /**
     * 用户真实姓名
     */
    private String name;//真实姓名
    
    /**
     * 出生日期，格式为yyyy-MM-dd
     */
    private String birthday;//出生日期
    
    /**
     * 性别，取值为"男"或"女"
     */
    private String sex;//男或女
    
    /**
     * 手机号码，11位数字
     */
    private String telephone;//手机号
    
    /**
     * 邮箱地址，用于接收激活邮件和密码重置邮件
     */
    private String email;//邮箱
    
    /**
     * 激活状态，Y代表已激活，N代表未激活
     */
    private String status;//激活状态，Y代表激活，N代表未激活
    
    /**
     * 激活码，唯一，用于用户注册后的邮箱激活
     */
    private String code;//激活码（要求唯一）
    
    /**
     * 用户角色，如"普通用户"、"管理员"等
     */
    private String role;
    
    /**
     * 盐值，用于密码加密，8位随机字符串
     */
    private String salt; // 新增盐字段

    /**
     * 获取盐值
     * @return String 盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐值
     * @param salt 盐值
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 无参构造方法
     * 老师可能提问：
     * 1. 为什么需要无参构造方法？
     * 2. 无参构造方法在Spring框架中有什么作用？
     */
    public User() {
    }

    /**
     * 有参构造方法
     * @param uid 用户ID
     * @param username 用户名
     * @param password 密码
     * @param name 真实姓名
     * @param birthday 出生日期
     * @param sex 性别
     * @param telephone 手机号
     * @param email 邮箱
     * @param status 激活状态
     * @param code 激活码
     * 老师可能提问：
     * 1. 为什么构造方法中没有包含salt和role字段？
     * 2. 使用构造方法和setter方法设置属性有什么区别？
     */
    public User(int uid, String username, String password, String name, String birthday, String sex, String telephone, String email, String status, String code) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.telephone = telephone;
        this.email = email;
        this.status = status;
        this.code = code;
    }

    /**
     * 获取用户ID
     * @return int 用户ID
     */
    public int getUid() {
        return uid;
    }

    /**
     * 设置用户ID
     * @param uid 用户ID
     */
    public void setUid(int uid) {
        this.uid = uid;
    }

    /**
     * 获取用户名
     * @return String 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     * @return String 密码（密文）
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * @param password 密码（密文）
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取真实姓名
     * @return String 真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置真实姓名
     * @param name 真实姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取出生日期
     * @return String 出生日期
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     * @param birthday 出生日期
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取性别
     * @return String 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取手机号
     * @return String 手机号
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置手机号
     * @param telephone 手机号
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取邮箱
     * @return String 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取激活状态
     * @return String 激活状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置激活状态
     * @param status 激活状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取激活码
     * @return String 激活码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置激活码
     * @param code 激活码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取用户角色
     * @return String 用户角色
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置用户角色
     * @param role 用户角色
     */
    public void setRole(String role) {
        this.role = role;
    }
}
