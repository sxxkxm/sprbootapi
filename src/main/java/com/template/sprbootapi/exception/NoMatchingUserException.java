package com.template.sprbootapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

/**
 *  찾으려고 하는 Account가 없을 때 사용되는 오류
 *  HttpStatus 404
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
@NoArgsConstructor
public class NoMatchingUserException extends RuntimeException {
	public NoMatchingUserException(String message) {
		super(message);
	}
}