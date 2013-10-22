package com.ciandt.article.camel;

import java.util.ArrayList;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 * 
 */
public class FileJava {

	private static Logger logger = LoggerFactory.getLogger(FileJava.class
			.getName());

	public static void main(String[] args) throws Exception {
		
		// Criação da contexto camel.
		CamelContext camelContext = new DefaultCamelContext();
		// adicionando configuração das rotas
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				
				//ROTA 1: Responsável por receber o arquivo csv 
				//        e também separar o conteudo do arquivo em várias linhas
				from("file:dados/recebidos?noop=true&include=.*.csv$")
				.routeId("recebidos-csv")
					.unmarshal().csv()
						.split(body(ArrayList.class))
				.to("direct:processa");

				// ROTA: Separa os tipos de registros
				from("direct:processa")
				.routeId("processa-item")
				.choice()
					.when(simple("${body[2]} == 'download'"))
						.to("direct:processaItemDownload")
					.otherwise()
						.to("direct:processaItemFisico")
				.end();
				
				// ROTA: Processa itens para download
				from("direct:processaItemDownload")
				.routeId("processaItemDownload")
					.process(new ProcessorItemDownload()) ;
				
				// ROTA: Processa itens gerericos para entrega
				from("direct:processaItemFisico")
				.routeId("processaItemFisico")
						.process(new ProcessorItemFisico()) ;

			}
			
		});
		
		camelContext.start();

		Thread.sleep(2000);

		camelContext.stop();		

	}
}
