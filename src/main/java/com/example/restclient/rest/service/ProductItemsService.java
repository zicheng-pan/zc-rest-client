package com.example.restclient.rest.service;

import com.example.restclient.rest.annotation.*;

import java.util.List;

@Path("/services")
public interface ProductItemsService {

    @Get
    @Path("/get/public/{code}/products/group/{store}")
    String getProductList(@PathParam("store") String store, String temp, @PathParam("code") String code,
                          @QueryParam("query1") String query1, @QueryParam("query2") String query2);

    @Post
    @Path("/post/public/{code}/products/group/{store}")
    String postRequest(@PathParam("store") String store, String temp, @PathParam("code") String code,
                       @QueryParam("query1") String query1, @QueryParam("query2") String query2);

    @Post
    @Path("/actuator/shutdown")
    String postShutDown();
}
