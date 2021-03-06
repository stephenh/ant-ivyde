/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.ivyde.common.model;

public class IvyBooleanTagAttribute extends IvyTagAttribute {

    protected static final String[] BOOLEAN_VALUES = new String[] {"true", "false"};

    public static final IValueProvider VALUE_PROVIDER = new IValueProvider() {
        public String[] getValuesfor(IvyTagAttribute att, IvyFile ivyFile) {
            return BOOLEAN_VALUES;
        }

    };

    public IvyBooleanTagAttribute(String name, String doc, boolean mandatory) {
        super(name, doc, mandatory);
        setValueProvider(VALUE_PROVIDER);
    }

    public IvyBooleanTagAttribute(String name, String doc) {
        super(name, doc);
        setValueProvider(VALUE_PROVIDER);
    }

    public IvyBooleanTagAttribute(String name) {
        super(name);
        setValueProvider(VALUE_PROVIDER);
    }

}
