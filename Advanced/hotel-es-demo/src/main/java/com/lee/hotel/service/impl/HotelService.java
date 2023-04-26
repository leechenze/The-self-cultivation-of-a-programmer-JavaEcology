package com.lee.hotel.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lee.hotel.mapper.HotelMapper;
import com.lee.hotel.pojo.Hotel;
import com.lee.hotel.pojo.HotelDoc;
import com.lee.hotel.pojo.PageResult;
import com.lee.hotel.pojo.RequestParams;
import com.lee.hotel.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.bind.v2.TODO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public PageResult search(RequestParams params) {
        try {
            // 1.准备Request
            SearchRequest request = new SearchRequest("hotel");
            // 2.准备DSL
            // 2.1.关键字搜索
            String key = params.getKey();
            if (key == null || "".equals(key)) {
                request.source().query(QueryBuilders.matchAllQuery());
            }else{
                request.source().query(QueryBuilders.matchQuery("all", key));
            }
            // 2.2.分页
            int page = params.getPage();
            int size = params.getSize();
            request.source().from((page - 1) * size).size(size);
            // 3.发送请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println(response);
            // 4，5，6（解析相关操作，抽取为了公共方法）
            return extractedResolution(response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 公共方法：解析的相关代码封装（选中需要提取的代码片段，快捷键为：option+command+m）
     * @param response
     */
    private PageResult extractedResolution(SearchResponse response) {
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 5.获取总条数
        long total = searchHits.getTotalHits().value;
        // 6.获取文档数组
        SearchHit[] hits = searchHits.getHits();
        // 7.准备hotels集合
        List<HotelDoc> hotels = new ArrayList<>();
        for (SearchHit hit : hits) {
            // 获取文档
            String json = hit.getSourceAsString();
            // 反序列化
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            hotels.add(hotelDoc);
        }
        // 8.封装返回对象
        return new PageResult(total, hotels);
    }

}
