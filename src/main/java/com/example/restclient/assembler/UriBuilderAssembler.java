package com.example.restclient.assembler;

import com.example.restclient.request.RequestTemplate;
import com.example.restclient.uri.UriBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public interface UriBuilderAssembler<A extends Annotation> {

    default void assemble(UriBuilder uriBuilder, RequestTemplate requestTemplate, Object[] args) {
        assemble(uriBuilder, requestTemplate.getAnnotatedParamMetadata(getAnnotationType()), args);
    }

    default void assemble(UriBuilder uriBuilder, Map<String, String> annotatedParamMetadata, Object[] args) {
        for (Map.Entry<String, String> entry : annotatedParamMetadata.entrySet()) {
            assemble(uriBuilder, entry, args);
        }
    }

    void assemble(UriBuilder uriBuilder, Map.Entry<String, String> keyvalue, Object[] args);

    default Class<A> getAnnotationType() {
        Type[] types = this.getClass().getGenericInterfaces();

        ParameterizedType parameterizedType = (ParameterizedType) types[0];

        Type type = parameterizedType.getActualTypeArguments()[0];
        return (Class<A>) type;
    }
}