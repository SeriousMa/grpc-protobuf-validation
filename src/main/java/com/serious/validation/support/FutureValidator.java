package com.serious.validation.support;

import com.google.common.base.Preconditions;
import com.serious.validation.AbstractValidator;

/**
 * Created by Serious on 2017/6/28.
 */
public class FutureValidator extends AbstractValidator {

    @Override
    protected void doValidate(Object fieldValue, Object extensionValue, String errInfo) {
        Boolean extensionValueBoolean = (Boolean) extensionValue;
        Long fieldValueLong = (Long) fieldValue;
        String err = errInfo + "error with Future";
        if (extensionValueBoolean) {
            long now = System.currentTimeMillis();
            Preconditions.checkArgument(now < fieldValueLong, err);
        }
    }
}
