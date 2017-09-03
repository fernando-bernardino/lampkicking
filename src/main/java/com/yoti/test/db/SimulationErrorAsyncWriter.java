package com.yoti.test.db;

import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.async.client.MongoCollection;
import com.yoti.test.entities.SimulationError;

@Repository
public class SimulationErrorAsyncWriter extends AbstractAsyncWriter<SimulationError> implements InitializingBean {
	
	@Autowired
	private AsyncMongoClient mongoClient;
	
	
	private MongoCollection<Document> collection;
	
	
	private String collectionName = "SimulationError";

	@Override
	public void afterPropertiesSet() throws Exception {
		collection = mongoClient.getDatabase().getCollection(collectionName);
	}
	
	@Override
	public String getCollectionName() {
		return collectionName;
	}

	@Override
	public MongoCollection<Document> getCollection() {
		return collection;
	}
}
