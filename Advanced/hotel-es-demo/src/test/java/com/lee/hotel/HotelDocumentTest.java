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
import org.elasticsearch.client.Request;
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

    @Test
    void testGetDocumentById() throws IOException {
        // 准备Request对象
        GetRequest request = new GetRequest("hotel", "36934");
        // 发送请求，获取响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 解析响应结果
        String json = response.getSourceAsString();
        // 反序列化JSON对象
        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
        // 输出
        System.out.println(hotelDoc);
    }

    @Test
    void testUpdateDocument() throws IOException {
        // 准备Request对象
        UpdateRequest request = new UpdateRequest("hotel", "36934");
        // 准备请求参数
        request.doc("price", "633", "starName", "三钻");
        // 发送请求
        client.update(request, RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteDocument() throws IOException {
        // 准备Request对象
        DeleteRequest request = new DeleteRequest("hotel", "36934");
        // 发送请求
        client.delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testBulkRequest() throws IOException {
        // 批量查询酒店数据
        List<Hotel> hotels = hotelService.list();
        // 创建Request对象
        BulkRequest bulkRequest = new BulkRequest();
        // 转换文档类型Hotel为HotelDoc
        for (Hotel hotel : hotels) {
            HotelDoc hotelDoc = new HotelDoc(hotel);
            // 为每一个新增的Request添加参数
            bulkRequest.add(new IndexRequest("hotel").id(hotel.getId().toString()).source(JSON.toJSONString(hotelDoc), XContentType.JSON));
        }
        // 发送请求
        client.bulk(bulkRequest, RequestOptions.DEFAULT);
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
