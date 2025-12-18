//注册
package com.zjtec.travel.controller;

import com.zjtec.travel.Application;
import com.zjtec.travel.constant.Const;
import com.zjtec.travel.domain.User;
import com.zjtec.travel.service.EmailService;
import com.zjtec.travel.service.UserService;
import com.zjtec.travel.vo.ResMsg;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 注册控制器
 * 功能：处理用户注册、激活功能
 * 1. 注册流程是怎样的？
 * 2. 为什么需要用户激活？
 * 3. 激活码是如何生成和使用的？
 * 4. 注册时如何保证数据的有效性？
 */
@Controller
public class RegisterController {

  // 日志记录器
  private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

  // 注入用户服务
  @Autowired
  private UserService userService;

  // 注入邮件服务，用于发送激活邮件
  @Autowired
  private EmailService emailService;

  // 注入HttpSession
  @Autowired
  public HttpSession session;

  /**
   * 用户注册功能
   * @param ue 用户注册信息，包含用户名、密码、邮箱等
   * @return ResMsg 响应结果
   * 老师可能提问：
   * 1. 注册验证的步骤有哪些？
   * 2. 如何防止重复注册？
   * 3. 为什么要使用激活码？
   * 4. 密码是否进行了加密处理？
   */
  @RequestMapping(value = "/signup")
  @ResponseBody
  public ResMsg signup(@RequestBody User ue) {
    ResMsg resmsg = new ResMsg();

    // 1. 校验验证码 - 防止机器人注册
    String captcha = (String) session.getAttribute(Const.SESSION_KEY_CAPTCHA);
    if (captcha == null || !captcha.equalsIgnoreCase(ue.getCode())) {
      resmsg.setErrcode("4");
      resmsg.setErrmsg("验证码不正确");
      return resmsg;
    }

    // 2. 校验所有必填字段非空 - 确保用户信息完整性
    if (StringUtils.isBlank(ue.getUsername()) ||
            StringUtils.isBlank(ue.getPassword()) ||
            StringUtils.isBlank(ue.getEmail()) ||
            StringUtils.isBlank(ue.getName()) ||
            StringUtils.isBlank(ue.getTelephone()) ||
            StringUtils.isBlank(ue.getBirthday()) ||
            StringUtils.isBlank(ue.getSex())) {
      resmsg.setErrcode("3");
      resmsg.setErrmsg("注册表格输入框均不能为空");
      return resmsg;
    }

    // 3. 校验用户名和邮箱是否已存在 - 防止重复注册
    if (userService.existUserNameOrEmail(ue.getUsername(), ue.getEmail())) {
      resmsg.setErrcode("2");
      resmsg.setErrmsg("用户名或Email已存在");
      return resmsg;
    }

    // 4. 设置用户初始状态和激活码
    ue.setStatus(Const.USER_STATUS_INACTIVE); // 设置为未激活状态
    ue.setCode(RandomStringUtils.random(20, Const.CHARSET_ALPHA)); // 生成20位随机激活码
    ue.setRole(Const.USER_ROLE_MEMBER); // 默认设置为普通会员角色

    // 5. 保存用户信息到数据库
    try {
      userService.save(ue);
      resmsg.setErrcode("0");
      resmsg.setErrmsg("注册成功");
      
      // 生成激活链接
      String activationUrl = String.format("http://localhost:8082/activation?username=%s&code=%s",
              ue.getUsername(), ue.getCode());
      
      // 发送激活邮件
      emailService.sendEmail("2035745264@qq.com", "账号激活链接", activationUrl);
      logger.info("激活链接 -> " + activationUrl);
    } catch (Exception e) {
      resmsg.setErrcode("1");
      resmsg.setErrmsg("注册失败");
      logger.error("注册失败", e);
    }

    return resmsg;
  }

  /**
   * 用户激活功能
   * @param map 模型对象，用于传递数据到视图
   * @param username 用户名
   * @param code 激活码
   * @return String 视图名称
   * 老师可能提问：
   * 1. 激活流程是怎样的？
   * 2. 激活失败的原因可能有哪些？
   * 3. 激活成功后为什么要跳转到登录页面？
   */
  @RequestMapping(value = "/activation")
  public String activation(ModelMap map, String username, String code){
    // 验证用户名和激活码是否为空
    if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(code)){
      boolean activationResult = false;
      try {
        // 调用Service层进行激活
        activationResult = userService.activeUser(username, code);
      } catch (Exception e) {
        logger.error("激活用户异常", e);
      }

      // 根据激活结果设置不同的响应信息
      if(activationResult){
        map.put("msg", "激活成功，请点击跳转链接登录");
        map.put("redirect", "/login.html");
      }else{
        map.put("msg", "激活失败，点击跳转链接返回首页");
        map.put("redirect", "/");
      }
    }else{
      map.put("msg", "激活失败，点击跳转链接返回首页");
      map.put("redirect", "/");
    }
    
    map.put("title", "用户激活");
    return "msg"; // 返回消息提示页面
  }
}