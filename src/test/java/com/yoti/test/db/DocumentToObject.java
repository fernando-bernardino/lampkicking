package com.yoti.test.db;

import org.bson.Document;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DocumentToObject {

	private static ObjectMapper mapper = new ObjectMapper();
	
	static {
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static <M> M toObject(Document document, Class<M> clazz) {
		
		try {
			
			return (M) mapper.readValue(document.toJson(), clazz);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw new RuntimeException();
		}
	}}
