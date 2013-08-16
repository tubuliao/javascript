package com.isoftstone.tyw.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.action.suggest.SuggestRequestBuilder;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.CustomScoreQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.strings.InternalStringTermsFacet;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isoftstone.tyw.dto.info.ESPage;

/**
 * 
 * @author JQZ
 * ES查询接口，提供了分词查询，索引数据，查询搜索引擎中数据等方法
 */
@Component
public class ElasticsearchService {
	private static final Logger log = Logger
			.getLogger(ElasticsearchService.class);

	public static final String SORT_ASC = "ASC";

	public static final String SORT_DESC = "DESC";

	@Autowired
	Client client;

	/**
	 * 获取ES传输客户端池
	 * 
	 * @return
	 */
//	public EsClientCommonPool clientPool() {
//		EsClientCommonPool pool = EsClientCommonPool.getPoolInstance();
//		return pool;
//	}

	/**
	 * 创建索引
	 * 
	 * @param indices
	 *            索引名称
	 */
	public void createIndex(String indices) {
		// Client client = this.clientPool().borrowClient();
		client.admin().indices().prepareCreate(indices).execute().actionGet();
		// this.clientPool().returnClient(client);
	}

	/**
	 * 创建映射
	 * 
	 * @param indices
	 *            索引名称
	 * @param mappingType
	 *            类型
	 * @throws Exception
	 */
	public void createMapping(String indices, String mappingType)
			throws Exception {
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject()
				.startObject(indices).startObject("properties")
				.startObject("title").field("type", "string")
				.field("store", "yes").field("indexAnalyzer", "ik")
				.field("searchAnalyzer", "ik").endObject()
				.startObject("createDate").field("type", "date")
				.field("format", "YYYY-MM-dd HH:mm:ss").field("store", "yes")
				.endObject().startObject("summary").field("type", "string")
				.field("store", "yes").field("indexAnalyzer", "ik")
				.field("searchAnalyzer", "ik").endObject().startObject("type")
				.field("type", "integer").field("store", "yes").endObject()
				.startObject("source").field("type", "string")
				.field("store", "yes").field("indexAnalyzer", "ik")
				.field("searchAnalyzer", "ik").endObject()
				.startObject("description").field("type", "string")
				.field("store", "yes").field("indexAnalyzer", "ik")
				.field("searchAnalyzer", "ik").endObject().startObject("tags")
				.field("type", "string").field("store", "yes")
				.field("indexAnalyzer", "ik").field("searchAnalyzer", "ik")
				.endObject().startObject("content").field("type", "string")
				.field("store", "yes").field("indexAnalyzer", "ik")
				.field("searchAnalyzer", "ik").endObject().startObject("url")
				.field("type", "string").field("store", "yes").endObject()
				.endObject().endObject().endObject();
		PutMappingRequest mappingRequest = Requests.putMappingRequest(indices)
				.type(mappingType).source(mapping);
		// Client client = this.clientPool().borrowClient();
		client.admin().indices().putMapping(mappingRequest).actionGet();
		// this.clientPool().returnClient(client);
	}

	/**
	 * 索引数据
	 * 
	 * @param indices
	 *            索引名称
	 * @param type
	 *            类型
	 * @param doc
	 *            数据
	 * @throws Exception
	 */
	public void indexData(String indices, String type, XContentBuilder doc)
			throws Exception {
		// Client client = this.clientPool().borrowClient();
		client.prepareIndex(indices, type).setSource(doc).execute().actionGet();
		// this.clientPool().returnClient(client);
	}

	/**
	 * 索引数据
	 * 
	 * @param indices
	 * @param type
	 * @param map
	 * @throws Exception
	 */
	public void indexData(String indices, String type, Map<String, Object> map)
			throws Exception {
		// Client client = this.clientPool().borrowClient();
		client.prepareIndex(indices, type).setSource(map).execute().actionGet();
		// this.clientPool().returnClient(client);
	}

	/**
	 * 索引数据
	 * 
	 * @param indices
	 * @param type
	 * @param source
	 * @throws Exception
	 */
	public void indexData(String indices, String type, String source)
			throws Exception {
		// Client client = this.clientPool().borrowClient();
		client.prepareIndex(indices, type).setSource(source).execute()
				.actionGet();
		// this.clientPool().returnClient(client);
	}

	/**
	 * 删除索引数据根据ID
	 * 
	 * @param indices
	 * @param type
	 * @param id
	 */
	public void deleteIndexDataById(String indices, String type, String id) {
		// Client client = this.clientPool().borrowClient();
		client.prepareDelete(indices, type, id).execute().actionGet();
		// this.clientPool().returnClient(client);
	}

