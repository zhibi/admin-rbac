package com.fgt.console.mapper;

import com.fgt.console.domain.Admin;
import com.fgt.common.core.CustomerMapper;
import org.springframework.stereotype.Service;

/**
 * @author 执笔
 */
@Service
public interface AdminMapper extends CustomerMapper<Admin> {
    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    Admin selectByUsername(String userName);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}
