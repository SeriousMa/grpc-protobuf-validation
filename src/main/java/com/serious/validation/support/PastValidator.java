/*
 * Copyright 2017-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.serious.validation.support;

import com.google.common.base.Preconditions;
import com.serious.validation.AbstractValidator;

/**
 *
 * @author Serious
 * @date 2017/6/28
 */
public class PastValidator extends AbstractValidator {
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

    @Override
    public String toString() {
        return "PastValidator";
    }
}
