package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.example.demo.entities.DefaultEntity;

@NoRepositoryBean
public interface DefaultRepository<T extends DefaultEntity> extends JpaRepository<T, Long> {

}
