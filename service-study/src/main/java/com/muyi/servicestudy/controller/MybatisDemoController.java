package com.muyi.servicestudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muyi.servicestudy.entity.muyi.Album;
import com.muyi.servicestudy.mapper.muyi.AlbumMapper;
import com.muyi.servicestudy.service.muyi.AlbumServiceImpl;
import com.muyi.servicestudy.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2020-04-17.
 */
@Slf4j
@RequestMapping("/demo/mysql")
@RestController
public class MybatisDemoController {
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    private AlbumServiceImpl albumService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public void mybatisPlus() {
        //query list
        List<Album> albums = albumMapper.selectList(new QueryWrapper<Album>()
                .select("album_name, artist_name, artist_id")
                .lt("artist_id", 1)
                .gt("id", 100)
                .eq("is_online", 1)
                .last("limit 10"));

        //query one
        Album album = albumMapper.selectOne(new QueryWrapper<Album>()
                .eq("artist_id", 10)
                .last("limit 1"));
//        Album album1 = albumMapper.selectById("10");

        //add
        Album albumAdd = new Album();
        albumAdd.setAlbumName("test");
        albumService.save(albumAdd);

        try {
            Thread.sleep(2000);
        } catch (Exception e) {}

        //update
        albumAdd.setArtistName("test");
        albumService.updateById(albumAdd);
        log.info("albums :"+ albums);

        //DELETE FROM bm_music WHERE artist_id = 14
        albumService.remove(new QueryWrapper<Album>().eq("artist_id", 14));
    }

    /**
     * 根据注解和反射实现的通用查询方法
     * @param request
     * @return
     */
    @GetMapping("/list")
    public Result list(HttpServletRequest request) {
        return Result.wrapSuccessfulResult(albumService.getList(Album.class, request));
    }
}
