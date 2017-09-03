package com.yoti.test.db;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;

public abstract class AbstractAsyncWriter<M> {
	
	private ObjectMapper mapper = new ObjectMapper();

	public abstract MongoCollection<Document> getCollection();
	
	public abstract String getCollectionName();
	
	public void write(M object, SingleResultCallback<Void> callback) throws Exception {
		
		Document doc = Document.parse(mapper.writeValueAsString(object));
		
		getCollection().insertOne(doc, callback);
	}

}
