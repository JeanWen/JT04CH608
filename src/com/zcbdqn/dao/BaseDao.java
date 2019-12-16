package com.zcbdqn.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 公共的工具类
 * 包含对数据库操作的基本方法,
 * 1.获得连接
 * 2.释放资源
 * 3.公共的查询方法
 * 4.公共的 增  删  改
 */
public abstract class BaseDao<T> {

   /* *//**
     * 获得连的方法
     *
     * @return
     *//*
    public static Connection getConnection() {

        String driver= ConfigManager.getInstance().getValue("jdbc.driver");
        String url= ConfigManager.getInstance().getValue("jdbc.url");
        String username= ConfigManager.getInstance().getValue("jdbc.username");
        String password= ConfigManager.getInstance().getValue("jdbc.password");

        Connection conn = null;
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.获得连接
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("连结数据库失败,请联系管理员",e);
        }
    }*/

    public static Connection getConnectionByJNDI() {

        Connection conn = null;
        try {
            //1. 获取上下文
            Context cxt=new InitialContext();
            //2.获取数据源 DataSource
            DataSource dataSource= (DataSource) cxt.lookup("java:comp/env/jdbc/smbms");
            //3.获得连接
            conn=dataSource.getConnection();
            return conn;
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("JNDI访问资源不存在,请联系管理员",e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("连结数据库失败,请联系管理员",e);
        }
    }


    public static void closeResource(Connection conn, Statement stmt,  ResultSet rs) {
        try {
            if (rs!=null){
                rs.close();
            }
            if (stmt!=null){
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行查询的方法
     * @param conn 连接对象
     * @param sql 要执行的sql
     * @param params 参数
     * @return 查询到的结果
     */
    public List<T> executeQuery(Connection conn,String sql,Object... params){
        List<T> result=new ArrayList<>();
        //创建语名集对象,使用它发送sql
        PreparedStatement ps=null;
        ResultSet rs =null;
        try {
            //创建语名集对象,使用它发送sql   sql:select * from news_category where id=?
            ps = conn.prepareStatement(sql);
            //注入参数
            injectionParameter(params, ps);
            //执行查询,返回结果集
            rs = ps.executeQuery();
            while (rs.next()){
                //获得实体
                T entity = getEntity(rs);
                result.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("执行查询的方法出错",e);
        }finally {
            closeResource(null,ps,rs);
            System.out.println(sql);
        }
        return result;
    }


    public T executeSingleQuery(Connection conn,String sql,Object... params){
        //创建语名集对象,使用它发送sql
        PreparedStatement ps=null;
        ResultSet rs =null;
        try {
            //创建语名集对象,使用它发送sql   sql:select * from news_category where id=?
            ps = conn.prepareStatement(sql);
            //注入参数
            injectionParameter(params, ps);
            //执行查询,返回结果集
            rs = ps.executeQuery();
            if (rs.next()){
                //获得实体
                T entity = getEntity(rs);
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("执行查询出错!",e);
        }finally {
            closeResource(null,ps,rs);
            System.out.println(sql);
        }
        return null;
    }

    /**
     * 注入参数
     * @param params
     * @param ps
     * @throws SQLException
     */
    private void injectionParameter(Object[] params, PreparedStatement ps) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        }
    }

    /**
     * 查询数量
     * @param conn
     * @param sql
     * @param params
     * @return
     */
    public int executeQueryCount(Connection conn,String sql,Object... params){
        //创建语名集对象,使用它发送sql
        PreparedStatement ps=null;
        ResultSet rs =null;
        try {
            //创建语名集对象,使用它发送sql   sql:select * from news_category where id=?
            ps = conn.prepareStatement(sql);
            //注入参数
            injectionParameter(params, ps);
            //执行查询,返回结果集
            rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("执行查询出错!",e);
        }finally {
            closeResource(null,ps,rs);
            System.out.println(sql);
        }
        return -1;
    }

    /**
     * 获得实体
     * 由子类实现
     * @param rs
     * @return
     */
    public abstract T getEntity(ResultSet rs);


    /**
     * 对数据执行增删改
     * @param conn 连接
     * @param sql 执行的sql
     * @param params 参数
     * @return 影响数库的行数
     */
    public int executeUpdate(Connection conn,String sql,Object... params){
        PreparedStatement ps =null;
        try {
            //1.创建语句集对象,用于发送sql
            ps = conn.prepareStatement(sql);
            //2.注入参数,
            injectionParameter(params,ps);
            //3.发送要执行sql
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("对数据执行增删改操作失败",e);
        }finally {
            closeResource(null,ps,null);
            System.out.println(sql);
        }
    }

}
