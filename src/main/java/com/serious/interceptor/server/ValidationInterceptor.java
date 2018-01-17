package com.serious.interceptor.server;

import com.google.protobuf.GeneratedMessageV3;
import com.serious.interceptor.AbstractValidationInterceptor;
import io.grpc.*;

/**
 * @author Serious
 * @date 2017/6/26
 */
public class ValidationInterceptor extends AbstractValidationInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(final ServerCall<ReqT, RespT> call, final Metadata headers, final ServerCallHandler<ReqT, RespT> next) {
        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {
            @Override
            public void onMessage(ReqT message) {
                GeneratedMessageV3 messageV3 = (GeneratedMessageV3) message;
                try {
                    validate(messageV3);
                    super.onMessage(message);
                } catch (Exception e) {
                    Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage());
                    call.close(status, headers);
                    throw new StatusRuntimeException(status);
                }
            }
        };
    }
}
