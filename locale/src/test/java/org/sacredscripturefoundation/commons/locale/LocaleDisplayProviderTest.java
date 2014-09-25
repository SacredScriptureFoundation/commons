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
import static java.util.Locale.ITALIAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.sacredscripturefoundation.commons.locale.LocaleDisplay;
import org.sacredscripturefoundation.commons.locale.LocaleDisplayProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link LocaleDisplayProvider} class.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class LocaleDisplayProviderTest {

    private LocaleDisplayProvider provider;

    @Before
    public void setUp() {
        provider = new LocaleDisplayProvider();
    }

    /**
     * Verifies the return of the display properties.
     */
    @Test
    public void testGet() {
        LocaleDisplay display = provider.get("en", new Locale("es"));
        assertEquals("en", display.getLocale().getLanguage());
        assertEquals("es", display.getDisplayLocale().getLanguage());
    }

    /**
     * Verifies the return of the display language.
     */
    @Test
    public void testGetLanguage() {
        assertEquals("Inglés", provider.getLanguage("en", new Locale("es")));
    }

    /**
     * Verifies nothing is returned for an unsupported language code.
     */
    @Test
    public void testGetLanguageWithBadLanguageCode() {
        assertNull(provider.getLanguage("xx", ENGLISH));
    }

    /**
     * Verifies the error to query from a bogus bundle location.
     */
    @Test(expected = MissingResourceException.class)
    public void testGetWhenBundleLocationIsBogus() {
        provider.setBundleLocation("bogus");
        provider.get("en", ENGLISH);
    }

    /**
     * Verifies the error to query using a bad language code.
     */
    @Test(expected = MissingResourceException.class)
    public void testGetWithBadLanguageCode() {
        provider.get("xx", ENGLISH);
    }

    /**
     * Verifies the return of a list of display properties.
     */
    @Test
    public void testGetWithCodeList() {
        List<LocaleDisplay> displays = provider.get(Arrays.asList("en", "it"), new Locale("es"));
        assertEquals("en", displays.get(0).getLocale().getLanguage());
        assertEquals("es", displays.get(0).getDisplayLocale().getLanguage());
        assertEquals("it", displays.get(1).getLocale().getLanguage());
        assertEquals("es", displays.get(1).getDisplayLocale().getLanguage());
    }

    @Test
    public void testGetWithLocaleList() {
        List<LocaleDisplay> displays = provider.get(Arrays.asList(ENGLISH, ITALIAN), new Locale("es"));
        assertEquals("en", displays.get(0).getLocale().getLanguage());
        assertEquals("es", displays.get(0).getDisplayLocale().getLanguage());
        assertEquals("it", displays.get(1).getLocale().getLanguage());
        assertEquals("es", displays.get(1).getDisplayLocale().getLanguage());
    }

}
