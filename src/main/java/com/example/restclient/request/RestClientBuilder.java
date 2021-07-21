package com.example.restclient.request;

import com.example.restclient.rest.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class RestClientBuilder {

    private static RestClientBuilder restClientBuilder;

    private static String hostName = null;
    private static int port = 0;

    public static RestClientBuilder newBuilder() {
        restClientBuilder = new RestClientBuilder();
        return restClientBuilder;
    }

    public static RestClientBuilder buildUri(URI uri) {
        hostName = uri.getHost();
        port = uri.getPort();
        return restClientBuilder;
    }

    public <T> T build(Class<T> clazz) {
        Map<Method, RequestTemplate> methodRequestTemplateMap = null;
        try {
            methodRequestTemplateMap = resolveRequestTemplates(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) Proxy.newProxyInstance(RestClientBuilder.class.getClassLoader(), new Class[]{clazz}, new RequestHandler(methodRequestTemplateMap, hostName, port));

    }

    private String judgeHttpMethod(Annotation annotation) {
        String method = null;
        if (annotation.annotationType().getName().equals(Get.class.getName())) {
            method = "Get";
        } else if (annotation.annotationType().getName().equals(Post.class.getName())) {
            method = "Post";
        }
        return method;
    }

    private <T> String generateServiceName(Class<T> clazz) {
        String serviceName = "";
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().getName().equals(Path.class.getName())) {
                serviceName = ((Path) annotation).value();
                break;
            }
        }
        return serviceName;
    }

    private <T> Map<Method, RequestTemplate> resolveRequestTemplates(Class<T> clazz) throws Exception {
        Map<Method, RequestTemplate> methodRequestTemplateMap = new HashMap<>();
        String serviceName = generateServiceName(clazz);
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            RequestTemplate requestTemplate = new RequestTemplate();
            requestTemplate.serviceName = serviceName;
            Annotation[] methodAnnotations = method.getAnnotations();
            boolean isHttpRequest = false;
            for (Annotation annotation : methodAnnotations) {
                String httpMethod = judgeHttpMethod(annotation);
                if (httpMethod != null) {
                    isHttpRequest = true;
                    requestTemplate.method = httpMethod;
                }
                if (annotation.annotationType().getName().equals(Path.class.getName())) {
                    String pathParamName = ((Path) annotation).value();
                    requestTemplate.path = pathParamName;
                }
            }
            if (isHttpRequest) {
                Parameter[] parameters = method.getParameters();
                int paramIndex = 0;
                for (Parameter parameter : parameters) {
                    Annotation[] parameterAnnotations = parameter.getAnnotations();
                    for (Annotation annotation : parameterAnnotations) {
                        if (annotation.annotationType().getName().equals(PathParam.class.getName())) {
                            String pathParamName = ((PathParam) annotation).value();
                            requestTemplate.pathParam.put(pathParamName, String.valueOf(paramIndex));
                        } else if (annotation.annotationType().getName().equals(QueryParam.class.getName())) {
                            String queryParamName = ((QueryParam) annotation).value();
                            requestTemplate.urlParam.put(queryParamName, String.valueOf(paramIndex));
                        }
                    }
                    paramIndex++;
                }
            } else {
                throw new RuntimeException("Method not supported, this is not a http request!!");
            }
            methodRequestTemplateMap.put(method, requestTemplate);
        }
        return methodRequestTemplateMap;
    }


}
