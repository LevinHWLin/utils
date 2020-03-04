/**
 * 
 */
package com.common.es;

import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;


/**
 * 创建索引
 * @author linhangwu
 *
 */
public class CreateIndex
{
    public void createIndex(RestHighLevelClient client,String indexName) {

        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        // 创建的每个索引都可以有与之关联的特定设置
        request.settings(Settings.builder().put("index.number_of_shards",3).put("index_number_of_replicas",2));

        // 创建索引时，创建文档类型映射
        request.mapping("es-text", "{\"es-text\":{\"properties\":{\"message\":{\"type\":\"text\"}}}}");

        // 为索引设置一个别名
        request.alias(new Alias("es-text-alias"));

        // 超时，等待所有节点被确认
        request.timeout(TimeValue.timeValueMinutes(2));

        // 链接master节点的超时时间
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));

        // 在创建索引API返回响应之前等待的活动分片副本的数量
        request.waitForActiveShards(2);

//        CreateIndexResponse createIndexResponse = client.(request, RequestOptions.DEFAULT);
    }
}
