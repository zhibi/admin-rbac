package zhibi.admin.role.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.admin.role.common.base.dto.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author 执笔
 */
@Table(name = "admin_role")
@Data
@Accessors(chain = true)
public class UserRole extends BaseDomain {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "role_id")
    private Integer roleId;
}