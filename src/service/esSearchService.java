package service;

import com.alibaba.fastjson.JSONArray;
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
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class esSearchService {
    private static RestHighLevelClient client;
    public esSearchService(){
        String ipStr ="10.20.1.52";
        int port = 9200;
        start(ipStr,port);
    }


    public static void start(String ip , int port){
        HttpHost httpHost;
        httpHost = new HttpHost(ip, port, "http");
        client = new RestHighLevelClient(RestClient.builder(httpHost));


    }

    public static void insert(FileUtil fileUtil){

//        Date date = new Date();


        SimpleDateFormat lFormat;
        //格式可以自己根据需要修改
        lFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String gRtnStr = lFormat.format(new Date());
        System.out.println(gRtnStr);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("group", fileUtil.getGroup());
        jsonMap.put("path", fileUtil.getPath());
        jsonMap.put("filename", fileUtil.getFilename());
        jsonMap.put("filetype", fileUtil.getFiletype());
        jsonMap.put("content", fileUtil.getContent());
        jsonMap.put("timestamp", gRtnStr);

        System.out.println(jsonMap);
        IndexRequest indexRequest =new IndexRequest("testindex001").source(jsonMap);
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(indexResponse.status().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String source = JSONObject.toJSONString(fileUtil);
//        System.out.println(source);
//        IndexRequest indexRequest =new IndexRequest("testindex001").source(source);
    }


    public static JSONObject fileSearch(String nameField, String contentField) throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("testindex001");
        SearchSourceBuilder sourceBuilder =new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder =new BoolQueryBuilder();
        boolQueryBuilder.should(QueryBuilders.matchQuery("filename",nameField));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content",contentField));

        sourceBuilder.query(boolQueryBuilder);

        String[] includes ={"filename","filetype","group","path"};
        String[] excludes = Strings.EMPTY_ARRAY;

        sourceBuilder.fetchSource(includes, excludes);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for(String hb :includes){
            highlightBuilder.field(hb);
        }
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);


        SearchResponse searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits =searchResponse.getHits();
        SearchHit[] searchHit = hits.getHits();

        JSONObject result = new JSONObject();
        JSONArray rs = new JSONArray();


        for(SearchHit hit: searchHit){
            JSONObject rd = new JSONObject(hit.getSourceAsMap());
            System.out.println("result"+hit.getSourceAsString());

//            for(String hf_name:hit.getHighlightFields().keySet()){
//
//                HighlightField hf =hit.getHighlightFields().get(hf_name);
//                rd.put(hf_name, hf.getFragments()[0].toString());
//            }

            Map map = hit.getSourceAsMap();
            for (String key : hit.getFields().keySet()) {
                String fc= hit.getHighlightFields().get(key).toString();
                rd.put(key, fc);
            }


            rs.add(rd);

        }
        result.put("records",rs);
        return result;


    }




}
