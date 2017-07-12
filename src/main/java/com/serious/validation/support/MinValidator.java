package com.serious.validation.support;

import com.google.common.base.Preconditions;
import com.serious.validation.AbstractValidator;

/**
 * Created by Serious on 2017/6/28.
 */
public class MinValidator extends AbstractValidator {

    @Override
    protected void doValidate(Object fieldValue, Object extensionValue, String errInfo) {
        String extensionValueStr = extensionValue.toString();
        String fieldValueStr = fieldValue.toString();
        String err = errInfo + "error with Min";
        if (fieldValue instanceof Long) {
            Preconditions.checkArgument(Long.valueOf(extensionValueStr) < Long.valueOf(fieldValueStr), err);
        }
        if (fieldValue instanceof Integer) {
            Preconditions.checkArgument(Integer.valueOf(extensionValueStr) < Integer.valueOf(fieldValueStr), err);
        }
        if (fieldValue instanceof Float) {
            Preconditions.checkArgument(Float.valueOf(extensionValueStr) < Float.valueOf(fieldValueStr), err);
        }
        if (fieldValue instanceof Double) {
            Preconditions.checkArgument(Double.valueOf(extensionValueStr) < Double.valueOf(fieldValueStr), err);
        }
    }
}
