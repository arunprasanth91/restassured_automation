package org.example.utils;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class RequestConfig {
	private String baseUrl;
	private String basePath;
	private Map<String, String> headers;
	private Map<String, Object> queryParams;
	private Object body;
}
