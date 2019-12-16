package com.zcbdqn.service.impl;

import com.zcbdqn.dao.BaseDao;
import com.zcbdqn.dao.UserDao;
import com.zcbdqn.exception.LoginFailException;
import com.zcbdqn.pojo.User;
import com.zcbdqn.service.UserService;
import com.zcbdqn.util.PageSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User findLoginUser(String userCode, String userPassword) {
        Connection conn = BaseDao.getConnectionByJNDI();
        try {
            User user = userDao.login(userCode, conn);
            if (user!=null && user.getUserPassword().equals(userPassword)){
                return user;
            }
        } finally {
            BaseDao.closeResource(conn,null,null);
        }
        throw new LoginFailException("用户名或密码不正确");
    }

    @Override
    public PageSupport<User> findUsers(String userName, Integer userRole, Integer pageIndex, Integer pageSize) {
        Connection conn = BaseDao.getConnectionByJNDI();
        try {
            PageSupport<User> pageSupport=new PageSupport<>();
            //总记录数
            int totalCount = userDao.count(userName, userRole, conn);
            pageSupport.setTotalCount(totalCount);
            //页面容量
            pageSupport.setPageSize(pageSize);
            //页码
            pageSupport.setPageIndex(pageIndex);
            //数据
            if (totalCount>0){
                List<User> users = userDao.getUsers(userName, userRole, pageSupport.getStartRow(), pageSupport.getPageSize(), conn);
                pageSupport.setList(users);
            }
            return pageSupport;
        } finally {
            BaseDao.closeResource(conn,null,null);
        }
    }

    @Override
    public int addUser(User user) {
        Connection connection = BaseDao.getConnectionByJNDI();
        try {
            connection.setAutoCommit(false);
            int count = userDao.add(user,connection);
            connection.commit();
            connection.setAutoCommit(true);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        throw new RuntimeException("添加用户失败");
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
    }

    @Override
    public User findUserById(Integer id) {
        Connection connection = BaseDao.getConnectionByJNDI();
        try {
            User user = userDao.getUserById(id, connection);
            return user;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据Id获取用户失败");
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
    }

    @Override
    public int updateUser(User user) {
        Connection connection = BaseDao.getConnectionByJNDI();
        try {
            connection.setAutoCommit(false);
            int count = userDao.updateUser(user,connection);
            connection.commit();
            connection.setAutoCommit(true);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("修改用户失败");
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
    }

    @Override
    public int findCountByUserCode(String userCode) {
        Connection connection = BaseDao.getConnectionByJNDI();
        try {
            int countByUserCode = userDao.getCountByUserCode(userCode, connection);
            return countByUserCode;
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
    }

    @Override
    public int deleteUser(Integer id) {
        Connection connection = BaseDao.getConnectionByJNDI();
        try {
            connection.setAutoCommit(false);
            int count = userDao.deleteUser(id,connection);
            connection.commit();
            connection.setAutoCommit(true);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("删除用户失败");
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
    }
}
