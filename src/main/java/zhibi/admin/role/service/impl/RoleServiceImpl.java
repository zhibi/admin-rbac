package zhibi.admin.role.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import zhibi.admin.role.domain.Role;
import zhibi.admin.role.mapper.RoleMapper;
import zhibi.admin.role.service.RoleService;
import zhibi.fast.mybatis.example.MybatisExample;
import zhibi.fast.mybatis.service.impl.BaseServiceImpl;

import java.util.List;

/**
 * @author 执笔
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllEnable() {
        MybatisExample example = new MybatisExample()
                .eq("enable", 1);
        return roleMapper.selectByExample(example);
    }
}
