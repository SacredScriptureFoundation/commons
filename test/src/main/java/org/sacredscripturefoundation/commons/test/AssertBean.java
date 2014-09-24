/*
 * Copyright (c) 2013, 2014 Sacred Scripture Foundation.
 * "All scripture is given by inspiration of God, and is profitable for
 * doctrine, for reproof, for correction, for instruction in righteousness:
 * That the man of God may be perfect, throughly furnished unto all good
 * works." (2 Tim 3:16-17)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sacredscripturefoundation.commons.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.sacredscripturefoundation.commons.InstantiationNotIntendedError;
import org.sacredscripturefoundation.commons.Property;

/**
 * This class contains a set of assertions for verifying bean property types
 * according to the expectations of this framework.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public final class AssertBean {

    /**
     * Asserts that the specified boolean property accepts {@code true},
     * {@code false}, and (if an object) {@code null}.
     *
     * @param property the property to verify
     * @param initialValue the expected initial value of the property
     */
    public static void assertSimpleBooleanProperty(Property<Boolean> property, Boolean initialValue) {
        // Verify initial value
        assertEquals(initialValue, property.get());

        // Verify truth
        property.set(Boolean.TRUE);
        assertEquals(Boolean.TRUE, property.get());

        // Verify falsehood
        property.set(Boolean.FALSE);
        assertEquals(Boolean.FALSE, property.get());
    }

    /**
     * Asserts that the specified string property accepts non-empty, empty, and
     * {@code null} values appropriately.
     *
     * @param property the property to verify
     * @param initialValue the expected initial value of the property
     */
    public static void assertSimpleStringProperty(Property<String> property, Object initialValue) {
        // Verify initial value
        assertEquals(initialValue, property.get());

        // Verify nullability
        property.set(null);
        assertEquals(null, property.get());

        // Verify non-empty string
        assertTrue((STRING_VALUE != null) && (STRING_VALUE.trim().length() > 0));
        property.set(STRING_VALUE);
        assertEquals(STRING_VALUE, property.get());

        // Verify empty string interpretation
        property.set(EMPTY_STRING);
        assertEquals(null, property.get());
    }

    private static final String EMPTY_STRING = "";
    private static final String STRING_VALUE = "value";

    /**
     * Suppresss default constructor for noninstantiability.
     *
     * @throws InstantiationNotIntendedError always
     */
    private AssertBean() {
        throw new InstantiationNotIntendedError();
    }

}
