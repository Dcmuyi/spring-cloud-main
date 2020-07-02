package com.muyi.servicestudy.entity.muyi;

import java.io.Serializable;
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
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 歌曲编号
     */
    private Integer id;

    /**
     * 歌曲名称
     */
    private String musicName;

    /**
     * 歌曲别名
     */
    private String alias;

    /**
     * 单曲封面
     */
    private String cover;

    /**
     * 歌曲时长，单位为秒
     */
    private Integer duration;

    /**
     * 歌词文件路径
     */
    private String lyric;

    /**
     * 发行时间
     */
    private String issueDate;

    /**
     * 专辑ID
     */
    private Integer albumId;

    /**
     * 专辑名
     */
    private String albumName;

    /**
     * 歌手ID
     */
    private Integer artistId;

    /**
     * 歌手名
     */
    private String artistName;

    /**
     * 是否上架，1-上架，0-下架
     */
    private Integer isOnline;

    /**
     * 是否付费，0-免费，1-付费
     */
    private Integer isPaid;

    /**
     * 人工热度
     */
    private Integer hot;

    /**
     * 是否无损
     */
    private Integer lossLess;

    /**
     * 是否有mv
     */
    private Integer hasMv;

    /**
     * 未知
     */
    private Integer pay;

    /**
     * 创建时间
     */
    private Integer createdAt;

    /**
     * 更新时间
     */
    private Integer updatedAt;


}
