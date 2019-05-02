package zhibi.admin.role.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.admin.role.common.base.dto.BaseDomain;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

/**
 * @author 执笔
 */
@Data
@Accessors(chain = true)
public class Menu extends BaseDomain {


    @NotEmpty(message = "菜单名称不能为空")
    private String name;

    @Column
    private MenuTypeStatus type;

    @NotEmpty(message = "菜单URL不能为空")
    private String url;

    @NotEmpty(message = "菜单标识不能为空")
    private String code;

    @NotEmpty(message = "父类ID不能为空")
    private Integer parentId;

    private Integer childNum;

    private Integer sort;

    /**
     * 图标
     * fa fa-dashboard
     */
    private String icon;


    public enum MenuTypeStatus {
        /**
         * 菜单显示
         */
        MENU,
        /**
         * 权限
         */
        AUTH,
        /**
         * 按钮
         */
        BUTTON;
    }

}