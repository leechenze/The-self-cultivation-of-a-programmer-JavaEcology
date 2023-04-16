package com.lee.hotel;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.Alias;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.function.Function;

import static com.lee.hotel.constants.HotelIndexConstants.MAPPING_TEMPLATE;

@SpringBootTest
class HotelIndexTest {

    public ElasticsearchClient client;
    public RestClient restClient;
    @Test
    void testCreateIndex() throws IOException {
        // // 1.准备Request      PUT /hotel
        // CreateIndexRequest request = new CreateIndexRequest("hotel");
        // // 2.准备请求参数
        // request.source(MAPPING_TEMPLATE, XContentType.JSON);
        // // 3.发送请求
        // client.indices().create(request, RequestOptions.DEFAULT);

        client.indices().create(createIndexBuilder -> createIndexBuilder.index("hotel"));

    }


    @BeforeEach
    void setUp() {

        RestClient restClient = RestClient.builder(HttpHost.create("http://127.0.0.1:9200")).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        ElasticsearchClient client = new ElasticsearchClient(transport);

    }

    @AfterEach
    void tearDown() throws IOException {
        // client.close();
    }

}
