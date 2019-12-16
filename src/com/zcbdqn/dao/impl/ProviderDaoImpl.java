package com.zcbdqn.dao.impl;

import com.zcbdqn.dao.BaseDao;
import com.zcbdqn.dao.ProviderDao;
import com.zcbdqn.pojo.Provider;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProviderDaoImpl extends BaseDao<Provider> implements ProviderDao {
    @Override
    public Provider getEntity(ResultSet rs) {
            try {
                if (null!=rs) {
                    Provider provider = new Provider(rs.getInt("id"), rs.getString("proCode"),
                            rs.getString("proName"), rs.getString("proDesc"), rs.getString("proContact"),
                            rs.getString("proPhone"), rs.getString("proFax"), rs.getString("proAddress"),
                            rs.getInt("createdBy"), rs.getInt("modifyBy"), rs.getDate("creationDate"), rs.getDate("modifyDate"));
                    provider.setOrgPicPath(rs.getString("orgPicPath"));
                    provider.setComPicPath(rs.getString("comPicPath"));
                    return provider;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public List<Provider> getProviders(String proCode, String proName, Integer startRow, Integer pageSize, Connection conn) {
        StringBuffer sql= new StringBuffer("select * from smbms_provider where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (null !=proCode && !"".equals(proCode)){
            sql.append(" and proCode like concat('%',?,'%') ");
            params.add(proCode);
        }
        if (null !=proName && !"".equals(proName)){
            sql.append(" and proName like concat('%',?,'%') ");
            params.add(proName);
        }
        params.add(startRow);
        params.add(pageSize);
        sql.append(" order by creationDate desc ");
        sql.append(" limit ?,?");
        return super.executeQuery(conn,sql.toString(),params.toArray());
    }

    @Override
    public int count(String proCode, String proName, Connection conn) {
        StringBuffer sql= new StringBuffer("select count(1) from smbms_provider where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (null !=proCode && !"".equals(proCode)){
            sql.append(" and proCode like concat('%',?,'%') ");
            params.add(proCode);
        }
        if (null !=proName && !"".equals(proName)){
            sql.append(" and proName like concat('%',?,'%') ");
            params.add(proName);
        }
        return super.executeQueryCount(conn,sql.toString(),params.toArray());
    }

    @Override
    public int addProvider(Provider provider, Connection conn) {
        String sql="insert into smbms_provider (proCode,proName,proDesc,proContact,proPhone,proFax,proAddress,createdBy,creationDate,orgPicPath,comPicPath) values(?,?,?,?,?,?,?,?,?,?,?)";
        return super.executeUpdate(conn,sql,provider.getProCode(),provider.getProName(),provider.getProDesc(),provider.getProContact(),provider.getProPhone(),provider.getProFax(),provider.getProAddress(),provider.getCreatedBy(),provider.getCreationDate(),provider.getOrgPicPath(),provider.getComPicPath());
    }

    @Override
    public Provider getProviderById(Integer id, Connection conn) {
        String sql ="select * from smbms_provider where id=?";
        return super.executeSingleQuery(conn,sql,id);
    }

    @Override
    public int updateProvider(Provider provider, Connection conn) {
        String sql="update smbms_provider set proCode=?,proName=?,proContact=?,proPhone=?,proAddress=?,proFax=?,proDesc=?,orgPicPath=?,comPicPath=? where id=?";
        return super.executeUpdate(conn,sql,provider.getProCode(),provider.getProName(),provider.getProContact(),provider.getProPhone(),provider.getProAddress(),provider.getProFax(),provider.getProDesc(),provider.getOrgPicPath(),provider.getComPicPath(),provider.getId());
    }
}
