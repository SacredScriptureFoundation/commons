/*
 * Copyright (c) 2015 Sacred Scripture Foundation.
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
package org.sacredscripturefoundation.commons.entity.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link Base64UuidGenerator}.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class Base64UuidGeneratorTest {

    private Base64UuidGenerator gen;

    @Before
    public void setUp() {
        gen = new Base64UuidGenerator();
    }

    /**
     * Verifies the ID format.
     */
    @Test
    public void testGenerateFormat() {
        String id = (String) gen.generate();
        assertTrue(id.matches("^[0-9A-Za-z+/]{22}$"));
    }

    /**
     * Verifies the ID is related to a UUID.
     */
    @Test
    public void testGenerateFromUUID() throws Exception {
        String id = gen.generate0(UUID.fromString("1bc0e2a9-1c0d-3dd8-935e-662c49b9"));
        assertEquals("G8DiqRwNPdiTXgAAZixJuQ", id);
    }

    /**
     * Verifies the web-safe encoding substitution.
     */
    @Test
    public void testGenerateWebSafe() throws Exception {
        // Actually "+/pkUOXFSPKFqKFQZnBUeg"
        gen = new Base64UuidGenerator(true);
        String id = gen.generate0(UUID.fromString("fbfa6450-e5c5-48f2-85a8-a1506670547a"));
        assertEquals("-_pkUOXFSPKFqKFQZnBUeg", id);
    }

}
