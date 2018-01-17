package com.serious.example.server;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.HelloRequest;
import com.example.grpc.HelloResponse;
import com.serious.interceptor.server.ValidationInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

/**
 * 2018/1/16
 *
 * @author Serious
 */
public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server greetingServer = ServerBuilder.forPort(8080).addService(ServerInterceptors.intercept(new GreetingServiceImpl(), new ValidationInterceptor())).build();
        greetingServer.start();

        System.out.println("Server started!");
        greetingServer.awaitTermination();
    }

    public static class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
        @Override
        public void greeting(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
            System.out.println(request);
            String greeting = "Hello there, " + request.getName();
            HelloResponse response = HelloResponse.newBuilder().setGreeting(greeting).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
