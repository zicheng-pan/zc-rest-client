package com.example.restclient.request;

import com.example.restclient.assembler.UriBuilderAssembler;
import com.example.restclient.util.SocketForHttp;
import com.example.restclient.uri.UriBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class RequestHandler implements InvocationHandler {

    private String hostName;
    private int port;
    private List<UriBuilderAssembler> uriBuilders;
    private Map<Method, RequestTemplate> methodRequestTemplateMap;

    public RequestHandler(Map<Method, RequestTemplate> methodRequestTemplateMap, String hostname, int port) {
        this.methodRequestTemplateMap = methodRequestTemplateMap;
        this.uriBuilders = initUriBuilders();
        this.hostName = hostname;
        this.port = port;
    }

    private <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }

    private List<UriBuilderAssembler> initUriBuilders() {
        return copyIterator(ServiceLoader.load(UriBuilderAssembler.class).iterator());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestTemplate requestTemplate = methodRequestTemplateMap.get(method);
        UriBuilder builder = new UriBuilder(requestTemplate.serviceName + requestTemplate.path, requestTemplate.method);

        for (UriBuilderAssembler assembler : uriBuilders) {
            assembler.assemble(builder, requestTemplate, args);
        }
        String result = null;
        SocketForHttp socketForHttp = new SocketForHttp(hostName, port);
        if (requestTemplate.method == "Get") {
            result = socketForHttp.sendGet(builder.getURL());
        } else if (requestTemplate.method == "Post") {
            result = socketForHttp.sendPost(builder.getURL(), builder.getPostData());
        }

        return result;
    }
}