	/**
	 * 删除索引数据通过查询条件
	 * 
	 * @param indices
	 * @param fieldName
	 * @param fieldValue
	 */
	public void deleteIndexDataByQuery(String indices, String fieldName,
			String fieldValue) {
		// Client client = this.clientPool().borrowClient();
		QueryBuilder query = QueryBuilders.fieldQuery(fieldName, fieldValue);
		client.prepareDeleteByQuery(indices).setQuery(query).execute()
				.actionGet();
		// this.clientPool().returnClient(client);
	}

	public ESPage queryIndexData(String indices, String type,
			String queryString, int pageSize, int pageNo) {
		return null;
	}

	/**
	 * 
	 * @param indices
	 *            索引
	 * @param type
	 *            类型
	 * @param defaultOperator
	 *            操作类型 AND 和 OR 默认 AND
	 * @param queryString
	 *            查询字符串 ,查询字符串可以内可以使用 AND OR NOT(),也可以使用 filed:value 方式
	 *            .例如(Tags:质量验收 质量验收) 110～500KV
	 * @param heightPreTag
	 *            高亮前缀
	 * @param heightPostTag
	 *            高亮后缀
	 * @param pageSize
	 *            每页结果数
	 * @param pageNo
	 *            页码
	 * @return 查询结果
	 * @throws Exception
	 */
	public ESPage queryByStringBuilder(String indices, String type,
			String defaultOperator, String queryString, String heightPreTag,
			String heightPostTag, int pageSize, int pageNo) throws Exception {
		log.info("queryByStringBuilder:" + queryString);
		if (pageNo < 1) {
			throw new IllegalArgumentException(
					"The parameters PageNo not less than 0");
		}
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(queryString);
		// Client client = this.clientPool().borrowClient();
		SearchRequestBuilder sqb = client.prepareSearch(indices);
		if (type != null && !"".equals(type)) {
			sqb.setTypes(type);
		}
		sqb.setQuery(qsqb);
		if (heightPreTag != null && !"".equals(heightPreTag)
				&& !"".equals(heightPostTag) && heightPostTag != null) {
			sqb.setHighlighterPreTags(heightPreTag).setHighlighterPostTags(
					heightPostTag);
		}
		SearchResponse searchResponse = sqb.addHighlightedField("Title")
				.addHighlightedField("Source").addHighlightedField("Content")
				.setExplain(true).setFrom((pageNo - 1) * pageSize)
				.setSize(pageSize).execute().actionGet();
		List<Map<String, Object>> resultHightligth = new ArrayList<Map<String, Object>>();
		for (SearchHit searchHit : searchResponse.getHits()) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Id", searchHit.getId());
			resultMap.put("Type", this.getType(searchHit.type()));
			resultMap
					.put("CreateDate", searchHit.getSource().get("CreateDate"));
			resultMap.put("CommCount", searchHit.getSource().get("CommCount"));
			resultMap.put("FavCount", searchHit.getSource().get("FavCount"));
			resultMap.put("LikeCount", searchHit.getSource().get("LikeCount"));
//			resultMap.put("Description",
//					searchHit.getSource().get("Description"));
			resultMap.put("Tags", searchHit.getSource().get("Tags"));
			resultMap.put("Url", searchHit.getSource().get("Url"));
			resultMap.put("PicUrl", searchHit.getSource().get("PicUrl"));
			if (searchHit.getSource().get("SegItem") != null)
				resultMap.put("SegItem", searchHit.getSource().get("SegItem"));
			StringBuffer source = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Source") != null)
				for (Text text : searchHit.getHighlightFields().get("Source")
						.getFragments()) {
					source.append(text.toString());
				}
			if ("".equals(source.toString())) {
				resultMap.put("Source", searchHit.getSource().get("Source"));
			} else {
				resultMap.put("Source", source.toString());
			}
			StringBuffer title = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Title") != null)
				for (Text text : searchHit.getHighlightFields().get("Title")
						.getFragments()) {
					title.append(text.toString());
				}
			if ("".equals(title.toString())) {
				resultMap.put("Title", searchHit.getSource().get("Title"));
			} else {
				resultMap.put("Title", title.toString());
			}
			StringBuffer content = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Content") != null)
				for (Text text : searchHit.getHighlightFields().get("Content")
						.getFragments()) {
					content.append(text.toString());
				}
			if ("".equals(content.toString())) {
				resultMap.put("Content", searchHit.getSource().get("Content"));
			} else {
				resultMap.put("Content", content.toString());
			}
			resultHightligth.add(resultMap);
		}
		BigDecimal took = new BigDecimal(searchResponse.getTook().millis());
		String tookString = took.divide(new BigDecimal(1000)).toString();
		ESPage ep = new ESPage(null, resultHightligth, searchResponse.getHits()
				.getTotalHits(), tookString);
		// this.clientPool().returnClient(client);
		return ep;
	}

	/**
	 * 
	 * @param indices
	 *            索引
	 * @param type
	 *            类型
	 * @param defaultOperator
	 *            操作类型 AND 和 OR 默认 AND
	 * @param queryString
	 *            查询字符串 ,查询字符串可以内可以使用 AND OR NOT(),也可以使用 filed:value 方式
	 *            .例如(Tags:质量验收 质量验收) 110～500KV
	 * @param heightPreTag
	 *            高亮前缀
	 * @param heightPostTag
	 *            高亮后缀
	 * @param sortField
	 *            排序字段
	 * @param sortOrder
	 *            排序方式 ASC 或者 DESC
	 * @param pageSize
	 *            每页结果数
	 * @param pageNo
	 *            页码
	 * @return 查询结果
	 * @throws Exception
	 */
	public ESPage queryByStringBuilder(String indices, String type,
			String defaultOperator, String queryString, String heightPreTag,
			String heightPostTag, String sortField, String sortOrder,
			int pageSize, int pageNo) throws Exception {
		log.info("queryByStringBuilder:" + queryString);
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(queryString);
		if (pageNo < 1) {
			throw new IllegalArgumentException(
					"The parameters PageNo not less than 0");
		}
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
		// Client client = this.clientPool().borrowClient();
		SearchRequestBuilder sqb = client.prepareSearch(indices);
		if (type != null && !"".equals(type)) {
			sqb.setTypes(type);
		}
		sqb.setQuery(qsqb);
		if (heightPreTag != null && !"".equals(heightPreTag)
				&& !"".equals(heightPostTag) && heightPostTag != null) {
			sqb.setHighlighterPreTags(heightPreTag).setHighlighterPostTags(
					heightPostTag);
		}
		if (sortField != null
				&& !"".equals(sortField)
				&& (ElasticsearchService.SORT_ASC.equals(sortOrder) || ElasticsearchService.SORT_DESC
						.equals(sortOrder))) {
			if (ElasticsearchService.SORT_ASC.equals(sortOrder)) {
				sqb.addSort(sortField, SortOrder.ASC);
			} else {
				sqb.addSort(sortField, SortOrder.DESC);
			}
		}

		SearchResponse searchResponse = sqb.addHighlightedField("Title")
				.addHighlightedField("Source").setExplain(true)
				.setFrom((pageNo - 1) * pageSize).setSize(pageSize).execute()
				.actionGet();
		List<Map<String, Object>> resultHightligth = new ArrayList<Map<String, Object>>();
		for (SearchHit searchHit : searchResponse.getHits()) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Id", searchHit.getId());
			resultMap.put("Type", this.getType(searchHit.type()));
			resultMap
					.put("CreateDate", searchHit.getSource().get("CreateDate"));
			resultMap.put("CommCount", searchHit.getSource().get("CommCount"));
			resultMap.put("FavCount", searchHit.getSource().get("FavCount"));
			resultMap.put("LikeCount", searchHit.getSource().get("LikeCount"));
//			resultMap.put("Description",
//					searchHit.getSource().get("Description"));
			resultMap.put("Tags", searchHit.getSource().get("Tags"));
			resultMap.put("Url", searchHit.getSource().get("Url"));
			resultMap.put("PicUrl", searchHit.getSource().get("PicUrl"));
			if (searchHit.getSource().get("SegItem") != null)
				resultMap.put("SegItem", searchHit.getSource().get("SegItem"));
			StringBuffer source = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Source") != null)
				for (Text text : searchHit.getHighlightFields().get("Source")
						.getFragments()) {
					source.append(text.toString());
				}
			if ("".equals(source.toString())) {
				resultMap.put("Source", searchHit.getSource().get("Source"));
			} else {
				resultMap.put("Source", source.toString());
			}
			StringBuffer title = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Title") != null)
				for (Text text : searchHit.getHighlightFields().get("Title")
						.getFragments()) {
					title.append(text.toString());
				}
			if ("".equals(title.toString())) {
				resultMap.put("Title", searchHit.getSource().get("Title"));
			} else {
				resultMap.put("Title", title.toString());
			}
			StringBuffer content = new StringBuffer("");
			if ("".equals(content.toString())) {
				resultMap.put("Content", searchHit.getSource().get("Content"));
			} else {
				resultMap.put("Content", content.toString());
			}
			resultHightligth.add(resultMap);
		}
		BigDecimal took = new BigDecimal(searchResponse.getTook().millis());
		String tookString = took.divide(new BigDecimal(1000)).toString();
		ESPage ep = new ESPage(null, resultHightligth, searchResponse.getHits()
				.getTotalHits(), tookString);
		// this.clientPool().returnClient(client);
		return ep;
	}

	/**
	 * @param indices
	 *            索引
	 * @param type
	 *            类型
	 * @param queryString
	 *            查询字符串
	 * @param sortField
	 *            排序字段
	 * @param sortOrder
	 *            排序类型
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public ESPage queryByStringBuilder(String indices, String type,
			String queryString, String sortField, String sortOrder,
			int pageSize, int pageNo) throws Exception {
		log.info("queryByStringBuilder:" + queryString);
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(queryString);

		if (pageNo < 1) {
			throw new IllegalArgumentException(
					"The parameters PageNo not less than 0");
		}
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
		// Client client = this.clientPool().borrowClient();
		SearchRequestBuilder sqb = client.prepareSearch(indices);
		if (type != null && !"".equals(type)) {
			sqb.setTypes(type);
		}
		sqb.setQuery(qsqb);
		if (sortField != null
				&& !"".equals(sortField)
				&& (ElasticsearchService.SORT_ASC.equals(sortOrder) || ElasticsearchService.SORT_DESC
						.equals(sortOrder))) {
			if (ElasticsearchService.SORT_ASC.equals(sortOrder)) {
				sqb.addSort(sortField, SortOrder.ASC);
			} else {
				sqb.addSort(sortField, SortOrder.DESC);
			}
		}

		SearchResponse searchResponse = sqb.setExplain(true)
				.setFrom((pageNo - 1) * pageSize).setSize(pageSize).execute()
				.actionGet();
		List<Map<String, Object>> resultHightligth = new ArrayList<Map<String, Object>>();
		for (SearchHit searchHit : searchResponse.getHits()) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Id", searchHit.getId());
			resultMap.put("Type", this.getType(searchHit.type()));
			resultMap
					.put("CreateDate", searchHit.getSource().get("CreateDate"));
			resultMap.put("CommCount", searchHit.getSource().get("CommCount"));
			resultMap.put("FavCount", searchHit.getSource().get("FavCount"));
			resultMap.put("LikeCount", searchHit.getSource().get("LikeCount"));
//			resultMap.put("Description",
//					searchHit.getSource().get("Description"));
			resultMap.put("Tags", searchHit.getSource().get("Tags"));
			resultMap.put("Url", searchHit.getSource().get("Url"));
			resultMap.put("PicUrl", searchHit.getSource().get("PicUrl"));
			resultMap.put("Source", searchHit.getSource().get("Source"));
			resultMap.put("Title", searchHit.getSource().get("Title"));
			resultMap.put("Content", searchHit.getSource().get("Content"));
			if (searchHit.getSource().get("SegItem") != null)
				resultMap.put("SegItem", searchHit.getSource().get("SegItem"));
			resultHightligth.add(resultMap);
		}
		BigDecimal took = new BigDecimal(searchResponse.getTook().millis());
		String tookString = took.divide(new BigDecimal(1000)).toString();
		ESPage ep = new ESPage(null, resultHightligth, searchResponse.getHits()
				.getTotalHits(), tookString);
		// this.clientPool().returnClient(client);
		return ep;
	}

	/**
	 * @param indices
	 *            索引
	 * @param type
	 *            类型
	 * @param queryString
	 *            查询字符串
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws Exception
	 */
	public ESPage queryByStringBuilder(String indices, String type,
			String queryString, int pageSize, int pageNo) throws Exception {
		log.info("queryByStringBuilder:" + queryString);
		QueryStringQueryBuilder qsqb = QueryBuilders.queryString(queryString);
		if (pageNo < 1) {
			throw new IllegalArgumentException(
					"The parameters PageNo not less than 0");
		}
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
		// Client client = this.clientPool().borrowClient();
		SearchRequestBuilder sqb = client.prepareSearch(indices);
		if (type != null && !"".equals(type)) {
			sqb.setTypes(type);
		}
		sqb.setQuery(qsqb);

		SearchResponse searchResponse = sqb.setExplain(true)
				.setFrom((pageNo - 1) * pageSize).setSize(pageSize).execute()
				.actionGet();
		List<Map<String, Object>> resultHightligth = new ArrayList<Map<String, Object>>();
		for (SearchHit searchHit : searchResponse.getHits()) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Id", searchHit.getId());
			resultMap.put("Type", this.getType(searchHit.type()));
			resultMap
					.put("CreateDate", searchHit.getSource().get("CreateDate"));
			resultMap.put("CommCount", searchHit.getSource().get("CommCount"));
			resultMap.put("FavCount", searchHit.getSource().get("FavCount"));
			resultMap.put("LikeCount", searchHit.getSource().get("LikeCount"));
//			resultMap.put("Description",
//					searchHit.getSource().get("Description"));
			resultMap.put("Tags", searchHit.getSource().get("Tags"));
			resultMap.put("Url", searchHit.getSource().get("Url"));
			resultMap.put("PicUrl", searchHit.getSource().get("PicUrl"));
			resultMap.put("Source", searchHit.getSource().get("Source"));
			resultMap.put("Title", searchHit.getSource().get("Title"));
			resultMap.put("Content", searchHit.getSource().get("Content"));
			if (searchHit.getSource().get("SegItem") != null)
				resultMap.put("SegItem", searchHit.getSource().get("SegItem"));
			resultHightligth.add(resultMap);
		}
		BigDecimal took = new BigDecimal(searchResponse.getTook().millis());
		String tookString = took.divide(new BigDecimal(1000)).toString();
		ESPage ep = new ESPage(null, resultHightligth, searchResponse.getHits()
				.getTotalHits(), tookString);
		// this.clientPool().returnClient(client);
		return ep;
	}

	/**
	 * 查询过滤
	 * 
	 * @param indices
	 *            索引
	 * @param type
	 *            类型
	 * @param queryString
	 *            查询字符串
	 * @param filterString
	 *            过滤字符串
	 * @param heightPreTag
	 *            高亮前缀
	 * @param heightPostTag
	 *            高亮后缀
	 * @param pageSize
	 *            每页数量
	 * @param pageNo
	 *            页码
	 * @return
	 */
	public ESPage queryAndFilterByString(String indices,  String type,
			String queryString, String filterString, String heightPreTag,
			String heightPostTag, int pageSize, int pageNo) {
		log.info("queryString:" + queryString + "   filterString:"
				+ filterString);

		if (pageNo < 1) {
			throw new IllegalArgumentException(
					"The parameters PageNo not less than 0");
		}
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
//		Client client = this.clientPool().borrowClient();
		SearchRequestBuilder sqb = client.prepareSearch(indices);
		if (type != null && !"".equals(type)) {
			sqb.setTypes(type.split(","));
		}

		if (queryString != null && !"".equals(queryString)) {
			CustomScoreQueryBuilder ssqb = QueryBuilders.customScoreQuery(
					QueryBuilders.queryString(queryString)).script(
					"_score + 0.01 * doc['FavCount'].value");
			sqb.setQuery(ssqb);
		} else {
			sqb.setQuery(QueryBuilders.matchAllQuery());
		}

		if (filterString != null && !"".equals(filterString)) {
			sqb.setFilter(FilterBuilders.queryFilter(QueryBuilders
					.queryString(filterString)));
		}
		if (heightPreTag != null && !"".equals(heightPreTag)
				&& !"".equals(heightPostTag) && heightPostTag != null) {
			sqb.setHighlighterPreTags(heightPreTag).setHighlighterPostTags(
					heightPostTag);
		}
		SearchResponse searchResponse = sqb.addHighlightedField("Title")
				.addHighlightedField("Source").addHighlightedField("Content")
				.setFrom((pageNo - 1) * pageSize).setSize(pageSize)
				.setExplain(true).execute().actionGet();
		List<Map<String, Object>> resultHightligth = new ArrayList<Map<String, Object>>();
		for (SearchHit searchHit : searchResponse.getHits()) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Id", searchHit.getId());
			resultMap.put("Type", this.getType(searchHit.type()));
			resultMap
					.put("CreateDate", searchHit.getSource().get("CreateDate"));
			resultMap.put("CommCount", searchHit.getSource().get("CommCount"));
			resultMap.put("FavCount", searchHit.getSource().get("FavCount"));
			resultMap.put("LikeCount", searchHit.getSource().get("LikeCount"));
//			resultMap.put("Description",
//					searchHit.getSource().get("Description"));
			resultMap.put("Tags", searchHit.getSource().get("Tags"));
			resultMap.put("Url", searchHit.getSource().get("Url"));
			resultMap.put("PicUrl", searchHit.getSource().get("PicUrl"));
			if (searchHit.getSource().get("SegItem") != null)
				resultMap.put("SegItem", searchHit.getSource().get("SegItem"));
			StringBuffer source = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Source") != null)
				for (Text text : searchHit.getHighlightFields().get("Source")
						.getFragments()) {
					source.append(text.toString());
				}
			if ("".equals(source.toString())) {
				resultMap.put("Source", searchHit.getSource().get("Source"));
			} else {
				resultMap.put("Source", source.toString());
			}
			StringBuffer title = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Title") != null)
				for (Text text : searchHit.getHighlightFields().get("Title")
						.getFragments()) {
					title.append(text.toString());
				}
			if ("".equals(title.toString())) {
				resultMap.put("Title", searchHit.getSource().get("Title"));
			} else {
				resultMap.put("Title", title.toString());
			}
			StringBuffer content = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Content") != null)
				for (Text text : searchHit.getHighlightFields().get("Content")
						.getFragments()) {
					content.append(text.toString());
				}
			if ("".equals(content.toString())) {
				resultMap.put("Content", searchHit.getSource().get("Content"));
			} else {
				resultMap.put("Content", content.toString());
			}
			resultHightligth.add(resultMap);
		}
		BigDecimal took = new BigDecimal(searchResponse.getTook().millis());
		String tookString = took.divide(new BigDecimal(1000)).toString();
		ESPage ep = new ESPage(null, resultHightligth, searchResponse.getHits()
				.getTotalHits(), tookString);
