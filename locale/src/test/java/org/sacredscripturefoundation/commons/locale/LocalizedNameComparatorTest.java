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

import static org.junit.Assert.assertTrue;

import org.sacredscripturefoundation.commons.Named;
import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;
import org.sacredscripturefoundation.commons.locale.LocaleDisplayProvider;
import org.sacredscripturefoundation.commons.locale.LocaleProvider;
import org.sacredscripturefoundation.commons.locale.LocalizedNameComparator;

import java.util.Arrays;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link LocalizedNameComparator}.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class LocalizedNameComparatorTest {

    private static class MockLocalizedNamed implements LocaleProvider, Named {

        private final Locale locale;
        private final String name;

        public MockLocalizedNamed(Locale locale, String name) {
            this.locale = locale;
            this.name = name;
        }

        @Override
        public Locale getLocale() {
            return locale;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    private LocalizedNameComparator<MockLocalizedNamed> comparator;
    private LocaleDisplayProvider displayProvider;

    @Before
    public void setUp() throws Exception {
        displayProvider = new LocaleDisplayProvider();
    }

    @Test
    public void testCompare() {
        final Locale userLocale = new Locale("es");
        LocaleContextHolder.setLocale(userLocale);
        comparator = new LocalizedNameComparator<MockLocalizedNamed>(displayProvider);

        MockLocalizedNamed[] entities = new MockLocalizedNamed[5];
        entities[0] = new MockLocalizedNamed(new Locale("en"), "Edición Estándar Revisada - Versión Católica");
        entities[1] = new MockLocalizedNamed(new Locale("iw"), "El Texto Masoretic");
        entities[2] = new MockLocalizedNamed(new Locale("la"), "Nova Vulgata");
        entities[3] = new MockLocalizedNamed(new Locale("en"), "Version del Rey Jaime");
        entities[4] = new MockLocalizedNamed(new Locale("la"), "Vulgata Clementina");

        Arrays.sort(entities, comparator);

        Locale previousLocale = null;
        String previousName = null;
        for (MockLocalizedNamed e : entities) {
            final Locale currentLocale = e.getLocale();
            final String currentName = e.getName();

            if (previousLocale != null) {
                // Verify the locales are in ascending order
                String previousLang = displayProvider.getLanguage(previousLocale.getLanguage(), userLocale);
                String currentLang = displayProvider.getLanguage(currentLocale.getLanguage(), userLocale);
                assertTrue(previousLang.compareTo(currentLang) <= 0);

                // Names should be alphabetical within a locale
                if (previousLang.equals(currentLang)) {
                    assertTrue(previousName.compareTo(currentName) < 0);
                }
            }

            previousLocale = currentLocale;
            previousName = currentName;
        }
    }

}
