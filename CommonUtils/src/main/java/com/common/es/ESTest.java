package com.common.es;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

public class ESTest
{

    public static void main(String[] args)
    {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "ttxn-es-el-2019"));
        
        HttpHost httpHost = new HttpHost("127.0.0.1", 9200, "http");
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost).setHttpClientConfigCallback(new HttpClientConfigCallback()
        {
            
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder)
            {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }));
        
//        SearchRequest searchRequest = new SearchRequest("articles");
//        SearchSourceBuilder searchSourceBuilder =  new SearchSourceBuilder();
//        //��ѯ���������Բο������ֲ�
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        boolQuery.must(QueryBuilders.matchQuery("articleTitle", "ˮ��ѿ������տ�ݷѸߵ緹��"));
//        searchSourceBuilder.query(boolQuery);
//        searchRequest.source(searchSourceBuilder);
//        try {
//            System.out.println(client.ping(RequestOptions.DEFAULT));
//            
//            //��ѯ���
//            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//            SearchHits hits = searchResponse.getHits();
//            SearchHit[] searchHits = hits.getHits();
//            for(SearchHit hit : searchHits) {
//                System.out.println(hit.getSourceAsString());
//            }
//            
//            System.out.println(searchResponse.status());
//            
//            
//            client.close();
//            
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        String keyword = "ˮ��ѿ������տ�ݷѸߵ緹��";
        
        final MultiSearchRequest request = new MultiSearchRequest();
        final SearchSourceBuilder searchArticleBuilder = new SearchSourceBuilder().size(11).
                query(QueryBuilders.disMaxQuery().add(QueryBuilders.matchQuery("articleTitle", keyword).boost(1.5f)).add(QueryBuilders.matchQuery("articleContent", keyword)));
        searchArticleBuilder.highlighter(new HighlightBuilder().field(new HighlightBuilder.Field("articleTitle")).field(new HighlightBuilder.Field("articleContent")));
        final SearchRequest searchArticleReq = new SearchRequest("articles").source(searchArticleBuilder);
        request.add(searchArticleReq);
        try {
            System.out.println(client.ping(RequestOptions.DEFAULT));
            
            //��ѯ���
            MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);
            
            MultiSearchResponse.Item[] items = response.getResponses();
            final SearchResponse articleResult = items[0].getResponse();
            final SearchHit[] articleHits = articleResult.getHits().getHits();
            for (final SearchHit hit : articleHits) {
                final Map<String, Object> source = hit.getSourceAsMap();
                System.out.println(source);
            }
//            SearchHits hits = searchResponse.getHits();
//            SearchHit[] searchHits = hits.getHits();
//            for(SearchHit hit : searchHits) {
//                System.out.println(hit.getSourceAsString());
//            }
//            
//            System.out.println(searchResponse.status());
            
            
            client.close();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