//		this.clientPool().returnClient(client);
		return ep;
	}

	/**
	 * 
	 * @param indices
	 *            索引
	 * @param type
	 *            类型
	 * @param queryString
	 *            查询字符串
	 * @param filterString
	 *            过滤字符串
	 * @param heightPreTag
	 *            高亮前缀
	 * @param heightPostTag
	 *            高亮后缀
	 * @param sortField
	 *            排序字段 CreateDate FavCount ClickCount LikeCount
	 * @param sortOrder
	 *            排序方式 ASC OR DESC
	 * @param pageSize
	 *            每页数据
	 * @param pageNo
	 *            页码
	 * @return
	 */
	public ESPage queryAndFilterByString(String indices, String type,
			String queryString, String filterString, String heightPreTag,
			String heightPostTag, String sortField, String sortOrder,
			int pageSize, int pageNo) {
		log.info("queryString:" + queryString + "   filterString:"
				+ filterString);

		if (pageNo < 1) {
			throw new IllegalArgumentException(
					"The parameters PageNo not less than 0");
		}
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
		// Client client = this.clientPool().borrowClient();
		SearchRequestBuilder sqb = client.prepareSearch(indices);
		if (type != null && !"".equals(type)) {
		    sqb.setTypes(type.split(","));
		}

		if (queryString != null && !"".equals(queryString)) {
			CustomScoreQueryBuilder ssqb = QueryBuilders.customScoreQuery(
					QueryBuilders.queryString(queryString)).script(
					"_score + 0.01 * doc['FavCount'].value");
			sqb.setQuery(ssqb);
		} else {
			sqb.setQuery(QueryBuilders.matchAllQuery());
		}

		if (filterString != null && !"".equals(filterString)) {
			sqb.setFilter(FilterBuilders.queryFilter(QueryBuilders
					.queryString(filterString)));
		}
		if (heightPreTag != null && !"".equals(heightPreTag)
				&& !"".equals(heightPostTag) && heightPostTag != null) {
			sqb.setHighlighterPreTags(heightPreTag).setHighlighterPostTags(
					heightPostTag);
		}

		if (sortField != null
				&& !"".equals(sortField)
				&& (ElasticsearchService.SORT_ASC.equals(sortOrder) || ElasticsearchService.SORT_DESC
						.equals(sortOrder))) {
			if (ElasticsearchService.SORT_ASC.equals(sortOrder)) {
				sqb.addSort(sortField, SortOrder.ASC);
			} else {
				sqb.addSort(sortField, SortOrder.DESC);
			}
		}
		SearchResponse searchResponse = sqb.addHighlightedField("Title")
				.addHighlightedField("Source").addHighlightedField("Content")
				.setFrom((pageNo - 1) * pageSize).setSize(pageSize).execute()
				.actionGet();
		List<Map<String, Object>> resultHightligth = new ArrayList<Map<String, Object>>();
		for (SearchHit searchHit : searchResponse.getHits()) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Id", searchHit.getId());
			resultMap.put("Type", this.getType(searchHit.type()));
			resultMap
					.put("CreateDate", searchHit.getSource().get("CreateDate"));
			resultMap.put("CommCount", searchHit.getSource().get("CommCount"));
			resultMap.put("FavCount", searchHit.getSource().get("FavCount"));
			resultMap.put("LikeCount", searchHit.getSource().get("LikeCount"));
//			resultMap.put("Description",
//					searchHit.getSource().get("Description"));
			resultMap.put("Tags", searchHit.getSource().get("Tags"));
			resultMap.put("Url", searchHit.getSource().get("Url"));
			resultMap.put("PicUrl", searchHit.getSource().get("PicUrl"));
			if (searchHit.getSource().get("SegItem") != null)
				resultMap.put("SegItem", searchHit.getSource().get("SegItem"));
			StringBuffer source = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Source") != null)
				for (Text text : searchHit.getHighlightFields().get("Source")
						.getFragments()) {
					source.append(text.toString());
				}
			if ("".equals(source.toString())) {
				resultMap.put("Source", searchHit.getSource().get("Source"));
			} else {
				resultMap.put("Source", source.toString());
			}
			StringBuffer title = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Title") != null)
				for (Text text : searchHit.getHighlightFields().get("Title")
						.getFragments()) {
					title.append(text.toString());
				}
			if ("".equals(title.toString())) {
				resultMap.put("Title", searchHit.getSource().get("Title"));
			} else {
				resultMap.put("Title", title.toString());
			}
			StringBuffer content = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Content") != null)
				for (Text text : searchHit.getHighlightFields().get("Content")
						.getFragments()) {
					content.append(text.toString());
				}
			if ("".equals(content.toString())) {
				resultMap.put("Content", searchHit.getSource().get("Content"));
			} else {
				resultMap.put("Content", content.toString());
			}
			resultHightligth.add(resultMap);
		}
		BigDecimal took = new BigDecimal(searchResponse.getTook().millis());
		String tookString = took.divide(new BigDecimal(1000)).toString();
		ESPage ep = new ESPage(null, resultHightligth, searchResponse.getHits()
				.getTotalHits(), tookString);
		// this.clientPool().returnClient(client);
		return ep;
	}

	/**
	 * 查询建议词
	 * 
	 * @param indices
	 *            索引
	 * @param field
	 *            字段
	 * @param keyword
	 *            关键词
	 * @param size
	 *            建议词数量
	 * @return 建议词
	 */
	public List<String> findSuggestions(String indices, String field,
			String keyword, Integer size) {
		// Client client = this.clientPool().borrowClient();
		List<String> suggests = new SuggestRequestBuilder(client)
				.setIndices(indices).field(field).term(keyword).size(size)
				.execute().actionGet().getSuggestions();
		// this.clientPool().returnClient(client);
		return suggests;
	}

	/**
	 * 
	 * @param indices
	 *            索引
	 * @param type
	 *            类型
	 * @param termName
	 *            term字段
	 * @param termValue
	 *            term值
	 * @param filterString
	 *            过滤字符串
	 * @param pageSize
	 *            每页大小
	 * @param pageNo
	 *            页码
	 * @return
	 */
	public ESPage queryByTerm(String indices, String type, String termName,
			String termValue, String filterString, int pageSize, int pageNo) {
		log.info("termString:" + termName + "   filterString:" + filterString);
		if (pageNo < 1) {
			throw new IllegalArgumentException(
					"The parameters PageNo not less than 0");
		}
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
//		Client client = this.clientPool().borrowClient();
		SearchRequestBuilder sqb = client.prepareSearch(indices);
		if (type != null && !"".equals(type)) {
			sqb.setTypes(type);
		}

		if (termName != null && !"".equals(termName) && termValue != null
				&& !"".equals(termValue)) {
			sqb.setQuery(QueryBuilders.termQuery(termName, termValue));
		}
		if (filterString != null && !"".equals(filterString)) {
			sqb.setFilter(FilterBuilders.queryFilter(QueryBuilders
					.queryString(filterString)));
		}

		SearchResponse searchResponse = sqb.addHighlightedField("Title")
				.addHighlightedField("Source").addHighlightedField("Content")
				.setFrom((pageNo - 1) * pageSize).setSize(pageSize).execute()
				.actionGet();
		List<Map<String, Object>> resultHightligth = new ArrayList<Map<String, Object>>();
		for (SearchHit searchHit : searchResponse.getHits()) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("Id", searchHit.getId());
			resultMap.put("Type", this.getType(searchHit.type()));
			resultMap
					.put("CreateDate", searchHit.getSource().get("CreateDate"));
			resultMap.put("CommCount", searchHit.getSource().get("CommCount"));
			resultMap.put("FavCount", searchHit.getSource().get("FavCount"));
			resultMap.put("LikeCount", searchHit.getSource().get("LikeCount"));
//			resultMap.put("Description",
//					searchHit.getSource().get("Description"));
			resultMap.put("Tags", searchHit.getSource().get("Tags"));
			resultMap.put("Url", searchHit.getSource().get("Url"));
			resultMap.put("PicUrl", searchHit.getSource().get("PicUrl"));
			if (searchHit.getSource().get("SegItem") != null)
				resultMap.put("SegItem", searchHit.getSource().get("SegItem"));
			StringBuffer source = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Source") != null)
				for (Text text : searchHit.getHighlightFields().get("Source")
						.getFragments()) {
					source.append(text.toString());
				}
			if ("".equals(source.toString())) {
				resultMap.put("Source", searchHit.getSource().get("Source"));
			} else {
				resultMap.put("Source", source.toString());
			}
			StringBuffer title = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Title") != null)
				for (Text text : searchHit.getHighlightFields().get("Title")
						.getFragments()) {
					title.append(text.toString());
				}
			if ("".equals(title.toString())) {
				resultMap.put("Title", searchHit.getSource().get("Title"));
			} else {
				resultMap.put("Title", title.toString());
			}
			StringBuffer content = new StringBuffer("");
			if (searchHit.getHighlightFields().get("Content") != null)
				for (Text text : searchHit.getHighlightFields().get("Content")
						.getFragments()) {
					content.append(text.toString());
				}
			if ("".equals(content.toString())) {
				resultMap.put("Content", searchHit.getSource().get("Content"));
			} else {
				resultMap.put("Content", content.toString());
			}
			resultHightligth.add(resultMap);
		}
		BigDecimal took = new BigDecimal(searchResponse.getTook().millis());
		String tookString = took.divide(new BigDecimal(1000)).toString();
		ESPage ep = new ESPage(null, resultHightligth, searchResponse.getHits()
				.getTotalHits(), tookString);
