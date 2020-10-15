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

import com.example.demo.entities.DefaultEntity;
import com.example.demo.repositories.DefaultRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
@Transactional
public abstract class DefaultServiceIntegrationTest<T extends DefaultEntity> {
	@Autowired
	DefaultRepository<T> repo;

	@Autowired
	DefaultService<T> service;

	List<T> mockEntities = new ArrayList<>();

	@BeforeAll
	public void before() {
	}

	@Test
	public void findAll_withNElement_returnListNElement() {
		// Arrange
		T entity = this.generateEntity();
		mockEntities.add(entity);
		repo.saveAll(mockEntities);

		// Act
		List<T> entities = service.findAll();

		log.info(entities.toString());

		// Assert
		assertEquals(mockEntities, entities, "He did not returned elements");

	}

	@Test
	public void findById_onPresentEntity_willReturnIt() {
		// Arrange
		T entity = this.generateEntity();
		entity = repo.save(entity);

		// Act
		T foundEntity = service.findById(entity.getId()).get();

		// Assert
		assertEquals(entity, foundEntity, "He did not returned elements");
	}

	@Test
	public void readFromDB_onPrePersistedEntity_willReturnItWithAudit() {
		// Arrange
		T entity = this.generateEntity();
		entity = repo.save(entity);

		// Act
		T foundT = service.findById(entity.getId()).get();

		// Assert
		assertTrue(foundT.getLastModifiedDate() != null, "Audit did not create LastModifiedDate");
		assertTrue(foundT.getCreatedDate() != null, "Audit did not create CreatedDate");
	}

	@Test
	public void create_onNewEntity_willCreateIt() {
		// Arrange
		T entity = this.generateEntity();

		// Act
		entity = service.save(entity);

		// Assert
		T returnedEntity = repo.findById(entity.getId()).get();
		assertEquals(entity, returnedEntity, "He did not returned elements");

	}

	@Test
	public void save_onExistingEntity_willUpdateIt() {
		// Arrange
		T entity = this.generateEntity();
		entity = repo.save(entity);

		// Act
		T modifiedEntity = this.modifyEntity(entity);
		service.save(modifiedEntity);

		// Assert
		T returnedEntity = repo.findById(entity.getId()).get();
		assertEquals(entity, returnedEntity, "He did not returned elements");

	}

	@Test
	public void delete_onPresentEntity_willDeleteIt() {
		// Arrange
		T entity = this.generateEntity();
		entity = repo.save(entity);

		// Act
		service.delete(entity.getId());

		// Assert
		Optional<T> opt = repo.findById(entity.getId());
		assertTrue(!opt.isPresent(), "He did not returned elements");

	}

	public abstract T generateEntity();

	public abstract T modifyEntity(T t);

}
