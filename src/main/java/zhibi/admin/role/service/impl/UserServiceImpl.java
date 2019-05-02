package zhibi.admin.role.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhibi.admin.role.common.base.service.BaseServiceImpl;
import zhibi.admin.role.common.exception.MessageException;
import zhibi.admin.role.common.utils.PasswordUtils;
import zhibi.admin.role.domain.User;
import zhibi.admin.role.domain.UserRole;
import zhibi.admin.role.mapper.UserMapper;
import zhibi.admin.role.mapper.UserRoleMapper;
import zhibi.admin.role.service.UserService;

/**
 * @author 执笔
 * @date 2019/5/1 22:57
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper     userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrSaveUser(User user, String[] roleIds) {
        if (user.getId() == null) {
            boolean exist = isExist(new User().setUsername(user.getUsername()));
            if (exist) {
                throw new MessageException("用户名已存在");
            }
            if (StringUtils.isEmpty(user.getPassword())) {
                throw new MessageException("密码不能为空");
            }
            String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
            user.setSalt(salt);
            String password = PasswordUtils.createPwd(user.getPassword(), salt);
            user.setPassword(password);
            user.setType(User.UserTypeEnum.USER);
            userMapper.insertSelective(user);
        } else {
            User updateUser = userMapper.selectByPrimaryKey(user.getId());
            if (null != updateUser) {
                if (!StringUtils.isEmpty(user.getPassword())) {
                    String password = PasswordUtils.createPwd(user.getPassword(), updateUser.getSalt());
                    user.setPassword(password);
                }
                userMapper.insertSelective(user);
            } else {
                throw new MessageException("操作失败，用户不存在");
            }
        }
        userRoleMapper.delete(new UserRole().setUserId(user.getId()));
        if (roleIds != null) {
            userRoleMapper.delete(new UserRole().setUserId(user.getId()));
            for (String roleId : roleIds) {
                UserRole userRole = new UserRole()
                        .setUserId(user.getId())
                        .setRoleId(Integer.parseInt(roleId));
                userRoleMapper.insertSelective(userRole);
            }
        }
    }
}
