package com.muyi.servicestudy.entity.muyi;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
     * 专辑封面
     */
    private String cover;

    /**
     * 专辑发布时间
     */
    private String publishTime;

    /**
     * 语言
     */
    private String lang;

    /**
     * 未知
     */
    private Integer pay;

    /**
     * 人工热度
     */
    private Integer hot;

    /**
     * 点击热度
     */
    private Integer clicks;

    /**
     * 是否上架，1-上架，0-下架
     */
    private Integer isOnline;

    /**
     * 是否付费，0-免费，1-付费
     */
    private Integer isPaid;

    /**
     * 创建时间-添加自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createdAt;

    /**
     * 更新时间-更新添加自动填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updatedAt;

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
}
