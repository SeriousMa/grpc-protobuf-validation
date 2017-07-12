package com.serious.interceptor;

import com.google.common.collect.Maps;
import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import com.serious.validation.Validator;
import com.serious.validation.support.*;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import validation.Validation;

import java.util.Map;

/**
 * Created by Serious on 2017/6/26.
 */
public class ValidationInterceptor implements ClientInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ValidationInterceptor.class);

    private static Map<Descriptors.FieldDescriptor, Validator> validatorMap;

    static {
        validatorMap = Maps.newConcurrentMap();
        validatorMap.put(Validation.max.getDescriptor(), new MaxValidator());
        validatorMap.put(Validation.min.getDescriptor(), new MinValidator());
        validatorMap.put(Validation.repeatMax.getDescriptor(), new RepeatMaxValidator());
        validatorMap.put(Validation.repeatMin.getDescriptor(), new RepeatMinValidator());
        validatorMap.put(Validation.future.getDescriptor(), new FutureValidator());
        validatorMap.put(Validation.past.getDescriptor(), new PastValidator());
        validatorMap.put(Validation.regex.getDescriptor(), new RegexValidator());
    }


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

    private void validate(GeneratedMessageV3 messageV3) {
        for (Descriptors.FieldDescriptor fieldDescriptor : messageV3.getDescriptorForType().getFields()) {
            if (messageV3.getField(fieldDescriptor) instanceof GeneratedMessageV3) {
                GeneratedMessageV3 subMessageV3 = (GeneratedMessageV3) messageV3.getField(fieldDescriptor);
                validate(subMessageV3);
            } else if (fieldDescriptor.getOptions().getAllFields().size() > 0) {
                doValidate(fieldDescriptor.getFile().getFullName(), fieldDescriptor.getFullName(), messageV3.getField(fieldDescriptor), fieldDescriptor.getOptions());
            }
        }
    }

    private void doValidate(String protoName, String fieldName, Object fieldValue, DescriptorProtos.FieldOptions options) throws IllegalArgumentException {
        logger.debug("validate protoName:{},fieldName:{},fieldValue:{}", protoName, fieldName, fieldValue);

        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : options.getAllFields().entrySet()) {
            validatorMap.get(entry.getKey()).validate(protoName, fieldName, fieldValue, entry.getValue());
        }
    }


}
