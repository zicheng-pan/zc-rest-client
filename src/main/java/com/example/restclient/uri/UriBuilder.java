package com.example.restclient.uri;

import java.util.HashMap;
import java.util.Map;

public class UriBuilder {

    private String method;
    private String path;
    private String parameters = "";

    public Map<String, String> getPostData() {
        return postData;
    }

    private Map<String, String> postData = new HashMap<>();

    public UriBuilder(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public void resolvePath(String key, int index, Object[] args) {
        this.path = path.replaceAll("\\{" + key + "\\}", (String) args[index]);
    }


    public void resolveParam(String key, int index, Object[] args) {
        if (method == "Get") {
            parameters = parameters + "&" + key + "=" + args[index];
        } else if (method == "Post") {
            postData.put(key, (String) args[index]);
        }
    }

    public String getURL() {
        if (!parameters.isEmpty()) {
            return path + "?" + parameters.substring(1, parameters.length());
        }
        return path;
    }
}
