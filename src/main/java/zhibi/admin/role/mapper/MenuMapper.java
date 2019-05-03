package zhibi.admin.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import zhibi.fast.mybatis.mapper.BaseMapper;
import zhibi.admin.role.domain.Menu;
import zhibi.fast.mybatis.mapper.BaseMapper;

import java.util.List;
import java.util.Set;

/**
 * @author 执笔
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取某一个用户的菜单编码
     *
     * @param userId
     * @return
     */
    Set<String> selectMenuCodeByUserId(Long userId);

    /**
     * 获取所有菜单编码
     *
     * @return
     */
    Set<String> selectALLMenuCode();

    /**
     * 根据用户ID获取菜单
     *
     * @param userId
     * @return
     */
    List<Menu> selectMenuByUserId(Long userId);

    /**
     * 获取所有菜单
     *
     * @return
     */
    List<Menu> selectAllMenu();

    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return
     */
    List<Menu> selectMenuByRoleId(Long roleId);

    /**
     * 删除菜单
     * @param id
     */
    void deleteById(Long id);
}