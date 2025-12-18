package com.zjtec.travel.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC工具类
 * 功能：使用Druid连接池管理数据库连接
 * 特点：
 * 1. 提供连接池的初始化和获取
 * 2. 提供获取数据库连接的方法
 * 3. 提供关闭数据库资源的方法
 * 老师可能提问：
 * 1. 为什么使用Druid连接池？
 * 2. 连接池的优点是什么？
 * 3. Druid连接池的配置文件在哪里？
 */
public class JDBCUtils {
	/**
	 * 静态数据源成员变量
	 * 作用：存储Druid连接池对象
	 */
	private static DataSource ds;

	/**
	 * 静态代码块
	 * 作用：初始化连接池对象
	 * 执行时机：类加载时执行一次
	 */
	static {
		// 加载配置文件中的数据
		InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
		Properties pp = new Properties();
		try {
			pp.load(is);
			// 创建连接池，使用配置文件中的参数
			ds = DruidDataSourceFactory.createDataSource(pp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据源
	 * @return DataSource Druid连接池对象
	 * 老师可能提问：
	 * 1. 为什么要提供获取数据源的方法？
	 */
	public static DataSource getDataSource() {
		return ds;
	}

	/**
	 * 获取数据库连接
	 * @return Connection 数据库连接对象
	 * @throws SQLException 获取连接时可能出现的异常
	 * 老师可能提问：
	 * 1. 从连接池获取的连接和直接创建的连接有什么区别？
	 */
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	/**
	 * 关闭数据库资源
	 * @param conn 数据库连接对象
	 * @param stmt SQL语句执行对象
	 * @param rs 结果集对象
	 * 关闭顺序：ResultSet -> Statement -> Connection
	 * 老师可能提问：
	 * 1. 为什么要按这个顺序关闭资源？
	 * 2. 为什么要在finally块中关闭资源？
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {}
		}
	}

	/**
	 * 重载关闭资源的方法（无结果集）
	 * @param conn 数据库连接对象
	 * @param stmt SQL语句执行对象
	 * 老师可能提问：
	 * 1. 为什么要重载close方法？
	 */
	public static void close(Connection conn, Statement stmt) {
		close(conn, stmt, null);
	}
}
