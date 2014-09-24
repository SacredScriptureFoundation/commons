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
package org.sacredscripturefoundation.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link CountImpl}.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class CountImplTest {

    private CountImpl<Object> count;

    @Before
    public void setUp() throws Exception {
        count = new CountImpl<Object>();
    }

    /**
     * Verifies the initial values after construction.
     */
    @Test
    public void testConstructor() {
        assertEquals(0, count.getCount());
        assertNull(count.getCounted());
    }

    /**
     * Verifies the failure to construct with a null counted.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructorWithCountAndNullObject() {
        new CountImpl<Object>(1, null);
    }

    /**
     * Verifies constructing with the provided count and object.
     */
    @Test
    public void testConstructorWithCountAndObject() {
        Object o = new Object();
        count = new CountImpl<Object>(1, o);
        assertEquals(1, count.getCount());
        assertSame(o, count.getCounted());
    }

    /**
     * Verifies the failure to construct with a negative count.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeCountAndObject() {
        new CountImpl<Object>(-1, new Object());
    }

    /**
     * Verifies the mutability of the count.
     */
    @Test
    public void testSetCount() {
        count.setCount(0);
        assertEquals(0, count.getCount());
        count.setCount(1);
        assertEquals(1, count.getCount());
    }

    /**
     * Verifies the mutability of what is counted.
     */
    @Test
    public void testSetCounted() {
        Object o = new Object();
        count.setCounted(o);
        assertSame(o, count.getCounted());
    }

    /**
     * Verifies the failure to set a null counted.
     */
    @Test(expected = NullPointerException.class)
    public void testSetCountedNull() {
        count.setCounted(null);
    }

    /**
     * Verifies the failure to set a negative count.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetCountNegative() {
        count.setCount(-1);
    }

    /**
     * Verifies the collection of counts tally up to their collective sum.
     */
    @Test
    public void testTotal() {
        LinkedList<Count<Object>> counts = new LinkedList<Count<Object>>();
        for (int i = 0; i < 10; i++) {
            counts.add(new CountImpl<Object>(1, new Object()));
        }
        assertEquals(10, CountImpl.total(counts));
    }

}
