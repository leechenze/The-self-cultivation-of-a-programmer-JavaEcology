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
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.xcontent.XContentType;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, List<String>> filters(RequestParams params) {
        try {
            // 准备Request
            SearchRequest request = new SearchRequest("hotel");
            // 准备DSL
            // 基础查询条件方法，用来限制聚合的范围
            buildBasicQuery(params, request);
            // 设置size，表示不要文档结果，只要聚合结果
            request.source().size(0);
            // 聚合
            request.source().aggregation(
                    AggregationBuilders
                            .terms("brandAgg")
                            .field("brand")
                            .size(10)
            );
            request.source().aggregation(
                    AggregationBuilders
                            .terms("cityAgg")
                            .field("city")
                            .size(10)
            );
            request.source().aggregation(
                    AggregationBuilders
                            .terms("starNameAgg")
                            .field("starName")
                            .size(10)
            );
            // 发出请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // 定义Map
            Map<String, List<String>> result = new HashMap<>();
            // 解析结果
            Aggregations aggregations = response.getAggregations();
            // 根据名称获取品牌的结果
            List<String> brandList = getAggByName(aggregations, "brandAgg");
            // 将聚合结果put到Map中
            result.put("品牌", brandList);
            // 城市和星级亦同（复制粘贴）
            List<String> cityList = getAggByName(aggregations, "cityAgg");
            result.put("城市", cityList);
            List<String> starNameList = getAggByName(aggregations, "starNameAgg");
            result.put("星级", starNameList);
            // 结果返回
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getSuggestions(String prefix) {
        try {
            // 准备Request
            SearchRequest request = new SearchRequest("hotel");
            // 准备DSL（最好对照控制台 # ==========查询suggest========== 写RestAPI代码）
            request.source().suggest(new SuggestBuilder().addSuggestion(
                    "suggestions",
                    SuggestBuilders.completionSuggestion("suggestion")
                            .prefix(prefix)
                            .skipDuplicates(true)
                            .size(10)
            ));
            // 发起请求
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            // System.out.println(response);
            // 解析结果
            Suggest suggest = response.getSuggest();
            // 根据补全查询名称，获取补全结果
            CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
            // 获取options
            List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
            // 准备List (长度即使options的长度)
            List<String> list = new ArrayList<>(options.size());
            // 遍历
            for (CompletionSuggestion.Entry.Option option : options) {
                String text = option.getText().toString();
                list.add(text);
            }
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertById(Long id) {
        try {
            // 根据ID查询酒店数据
            Hotel hotel = getById(id);
            // 转换为文档类型
            HotelDoc hotelDoc = new HotelDoc(hotel);
            // 准备Request对象
            IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
            // 准备Json DSL文档
            request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
            // 发送请求
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            // 准备Request对象
            DeleteRequest request = new DeleteRequest("hotel", String.valueOf(id));
            // 发送请求
            client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private List<String> getAggByName(Aggregations aggregations, String aggName) {
        // 根据聚合名称获取聚合结果
        Terms aggregation = aggregations.get(aggName);
        // 获取buckets
        List<? extends Terms.Bucket> buckets = aggregation.getBuckets();
        // 定义List
        List<String> brandList = new ArrayList<>();
        // 遍历
        for (Terms.Bucket bucket : buckets) {
            // 获取key
            String key = bucket.getKeyAsString();
            brandList.add(key);
        }
        return brandList;
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
