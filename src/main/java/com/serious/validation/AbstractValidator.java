package com.serious.validation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Serious
 * @date 2017/6/28
 */
public abstract class AbstractValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(AbstractValidator.class);

    @Override
    public void validate(String protoName, String fieldName, Object fieldValue, Object extensionValue)throws IllegalArgumentException {
        String errInfo = String.format("validate error protoName:%s,fieldName:%s,fieldValue:%s,extensionValue:%s,", protoName, fieldName, fieldValue, extensionValue);
        logger.debug("validate protoName:{},fieldName:{},fieldValue:{},extensionValue:{}", protoName, fieldName, fieldValue, extensionValue);
        doValidate(fieldValue, extensionValue, errInfo);
    }

    protected abstract void doValidate(Object fieldValue, Object extensionValue, String errInfo) throws IllegalArgumentException;

}
