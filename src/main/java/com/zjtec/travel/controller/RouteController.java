package com.zjtec.travel.controller;

import com.zjtec.travel.domain.PageBean;
import com.zjtec.travel.domain.Route;
import com.zjtec.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 旅游路线控制器
 * 功能：处理旅游路线相关的请求，包括分页查询和详情查询
 * 老师可能提问：
 * 1. 为什么使用@RequestMapping("/route")作为类级别的映射？
 * 2. @ResponseBody的作用是什么？
 * 3. 分页查询的实现原理是什么？
 */
@Controller
@RequestMapping("/route") // 类级别的请求映射，所有方法都以/route开头
public class RouteController {

  // 注入路线服务，用于处理路线相关业务逻辑
  @Autowired
  private RouteService routeService;

  /**
   * 分页查询旅游路线
   * @param cid 分类ID，用于查询特定分类下的路线
   * @param pageSize 每页显示的记录数，默认5条
   * @param currentPage 当前页码，默认1
   * @return PageBean<Route> 分页结果，包含路线列表和分页信息
   * 老师可能提问：
   * 1. 为什么使用@RequestParam注解？
   * 2. 如何处理参数为空的情况？
   * 3. 分页查询的SQL语句是如何实现的？
   * 4. PageBean类包含哪些信息？
   */
  @RequestMapping("/pageQuery")
  @ResponseBody // 返回JSON格式的数据
  public PageBean<Route> pageQuery(
          @RequestParam("cid") Integer cid, // 必须参数
          @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize, // 可选参数，默认5
          @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage) { // 可选参数，默认1
    
    // 额外的参数验证和默认值设置
    if (currentPage == null || currentPage < 1) {
      currentPage = 1;
    }
    if (pageSize == null || pageSize < 1) {
      pageSize = 5;
    }
    
    // 调用Service层方法获取分页数据
    PageBean<Route> pageBean = routeService.pageQuery(cid, currentPage, pageSize);
    return pageBean;
  }

  /**
   * 查询旅游路线详情
   * @param rid 路线ID
   * @return Route 路线详细信息
   * 老师可能提问：
   * 1. 路线详情包含哪些信息？
   * 2. 如何处理rid为null的情况？
   * 3. 为什么不使用@RequestParam注解？
   */
  @RequestMapping("/queryDetail")
  @ResponseBody // 返回JSON格式的数据
  public Route queryDetail(Integer rid){
    // 调用Service层方法获取路线详情
    return routeService.findRouteDetail(rid);
  }
}
