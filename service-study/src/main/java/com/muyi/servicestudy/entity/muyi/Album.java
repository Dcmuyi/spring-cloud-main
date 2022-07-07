package com.muyi.servicestudy.entity.muyi;

import java.io.Serializable;
import java.util.function.Function;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.muyi.servicestudy.annotation.QueryField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Muyi, dcmuyi@qq.com
 * @since 2020-06-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 源id
     */
    @TableField
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 专辑名称
     */
    private String albumName;

    /**
     * 艺术家名称
     */
    @QueryField(name = "artist_name")
    private String artistName;

    /**
     * 艺术家id
     */
    @QueryField(name = "artist_id", must = true)
    private Integer artistId;

    /**
     * 是否上架，1-上架，0-下架
     */
    private Integer isOnline;

    /**
     * 创建时间-添加自动填充
     */
    private Long createdAt;

    /**
     * 更新时间-更新添加自动填充
     */
    private Long updatedAt;

    /**
     * 标记不参与数据库操作
     */
    @TableField(exist = false)
    private Integer NotInMysql;

    /**
     * 自定义get方法
     * @return
     */
    public Integer getNotInMysql() {
        return this.artistId;
    }

    public String getHello(Function<String,String> function) {
        return function.apply(albumName);
    }
}
