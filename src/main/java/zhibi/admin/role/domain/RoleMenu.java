package zhibi.admin.role.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.fast.mybatis.dto.BaseDomain;


import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author 执笔
 */
@Data
@Table(name = "role_menu")
@Accessors(chain = true)
public class RoleMenu extends BaseDomain {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "menu_id")
    private Long menuId;

}