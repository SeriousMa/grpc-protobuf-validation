package com.serious.validation;

/**
 *
 * @author Serious
 * @date 2017/6/28
 */
public interface Validator {

    void validate(String protoName, String fieldName, Object fieldValue, Object extensionValue) throws IllegalArgumentException;
}
