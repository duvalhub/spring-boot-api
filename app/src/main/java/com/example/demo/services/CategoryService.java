package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.entities.CategoryEntity;
import com.example.demo.repositories.DefaultRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService extends DefaultService<CategoryEntity> {
	public CategoryService(DefaultRepository<CategoryEntity> repo) {
		super(repo);
	}

}
