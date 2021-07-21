package com.example.restclient.request;

import com.example.restclient.rest.annotation.Path;
import com.example.restclient.rest.annotation.PathParam;
import com.example.restclient.rest.annotation.QueryParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestTemplate {

    String method;
    String serviceName;
    String path;
    Map<String, String> pathParam = new HashMap<>();
    Map<String, String> urlParam = new HashMap<>();

    Map<Class, Map> annotatedParamMetadata = new HashMap<>();

    public RequestTemplate() {
        annotatedParamMetadata.put(PathParam.class, pathParam);
        annotatedParamMetadata.put(QueryParam.class, urlParam);
    }
//com.example.restclient.rest.annotation.PathParam

    public <T> Map<String, String> getAnnotatedParamMetadata(Class<T> clazz) {
        return annotatedParamMetadata.get(clazz);
    }
}
