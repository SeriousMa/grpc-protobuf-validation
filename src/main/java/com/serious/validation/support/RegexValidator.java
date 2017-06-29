package com.serious.validation.support;

import com.google.common.base.Preconditions;
import com.serious.validation.AbstractValidator;

import java.util.regex.Pattern;

/**
 * Created by Serious on 2017/6/28.
 */
public class RegexValidator extends AbstractValidator {

    @Override
    protected void doValidate(Object fieldValue, Object extensionValue, String errInfo) {
        String extensionValueStr = extensionValue.toString();
        String fieldValueStr = fieldValue.toString();
        String err = errInfo + "error with Regex";
        Pattern pattern = Pattern.compile(extensionValueStr);
        Preconditions.checkArgument(pattern.matcher(fieldValueStr).matches(), err);
    }
}
