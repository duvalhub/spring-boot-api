package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Category;
import com.example.demo.repositories.DefaultRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryService extends DefaultService<Category> {
	public CategoryService(DefaultRepository<Category> repo) {
		super(repo);
	}

}
