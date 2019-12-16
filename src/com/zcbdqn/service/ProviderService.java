package com.zcbdqn.service;

import com.zcbdqn.pojo.Provider;
import com.zcbdqn.util.PageSupport;

public interface ProviderService {

    /**
     *
     * @param proCode
     * @param proName
     * @param pageIndex 页码  startRow=(pageIndex-1)*pageSize
     * @param pageSize
     * @return
     */
    PageSupport<Provider> findProviders(String proCode, String proName, Integer pageIndex, Integer pageSize);

    /**
     * 添加供应商
     * @param provider
     * @return
     */
    int addProvider(Provider provider);
    /**
     * 根据id查用户
     * @param id
     * @return
     */
    Provider findProviderById(Integer id);

    /**
     * 更改用户信息
     * @param provider
     * @return
     */
    int updateProvider(Provider provider);
}
