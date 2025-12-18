sequenceDiagram
    participant User as 用户
    participant Browser as 浏览器
    participant RouteController as RouteController
    participant RouteService as RouteService
    participant RouteDao as RouteDao
    participant Database as 数据库
    participant Redis as Redis缓存

    User->>Browser: 访问旅游线路页面
    Browser->>RouteController: 请求分类(cid)下的线路列表(/route/pageQuery)
    Note over RouteController: 接收参数:<br/>- cid(分类ID)<br/>- currentPage(当前页)<br/>- pageSize(每页条数)

    RouteController->>RouteService: 调用分页查询方法<br/>pageQuery(cid, currentPage, pageSize)
    
    RouteService->>RouteDao: 查询总记录数<br/>findTotalCount(cid)
    RouteDao->>Database: 执行SQL查询<br/>SELECT COUNT(*) FROM tab_route WHERE cid = ?
    Database-->>RouteDao: 返回总记录数
    RouteDao-->>RouteService: 返回总记录数
    
    RouteService->>RouteDao: 查询当前页数据<br/>findByPage(cid, start, pageSize)
    RouteDao->>Database: 执行分页查询<br/>SELECT * FROM tab_route WHERE cid = ?<br/>ORDER BY rid LIMIT ?, ?
    Database-->>RouteDao: 返回线路列表
    RouteDao-->>RouteService: 返回线路列表
    
    RouteService->>RouteService: 计算总页数<br/>totalPage = totalCount/pageSize (+1 if remainder)
    
    RouteService-->>RouteController: 返回PageBean对象<br/>(包含totalCount, totalPage,<br/>currentPage, pageSize, list)
    
    RouteController-->>Browser: 返回JSON格式数据
    
    Browser->>Browser: 渲染页面<br/>显示分页线路列表
    
    alt 首页分类列表加载
      Browser->>CategoryController: 请求分类列表(/category/fisequenceDiagram
    participant User as 用户
    participant Browser as 浏览器
    participant RouteController as RouteController
    participant RouteService as RouteService
    participant RouteDao as RouteDao
    participant Database as 数据库
    participant Redis as Redis缓存

    User->>Browser: 访问旅游线路页面
    Browser->>RouteController: 请求分类(cid)下的线路列表(/route/pageQuery)
    Note over RouteController: 接收参数:<br/>- cid(分类ID)<br/>- currentPage(当前页)<br/>- pageSize(每页条数)

    RouteController->>RouteService: 调用分页查询方法<br/>pageQuery(cid, currentPage, pageSize)
    
    RouteService->>RouteDao: 查询总记录数<br/>findTotalCount(cid)
    RouteDao->>Database: 执行SQL查询<br/>SELECT COUNT(*) FROM tab_route WHERE cid = ?
    Database-->>RouteDao: 返回总记录数
    RouteDao-->>RouteService: 返回总记录数
    
    RouteService->>RouteDao: 查询当前页数据<br/>findByPage(cid, start, pageSize)
    RouteDao->>Database: 执行分页查询<br/>SELECT * FROM tab_route WHERE cid = ?<br/>ORDER BY rid LIMIT ?, ?
    Database-->>RouteDao: 返回线路列表
    RouteDao-->>RouteService: 返回线路列表
    
    RouteService->>RouteService: 计算总页数<br/>totalPage = totalCount/pageSize (+1 if remainder)
    
    RouteService-->>RouteController: 返回PageBean对象<br/>(包含totalCount, totalPage,<br/>currentPage, pageSize, list)
    
    RouteController-->>Browser: 返回JSON格式数据
    
    Browser->>Browser: 渲染页面<br/>显示分页线路列表
    
    alt 首页分类列表加载
      Browser->>CategoryController: 请求分类列表(/category/fi# 学生获取的初始项目
1. 项目要成功运行，先要执行src/main/resources/db/migration目录下所有脚本，建立数据库。
2. 修改数据库的链接配置 src/main/resources/druid.properties
3. 导入项目到IDEA 中，并构建项目。
4. 使用Maven 命令编译打包 `mvn clean compile war:exploded`
5. 运行`com.zjtec.travel.Application`
6. 访问[http://localhost:8082/](http://localhost:8082/)
