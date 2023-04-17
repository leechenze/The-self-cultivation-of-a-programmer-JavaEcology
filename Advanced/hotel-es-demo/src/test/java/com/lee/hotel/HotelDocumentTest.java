package com.lee.hotel;

import com.alibaba.fastjson2.JSON;
import com.lee.hotel.pojo.Hotel;
import com.lee.hotel.pojo.HotelDoc;
import com.lee.hotel.service.IHotelService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class HotelDocumentTest {

    private RestHighLevelClient client;

    @Autowired
    private IHotelService hotelService;

    @Test
    void testAddDocument() throws IOException {
        // 根据ID查询酒店数据
        Hotel hotel = hotelService.getById(36934L);
        // 转换为文档类型
        HotelDoc hotelDoc = new HotelDoc(hotel);
        // 准备Request对象
        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
        // 准备Json DSL文档
        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
        // 发送请求
        client.index(request, RequestOptions.DEFAULT);
    }

    @BeforeEach
    void setUp() {
        client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://127.0.0.1:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        client.close();
    }



}
