package zhibi.admin.role.domain;

import lombok.Data;
import zhibi.admin.role.common.base.dto.BaseDomain;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 执笔
 */
@Data
public class Role extends BaseDomain {

    @NotEmpty(message = "角色名称不能为空")
    private String name;

    /**
     * 是否可用
     */
    private Boolean enable;


    @Transient
    private List<Menu> menuList;

}
