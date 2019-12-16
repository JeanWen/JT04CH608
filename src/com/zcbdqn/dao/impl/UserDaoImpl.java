package com.zcbdqn.dao.impl;

import com.zcbdqn.dao.BaseDao;
import com.zcbdqn.dao.UserDao;
import com.zcbdqn.pojo.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Override
    public User getEntity(ResultSet rs) {
        if (rs!=null){

            try {
                int id = rs.getInt("id");
                int gender = rs.getInt("gender");
                int userRole = rs.getInt("userRole");
                int createdBy = rs.getInt("createdBy");
                int modifyBy = rs.getInt("modifyBy");
                String userCode = rs.getString("userCode");
                String userName = rs.getString("userName");
                String userPassword = rs.getString("userPassword");
                String phone = rs.getString("phone");
                String address = rs.getString("address");

                String userRoleName = rs.getString("userRoleName");
                String idPicPath = rs.getString("idPicPath");
                String workPicPath = rs.getString("workPicPath");

                Timestamp birthdayTimestamp = rs.getTimestamp("birthday");
                Timestamp creationDateTimestamp = rs.getTimestamp("creationDate");
                Timestamp modifyDateTimestamp = rs.getTimestamp("modifyDate");

                Date birthday=birthdayTimestamp==null?null:new Date(birthdayTimestamp.getTime());
                Date creationDate=creationDateTimestamp==null?null:new Date(creationDateTimestamp.getTime());
                Date modifyDate=modifyDateTimestamp==null?null:new Date(modifyDateTimestamp.getTime());

                User user=new User(id,userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate,modifyBy,modifyDate);
                user.setUserRoleName(userRoleName);
                user.setIdPicPath(idPicPath);
                user.setWorkPicPath(workPicPath);
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User login(String userCode, Connection conn) {
        String sql="select su.*,sr.roleName as userRoleName from smbms_user su,smbms_role sr where su.userRole=sr.id and su.userCode=?";
        return super.executeSingleQuery(conn,sql,userCode);
    }

    @Override
    public List<User> getUsers(String userName, Integer userRole, Integer startRow, Integer pageSize, Connection conn) {
        String sql="select su.*,sr.roleName as userRoleName from smbms_user su,smbms_role sr where su.userRole=sr.id ";
        List<Object> params=new ArrayList<>();
        if (userName!=null && !"".equals(userName)){
            sql=sql.concat(" and userName like concat('%',?,'%') ");
            params.add(userName);
        }
        if (userRole!=null && userRole!=0){
            sql=sql.concat(" and userRole =? ");
            params.add(userRole);
        }
        params.add(startRow);
        params.add(pageSize);
        sql=sql.concat(" order by creationDate desc ");
        sql=sql.concat("limit ?,?");
        return super.executeQuery(conn,sql,params.toArray());
    }

    @Override
    public int count(String userName, Integer userRole, Connection conn) {
        String sql="select count(1) from smbms_user where 1=1 ";
        List<Object> params=new ArrayList<>();
        if (userName!=null && !"".equals(userName)){
            sql=sql.concat(" and userName like concat('%',?,'%') ");
            params.add(userName);
        }
        if (userRole!=null && userRole!=0){
            sql=sql.concat(" and userRole =? ");
            params.add(userRole);
        }
        return super.executeQueryCount(conn,sql,params.toArray());
    }

    @Override
    public int add(User user, Connection conn) {
        String sql=" insert into smbms_user (userCode, userName, gender, userPassword, birthday, phone, address, userRole, createdBy, creationDate,idPicPath,workPicPath) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        return super.executeUpdate(conn,sql,user.getUserCode(),user.getUserName(),user.getGender(),user.getUserPassword(),user.getBirthday(),user.getPhone(),user.getAddress(),user.getUserRole(),user.getCreatedBy(),user.getCreationDate(),user.getIdPicPath(),user.getWorkPicPath());
    }

    @Override
    public User getUserById(Integer id, Connection conn) {
        String sql="select su.*,sr.roleName as userRoleName from smbms_user su,smbms_role sr where su.userRole=sr.id and su.id=?";
        return super.executeSingleQuery(conn,sql,id);
    }

    @Override
    public int updateUser(User user, Connection conn) {
        String sql="update smbms_user set userName=?,userPassword=?,birthday=?,gender=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=?,idPicPath=?,workPicPath=? where id=?";
        return super.executeUpdate(conn,sql,user.getUserName(),user.getUserPassword(),user.getBirthday(),user.getGender(),user.getPhone(),user.getAddress(),user.getUserRole(),user.getModifyBy(),user.getModifyDate(),user.getIdPicPath(),user.getWorkPicPath(),user.getId());
    }

    @Override
    public int getCountByUserCode(String userCode, Connection conn) {
        String sql ="select count(1) from smbms_user where userCode=?";
        return super.executeQueryCount(conn,sql,userCode);
    }

    @Override
    public int deleteUser(Integer id, Connection conn) {
        String sql="delete from smbms_user where id =?";
        return super.executeUpdate(conn,sql,id);
    }
}
