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
import static java.util.Locale.FRENCH;
import static java.util.Locale.GERMAN;
import static java.util.Locale.SIMPLIFIED_CHINESE;
import static java.util.Locale.UK;
import static java.util.Locale.US;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.sacredscripturefoundation.commons.locale.AcceptableLocales;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import org.junit.Test;

/**
 * Unit tests for the {@link AcceptableLocales} class.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class AcceptableLocalesTest {

    /**
     * Tests that the locale string is correctly parsed.
     */
    @Test
    public void testGet() {
        AcceptableLocales a = new AcceptableLocales("en fr de zh_CN");
        Collection<Locale> c = a.get();
        assertEquals(4, c.size());
        Iterator<Locale> i = c.iterator();
        assertEquals(ENGLISH, i.next());
        assertEquals(FRENCH, i.next());
        assertEquals(GERMAN, i.next());
        assertEquals(SIMPLIFIED_CHINESE, i.next());
    }

    /**
     * Verifies the match of locales.
     */
    @Test
    public void testMatchList() {
        AcceptableLocales a = new AcceptableLocales("en fr zh_CN");
        assertEquals(ENGLISH, a.match(ENGLISH));
        assertEquals(ENGLISH, a.match(US));
        assertEquals(FRENCH, a.match(FRENCH));
        assertEquals(SIMPLIFIED_CHINESE, a.match(new Locale("zh")));
        assertNull(a.match(GERMAN));
    }

    /**
     * Verifies that the acceptable country locale is returned for all locales
     * of equal language.
     */
    @Test
    public void testMatchWhenAcceptingCountry() {
        AcceptableLocales a = new AcceptableLocales("en_US");
        assertEquals(US, a.match(ENGLISH));
        assertEquals(US, a.match(US));
        assertEquals(US, a.match(UK));
    }

    /**
     * Verifies that the acceptable language locale is returned for all locales
     * of equal language.
     */
    @Test
    public void testMatchWhenAcceptingLanguage() {
        AcceptableLocales a = new AcceptableLocales("en");
        assertEquals(ENGLISH, a.match(ENGLISH));
        assertEquals(ENGLISH, a.match(US));
        assertEquals(ENGLISH, a.match(UK));
    }

    /**
     * Verifies the matching when also supplying a fallback.
     */
    @Test
    public void testMatchWithFallback() {
        AcceptableLocales a = new AcceptableLocales("en");
        assertEquals(ENGLISH, a.match(ENGLISH, FRENCH));
        assertEquals(FRENCH, a.match(GERMAN, FRENCH));
    }

}
