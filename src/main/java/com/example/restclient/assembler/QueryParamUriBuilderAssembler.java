package com.example.restclient.assembler;

import com.example.restclient.rest.annotation.QueryParam;
import com.example.restclient.uri.UriBuilder;

import java.util.Map;

public class QueryParamUriBuilderAssembler implements UriBuilderAssembler<QueryParam> {
    @Override
    public void assemble(UriBuilder uriBuilder, Map.Entry<String, String> keyvalue, Object[] args) {
        uriBuilder.resolveParam(keyvalue.getKey(), Integer.parseInt(keyvalue.getValue()), args);
    }
}
