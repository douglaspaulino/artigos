package com.ciandt.article.camel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class ArrayList2CSVAggregationStrategy implements AggregationStrategy {

	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Object newBody = newExchange.getIn().getBody();
		List<Map<String, Object>> csv;
		Map<String, Object> map = null;
		if (oldExchange == null) {
			map = new HashMap<String, Object>();
			
			String[] b = newBody.toString().split(",");
			for (int i = 0; i < b.length; i++) {
				map.put(String.valueOf(i), b[i].replaceAll("\\[", "").replaceAll("\\]", ""));
			}
			
			csv = new ArrayList<Map<String, Object>>();
			csv.add(map);
			newExchange.getIn().setBody(csv);
			return newExchange;
		} else {
			csv = oldExchange.getIn().getBody(ArrayList.class);
			String[] b = newBody.toString().split(",");
			map = new HashMap<String, Object>();
			for (int i = 0; i < b.length; i++) {
				map.put(String.valueOf(i), b[i].replaceAll("\\[", "").replaceAll("\\]", ""));
			}
			csv.add(map);
			return oldExchange;
		}
	}

}
