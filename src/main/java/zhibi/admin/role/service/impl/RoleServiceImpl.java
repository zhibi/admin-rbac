package zhibi.admin.role.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhibi.admin.role.common.base.service.BaseServiceImpl;
import zhibi.admin.role.common.mybatis.condition.MybatisCondition;
import zhibi.admin.role.domain.Role;
import zhibi.admin.role.mapper.RoleMapper;
import zhibi.admin.role.service.RoleService;

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
        MybatisCondition example = new MybatisCondition()
                .eq("enable", 1);
        return roleMapper.selectByExample(example);
    }
}
