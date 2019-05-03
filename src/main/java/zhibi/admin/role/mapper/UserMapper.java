package zhibi.admin.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import zhibi.admin.role.domain.User;
import zhibi.fast.mybatis.mapper.BaseMapper;

/**
 * @author 执笔
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    User selectByUsername(String userName);

    /**
     * 根据ID删除
     *
     * @param id
     */
    void deleteById(Long id);
}
