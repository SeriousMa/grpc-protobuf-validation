package com.serious.interceptor;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import com.serious.validation.ValidatorRegistry;

import java.util.Map;

/**
 * 2018/1/17
 *
 * @author Serious
 */
public abstract class AbstractValidationInterceptor {
    protected void validate(GeneratedMessageV3 messageV3) {
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
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : options.getAllFields().entrySet()) {
            ValidatorRegistry.getValidator(entry.getKey()).validate(protoName, fieldName, fieldValue, entry.getValue());
        }
    }

}