//		this.clientPool().returnClient(client);
		return ep;
	}

	/**
	 * 
	 * @param indices
	 *            索引
	 * @param type
	 *            类型
	 * @param queryString
	 *            查询字符串
	 * @param filterString
	 *            过滤字符串
	 * @param facetFiled
	 *            统计字段
	 * @param pageSize
	 *            没页数据
	 * @param pageNo
	 *            页码
	 * @return
	 */
	public ESPage getFacets(String indices, String type, String queryString,
			String filterString, String facetFiled, int pageSize, int pageNo) {
		log.info("queryString:" + queryString + "   filterString:"
				+ filterString);
		if (pageNo < 1) {
			throw new IllegalArgumentException(
					"The parameters PageNo not less than 0");
		}
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
//		Client client = this.clientPool().borrowClient();
		SearchRequestBuilder sqb = client.prepareSearch(indices);
		if (type != null && !"".equals(type)) {
			sqb.setTypes(type);
		}

		if (queryString != null && !"".equals(queryString)) {
			CustomScoreQueryBuilder ssqb = QueryBuilders.customScoreQuery(
					QueryBuilders.queryString(queryString)).script(
					"_score + 0.01 * doc['FavCount'].value");
			sqb.setQuery(ssqb);
		} else {
			sqb.setQuery(QueryBuilders.matchAllQuery());
		}

		sqb.addFacet(FacetBuilders
				.termsFacet("facet_result")
				.field(facetFiled)
				.size(100000000)
				.order(org.elasticsearch.search.facet.terms.TermsFacet.ComparatorType.COUNT));
		if (filterString != null && !"".equals(filterString)) {
			sqb.setFilter(FilterBuilders.queryFilter(QueryBuilders
					.queryString(filterString)));
		}
		SearchResponse searchResponse = sqb.execute().actionGet();
		InternalStringTermsFacet facet = (InternalStringTermsFacet) searchResponse
				.getFacets().getFacets().get("facet_result");

		// 总记录数
		int totalCount = facet.entries().size();
		int startIndex = (pageNo - 1) * pageSize;
		// 结束索引大于总数和
		int endIndex = (startIndex + pageSize + 1 >= totalCount) ? totalCount
				: startIndex + pageSize;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = startIndex; i < endIndex; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Term", facet.entries().get(i).getTerm());
			map.put("Count", facet.getEntries().get(i).getCount());
			result.add(map);
		}
		BigDecimal took = new BigDecimal(searchResponse.getTook().millis());
		String tookString = took.divide(new BigDecimal(1000)).toString();
		ESPage ep = new ESPage(null, result, totalCount, tookString);
