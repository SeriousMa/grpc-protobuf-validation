package com.serious.interceptor.client;

import com.google.protobuf.GeneratedMessageV3;
import com.serious.interceptor.AbstractValidationInterceptor;
import io.grpc.*;

/**
 * @author Serious
 * @date 2017/6/26
 */
public class ValidationInterceptor extends AbstractValidationInterceptor implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void sendMessage(ReqT message) {
                GeneratedMessageV3 messageV3 = (GeneratedMessageV3) message;
                validate(messageV3);
                super.sendMessage(message);
            }
        };
    }

}
