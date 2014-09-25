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
package org.sacredscripturefoundation.commons.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractEntity}.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class AbstractEntityTest {

    private AbstractEntity<Object> entity;

    /**
     * Constructs an anonymous subclass with no arguments.
     */
    @Before
    public void setUp() {
        entity = new AbstractEntity<Object>() {
            // empty
        };
    }

    /**
     * Verifies the failure to construct with a {@code null} identifier.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("serial")
    public void testConstructorNullId() {
        entity = new AbstractEntity<Object>(null) {
            // empty
        };
    }

    /**
     * Verifies mutating the created timestamp.
     */
    @Test
    public void testSetCreated() {
        Date date = new Date();
        entity.setCreated(date);
        assertEquals(date, entity.getCreated());
    }

    /**
     * Verifies mutating the identifier to a non-numeric object.
     */
    @Test
    public void testSetIdNonNumeric() {
        Object id = new Object();
        entity.setId(id);
        assertEquals(id, entity.getId());
    }

    /**
     * Verifies mutating the identifier to {@code null}.
     */
    @Test
    public void testSetIdNull() {
        entity.setId(null);
        assertNull(entity.getId());
    }

    /**
     * Verifies mutating the identifer to a number.
     */
    @Test
    public void testSetIdNumber() {
        Long id = 1L;
        entity.setId(id);
        assertEquals(id, entity.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIdNumberLessThanOne() {
        entity.setId(-1);
    }

    /**
     * Verifies mutating the updated timestamp.
     */
    @Test
    public void testSetUpdated() {
        Date date = new Date();
        entity.setUpdated(date);
        assertEquals(date, entity.getUpdated());
    }

    /**
     * Verifies the string generation does not throw an exception.
     */
    @Test
    public void testToString() {
        assertNotNull(entity.toString());
    }

}
