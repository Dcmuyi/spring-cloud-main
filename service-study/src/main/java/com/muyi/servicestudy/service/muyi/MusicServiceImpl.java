package com.muyi.servicestudy.service.muyi;

import com.muyi.servicestudy.entity.muyi.Music;
import com.muyi.servicestudy.mapper.muyi.MusicMapper;
import com.muyi.servicestudy.service.muyi.IMusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Muyi, dcmuyi@qq.com
 * @since 2020-06-23
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements IMusicService {

}