//		this.clientPool().returnClient(client);
		return ep;
	}

	
	/**
	 * 查询分词
	 * @param indices  索引 
	 * @param analyzer 分词方式,天佑网使用的是 ik_analyzer
	 * @param text     要分词的字符串
	 * @return
	 */
	public List<String> findAnalyze(String indices,String analyzer,String text) {
//		Client client = this.clientPool().borrowClient();
		if (indices == null || "".equals(indices)) {
			throw new IllegalArgumentException(
					"Parameter indices can not be empty");
		}
		if (text == null || "".equals(text)) {
			throw new IllegalArgumentException(
					"Parameter text can not be empty");
		}
		if (analyzer == null || "".equals(analyzer)) {
			throw new IllegalArgumentException(
					"Parameter analyzer can not be empty");
		}
		AnalyzeRequest ar = new AnalyzeRequest(indices, text);
		ar.analyzer(analyzer);
		List<AnalyzeToken> tokens = client.admin().indices().analyze(ar)
				.actionGet().getTokens();
		List<String> mytokens = new ArrayList<String>();
		for (int i = 0; i < tokens.size(); i++) {
			mytokens.add(tokens.get(i).getTerm());
		}
//		this.clientPool().returnClient(client);
		return mytokens;
	}
	
	
	public int getType(String hitType){
		if ("segment".equals(hitType)) {
			return 2;
		}else if ("form".equals(hitType)) {
			return 4;
		}else if ("image".equals(hitType)) {
			return 5;
		}else if ("video".equals(hitType)) {
			return 3;
		}else if ("file".equals(hitType)) {
			return 1;
		}else if("ppt".equals(hitType)){
			return 6;
		}else{
			return 0;
		}
	}

}