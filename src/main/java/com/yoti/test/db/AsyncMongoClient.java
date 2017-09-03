package com.yoti.test.db;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;

@Component
public class AsyncMongoClient implements InitializingBean {
	
	@Value("${spring.data.mongodb.uri}")
	private String mongoDbUri;
	
	MongoClient mongoClient;
	
	MongoDatabase database;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		ConnectionString connectionString = new ConnectionString(mongoDbUri);
		
		mongoClient = MongoClients.create(connectionString);
		
		database = mongoClient.getDatabase(connectionString.getDatabase());
	}
	
	@PreDestroy
	public void cleanUp(){
		
		if (mongoClient != null) {
			
			mongoClient.close();
		}
	}
	
	public MongoDatabase getDatabase() {
		
		return database;
	}
}
