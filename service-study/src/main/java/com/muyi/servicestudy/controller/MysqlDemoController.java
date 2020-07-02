package com.muyi.servicestudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muyi.servicestudy.entity.muyi.Album;
import com.muyi.servicestudy.mapper.muyi.AlbumMapper;
import com.muyi.servicestudy.mapper.muyi.MusicMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Muyi,  dcmuyi@qq.com
 * @date 2020-04-17.
 */
@Slf4j
@RequestMapping("/demo/mysql")
@RestController
public class MysqlDemoController {
    @Autowired
    AlbumMapper albumMapper;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public void mybatisPlus() {
        //query list
        List<Album> albums = albumMapper.selectList(new QueryWrapper<Album>()
                .select("album_name, artist_name, artist_id, lang, is_online")
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
        albumMapper.insert(album);

        //update
        albumAdd.setArtistName("test");
        albumMapper.updateById(album);
        log.info("albums :"+ albums);

        //DELETE FROM bm_music WHERE music_id = 14
        albumMapper.delete(new QueryWrapper<Album>().eq("artist_id", 14));
    }
}
