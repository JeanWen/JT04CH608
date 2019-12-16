package com.zcbdqn.service;

import com.zcbdqn.pojo.User;
import com.zcbdqn.util.PageSupport;

public interface UserService {

    User findLoginUser(String userCode,String userPassword);

    /**
     *
     * @param userName
     * @param userRole
     * @param pageIndex 页码  startRow=(pageIndex-1)*pageSize
     * @param pageSize
     * @return
     */
    PageSupport<User> findUsers(String userName,Integer userRole,Integer pageIndex,Integer pageSize);

    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据id查用户
     * @param id
     * @return
     */
    User findUserById(Integer id);

    /**
     * 更改用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    int findCountByUserCode(String userCode);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    int deleteUser(Integer id);
}
