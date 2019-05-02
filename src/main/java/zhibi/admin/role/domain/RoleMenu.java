package zhibi.admin.role.domain;

import lombok.Data;
import zhibi.admin.role.common.base.dto.BaseDomain;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author 执笔
 */
@Data
@Table(name = "role_menu")
public class RoleMenu extends BaseDomain {
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "menu_id")
    private Integer menuId;

}