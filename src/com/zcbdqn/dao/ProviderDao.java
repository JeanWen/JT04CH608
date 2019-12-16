package com.zcbdqn.dao;

import com.zcbdqn.pojo.Provider;

import java.sql.Connection;
import java.util.List;

public interface ProviderDao {
    /**
     * 分页查询供应商
     * @param proCode
     * @param proName
     * @param startRow
     * @param pageSize
     * @param conn
     * @return
     */
    List<Provider> getProviders(String proCode, String proName, Integer startRow, Integer pageSize, Connection conn);

    /**
     * 供应商总数
     * @param proCode
     * @param proName
     * @param conn
     * @return
     */
    int count(String proCode,String proName, Connection conn);

    /**
     * 添加供应商
     * @param provider
     * @param conn
     * @return
     */
    int addProvider(Provider provider, Connection conn);

    /**
     * 根据id获取供应商
     * @param id
     * @param conn
     * @return
     */
    Provider getProviderById(Integer id, Connection conn);

    /**
     * 修改供应商
     * @param provider
     * @param conn
     * @return
     */
    int updateProvider(Provider provider, Connection conn);
}
