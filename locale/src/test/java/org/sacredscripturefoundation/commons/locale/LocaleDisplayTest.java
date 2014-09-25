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
package org.sacredscripturefoundation.commons.locale;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.junit.Assert.assertEquals;

import org.sacredscripturefoundation.commons.locale.LocaleDisplay;

import org.junit.Test;

/**
 * Unit tests for the {@link LocaleDisplay} class.
 *
 * @author Paul
 * @since 1.0
 */
public class LocaleDisplayTest {

    /**
     * Verifies the failure to construct with a nulled display language.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructWithNullDisplayLanguage() {
        new LocaleDisplay(ENGLISH, ENGLISH, null);
    }

    /**
     * Verifies the failure to construct with a nulled display locale.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructWithNullDisplayLocale() {
        new LocaleDisplay(ENGLISH, null, ENGLISH.getDisplayLanguage());
    }

    /**
     * Verifies the failure to construct with a nulled locale.
     */
    @Test(expected = NullPointerException.class)
    public void testConstructWithNullLocale() {
        new LocaleDisplay(null, ENGLISH, ENGLISH.getDisplayLanguage());
    }

    /**
     * Tests the return of the display language.
     */
    @Test
    public void testGetDisplayLanguage() {
        LocaleDisplay display = new LocaleDisplay(ENGLISH, GERMAN, "Englisch");
        assertEquals("Englisch", display.getDisplayLanguage());
    }

    /**
     * Tests the return of the display locale.
     */
    @Test
    public void testGetDisplayLocale() {
        LocaleDisplay display = new LocaleDisplay(ENGLISH, GERMAN, "Englisch");
        assertEquals(GERMAN, display.getDisplayLocale());
    }

    /**
     * Tests the return of the locale.
     */
    @Test
    public void testGetLocale() {
        LocaleDisplay display = new LocaleDisplay(ENGLISH, GERMAN, "Englisch");
        assertEquals(ENGLISH, display.getLocale());
    }

}