package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.CategoryEntity;
import com.example.demo.services.DefaultService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController extends DefaultController<CategoryEntity> {

	public CategoryController(DefaultService<CategoryEntity> service) {
		super(service);
	}

}
