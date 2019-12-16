package com.zcbdqn.service.impl;

import com.zcbdqn.dao.BaseDao;
import com.zcbdqn.dao.ProviderDao;
import com.zcbdqn.exception.LoginFailException;
import com.zcbdqn.pojo.Provider;
import com.zcbdqn.service.ProviderService;
import com.zcbdqn.util.PageSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Resource
    private ProviderDao providerDao;

    @Override
    public PageSupport<Provider> findProviders(String proCode, String proName, Integer pageIndex, Integer pageSize) {
        Connection conn = BaseDao.getConnectionByJNDI();
        PageSupport<Provider> pageSupport;
        try {
            pageSupport = new PageSupport<>();
            //总记录数
            int totalCount = providerDao.count(proCode, proName, conn);
            pageSupport.setTotalCount(totalCount);
            //页面容量
            pageSupport.setPageSize(pageSize);
            //页码
            pageSupport.setPageIndex(pageIndex);
            //数据
            if (totalCount>0){
                List<Provider> providers = providerDao.getProviders(proCode, proName, pageSupport.getStartRow(), pageSupport.getPageSize(), conn);
                pageSupport.setList(providers);
            }
        } finally {
            BaseDao.closeResource(conn,null,null);
        }

        return pageSupport;
    }

    @Override
    public int addProvider(Provider provider) {
        Connection connection = BaseDao.getConnectionByJNDI();
        try {
            connection.setAutoCommit(false);
            int count = providerDao.addProvider(provider,connection);
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
            throw new LoginFailException("添加供应商失败");
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
    }

    @Override
    public Provider findProviderById(Integer id) {
        Connection connection = BaseDao.getConnectionByJNDI();
        try {
            Provider provider = providerDao.getProviderById(id, connection);
            return provider;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("根据Id获取供应商失败");
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
    }

    @Override
    public int updateProvider(Provider provider) {
        Connection connection = BaseDao.getConnectionByJNDI();
        try {
            connection.setAutoCommit(false);
            int count = providerDao.updateProvider(provider,connection);
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
            throw new RuntimeException("修改供应商失败");
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
    }
}
