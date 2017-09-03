package com.yoti.test.db;

import static com.yoti.test.entities.SimulationObjectsBuilder.createMeaninfulSimulationResult;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.yoti.test.entities.SimulationResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SimulationErrorAsyncWriterTestIT {

	@Autowired
	SimulationResultAsyncWriter writer;
	
	@Autowired
	TestSyncMongoDatabase testSyncMongoDatabase;	
	
	@Autowired
	SynchronousCaller synchronousCaller;
	
	
	String collectionName;
		
	
	@Before
	public void setup() throws Exception {
		
		collectionName = writer.getCollectionName();
		
		clearCollection();
	}
	
	@After
	public void clearCollection() throws Exception {
		
		testSyncMongoDatabase.clearCollection(writer.getCollectionName());
	}
	
	
	private long count = 0;
	
	private SimulationResult result = null;
	
	@Test
	public void write_successfulInsert() throws Exception {

		SimulationResult result = createMeaninfulSimulationResult();
		
		
		synchronousCaller.execute((callback) -> 
		
				//	when
				writer.write(result, callback)
		);
		
		testSyncMongoDatabase.count(collectionName, (count) -> this.count = count);
		testSyncMongoDatabase.getFirstDocument(collectionName, SimulationResult.class, (document) -> this.result = document);
		
		assertEquals(1L, count);
		assertEquals(result, this.result);
	}
}
