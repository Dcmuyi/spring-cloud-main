package com.muyi.servicestudy.service.muyi;

import com.muyi.servicestudy.entity.muyi.Album;
import com.muyi.servicestudy.entity.response.PageData;
import com.muyi.servicestudy.mapper.muyi.AlbumMapper;
import com.muyi.servicestudy.service.BaseService;
import com.muyi.servicestudy.service.muyi.IAlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Muyi, dcmuyi@qq.com
 * @since 2020-06-23
 */
@Service
public class AlbumServiceImpl extends BaseService<AlbumMapper, Album> implements IAlbumService {
    public PageData<Album> getList(HttpServletRequest request) {
        return getList(Album.class, request);
    }
}
