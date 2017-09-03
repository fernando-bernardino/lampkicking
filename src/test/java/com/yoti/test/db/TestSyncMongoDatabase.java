package com.yoti.test.db;

import static com.yoti.test.db.DocumentToObject.toObject;

import java.util.function.Consumer;

import javax.annotation.PreDestroy;

import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;


@TestComponent
public class TestSyncMongoDatabase implements InitializingBean {

	
	@Value("${spring.data.mongodb.uri}")
	private String mongoDbUri;
	
	@Autowired
	SynchronousCaller synchronousCaller;

	
	private MongoClient mongoClient;
	
	
	private MongoDatabase database;

	
	public void clearCollection(String collectionName) throws Exception {
		
		MongoCollection<Document> collection = database.getCollection(collectionName);
		
		synchronousCaller.execute((callback) -> 

				collection.drop(callback));
	}

	public void count(String collectionName, Consumer<Long> callback) throws Exception {
			
		MongoCollection<Document> collection = database.getCollection(collectionName);

		synchronousCaller.execute(
				(innerCallback) -> collection.count(innerCallback),
				callback);
	}
	
	public <M> void getFirstDocument(String collectionName, Class<M> clazz, Consumer<M> callback) throws Exception {
		
		MongoCollection<Document> collection = database.getCollection(collectionName);


		synchronousCaller.execute((innerCallback) ->
			
				collection
						.find()
						.map(
								(document) -> toObject(document, clazz))
						.first(innerCallback),
						
				callback);
	}
	
	@PreDestroy
	public void cleanUp() throws Exception {
		mongoClient.close();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		ConnectionString connectionString = new ConnectionString(mongoDbUri);
		
		mongoClient = MongoClients.create(connectionString);
		
		database = mongoClient.getDatabase(connectionString.getDatabase());
	}
}
