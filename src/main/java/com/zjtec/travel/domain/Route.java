package com.zjtec.travel.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 旅游线路商品实体类
 * 功能：映射数据库中的route表，用于封装旅游线路信息
 * 实现Serializable接口的原因：支持对象序列化，便于在网络传输或存储
 * 老师可能提问：
 * 1. Route类与哪些实体类有关联关系？
 * 2. 这些关联关系分别是什么类型（一对一、一对多）？
 * 3. 为什么需要在实体类中定义关联关系？
 */
public class Route implements Serializable {

    /**
     * 线路ID，主键，自增长，必输
     */
    private int rid;//线路id，必输
    
    /**
     * 线路名称，必输
     */
    private String rname;//线路名称，必输
    
    /**
     * 线路价格，必输
     */
    private double price;//价格，必输
    
    /**
     * 线路详细介绍
     */
    private String routeIntroduce;//线路介绍
    
    /**
     * 是否上架，必输，0代表未上架，1代表已上架
     */
    private String rflag;   //是否上架，必输，0代表没有上架，1代表是上架
    
    /**
     * 上架时间，格式为yyyy-MM-dd
     */
    private String rdate;   //上架时间
    
    /**
     * 是否主题旅游，必输，0代表不是，1代表是
     */
    private String isThemeTour;//是否主题旅游，必输，0代表不是，1代表是
    
    /**
     * 收藏数量
     */
    private int count;//收藏数量
    
    /**
     * 所属分类ID，必输
     */
    private int cid;//所属分类，必输
    
    /**
     * 线路缩略图URL
     */
    private String rimage;//缩略图
    
    /**
     * 所属商家ID
     */
    private int sid;//所属商家
    
    /**
     * 抓取数据的来源ID，用于数据同步
     */
    private String sourceId;//抓取数据的来源id

    /**
     * 所属分类对象，一对一关系
     */
    private Category category;//所属分类
    
    /**
     * 所属商家对象，一对一关系
     */
    private Seller seller;//所属商家
    
    /**
     * 商品详情图片列表，一对多关系
     */
    private List<RouteImg> routeImgList;//商品详情图片列表

    /**
     * 无参构造方法
     * 老师可能提问：
     * 1. 为什么需要无参构造方法？
     * 2. MyBatis在映射实体类时是否需要无参构造方法？
     */
    public Route(){}

    /**
     * 有参构造方法
     * @param rid 线路ID
     * @param rname 线路名称
     * @param price 线路价格
     * @param routeIntroduce 线路介绍
     * @param rflag 是否上架
     * @param rdate 上架时间
     * @param isThemeTour 是否主题旅游
     * @param count 收藏数量
     * @param cid 所属分类ID
     * @param rimage 缩略图
     * @param sid 所属商家ID
     * @param sourceId 来源ID
     * 老师可能提问：
     * 1. 为什么构造方法中没有包含关联对象（category、seller、routeImgList）？
     * 2. 如何初始化这些关联对象？
     */
    public Route(int rid, String rname, double price, String routeIntroduce, String rflag, String rdate, String isThemeTour, int count, int cid, String rimage, int sid, String sourceId) {
        this.rid = rid;
        this.rname = rname;
        this.price = price;
        this.routeIntroduce = routeIntroduce;
        this.rflag = rflag;
        this.rdate = rdate;
        this.isThemeTour = isThemeTour;
        this.count = count;
        this.cid = cid;
        this.rimage = rimage;
        this.sid = sid;
        this.sourceId = sourceId;
    }

    /**
     * 获取商品详情图片列表
     * @return List<RouteImg> 图片列表
     */
    public List<RouteImg> getRouteImgList() {
        return routeImgList;
    }

    /**
     * 设置商品详情图片列表
     * @param routeImgList 图片列表
     */
    public void setRouteImgList(List<RouteImg> routeImgList) {
        this.routeImgList = routeImgList;
    }

    /**
     * 获取所属分类对象
     * @return Category 分类对象
     */
    public Category getCategory() {
        return category;
    }

    /**
     * 设置所属分类对象
     * @param category 分类对象
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * 获取所属商家对象
     * @return Seller 商家对象
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * 设置所属商家对象
     * @param seller 商家对象
     */
    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /**
     * 获取来源ID
     * @return String 来源ID
     */
    public String getSourceId() {
        return sourceId;
    }

    /**
     * 设置来源ID
     * @param sourceId 来源ID
     */
    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    /**
     * 获取线路ID
     * @return int 线路ID
     */
    public int getRid() {
        return rid;
    }

    /**
     * 设置线路ID
     * @param rid 线路ID
     */
    public void setRid(int rid) {
        this.rid = rid;
    }

    /**
     * 获取线路名称
     * @return String 线路名称
     */
    public String getRname() {
        return rname;
    }

    /**
     * 设置线路名称
     * @param rname 线路名称
     */
    public void setRname(String rname) {
        this.rname = rname;
    }

    /**
     * 获取线路价格
     * @return double 线路价格
     */
    public double getPrice() {
        return price;
    }

    /**
     * 设置线路价格
     * @param price 线路价格
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * 获取线路介绍
     * @return String 线路介绍
     */
    public String getRouteIntroduce() {
        return routeIntroduce;
    }

    /**
     * 设置线路介绍
     * @param routeIntroduce 线路介绍
     */
    public void setRouteIntroduce(String routeIntroduce) {
        this.routeIntroduce = routeIntroduce;
    }

    /**
     * 获取上架状态
     * @return String 上架状态
     */
    public String getRflag() {
        return rflag;
    }

    /**
     * 设置上架状态
     * @param rflag 上架状态
     */
    public void setRflag(String rflag) {
        this.rflag = rflag;
    }

    /**
     * 获取上架时间
     * @return String 上架时间
     */
    public String getRdate() {
        return rdate;
    }

    /**
     * 设置上架时间
     * @param rdate 上架时间
     */
    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    /**
     * 获取是否主题旅游
     * @return String 是否主题旅游
     */
    public String getIsThemeTour() {
        return isThemeTour;
    }

    /**
     * 设置是否主题旅游
     * @param isThemeTour 是否主题旅游
     */
    public void setIsThemeTour(String isThemeTour) {
        this.isThemeTour = isThemeTour;
    }

    /**
     * 获取收藏数量
     * @return int 收藏数量
     */
    public int getCount() {
        return count;
    }

    /**
     * 设置收藏数量
     * @param count 收藏数量
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 获取所属分类ID
     * @return int 所属分类ID
     */
    public int getCid() {
        return cid;
    }

    /**
     * 设置所属分类ID
     * @param cid 所属分类ID
     */
    public void setCid(int cid) {
        this.cid = cid;
    }

    /**
     * 获取缩略图
     * @return String 缩略图URL
     */
    public String getRimage() {
        return rimage;
    }

    /**
     * 设置缩略图
     * @param rimage 缩略图URL
     */
    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    /**
     * 获取所属商家ID
     * @return int 所属商家ID
     */
    public int getSid() {
        return sid;
    }

    /**
     * 设置所属商家ID
     * @param sid 所属商家ID
     */
    public void setSid(int sid) {
        this.sid = sid;
    }
}
