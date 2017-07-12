package com.serious.validation.support;

import com.google.common.base.Preconditions;
import com.serious.validation.AbstractValidator;
import validation.Validation;

/**
 * Created by Serious on 2017/6/28.
 */
public class PastValidator extends AbstractValidator {

    public PastValidator() {
        validators.put(Validation.past.getDescriptor(), this);
    }

    @Override
    protected void doValidate(Object fieldValue, Object extensionValue, String errInfo) {
        Boolean extensionValueBoolean = (Boolean) extensionValue;
        Long fieldValueLong = (Long) fieldValue;
        String err = errInfo + "error with Past";
        if (extensionValueBoolean) {
            long now = System.currentTimeMillis();
            Preconditions.checkArgument(now > fieldValueLong, err);
        }
    }
}
