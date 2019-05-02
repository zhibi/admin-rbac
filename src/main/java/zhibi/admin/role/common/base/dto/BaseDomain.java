package zhibi.admin.role.common.base.dto;

import lombok.Getter;
import lombok.Setter;
import zhibi.admin.role.common.mybatis.annotation.AutoTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * model基础信息
 *
 * @author 执笔
 */
@Getter
@Setter
public class BaseDomain {

    /**
     * 数据库表三要素
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer       id;
    @AutoTime(insert = true, update = false)
    private LocalDateTime createTime;
    @AutoTime(insert = true, update = true)
    private LocalDateTime updateTime;


    @Transient
    private Integer offset = 0;

    @Transient
    private Integer limit = 10;

    @Transient
    private Integer pageNum  = 0;
    @Transient
    private Integer pageSize = 10;

}
