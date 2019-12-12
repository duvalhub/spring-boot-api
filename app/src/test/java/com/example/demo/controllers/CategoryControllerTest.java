package com.example.demo.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import com.example.demo.entities.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.utils.EntityUtil;
import com.example.demo.utils.StringUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
@Transactional
public class CategoryControllerTest {

	private final String CATEGORY_ENDPOINT = "/api/v1/categories";
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoryRepository repo;

	@Test
	public void onRequestAll_withNElements_returnArrayOfNElements() throws Exception {
		// Arrange
		int size = 4;
		List<Category> categories = EntityUtil.generateCategoryEntities(size);
		categories = repo.saveAll(categories);

		// Act
		// Assert
		this.mockMvc.perform(get(CATEGORY_ENDPOINT)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(size)));

	}

	@Test
	public void onRequestAll_with0Elements_returnArrayOf0Elements() throws Exception {
		// Arrange
		// Act
		// Assert
		this.mockMvc.perform(get(CATEGORY_ENDPOINT)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

	}

	@Test
	public void onRequestById_withExistingElement_returnElement() throws Exception {
		// Arrange
		String name = StringUtil.generateRandomChars();
		Category entity = Category.builder().name(name).build();
		entity = repo.save(entity);

		// Act
		// Assert
		String endpoint = String.format("%s/%s", CATEGORY_ENDPOINT, entity.getId());
		this.mockMvc.perform(get(endpoint)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(name)));

	}

	@Test
	public void onRequestById_withMissingElement_returnStatus404() throws Exception {
		// Arrange
		// Act
		// Assert
		this.mockMvc.perform(get(CATEGORY_ENDPOINT + "/1")).andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	public void onCreate_withValidElement_createElement() throws Exception {
		// Arrange
		Category mockEntity = EntityUtil.generateCategoryEntity();

		// Act
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post(CATEGORY_ENDPOINT).content(mockEntity.asJsonString())
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String json = mvcResult.getResponse().getContentAsString();
		Category returnedEntity = (Category) EntityUtil.convertJSONStringToObject(json,
				Category.class);

		// Assert
		Category entity = repo.findById(returnedEntity.getId()).get();
		assertEquals(mockEntity.getName(), entity.getName(), "Post by id is misimplemented");

	}

	@Test
	public void onCreate_withInValidElement_return400() throws Exception {
		// Arrange
		Category mockEntity = Category.builder().build();

		// Act
		// Assert
		mockMvc.perform(MockMvcRequestBuilders.post(CATEGORY_ENDPOINT).content(mockEntity.asJsonString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void onUpdate_withValidElement_createElement() throws Exception {
		// Arrange
		Category mockEntity = EntityUtil.generateCategoryEntity();
		Long id = repo.save(EntityUtil.generateCategoryEntity()).getId();
		mockEntity.setName(StringUtil.generateRandomChars());
		Long randomLong = new Random().nextLong();
		mockEntity.setId(randomLong);

		// Act
		String endpoint = String.format("%s/%s", CATEGORY_ENDPOINT, id);
		mockMvc.perform(MockMvcRequestBuilders.put(endpoint).content(mockEntity.asJsonString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		// Assert
		Category entity = repo.findById(id).get();
		assertEquals(mockEntity.getName(), entity.getName(), "Post by id is misimplemented");

	}

	@Test
	public void onUpdate_withInValidElement_return400() throws Exception {
		// Arrange
		Category mockEntity = Category.builder().build();

		// Act
		// Assert
		String endpoint = String.format("%s/%s", CATEGORY_ENDPOINT, mockEntity.getId());
		mockMvc.perform(MockMvcRequestBuilders.put(endpoint).content(mockEntity.asJsonString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void onDelete_withValidElement_deleteIt() throws Exception {
		// Arrange
		Category mockEntity = EntityUtil.generateCategoryEntity();
		mockEntity = repo.save(mockEntity);

		// Act
		String endpoint = String.format("%s/%s", CATEGORY_ENDPOINT, mockEntity.getId());
		mockMvc.perform(MockMvcRequestBuilders.delete(endpoint).content(mockEntity.asJsonString())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		// Assert
		Optional<Category> opt = repo.findById(mockEntity.getId());
		assertTrue(!opt.isPresent(), "Entity was not well deleted");

	}

}
