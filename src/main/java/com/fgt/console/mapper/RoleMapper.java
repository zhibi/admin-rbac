package com.fgt.console.mapper;

import com.fgt.common.core.CustomerMapper;
import com.fgt.console.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author 执笔
 */
@Service
public interface RoleMapper extends CustomerMapper<Role> {
    /**
     * 查找用户的角色
     * @param userId
     * @return
     */
    Set<String> findRoleByUserId(String userId);

    /**
     * 根据ID获取角色
     * @param id
     * @return
     */
    List<Role> selectRoleListByAdminId(String id);
}
