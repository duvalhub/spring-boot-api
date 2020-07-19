package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entities.DefaultEntity;
import com.example.demo.services.DefaultService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultController<T extends DefaultEntity> {
	private final DefaultService<T> service;

	@GetMapping
	public List<T> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public T getById(@PathVariable Long id) {
		return service.findById(id).get();
	}

	@PostMapping
	public T create(@Valid @RequestBody T t) {
		return service.save(t);
	}

	@PutMapping("/{id}")
	public T update(@PathVariable Long id, @Valid @RequestBody T t) {
		return service.save(id, t);
	}

	@DeleteMapping("/{id}")
	public void detete(@PathVariable Long id) {
		service.delete(id);
	}

}
