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
import org.apache.lucene.queryparser.classic.QueryParserBase;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
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
            // 基础查询条件方法
            buildBasicQuery(params, request);
            // 分页
            int page = params.getPage();
            int size = params.getSize();
            request.source().from((page - 1) * size).size(size);
            // 排序
            String location = params.getLocation();
            if (location != null && !location.equals("")) {
                request.source().sort(
                    SortBuilders
                    .geoDistanceSort("location", new GeoPoint(location))
                    .order(SortOrder.ASC)
                    .unit(DistanceUnit.KILOMETERS)
                );
            }
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
     * 公共方法：基础查询条件封装
     * @param params
     * @param request
     */
    private void buildBasicQuery(RequestParams params, SearchRequest request) {
        // 构建组合查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 关键字搜索
        String key = params.getKey();
        if (key == null || "".equals(key)) {
            // request.source().query(QueryBuilders.matchAllQuery());
            boolQuery.must(QueryBuilders.matchAllQuery());
        }else{
            // request.source().query(QueryBuilders.matchQuery("all", key));
            boolQuery.must(QueryBuilders.matchQuery("all", key));
        }
        // 条件过滤：
        // 城市条件：
        if(params.getCity() != null && !params.getCity().equals("")){
            boolQuery.filter(QueryBuilders.termQuery("city", params.getCity()));
        }
        // 品牌条件：
        if(params.getBrand() != null && !params.getBrand().equals("")){
            boolQuery.filter(QueryBuilders.termQuery("brand", params.getBrand()));
        }
        // 星级条件：
        if(params.getStarName() != null && !params.getStarName().equals("")){
            boolQuery.filter(QueryBuilders.termQuery("starName", params.getStarName()));
        }
        // 价格条件：
        if(params.getMinPrice() != null && params.getMaxPrice() != null){
            boolQuery.filter(
                QueryBuilders
                .rangeQuery("price")
                .gte(params.getMinPrice())
                .lte(params.getMaxPrice())
            );
        }
        // 算分控制：function score给isAD（广告字段）添加权重。
        FunctionScoreQueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(
                // 原始查询，做相关性算分的查询。
                boolQuery,
                // function score的数组
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                        // 其中的一个Function Score元素
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                // 过滤条件
                                QueryBuilders.termQuery("isAD", true),
                                // 算分函数
                                ScoreFunctionBuilders.weightFactorFunction(10)
                        )
                }
        );
        // 执行查询
        request.source().query(functionScoreQuery);
    }


    /**
     * 公共方法：解析的相关代码封装（选中需要提取的代码片段，快捷键为：option+command+m）
     * @param response
     */
    private PageResult extractedResolution(SearchResponse response) {
        // 解析响应
        SearchHits searchHits = response.getHits();
        // 获取总条数
        long total = searchHits.getTotalHits().value;
        // 获取文档数组
        SearchHit[] hits = searchHits.getHits();
        // 准备hotels集合
        List<HotelDoc> hotels = new ArrayList<>();
        for (SearchHit hit : hits) {
            // 获取文档
            String json = hit.getSourceAsString();
            // 反序列化
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            // 获取排序值
            Object[] sortValues = hit.getSortValues();
            if(sortValues.length > 0) {
                Object sortValue = sortValues[0];
                hotelDoc.setDistance(sortValue);
            }
            hotels.add(hotelDoc);
        }
        // 封装返回对象
        return new PageResult(total, hotels);
    }

}
