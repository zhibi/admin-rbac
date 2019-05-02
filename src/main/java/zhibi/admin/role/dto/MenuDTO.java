package zhibi.admin.role.dto;

import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import zhibi.admin.role.domain.Menu;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author 执笔
 * @date 2019/5/1 22:15
 */
@Data
public class MenuDTO extends Menu {

    /**
     * 子菜单
     */
    List<MenuDTO> children;

    public MenuDTO(Menu menu) {
        try {
            BeanUtils.copyProperties(this, menu);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
