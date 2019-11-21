package com.webchat.utils;

import org.springframework.beans.BeanUtils;

public class DtoUtil {
	
	public static<T, S> T adapt(T dto, S model) {
		BeanUtils.copyProperties(model, dto);
		return dto;
	}
}
