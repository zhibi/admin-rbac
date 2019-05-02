package zhibi.admin.role.mapper;

import org.apache.ibatis.annotations.Mapper;
import zhibi.admin.role.common.CustomerMapper;
import zhibi.admin.role.domain.Log;

/**
 * @author 执笔
 */
@Mapper
public interface LogMapper extends CustomerMapper<Log> {
}
