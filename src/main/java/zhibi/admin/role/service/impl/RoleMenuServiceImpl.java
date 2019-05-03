package zhibi.admin.role.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import zhibi.admin.role.domain.RoleMenu;
import zhibi.admin.role.mapper.RoleMenuMapper;
import zhibi.admin.role.service.RoleMenuService;
import zhibi.fast.mybatis.service.impl.BaseServiceImpl;

import java.util.List;

/**
 * @author 执笔
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper,RoleMenu> implements RoleMenuService {}
