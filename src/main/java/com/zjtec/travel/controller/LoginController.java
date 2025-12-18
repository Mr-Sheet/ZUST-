//登录控制器
package com.zjtec.travel.controller;

import com.zjtec.travel.constant.Const;
import com.zjtec.travel.domain.User;
import com.zjtec.travel.service.UserService;
import com.zjtec.travel.vo.LoginVo;
import com.zjtec.travel.vo.RedirectVo;
import com.zjtec.travel.vo.ResMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 * 功能：处理用户登录、退出功能
 * 老师可能提问：
 * 1. 登录流程是怎样的？
 * 2. 如何保证登录安全？
 * 3. 为什么使用@Controller而不是@RestController？
 * 4. 登录成功后为什么要在Session中存储用户信息？
 */
@Controller
public class LoginController {

  // 注入用户服务，用于处理用户相关业务逻辑
  @Autowired
  private UserService userService;

  // 注入HttpSession，用于存储登录用户信息
  @Autowired
  public HttpSession session;

  /**
   * 用户登录功能
   * @param vo 登录请求参数，包含用户名、密码、验证码
   * @return ResMsg 响应结果，包含错误码、错误信息和跳转地址
   * 老师可能提问：
   * 1. 登录流程的验证步骤有哪些？
   * 2. 验证码是如何生成和验证的？
   * 3. 为什么密码验证要调用userService.validUserPwd而不是直接比较？
   * 4. 登录成功后为什么要根据用户角色跳转到不同页面？
   */
  @RequestMapping(value = "/signin")
  @ResponseBody
  public ResMsg signIn(@RequestBody LoginVo vo) {
    ResMsg resMsg = new ResMsg(); // 创建响应消息对象
    
    // 1. 验证验证码 - 从Session获取生成的验证码与用户输入的验证码比较
    String captcha = (String) session.getAttribute(Const.SESSION_KEY_CAPTCHA);
    if (captcha == null || !captcha.equalsIgnoreCase(vo.getCaptcha())) {
      resMsg.setErrcode("4");
      resMsg.setErrmsg("验证码不正确");
      return resMsg;
    }
    
    // 2. 校验用户名和密码是否为空
    if (StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword())) {
      resMsg.setErrcode("2");
      resMsg.setErrmsg("用户名密码均不能为空");
      return resMsg;
    }

    // 3. 根据用户名查询用户 - 检查用户是否存在且状态是否激活
    User dbUser = userService.getUserByUsername(vo.getUsername());
    if (dbUser == null || !"Y".equals(dbUser.getStatus())) {
      resMsg.setErrcode("3");
      resMsg.setErrmsg("用户名不存在");
      return resMsg;
    }

    // 4. 校验密码 - 调用Service层进行密码验证，实际项目中会使用加密算法
    if (!userService.validUserPwd(vo.getUsername(), vo.getPassword())) {
      resMsg.setErrcode("1");
      resMsg.setErrmsg("用户名或密码不正确");
      return resMsg;
    }

    // 5. 登录成功，将用户信息写入Session - 用于后续请求的身份验证
    session.setAttribute(Const.SESSION_KEY_USER, dbUser);
    session.setAttribute(Const.SESSION_KEY_USERNAME, dbUser.getUsername());
    session.setAttribute(Const.SESSION_KEY_USER_ROLE, dbUser.getRole());

    // 6. 根据用户角色设置不同的跳转地址 - 管理员跳转到后台，普通用户跳转到个人中心
    resMsg.setErrcode("0");
    resMsg.setErrmsg("登录成功");
    if (Const.USER_ROLE_ADMIN.equals(dbUser.getRole())) {
      resMsg.setResult(new RedirectVo("/dashboard"));
    } else if (Const.USER_ROLE_MEMBER.equals(dbUser.getRole())) {
      resMsg.setResult(new RedirectVo("/mine"));
    }

    return resMsg;
  }

  /**
   * 用户退出/注销功能
   * @param map 模型对象，用于传递数据到视图
   * @return String 视图名称
   * 老师可能提问：
   * 1. 退出登录时为什么要清除Session中的用户信息？
   * 2. 退出后为什么要跳转到提示页面而不是直接到首页？
   * 3. ModelMap的作用是什么？
   */
  @RequestMapping(value = "/logout")
  public String logout(ModelMap map) {
    // 清除Session中的用户相关信息，确保用户完全退出
    session.removeAttribute(Const.SESSION_KEY_USER);
    session.removeAttribute(Const.SESSION_KEY_USERNAME);
    session.removeAttribute(Const.SESSION_KEY_USER_ROLE);
    
    // 设置响应信息
    map.put("msg", "成功退出，点击跳转链接跳转到首页");
    map.put("redirect", "/");
    map.put("title", "用户退出");
    return "msg"; // 返回消息提示页面
  }
}