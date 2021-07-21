package com.example.restclient;

import com.example.restclient.request.RestClientBuilder;
import com.example.restclient.rest.service.ProductItemsService;

import java.net.URI;
import java.util.Scanner;

public class BootstrapTest {
    public static void main(String[] args) throws Exception {

        URI uri = new URI("http://127.0.0.1:5000");

        ProductItemsService service = RestClientBuilder.newBuilder().buildUri(uri).build(ProductItemsService.class);
        System.out.println("测试 get 请求");
        String result1 = service.getProductList("aaa", "cccc", "bbb", "query1", "query2");
        System.out.println(result1);

        System.out.println("测试 post 请求");
        String result2 = service.postRequest("aaa", "cccc", "bbb", "query1", "query2");
        System.out.println(result2);

        System.out.println("模拟测试 post 请求 /services/actuator/shutdown 接口");
        String result3 = service.postShutDown();
        System.out.println(result3);

        //TODO 后续可以讲http请求修改成异步，或者使用第三方的库 MicroProfile REST Client
        //如果请求是异步的需要阻塞不然main程序很快退出了没有看到返回的结果
        //new Scanner(System.in).hasNext();
    }
}
