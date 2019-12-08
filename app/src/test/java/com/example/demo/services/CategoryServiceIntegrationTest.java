package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entities.CategoryEntity;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.utils.StringUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
@Transactional
public class CategoryServiceIntegrationTest {
	@Autowired
	CategoryRepository repo;

	@Autowired
	DefaultService<CategoryEntity> categoryService;

	List<CategoryEntity> mockEntities = new ArrayList<>();

	@BeforeAll
	public void before() {
	}

	@Test
	public void findAll_withNElement_returnListNElement() {
		// Arrange
		CategoryEntity alex = CategoryEntity.builder().name("Alex").build();
		mockEntities.add(alex);
		repo.saveAll(mockEntities);

		// Act
		List<CategoryEntity> entities = categoryService.findAll();

		log.info(entities.toString());

		// Assert
		assertEquals(mockEntities.size(), entities.size(), "He did not returned elements");

	}

	@Test
	public void findById_onPresentEntity_willReturnIt() {
		// Arrange

		String name = StringUtil.generateRandomChars();
		CategoryEntity categoryEntity = CategoryEntity.builder().name(name).build();
		categoryEntity = repo.save(categoryEntity);

		// Act
		CategoryEntity foundCategory = categoryService.findById(categoryEntity.getId()).get();

		// Assert
		assertEquals(categoryEntity, foundCategory, "He did not returned elements");
	}

	@Test
	public void create_onNewEntity_willCreateIt() {
		// Arrange
		String categoryName = "houblon";
		CategoryEntity newCategory = CategoryEntity.builder().name(categoryName).build();

		// Act
		CategoryEntity savedCategory = categoryService.save(newCategory);

		// Assert
		CategoryEntity category = repo.findById(savedCategory.getId()).get();
		assertEquals(categoryName, category.getName(), "He did not returned elements");

	}

	@Test
	public void save_onExistingEntity_willUpdateIt() {
		// Arrange
		CategoryEntity categoryEntity = CategoryEntity.builder().name(StringUtil.generateRandomChars()).build();
		categoryEntity = repo.save(categoryEntity);

		String name = StringUtil.generateRandomChars();

		// Act
		CategoryEntity newCategory = CategoryEntity.builder().id(categoryEntity.getId()).name(name).build();
		categoryService.save(newCategory);

		// Assert
		CategoryEntity category = repo.findById(categoryEntity.getId()).get();
		assertEquals(name, category.getName(), "He did not returned elements");

	}

	@Test
	public void delete_onPresentEntity_willDeleteIt() {
		// Arrange
		String name = StringUtil.generateRandomChars();
		CategoryEntity categoryEntity = CategoryEntity.builder().name(name).build();
		categoryEntity = repo.save(categoryEntity);

		// Act
		categoryService.delete(categoryEntity.getId());

		// Assert
		Optional<CategoryEntity> opt = repo.findById(categoryEntity.getId());
		assertTrue(!opt.isPresent(), "He did not returned elements");

	}
}
