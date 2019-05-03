package zhibi.admin.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import zhibi.admin.role.domain.Role;
import zhibi.fast.mybatis.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * @author 执笔
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查找用户的角色
     *
     * @param userId
     * @return
     */
    Set<String> selectRoleByUserId(Long userId);

    /**
     * 根据用户ID获取角色
     *
     * @param userId
     * @return
     */
    List<Role> selectRoleListByUserId(Long userId);

    /**
     * 删除角色
     *
     * @param roleId
     */
    void deleteById(Long roleId);
}
