package com.ciandt.article.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorItemDownload implements Processor  {

	private static Logger logger = LoggerFactory.getLogger(ProcessorItemDownload.class
			.getName());
	
	public void process(Exchange exchange) throws Exception {
		
		// Exemplo da propriedade que pode ser utilizada após realizar o split da mensagem.		
//		logger.info(exchange.getProperty("CamelSplitIndex").toString());
		
		logger.info(exchange.getIn().getBody().toString());
		
	}

}
