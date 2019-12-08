package com.example.demo.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@MappedSuperclass
@NoArgsConstructor
public class DefaultEntity {
	@Id
	@GeneratedValue
	private Long id;
}
