package com.liuwu;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @User: liuwu_eva@163.com
 * @Date: 2017-06-09 下午 4:15
 */
public class ElasticsearchTest {
    private static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchTest.class);

    private static TransportClient client;
    private static final String host = "localhost";
    private static final int port = 9300;

    @Before
    @SuppressWarnings({"unchecked"})
    public void before() throws UnknownHostException, InterruptedException, ExecutionException {
        Settings esSettings = Settings.builder()
                .put("cluster.name", "elasticsearch") //设置ES实例的名称
                .put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
                .build();
        client = new PreBuiltTransportClient(esSettings);//初始化client较老版本发生了变化，此方法有几个重载方法，初始化插件等。
        //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
    }

    @Test
    public void index() throws Exception {
        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("name", "信息1111");
        infoMap.put("title", "我的广告2222");
        infoMap.put("createTime", new Date());
        infoMap.put("count", 1022);
        IndexResponse response = client.prepareIndex("test", "info")
                .setId("2")
                .setSource(infoMap)
                .execute()
                .actionGet();
        //多次index版本号会变
        logger.info("创建索引成功,版本号:{}|响应结果：{}", response.getVersion(), gson.toJson(response));
        client.close();
    }

    @Test
    public void get() throws Exception {
        GetResponse response = client.prepareGet("test", "info", "1").execute().actionGet();
        System.out.println("response.getId():" + response.getId());
        System.out.println("response.getSourceAsString():" + response.getSourceAsString());
        client.close();
    }

    @Test
    public void query() throws Exception {
        //term查询
        //QueryBuilder queryBuilder = QueryBuilders.termQuery("count", "1022");
        QueryBuilder queryBuilder = QueryBuilders.termQuery("name", "广告广告广告");
        //range查询
        //QueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("id").gt(100);
        SearchResponse searchResponse = client.prepareSearch("test")
                .setTypes("info")
                .setQuery(queryBuilder)
                .addSort("count", SortOrder.DESC)
                .setFrom(0)
                .setSize(20)
                .execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        System.out.println("查到记录数：" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                String id = hit.getId();
                String title = hit.getSource().get("title").toString();
                String name = hit.getSource().get("name").toString();
                String count = hit.getSource().get("count").toString();
                System.out.format("id :%s ,title:%s ,name:%s,count:%s \n", id, title, name, count);
            }
        }
        client.close();
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void deleteIndex() {
        DeleteResponse response = client.prepareDelete("feng", "test", "1").execute().actionGet();
    }

    @Test
    public void highlightQuery() throws Exception {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "广告"); //commonTermsQuery

        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("name"); //.requireFieldMatch(false)
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");

        SearchResponse searchResponse = client.prepareSearch("test")
                //.setTypes("info")
                .setQuery(queryBuilder)
                .highlighter(highlightBuilder)
                //.addSort("count", SortOrder.DESC)
                //.setFrom(0)
                //.setSize(20)
                .execute().actionGet();


        SearchHits hits = searchResponse.getHits();
        System.out.println("查到记录数：" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                String id = hit.getId();
                String title = hit.getSource().get("title").toString();
                String count = hit.getSource().get("count").toString();
                String name = hit.getSource().get("name").toString();
                System.out.format("id :%s,name:%s ,title:%s ,count:%s \n", id, name, title, count);
            }
            /*for (SearchHit hit : hits) {
                Iterator<Map.Entry<String, Object>> rpItor = hit.getSource().entrySet().iterator();
                while (rpItor.hasNext()) {
                    Map.Entry<String, Object> rpEnt = rpItor.next();
                    System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
                }
            }*/
        }
        client.close();
    }

    @Test
    public void highlightQuery2() throws Exception {
        Map map = search("广告信息1111", "test", "info", 0, 20);
        System.out.println(gson.toJson(map));
    }

    public static Map<String, Object> search(String key, String index, String type, int start, int row) {
        //创建查询索引,要查询的索引库为index
        SearchRequestBuilder builder = client.prepareSearch(index);
        builder.setTypes(type);
        builder.setFrom(start);
        builder.setSize(row);

        //设置查询类型：1.SearchType.DFS_QUERY_THEN_FETCH 精确查询； 2.SearchType.SCAN 扫描查询,无序
        builder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        if (StringUtils.isNotBlank(key)) {
            builder.setQuery(QueryBuilders.multiMatchQuery(key, "title")); // 设置查询关键词
            //builder.setQuery(QueryBuilders.matchPhraseQuery("name","广告"));
        }

        //设置是否按查询匹配度排序
        builder.setExplain(true);
        //设置高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder().field("*").requireFieldMatch(false);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        builder.highlighter(highlightBuilder);

        //执行搜索,返回搜索响应信息
        SearchResponse searchResponse = builder.get();
        SearchHits searchHits = searchResponse.getHits();

        //总命中数(查到记录数)
        long total = searchHits.getTotalHits();
        Map<String, Object> map = new HashMap<String, Object>();
        SearchHit[] hits = searchHits.getHits();
        map.put("count", total);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : hits) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();

            //title高亮
            HighlightField titleField = highlightFields.get("title");
            Map<String, Object> source = hit.getSource();
            if (titleField != null) {
                Text[] fragments = titleField.fragments();
                String name = "";
                for (Text text : fragments) {
                    name += text;
                }
                source.put("title", name);
            }

            //name高亮
            HighlightField describeField = highlightFields.get("name");
            if (describeField != null) {
                Text[] fragments = describeField.fragments();
                String describe = "";
                for (Text text : fragments) {
                    describe += text;
                }
                source.put("name", describe);
            }
            list.add(source);
        }
        map.put("dataList", list);
        return map;
    }


    //************************************************************************************************************************


}
