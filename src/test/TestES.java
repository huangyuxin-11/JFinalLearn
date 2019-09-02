package test;

import com.alibaba.fastjson.JSONObject;
import model.FileUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestES {

    private static RestHighLevelClient client;

    public static void main(String[] arg){
        String ipStr ="10.20.1.52";
        int port = 9200;
        start(ipStr,port);

//        insert();

        try {
            fileNameSearch();
        } catch (IOException e) {
            System.out.println("查询出错");
            e.printStackTrace();
        }

    }

    public static void start(String ip , int port){
        HttpHost httpHost;
        httpHost = new HttpHost(ip, port, "http");
        client = new RestHighLevelClient(RestClient.builder(httpHost));
        if(null == client){
            System.out.println("connect error");
        }

    }

    public static void insert(){

        Date date = new Date();

        FileUtil fileUtil = new FileUtil();
        fileUtil.setGroup("002");
        fileUtil.setPath("hufuf");
        fileUtil.setFilename("test");
        fileUtil.setFiletype("txt");
        fileUtil.setContent("hello");
//        fileUtil.setTimestamp();
//
//        String source = JSONObject.toJSONString(fileUtil);
//        System.out.println(source);
        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("group", "001");
//        jsonMap.put("path", "hufuf");
//        jsonMap.put("filename", "trying");
//        jsonMap.put("filetype", "001");
//        jsonMap.put("content", "hufuf");
//        jsonMap.put("timestamp", "2018-01-02 11:12:22");

        SimpleDateFormat lFormat;
        //格式可以自己根据需要修改
        lFormat   =     new   SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String   gRtnStr   =   lFormat.format(new Date());
        System.out.println(gRtnStr);

        jsonMap.put("group", fileUtil.getGroup());
        jsonMap.put("path", fileUtil.getPath());
        jsonMap.put("filename", fileUtil.getFilename());
        jsonMap.put("filetype", fileUtil.getFiletype());
        jsonMap.put("content", fileUtil.getContent());
        jsonMap.put("timestamp", gRtnStr);

        System.out.println(jsonMap);
        IndexRequest indexRequest =new IndexRequest("testindex001").source(jsonMap);
//        IndexRequest indexRequest =new IndexRequest("testindex001").source(source);
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(indexResponse.status().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileNameSearch() throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("testindex001");
        SearchSourceBuilder sourceBuilder =new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("filename","test"));

        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        SearchResponse searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits =searchResponse.getHits();
        SearchHit[] searchHit = hits.getHits();

        for(SearchHit hit: searchHit){
            String cont =hit.getSourceAsString();
            System.out.println(cont);
        }

    }
}
