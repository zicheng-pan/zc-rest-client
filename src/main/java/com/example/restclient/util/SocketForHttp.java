package com.example.restclient.util;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SocketForHttp {

    private int port;
    private String host;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public SocketForHttp(String host, int port) throws Exception {

        this.host = host;
        this.port = port;

        /**
         * http
         */
        socket = new Socket(this.host, this.port);

        /**
         * https
         */
//        socket = (SSLSocket) ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket(this.host, this.port);

    }

    public String sendGet(String requestPath) throws IOException {
        String requestUrlPath = requestPath;
        if (!requestUrlPath.startsWith("/")) {
            requestUrlPath = "/" + requestUrlPath;
        }

        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
        bufferedWriter = new BufferedWriter(streamWriter);
        bufferedWriter.write("GET " + requestUrlPath + " HTTP/1.1\r\n");
        bufferedWriter.write("Host: " + this.host + "\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();

        BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(streamReader, StandardCharsets.UTF_8));
        String line = null;
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
        return result.toString();
    }

    public String sendPost(String requestPath, String data) throws IOException {
        String requestUrlPath = requestPath;
        if (!requestUrlPath.startsWith("/")) {
            requestUrlPath = "/" + requestUrlPath;
        }

//        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + data);
        OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
        bufferedWriter = new BufferedWriter(streamWriter);
        bufferedWriter.write("POST " + requestUrlPath + " HTTP/1.1\r\n");
        bufferedWriter.write("Host: " + this.host + "\r\n");
        bufferedWriter.write("Content-Length: " + data.length() + "\r\n");
        bufferedWriter.write("Content-Type: application/text\r\n");
        bufferedWriter.write("\r\n");
        bufferedWriter.write(data);

        bufferedWriter.write("\r\n");
        bufferedWriter.flush();

        BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(streamReader, StandardCharsets.UTF_8));
        String line = "";
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
        return result.toString();
    }

    public String sendPost(String requestPath, Map<String, String> params) throws IOException {
        String data = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            data += URLEncoder.encode(entry.getKey(), "utf-8") + "=" + URLEncoder.encode(entry.getValue(), "utf-8") + "&";
        }
        if (!data.isEmpty()) {
            data = data.substring(0, data.length() - 1);
        }
        return sendPost(requestPath, data);
    }
}
