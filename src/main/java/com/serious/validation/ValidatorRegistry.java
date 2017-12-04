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

package com.serious.validation;

import com.google.common.collect.Maps;
import com.google.protobuf.Descriptors;
import com.serious.validation.support.*;
import validation.Validation;

import java.util.Map;

/**
 * @author Serious
 */
public class ValidatorRegistry {
    private static final Map<Descriptors.FieldDescriptor, Validator> REGISTRY = Maps.newHashMap();

    static {
        REGISTRY.put(Validation.max.getDescriptor(), new MaxValidator());
        REGISTRY.put(Validation.min.getDescriptor(), new MinValidator());
        REGISTRY.put(Validation.repeatMax.getDescriptor(), new RepeatMaxValidator());
        REGISTRY.put(Validation.repeatMin.getDescriptor(), new RepeatMinValidator());
        REGISTRY.put(Validation.future.getDescriptor(), new FutureValidator());
        REGISTRY.put(Validation.past.getDescriptor(), new PastValidator());
        REGISTRY.put(Validation.regex.getDescriptor(), new RegexValidator());
    }


    public static Validator getValidator(Descriptors.FieldDescriptor descriptor) {
        return REGISTRY.get(descriptor);
    }
}