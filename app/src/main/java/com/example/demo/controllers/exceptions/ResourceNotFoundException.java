package com.example.demo.controllers.exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {
	String code;
	String message;

}
