package com.muyi.servicestudy.controller;

import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;

/**
 * @author Muyi, dcmuyi@qq.com
 * @date 2019-04-17.
 */
@Slf4j
@RestController
@RequestMapping("/demo/es")
public class EsDemoController {
    private String index = "radio";
    private String type = "radio";

    @Autowired
    private TransportClient client;

    //添加索引
    @RequestMapping("/add-index")
    public boolean addIndex() {
        try {
            //title:字段名，  type:文本类型       analyzer ：分词器类型
            XContentBuilder mapping = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("title").field("type", "text").field("analyzer", "ik_smart").endObject()
                    .startObject("content").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .startObject("status").field("type", "integer").endObject()
                    .endObject()
                    .endObject();

            //索引设置
            Settings settings = Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 0)
                    .build();

            client.admin().indices().prepareAnalyze();
            //为索引添加映射
            CreateIndexRequestBuilder cid = client.admin().indices().prepareCreate(index).setSettings(settings);
            cid.addMapping(index, mapping);
            CreateIndexResponse res = cid.execute().actionGet();
            log.info("res =="+ JSONObject.toJSONString(res));
        } catch (IOException e) {
            log.info("error :" +JSONObject.toJSONString(e));
        }

        return true;
    }

    @RequestMapping("/add-doc")
    public long addDocument() throws IOException {
        // 创建文档,相当于往表里面insert一行数据
        XContentBuilder source = jsonBuilder().startObject()// 开始
                .field("title", "最小的不是你的窝")// 商品名称
                .field("goodsPrice", 22.34)// 商品价格
                .field("content", "这只竹鼠肯定中暑了,我们把它送走.")// 商品主人
                .field("buyUser", "大拿2")// 商品主人
                .field("status", 1)
                .field("goodsTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))// 商品上架时间
                .endObject();
        log.info("so:"+source.toString());
        IndexResponse response = client.prepareIndex(index, type)
                .setSource(source)
                .get();

        log.info("res:"+response.toString());
        return response.getVersion();
    }

    public boolean deleteIndex() {
        return isIndexExist()
                ? client.admin().indices().prepareDelete(index).execute().actionGet().isAcknowledged()
                : false;
    }

    public boolean isIndexExist() {
        return client.admin().indices().prepareExists(index).execute().actionGet().isExists();
    }

    public void query() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //matchQuery:单字段匹配关键字(分词)
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "最小的家"));
        //multiMatchQuery:多字段匹配关键字(分词)
        boolQueryBuilder.must(QueryBuilders.multiMatchQuery("最小的家", "title", "content"));
        //termQuery关键字－查询
        boolQueryBuilder.must(QueryBuilders.termQuery("title", "最小的家"));


        //主过滤条件
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        filterQueryBuilder.must(QueryBuilders.termQuery("goodsPrice",1));

        //or1
        BoolQueryBuilder freeMusicFilterQueryBuilder = QueryBuilders.boolQuery();
        freeMusicFilterQueryBuilder.must(QueryBuilders.termQuery("status", 1));
        freeMusicFilterQueryBuilder.must(QueryBuilders.termQuery("buyUser", "test"));

        //or2
        BoolQueryBuilder allMusicFilterQueryBuilder = QueryBuilders.boolQuery();
        allMusicFilterQueryBuilder.must(QueryBuilders.termQuery("status", 2));

        //组合：goodsPrice=1 and ( (status=1 and buyUser=test) or status = 2)
        BoolQueryBuilder paidMusicQuery = QueryBuilders.boolQuery();
        paidMusicQuery.should(freeMusicFilterQueryBuilder).should(allMusicFilterQueryBuilder);
        filterQueryBuilder.must(paidMusicQuery);

        SearchResponse response = client.prepareSearch("muyi").setTypes("muyi")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQueryBuilder)
                .setPostFilter(filterQueryBuilder)
                //排序
                .addSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                //分页数据
                .setFrom(0).setSize(10)
                .get();
        log.info("res:"+response.toString());
        log.info("收获查询出来的文档数量为:" + response.getHits().getTotalHits());
    }

    public void queryById(String index) {
        GetResponse te = client.prepareGet(index, index, "F9Uva2wBSgYbJBA7eMCL").get();
        log.info("result by id"+te);
    }



    /**
     * 添加type---6.*版本已经移除type
     */
    public boolean addIndexAndType(String index, String type) throws IOException {
        // 创建索引映射,相当于创建数据库中的表操作
        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(index);
        XContentBuilder mapping = jsonBuilder().startObject().startObject("properties") // 设置自定义字段
                .startObject("goodsName").field("type", "string").endObject() // 商品名称
                .startObject("goodsPrice").field("type", "double").endObject()// 商品价格
                .startObject("goodsUser").field("type", "string").endObject()// 商品主人
                .startObject("goodsTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss").endObject() // 商品上架时间
                .endObject().endObject();
        cib.addMapping(type, mapping);
        return cib.execute().actionGet().isAcknowledged();
    }

    public boolean isTypeExist() {
        return isIndexExist()
                ? client.admin().indices().prepareTypesExists(index).setTypes(type).execute().actionGet().isExists()
                : false;
    }



}
