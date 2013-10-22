package com.ciandt.article.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 * 
 */
public class File2JmsJava {
	
	
	private static Logger logger = LoggerFactory.getLogger(File2JmsJava.class.getName());
	
	public static void main(String[] args) throws Exception {
		
		CamelContext camelContext = new DefaultCamelContext();
		
		ConnectionFactory connectionFactory =
				new ActiveMQConnectionFactory("tcp://localhost:61616");
		camelContext.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		camelContext.setMessageHistory(true);
		camelContext.setTracing(true);
		
		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				
				from("file:dados/recebidos?noop=true")
					.to("file:dados/processados2")
					.to("jms:destino")
					.process(new ProcessorItemFisico());
				
				
			}
			
		});

		camelContext.start();

		Thread.sleep(2000);

		camelContext.stop();
	}
}
