package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entities.DefaultEntity;
import com.example.demo.repositories.DefaultRepository;

import lombok.Data;

@Service
@Data
public class DefaultService<T extends DefaultEntity> {

	private final DefaultRepository<T> repo;

	public List<T> findAll() {
		return repo.findAll();
	}

	public Optional<T> findById(Long id) {
		return repo.findById(id);
	}

	public T save(T t) {
		return repo.save(t);
	}

	public T save(Long id, T t) {
		t.setId(id);
		return repo.save(t);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}

}
