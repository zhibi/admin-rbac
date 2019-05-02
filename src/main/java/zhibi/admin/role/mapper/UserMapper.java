package zhibi.admin.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import zhibi.admin.role.common.CustomerMapper;
import zhibi.admin.role.domain.User;

/**
 * @author 执笔
 */
@Mapper
public interface UserMapper extends CustomerMapper<User> {
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
    void deleteById(String id);
}
