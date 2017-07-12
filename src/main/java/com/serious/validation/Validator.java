package com.serious.validation;

import com.google.common.collect.Maps;
import com.google.protobuf.Descriptors;

import java.util.Map;

/**
 * Created by Serious on 2017/6/28.
 */
public interface Validator {
    Map<Descriptors.FieldDescriptor, Validator> validators = Maps.newHashMap();

    void validate(String protoName, String fieldName, Object fieldValue, Object extensionValue) throws IllegalArgumentException;
}
