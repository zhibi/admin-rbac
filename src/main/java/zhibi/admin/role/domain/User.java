package zhibi.admin.role.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import zhibi.admin.role.common.base.dto.BaseDomain;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author 执笔
 */
@Data
@Accessors(chain = true)
public class User extends BaseDomain implements Serializable {

    @NotEmpty(message = "账号不能为空")
    private String username;

    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 用户状态
     */
    private UserStateEnum state;

    private UserTypeEnum type;

    public enum UserStateEnum {

        /**
         * 激活
         */
        ACTIVATION,
        /**
         * 锁定
         */
        LOCKING;
    }

    public enum UserTypeEnum {

        /**
         * 管理员
         */
        ADMIN,
        /**
         * 用户
         */
        USER;
    }


    @Transient
    private List<Role> roleList;
}