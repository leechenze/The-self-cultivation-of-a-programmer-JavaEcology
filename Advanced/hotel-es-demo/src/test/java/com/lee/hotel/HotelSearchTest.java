package com.lee.hotel;

import com.alibaba.fastjson2.JSON;
import com.lee.hotel.pojo.HotelDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggester;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.lee.hotel.constants.HotelIndexConstants.MAPPING_TEMPLATE;

@SpringBootTest
class HotelSearchTest {

    private RestHighLevelClient client;

    @Test
    void testMatchAll() throws IOException {
        // 1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        // 2.准备DSL
        request.source().query(QueryBuilders.matchAllQuery());
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        // 4，5，6（解析相关操作，抽取为了公共方法）
        extractedResolution(response);
    }

    @Test
    void testMatch() throws IOException {
        // 1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        // 2.准备DSL
        // 2.1.但字段查询
        // request.source().query(QueryBuilders.matchQuery("all", "如家"));
        // 2.2.多字段查询
        request.source().query(QueryBuilders.multiMatchQuery("如家", "name", "business"));
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        // 4，5，6（解析相关操作，抽取为了公共方法）
        extractedResolution(response);
    }


    @Test
    void testBool() throws IOException {
        // 1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        // 2.准备DSL
        // 2.1.准备BoolQuery
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 2.2.添加term条件
        boolQuery.must(QueryBuilders.termQuery("city", "上海"));
        // 2.2.添加range条件
        boolQuery.filter(QueryBuilders.rangeQuery("price").lte(350));
        // 2.3.将DSL查询传入到source的query中。
        request.source().query(boolQuery);
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        // 4，5，6（解析相关操作，抽取为了公共方法）
        extractedResolution(response);
    }

    @Test
    void testPageAndSort() throws IOException {
        int page = 2, size = 10;

        // 1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        // 2.准备DSL
        // 2.1.查询
        request.source().query(QueryBuilders.matchAllQuery());
        // 2.2.排序sort
        request.source().sort("price", SortOrder.ASC);
        // 2.3.分页from，size
        request.source().from((page - 1) * size).size(size);
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        // 4，5，6（解析相关操作，抽取为了公共方法）
        extractedResolution(response);
    }

    @Test
    void testHighLight() throws IOException {
        // 1.准备Request
        SearchRequest request = new SearchRequest("hotel");
        // 2.准备DSL
        // 2.1.查询（因为是关键字高亮，所以不能使用matchAll，这里就用match查询，指定具体的要高亮的关键字）
        request.source().query(QueryBuilders.matchQuery("all", "如家"));
        // 2.1.高亮
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false).preTags("<em>").postTags("</em>"));
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        // 4，5，6（解析相关操作，抽取为了公共方法）
        extractedResolution(response);
    }

    @Test
    void testAggregation() throws IOException {
        // 准备Request
        SearchRequest request = new SearchRequest("hotel");
        // 准备DSL
        // 设置size，表示不要文档结果，只要聚合结果
        request.source().size(0);
        // 聚合
        request.source().aggregation(
                AggregationBuilders
                        .terms("brandAgg")
                        .field("brand")
                        .size(10)
        );
        // 发出请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 解析结果
        Aggregations aggregations = response.getAggregations();
        // 根据聚合名称获取聚合结果
        Terms aggregation = aggregations.get("brandAgg");
        // 获取buckets
        List<? extends Terms.Bucket> buckets = aggregation.getBuckets();
        // 遍历
        for (Terms.Bucket bucket : buckets) {
            // 获取key
            String key = bucket.getKeyAsString();
            System.out.println(key);
        }
    }

    /**
     * 公共方法：解析的相关代码封装（选中需要提取的代码片段，快捷键为：option+command+m）
     * @param response
     */
    private void extractedResolution(SearchResponse response) {
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 5.获取总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("共搜索到" + total + "条数据");
        // 6.获取文档数组
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            // 获取文档
            String json = hit.getSourceAsString();
            // 反序列化
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            // 获取高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(!CollectionUtils.isEmpty(highlightFields)){
                // 根据字段名获取高亮结果
                HighlightField highlightField = highlightFields.get("name");
                if(highlightField != null) {
                    // 获取高亮值（一个string的数组）
                    String name = highlightField.getFragments()[0].string();
                    // 覆盖非高亮结果
                    hotelDoc.setName(name);
                }
            }
            // 结果打印
            System.out.println("hotelDoc = " + hotelDoc);
        }
    }

    @Test
    void testSuggest() throws IOException {
        // 准备Request
        SearchRequest request = new SearchRequest("hotel");
        // 准备DSL（最好对照控制台 # ==========查询suggest========== 写RestAPI代码）
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "suggestions",
                SuggestBuilders.completionSuggestion("suggestion")
                        .prefix("h")
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
        // 遍历
        for (CompletionSuggestion.Entry.Option option : options) {
            String text = option.getText().toString();
            System.out.println(text);
        }
    }

    /**
     * 初始化创建 ES 连接
     */
    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
            HttpHost.create("http://127.0.0.1:9200")
        ));
    }

    /**
     * 关闭 ES 连接
     * @throws IOException
     */
    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }



}
