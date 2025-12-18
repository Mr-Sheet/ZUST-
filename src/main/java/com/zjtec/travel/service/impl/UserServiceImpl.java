package com.zjtec.travel.service.impl;

import com.zjtec.travel.dao.UserDao;
import com.zjtec.travel.domain.User;
import com.zjtec.travel.service.UserService;
import com.zjtec.travel.util.MsgDigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * 功能：处理用户相关业务逻辑，包括登录验证、用户注册、激活等
 * 老师可能提问：
 * 1. 为什么使用@Service注解？
 * 2. 用户密码是如何加密的？
 * 3. 什么是盐值(salt)？为什么要使用盐值？
 */
@Service // 标记为Service层组件，由Spring容器管理
public class UserServiceImpl implements UserService {

    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    // 注入用户DAO，用于数据库操作
    @Autowired
    private UserDao userDao;

    /**
     * 验证用户密码
     * @param username 用户名
     * @param password 密码（明文）
     * @return boolean 验证结果
     * 老师可能提问：
     * 1. 密码验证的流程是怎样的？
     * 2. 为什么要查询激活状态的用户？
     * 3. 如何防止SQL注入？
     */
    @Override
    public boolean validUserPwd(String username, String password) {
        // 1. 根据用户名查询激活状态的用户
        User user = userDao.findActiveUserByUserName(username);
        if (user == null) {
            logger.info("用户{}不存在或未激活", username);
            return false;
        }
        // 2. 获取数据库中的盐值和加密后的密码
        String dbSalt = user.getSalt();
        String dbPassword = user.getPassword();
        // 3. 用相同的盐值加密用户输入的密码
        String encryptedInputPwd = MsgDigestUtils.encryptPassword(password, dbSalt);
        // 4. 对比加密后的密码是否一致
        boolean isValid = encryptedInputPwd.equals(dbPassword);
        if (!isValid) {
            logger.info("用户{}密码验证失败", username);
        }
        return isValid;
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return User 用户对象
     */
    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUserName(username);
    }

    /**
     * 保存用户信息（用户注册）
     * @param ue 用户注册信息
     * @return boolean 保存结果
     * 老师可能提问：
     * 1. 密码加密的算法是什么？
     * 2. 为什么要使用盐值？
     * 3. 加密过程中循环3次SHA256有什么作用？
     */
    @Override
    public boolean save(User ue) {
        // 1. 生成8位随机盐值
        String salt = MsgDigestUtils.generateSalt(8);

        // 2. 加密密码（使用盐值+明文密码，循环3次SHA256算法）
        String encryptedPwd = MsgDigestUtils.encryptPassword(ue.getPassword(), salt);

        // 3. 设置盐值和加密后的密码到用户对象
        ue.setSalt(salt);
        ue.setPassword(encryptedPwd);

        // 4. 保存用户到数据库
        return userDao.save(ue) > 0;
    }

    /**
     * 检查用户名或邮箱是否已存在
     * @param username 用户名
     * @param email 邮箱
     * @return boolean 检查结果
     * 老师可能提问：
     * 1. 如何实现的高效查询？
     * 2. 为什么要同时检查用户名和邮箱？
     */
    @Override
    public boolean existUserNameOrEmail(String username, String email) {
        return userDao.existUserNameOrEmail(username, email);
    }

    /**
     * 激活用户
     * @param username 用户名
     * @param code 激活码
     * @return boolean 激活结果
     * 老师可能提问：
     * 1. 激活流程是怎样的？
     * 2. 激活码的有效期是如何处理的？
     */
    @Override
    public boolean activeUser(String username, String code) {
        return userDao.activeUser(username, code);
    }

    /**
     * 根据用户名查询激活状态的用户
     * @param username 用户名
     * @return User 用户对象
     */
    @Override
    public User findActiveUserByUserName(String username) {
        return userDao.findActiveUserByUserName(username);
    }
}
