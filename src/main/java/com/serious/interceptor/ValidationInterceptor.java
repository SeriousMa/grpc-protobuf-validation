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
    private static Map<String, Validator> validatorMap;

    static {
        validatorMap = Maps.newConcurrentMap();
        validatorMap.put(Validation.max.getDescriptor().getFullName(), new MaxValidator());
        validatorMap.put(Validation.min.getDescriptor().getFullName(), new MinValidator());
        validatorMap.put(Validation.repeatMax.getDescriptor().getFullName(), new RepeatMaxValidator());
        validatorMap.put(Validation.repeatMin.getDescriptor().getFullName(), new RepeatMinValidator());
        validatorMap.put(Validation.future.getDescriptor().getFullName(), new FutureValidator());
        validatorMap.put(Validation.past.getDescriptor().getFullName(), new PastValidator());
        validatorMap.put(Validation.regex.getDescriptor().getFullName(), new RegexValidator());
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
        if (!options.getExtension(Validation.max).equals(Validation.max.getDefaultValue())) {
            validatorMap.get(Validation.max.getDescriptor().getFullName()).validate(protoName, fieldName, fieldValue, options.getExtension(Validation.max));
        }
        if (!options.getExtension(Validation.min).equals(Validation.min.getDefaultValue())) {
            validatorMap.get(Validation.min.getDescriptor().getFullName()).validate(protoName, fieldName, fieldValue, options.getExtension(Validation.min));
        }
        if (!options.getExtension(Validation.future).equals(Validation.future.getDefaultValue())) {
            validatorMap.get(Validation.future.getDescriptor().getFullName()).validate(protoName, fieldName, fieldValue, options.getExtension(Validation.future));
        }
        if (!options.getExtension(Validation.past).equals(Validation.past.getDefaultValue())) {
            validatorMap.get(Validation.past.getDescriptor().getFullName()).validate(protoName, fieldName, fieldValue, options.getExtension(Validation.past));
        }
        if (!options.getExtension(Validation.repeatMax).equals(Validation.repeatMax.getDefaultValue())) {
            validatorMap.get(Validation.repeatMax.getDescriptor().getFullName()).validate(protoName, fieldName, fieldValue, options.getExtension(Validation.repeatMax));
        }
        if (!options.getExtension(Validation.repeatMin).equals(Validation.repeatMin.getDefaultValue())) {
            validatorMap.get(Validation.repeatMin.getDescriptor().getFullName()).validate(protoName, fieldName, fieldValue, options.getExtension(Validation.repeatMin));
        }
        if (!options.getExtension(Validation.regex).equals(Validation.regex.getDefaultValue())) {
            validatorMap.get(Validation.regex.getDescriptor().getFullName()).validate(protoName, fieldName, fieldValue, options.getExtension(Validation.regex));
        }
    }


}
