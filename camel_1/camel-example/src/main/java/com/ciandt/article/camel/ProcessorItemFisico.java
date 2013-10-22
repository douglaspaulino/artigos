package com.ciandt.article.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorItemFisico implements Processor  {

	private static Logger logger = LoggerFactory.getLogger(ProcessorItemFisico.class
			.getName());
	
	public void process(Exchange exchange) throws Exception {
		
		Thread.sleep(1000);
		
		logger.info(exchange.getProperty("CamelSplitIndex").toString());
		logger.info(exchange.getIn().getBody().toString());
		
	}

}
