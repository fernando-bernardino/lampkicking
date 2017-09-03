package com.yoti.test.db;

import static com.yoti.test.entities.SimulationObjectsBuilder.createMeaninfulSimulationError;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.yoti.test.entities.SimulationError;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SimulationResultAsyncWriterTestIT {

	@Autowired
	SimulationErrorAsyncWriter writer;
	
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
	
	private SimulationError result = null;
	
	@Test
	public void write_successfulInsert() throws Exception {

		SimulationError error = createMeaninfulSimulationError();
		
		
		synchronousCaller.execute((callback) -> 
		
				//	when
				writer.write(error, callback)
		);
		
		testSyncMongoDatabase.count(collectionName, (count) -> this.count = count);
		testSyncMongoDatabase.getFirstDocument(collectionName, SimulationError.class, (document) -> this.result = document);
		
		assertEquals(1L, count);
		assertEquals(error, result);
	}
}
