package com.zcbdqn.dao;

import com.zcbdqn.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserDao {

    User login(String userCode, Connection conn);

    /**
     * 分页查询
     * @param userName
     * @param userRole
     * @param startRow 起始行  limit 0,5
     * @param pageSize
     * @param conn
     * @return
     */
    List<User> getUsers(String userName,Integer userRole,Integer startRow,Integer pageSize,Connection conn);

    /**
     *
     * @param userName
     * @param userRole
     * @param conn
     * @return
     */
    int count(String userName,Integer userRole, Connection conn);

    int add(User user, Connection conn);

    /**
     * 根据id查用户
     * @param id
     * @param conn
     * @return
     */
    User getUserById(Integer id, Connection conn);

    /**
     * 修改用户
     * @param user
     * @param conn
     * @return
     */
    int updateUser(User user, Connection conn);

    int getCountByUserCode(String userCode, Connection conn);
    int deleteUser(Integer id,Connection conn);
}
