package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Category;
import com.example.demo.services.DefaultService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController extends DefaultController<Category> {

	public CategoryController(DefaultService<Category> service) {
		super(service);
	}

}
