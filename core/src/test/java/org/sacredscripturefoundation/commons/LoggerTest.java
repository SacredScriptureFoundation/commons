/*
 * Copyright (c) 2014 Sacred Scripture Foundation.
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

import org.junit.Test;

/**
 * Unit tests for {@link Logger}.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class LoggerTest {

    @Test
    public void testFormat1PosEnd1Arg() {
        assertEquals("AB", Logger.formatMessage("A{}", "B"));
    }

    @Test
    public void testFormat1PosMiddle1Arg() {
        assertEquals("ABC", Logger.formatMessage("A{}C", "B"));
    }

    @Test
    public void testFormat1PosStart1Arg() {
        assertEquals("AB", Logger.formatMessage("{}B", "A"));
    }

    @Test
    public void testFormat2Pos2Args() {
        assertEquals("AB", Logger.formatMessage("{}{}", "A", "B"));
    }

    @Test
    public void testFormatBracesTooWide() {
        assertEquals("A { }", Logger.formatMessage("A { }", "B"));
    }

    @Test
    public void testFormatBraceUnbalanced() {
        assertEquals("{A", Logger.formatMessage("{A", "B"));
    }

    /**
     * Verifies an empty argument list performs no formatting.
     */
    @Test
    public void testFormatEmptyArgs() {
        assertEquals("{}", Logger.formatMessage("{}", new Object[0]));
    }

    /**
     * Verifies a {@code null} argument list performs no formatting.
     */
    @Test
    public void testFormatNullArgs() {
        assertEquals("{}", Logger.formatMessage("{}", (Object[]) null));
    }

    /**
     * Verifies too few parameters leave the leftover substitution tokens.
     */
    @Test
    public void testFormatParamsTooFew() {
        assertEquals("A{}", Logger.formatMessage("{}{}", "A"));
    }

    /**
     * Verifies extra parameters do not affect the parsing.
     */
    @Test
    public void testFormatParamsTooMany() {
        assertEquals("AB", Logger.formatMessage("{}{}", "A", "B", "C"));
    }

}
