package com.yoti.test.db;

import static com.yoti.test.db.DocumentToObject.toObject;
import static com.yoti.test.entities.SimulationObjectsBuilder.createMeaninfulSimulationResult;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mongodb.async.client.MongoCollection;
import com.yoti.test.entities.SimulationResult;

public class AbstractAsyncWriterTest {
	
	@Mock
	MongoCollection<Document> collection;
	
	@Captor
	ArgumentCaptor<Document> documentCaptor;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	AbstractAsyncWriter<SimulationResult> writer = new AbstractAsyncWriter<SimulationResult>(){

		@Override
		public MongoCollection<Document> getCollection() {
			return collection;
		}

		@Override
		public String getCollectionName() {
			return "SimulationResult";
		}
		
	};
	
	@Test
	public void write_validInput_correctDocumentIsWritten() throws Exception {
		
		SimulationResult simulationResult = createMeaninfulSimulationResult();
		
		writer.write(simulationResult, (result, throwable) -> {});
		
		Document document = getWrittenDocument();
		
		assertEquals(simulationResult, toObject(document, SimulationResult.class));
	}

	private Document getWrittenDocument() {
		
		verify(collection).insertOne(documentCaptor.capture(), any());
		
		return documentCaptor.getValue();
	}
}
