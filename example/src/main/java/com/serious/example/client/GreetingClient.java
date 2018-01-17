package com.serious.example.client;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.HelloRequest;
import com.serious.interceptor.client.ValidationInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * 2018/1/16
 *
 * @author Serious
 */
public class GreetingClient {
    public static void main(String[] args) {
        ManagedChannelBuilder builder = ManagedChannelBuilder.forAddress("127.0.0.1", 8080).usePlaintext(true);
//        builder.intercept(new ValidationInterceptor());
        ManagedChannel channel = builder.build();
        HelloRequest helloRequest = HelloRequest.newBuilder().setName("马多").setAge(16).build();
        System.out.println(GreetingServiceGrpc.newBlockingStub(channel).greeting(helloRequest));
    }
}
