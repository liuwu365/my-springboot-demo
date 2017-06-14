package com.liuwu;

import com.google.gson.Gson;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Description:
 * @User: liuwu_eva@163.com
 * @Date: 2017-06-12 上午 10:54
 */
public class ElasticSearchUtil {
    private static final Gson gson = new Gson();
    // 创建私有对象
    private static Client client;

    public ElasticSearchUtil() {
        try {
            Settings esSettings = Settings.builder()
                    .put("cluster.name", "elasticsearch") //设置ES实例的名称
                    .put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                    .build();
            client = new PreBuiltTransportClient(esSettings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ElasticSearchUtil test = new ElasticSearchUtil();
        test.generateIndex();
        //test.getIndex();
        //test.searchIndex();
        // test.deleteIndex();
        test.closeClient();
    }

    /**
     * 创建索引
     */
    public void generateIndex() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user", "sf");
        json.put("postDate", new Date());
        json.put("message", "trying out Elastic Search");

        IndexResponse response = client.prepareIndex("feng", "test", "1").setSource(json).execute().actionGet();
        System.out.println("创建索引成功:" + gson.toJson(response));
    }

    /**
     * 查询索引
     */
    public void getIndex() {
        GetResponse response = client.prepareGet("feng", "test", "1").execute().actionGet();
        //输出为map
        Map<String, Object> rpMap = response.getSource();
        if (rpMap == null) {
            System.out.println("empty");
            return;
        }
        Iterator<Entry<String, Object>> rpItor = rpMap.entrySet().iterator();
        System.out.println("=================== 响应结果 ========================");
        while (rpItor.hasNext()) {
            Entry<String, Object> rpEnt = rpItor.next();
            System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
        }
        //输出为字符串
        System.out.println("response.getSourceAsString():" + response.getSourceAsString());
        System.out.println("===========================================");

    }

    /**
     * 查询记录
     */
    public void searchIndex() {

        QueryBuilder qb = QueryBuilders.termQuery("user", "sf");
        // 100|hits|per|shard|will|be|returned|for|each|scroll
        SearchResponse scrollResp = client.prepareSearch("feng")
                .setTypes("test")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setScroll(new TimeValue(60000))
                .setQuery(qb)
                .setSize(100)
                .execute()
                .actionGet();
        // Scroll until no hits are returned
        SearchHits hits = scrollResp.getHits();
        System.out.println("查到记录数：" + hits.getTotalHits());
        while (true) {
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute().actionGet();
            for (SearchHit hit : hits) {
                Iterator<Entry<String, Object>> rpItor = hit.getSource().entrySet().iterator();
                while (rpItor.hasNext()) {
                    Entry<String, Object> rpEnt = rpItor.next();
                    System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
                }
            }
            // Break condition: No hits are returned
            if (scrollResp.getHits().hits().length == 0) {
                break;
            }
        }
    }

    public void deleteIndex() {
        DeleteResponse response = client.prepareDelete("feng", "test", "1").execute().actionGet();
    }

    public void closeClient() {
        client.close();
    }
}
