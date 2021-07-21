package com.example.restclient.assembler;

import com.example.restclient.rest.annotation.PathParam;
import com.example.restclient.uri.UriBuilder;

import java.util.Map;

public class PathParamUriBuilderAssembler implements UriBuilderAssembler<PathParam> {
    @Override
    public void assemble(UriBuilder uriBuilder, Map.Entry<String, String> keyvalue, Object[] args) {
        uriBuilder.resolvePath(keyvalue.getKey(), Integer.parseInt(keyvalue.getValue()), args);
    }
}
