package com.example.demo.services;

import com.example.demo.entities.Category;

//@SpringBootTest
//@TestInstance(Lifecycle.PER_CLASS)
//@Slf4j
//@Transactional
public class CategoryServiceIntegrationTest extends DefaultServiceIntegrationTest<Category> {

	@Override
	public Category generateEntity() {
		return Category.builder().name("Alex").build();
	}

	@Override
	public Category modifyEntity(Category t) {
		return t.toBuilder().name("pwe").build();
	}

//	@Autowired
//	CategoryRepository repo;
//
//	@Autowired
//	DefaultService<Category> categoryService;
//
//	List<Category> mockEntities = new ArrayList<>();
//
//	@BeforeAll
//	public void before() {
//	}
//
//	@Test
//	public void findAll_withNElement_returnListNElement() {
//		// Arrange
//		Category alex = Category.builder().name("Alex")
//
//				.build();
//		mockEntities.add(alex);
//		repo.saveAll(mockEntities);
//
//		// Act
//		List<Category> entities = categoryService.findAll();
//
//		log.info(entities.toString());
//
//		// Assert
//		assertEquals(mockEntities.size(), entities.size(), "He did not returned elements");
//
//	}
//
//	@Test
//	public void findById_onPresentEntity_willReturnIt() {
//		// Arrange
//
//		String name = StringUtil.generateRandomChars();
//		Category categoryEntity = Category.builder().name(name).build();
//		categoryEntity = repo.save(categoryEntity);
//
//		// Act
//		Category foundCategory = categoryService.findById(categoryEntity.getId()).get();
//
//		// Assert
//		assertEquals(categoryEntity, foundCategory, "He did not returned elements");
//	}
//
//	@Test
//	public void readFromDB_onPrePersistedEntity_willReturnItWithAudit() {
//		// Arrange
//
//		String name = StringUtil.generateRandomChars();
//		Category categoryEntity = Category.builder().name(name).build();
//		categoryEntity = repo.save(categoryEntity);
//
//		// Act
//		Category foundCategory = categoryService.findById(categoryEntity.getId()).get();
//		log.info("Category looks like in json : {}", foundCategory.asJsonString());
//		log.info("Category looks like as tostring : {}", foundCategory.toString());
//
//		// Assert
//		assertTrue(foundCategory.getLastModifiedDate() != null, "Audit did not create LastModifiedDate");
//		assertTrue(foundCategory.getCreatedDate() != null, "Audit did not create CreatedDate");
//	}
//
//	@Test
//	public void create_onNewEntity_willCreateIt() {
//		// Arrange
//		String categoryName = "houblon";
//		Category newCategory = Category.builder().name(categoryName).build();
//
//		// Act
//		Category savedCategory = categoryService.save(newCategory);
//
//		// Assert
//		Category category = repo.findById(savedCategory.getId()).get();
//		assertEquals(categoryName, category.getName(), "He did not returned elements");
//
//	}
//
//	@Test
//	public void save_onExistingEntity_willUpdateIt() {
//		// Arrange
//		Category categoryEntity = Category.builder().name(StringUtil.generateRandomChars()).build();
//		categoryEntity = repo.save(categoryEntity);
//
//		String name = StringUtil.generateRandomChars();
//
//		// Act
//		Category newCategory = Category.builder().id(categoryEntity.getId()).name(name).build();
//		categoryService.save(newCategory);
//
//		// Assert
//		Category category = repo.findById(categoryEntity.getId()).get();
//		assertEquals(name, category.getName(), "He did not returned elements");
//
//	}
//
//	@Test
//	public void delete_onPresentEntity_willDeleteIt() {
//		// Arrange
//		String name = StringUtil.generateRandomChars();
//		Category categoryEntity = Category.builder().name(name).build();
//		categoryEntity = repo.save(categoryEntity);
//
//		// Act
//		categoryService.delete(categoryEntity.getId());
//
//		// Assert
//		Optional<Category> opt = repo.findById(categoryEntity.getId());
//		assertTrue(!opt.isPresent(), "He did not returned elements");
//
//	}
}
