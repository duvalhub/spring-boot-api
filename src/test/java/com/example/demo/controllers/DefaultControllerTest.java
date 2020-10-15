package com.example.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.DefaultEntity;
import com.example.demo.repositories.DefaultRepository;
import com.example.demo.utils.EntityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
@Transactional
public abstract class DefaultControllerTest<T extends DefaultEntity> {
	@Autowired
	DefaultRepository<T> repo;
	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void onRequestAll_withNElements_returnArrayOfNElements() throws Exception {
		// Arrange
		int size = 4;
		List<T> categories = this.generateEntities(size);
		categories = repo.saveAll(categories);

		// Act
		// Assert
		this.mockMvc.perform(get(this.getEndpoint())).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(size)));

	}

	@Test
	public void onRequestAll_with0Elements_returnArrayOf0Elements() throws Exception {
		// Arrange
		// Act
		// Assert
		this.mockMvc.perform(get(this.getEndpoint())).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

	}

	@Test
	public void onRequestById_withExistingElement_returnElement() throws Exception {
		// Arrange
		T entity = this.generateEntity();
		entity = repo.save(entity);

		// Act
		// Assert
		String endpoint = String.format("%s/%s", this.getEndpoint(), entity.getId());
		String json = this.mockMvc.perform(get(endpoint)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		T returnedEntity = (T) EntityUtil.convertJSONStringToObject(json, this.getEntityClass());

		assertEquals(entity, returnedEntity, "Get by Id didn't returned expected entity.");

	}

	@Test
	public void onRequestById_withMissingElement_returnStatus404() throws Exception {
		// Arrange
		// Act
		// Assert
		this.mockMvc.perform(get(this.getEndpoint() + "/1")).andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	public void onCreate_withValidElement_createElement() throws Exception {
		// Arrange
		T mockEntity = this.generateEntity();

		// Act
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(this.getEndpoint()).content(mockEntity.asJsonString())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String json = mvcResult.getResponse().getContentAsString();
		T returnedEntity = (T) EntityUtil.convertJSONStringToObject(json, this.getEntityClass());

		// Assert
		T entity = repo.findById(returnedEntity.getId()).get();
		assertEquals(mockEntity, entity, "Post by id is misimplemented");

	}

	@Test
	public void onCreate_withInValidElement_return400() throws Exception {
		// Arrange
		T mockEntity = this.generateInvalidEntity();

		// Act
		// Assert
		mockMvc.perform(MockMvcRequestBuilders.post(this.getEndpoint()).content(mockEntity.asJsonString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void onUpdate_withValidElement_createElement() throws Exception {
		// Arrange
		T mockEntity = this.generateEntity();
		Long id = repo.save(this.generateEntity()).getId();
		mockEntity = this.modifyEntity(mockEntity);
		Long randomLong = new Random().nextLong();
		mockEntity.setId(randomLong);

		// Act
		String endpoint = String.format("%s/%s", this.getEndpoint(), id);
		mockMvc.perform(MockMvcRequestBuilders.put(endpoint).content(mockEntity.asJsonString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		// Assert
		T entity = repo.findById(id).get();
		assertEquals(mockEntity, entity, "Post by id is misimplemented");

	}

	@Test
	public void onUpdate_withInValidElement_return400() throws Exception {
		// Arrange
		T mockEntity = this.generateInvalidEntity();

		// Act
		// Assert
		String endpoint = String.format("%s/%s", this.getEndpoint(), mockEntity.getId());
		mockMvc.perform(MockMvcRequestBuilders.put(endpoint).content(mockEntity.asJsonString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void onDelete_withValidElement_deleteIt() throws Exception {
		// Arrange
		T mockEntity = this.generateEntity();
		mockEntity = repo.save(mockEntity);

		// Act
		String endpoint = String.format("%s/%s", this.getEndpoint(), mockEntity.getId());
		mockMvc.perform(MockMvcRequestBuilders.delete(endpoint).content(mockEntity.asJsonString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		// Assert
		Optional<T> opt = repo.findById(mockEntity.getId());
		assertTrue(!opt.isPresent(), "Entity was not well deleted");

	}

	public abstract Class<T> getEntityClass();

	public abstract T modifyEntity(T t);

	public abstract String getEndpoint();

	public abstract T generateEntity();

	public abstract T generateInvalidEntity();

	public abstract List<T> generateEntities(int size);

}
