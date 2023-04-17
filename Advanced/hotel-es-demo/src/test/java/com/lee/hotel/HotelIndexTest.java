package com.lee.hotel;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.lee.hotel.constants.HotelIndexConstants.MAPPING_TEMPLATE;

@SpringBootTest
class HotelIndexTest {

    private RestHighLevelClient client;

    /**
     * 创建索引库
     * 在 5601 端口的控制台上查询 hotel 库进行验证是否成功创建：
     * @throws IOException
     */
    @Test
    void testCreateIndex() throws IOException {
        // 1.准备Request      PUT /hotel
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        // 2.准备请求参数
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 判断索引库是否存在
     * @throws IOException
     */
    @Test
    void testExistsIndex() throws IOException {
        // 1.准备Request
        GetIndexRequest request = new GetIndexRequest("hotel");
        // 2.发送请求
        boolean isExists = client.indices().exists(request, RequestOptions.DEFAULT);
        // 3.输出
        System.out.println(isExists ? "hotel索引库已存在" : "hotel索引库不存在");
    }

    /**
     * 删除索引库
     * @throws IOException
     */
    @Test
    void testDeleteIndex() throws IOException {
        // 1.准备Request
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        // 2.发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
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
