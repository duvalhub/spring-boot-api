package com.example.demo.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EntityUtil {

	public static List<Category> generateCategoryEntities(int size) {
		List<Category> entities = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			entities.add(generateCategoryEntity());
		}
		return entities;
	}

	public static Category generateCategoryEntity() {
		return Category.builder().name(StringUtil.generateRandomChars()).build();
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> Object convertJSONStringToObject(String json, Class<T> objectClass) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		JavaTimeModule module = new JavaTimeModule();
		mapper.registerModule(module);
		return mapper.readValue(json, objectClass);
	}
}
