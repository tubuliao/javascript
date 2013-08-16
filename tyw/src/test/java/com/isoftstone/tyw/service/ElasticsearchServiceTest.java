package com.isoftstone.tyw.service;

public class ElasticsearchServiceTest {
	public static void  main(String [] args)throws Exception{
		ElasticsearchService es = new ElasticsearchService();
		// System.out.println(es.findSuggestions("title", "标", 3, "tyw"));
	    // es.createIndex("tyw");
		//es.createMapping("tyw", "base");
//		for(int i=0;i<100000;i++){
//			System.out.println(es.findSuggestions("tyw","Title", "建筑", 10));
//			log.info(i+"active:"+new ElasticsearchService().clientPool().getNumberActive());
//			log.info("idle:"+new ElasticsearchService().clientPool().getNumberIdle());
//		}
//		log.info("active:"+new ElasticsearchService().clientPool().getNumberActive());
//		log.info("idle:"+new ElasticsearchService().clientPool().getNumberIdle());
//
//		System.out.println(es.queryIndexData("tyw", "base", "建筑", 20,0).getHighlighterResult());
//		log.info("init over!");
//		 es.queryIndexData("tyw","title","标题",1,1);
//		log.info("over");
		
//		QueryBuilder qb=QueryBuilders.queryString("(Tags:质量验收 质量验收) 110～500KV").defaultOperator(Operator.AND);
//		Client client = es.clientPool().borrowClient();
//		SearchResponse searchResponse=client.prepareSearch("tyw").setTypes("base").setQuery(qb).setHighlighterPreTags("<span style='color:red'>").setHighlighterPostTags("</span>")
//		        .addHighlightedField("Title").addHighlightedField("Source").setExplain(true).setFrom(0).setSize(500).execute().actionGet();
//		Iterator<SearchHit> iterator=searchResponse.getHits().iterator();
//		while(iterator.hasNext()){
//			System.out.println(iterator.next().getSource());
//			System.out.println(iterator.next().getHighlightFields().get("Title").getFragments()[0]);
//		}
		//System.out.println(searchResponse.getHits().iterator().next().getSource());
		

//		System.out.println(es.queryByStringBuilder("tyw", null, "AND","建筑AND(Tags:北京市OR吉林省OR黑龙江省)AND(Tags:分布分项)AND(Tags:资料员)AND(Tags:测试)","","",10,1).getTotalElements());
	
//		(Tags:施工资料)AND(Tags:地基与基础工程)AND(Tags:天津市)
//		System.out.println(es.queryAndFilterByString("tyw", "test", null,"(Tags:核心条文)AND(Tags:技术管理人员)AND(Title:埋地钢质管道防腐保温层的成品管性能检验)AND(Source.name:《埋地钢质管道防腐保温层技术标准》GB/T50538-2010)","","",10,1).getTotalElements());
//		System.out.println(es.queryByTerm("tyw","test", "Source.untouched", "《埋地钢质管道防腐保温层技术标准》GB/T50538-2010", "(Tags:核心条文)AND(Tags:湖北省)AND(Tags:地基与基础工程)", 10,1).getHighlighterResult());
//		System.out.println(es.getFacets("tyw", "segment","", "(Tags:金属结构工程)AND(Tags:核心条文)","Source.unsource", 10,3).getTotalElements());
//		System.out.println(es.getFacets("tyw", "test","", "(Tags:地基与基础工程特殊季节施工)AND(Tags:核心条文)","Source.untouched", 10,3).getTotalElements());
//
//		System.out.println(es.getFacets("tyw", "segment","", "(Tags:地基与基础工程)AND(Tags:核心条文)","Source.unsource", 10,3).getTotalElements());
//		System.out.println(es.getFacets("tyw", "test","", "(Tags:地基与基础工程)AND(Tags:核心条文)","Source.untouched", 10,3).getTotalElements());

//		System.out.println(es.getFacets("tyw", "segment","", "(Tags:地基与基础工程)AND(Tags:核心条文)","Source.unsource", 10,3).getTotalTook());
		System.out.println(es.findAnalyze("tyw", "ik_analyzer","北京市建筑工程表格"));
		
	}
}
