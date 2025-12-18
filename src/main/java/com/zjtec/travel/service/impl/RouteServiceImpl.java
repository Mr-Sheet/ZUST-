package com.zjtec.travel.service.impl;

import com.zjtec.travel.dao.CategoryDao;
import com.zjtec.travel.dao.RouteDao;
import com.zjtec.travel.dao.SellerDao;
import com.zjtec.travel.domain.*;
import com.zjtec.travel.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 旅游路线服务实现类
 * 功能：处理旅游路线相关业务逻辑，包括分页查询和详情查询
 * 老师可能提问：
 * 1. 为什么需要同时注入多个DAO？
 * 2. 分页查询的原理是什么？
 * 3. 路线详情包含哪些关联信息？
 */
@Service // 标记为Service层组件，由Spring容器管理
public class RouteServiceImpl implements RouteService {

    // 日志记录器
    private static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);

    // 注入路线DAO，用于路线相关的数据库操作
    @Autowired
    private RouteDao routeDao;
    // 注入商家DAO，用于查询商家信息
    @Autowired
    private SellerDao sellerDao;
    // 注入分类DAO，用于查询分类信息
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 分页查询旅游路线
     * @param cid 分类ID
     * @param currentPage 当前页码
     * @param pageSize 每页显示条数
     * @return PageBean<Route> 分页结果对象
     * 老师可能提问：
     * 1. 分页查询的SQL语句是如何实现的？
     * 2. 总页数是如何计算的？
     * 3. PageBean类包含哪些字段？
     * 4. 为什么使用start参数而不是直接使用currentPage？
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        // 创建PageBean对象，用于封装分页信息
        PageBean<Route> pb = new PageBean<>();
        
        // 设置当前页码
        pb.setCurrentPage(currentPage);
        // 设置每页显示条数
        pb.setPageSize(pageSize);
        
        // 查询总记录数
        int totalCount = routeDao.findTotalCount(cid);
        pb.setTotalCount(totalCount);
        
        // 计算分页起始索引
        int start = (currentPage - 1) * pageSize;
        // 查询当前页的数据列表
        List<Route> list = routeDao.findByPage(cid, start, pageSize);
        pb.setList(list);
        
        // 计算总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    /**
     * 查询旅游路线详情
     * @param rid 路线ID
     * @return Route 包含完整信息的路线对象
     * 老师可能提问：
     * 1. 为什么要查询关联的图片、商家和分类信息？
     * 2. 这种查询方式有什么优缺点？
     * 3. 如何优化多表查询性能？
     */
    @Override
    public Route findRouteDetail(Integer rid) {
        // 查询路线基本信息
        Route route = routeDao.queryDetail(rid);
        
        // 查询路线图片列表
        List<RouteImg> listImg = routeDao.selectImg(rid);
        route.setRouteImgList(listImg);
        
        // 查询商家信息
        Seller seller = sellerDao.select();
        route.setSeller(seller);
        
        // 查询分类信息
        Category category = categoryDao.findById(route.getCid());
        route.setCategory(category);
        
        return route;
    }
}
