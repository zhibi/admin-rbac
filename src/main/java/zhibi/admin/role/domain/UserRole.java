package zhibi.admin.role.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.fast.mybatis.dto.BaseDomain;


import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author 执笔
 */
@Table(name = "user_role")
@Data
@Accessors(chain = true)
public class UserRole extends BaseDomain {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;
}