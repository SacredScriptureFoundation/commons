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
package org.sacredscripturefoundation.commons.locale.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link LocalizableEntity}.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class AbstractLocalizedEntityTest {

    private MockLocalizableEntity entity;

    @Before
    @SuppressWarnings("serial")
    public void setUp() throws Exception {
        entity = new MockLocalizableEntity();
    }

    /**
     * Verifies adding a localized content.
     */
    @Test
    public void testAddTranslation() {
        MockLocalizedContentEntity content = new MockLocalizedContentEntity(Locale.ENGLISH);
        entity.addLocalizedContent(content);
        assertEquals(content, entity.getLocalizedContent().get(Locale.ENGLISH));
    }

    /**
     * Verifies the error to add a {@code null} localized content.
     */
    @Test(expected = NullPointerException.class)
    public void testAddTranslationNull() {
        entity.addLocalizedContent(null);
    }

    /**
     * Verifies the error to add a localized content that specifies a
     * {@code null} locale.
     */
    @Test(expected = NullPointerException.class)
    public void testAddTranslationWithNullLocale() {
        MockLocalizedContentEntity content = new MockLocalizedContentEntity();
        content.setLocale(null);
        entity.addLocalizedContent(content);
    }

    /**
     * Verifies localizing when a locale matches the user's locale.
     */
    @Test
    public void testLocalize() {
        MockLocalizedContentEntity content = new MockLocalizedContentEntity(Locale.ENGLISH);
        entity.addLocalizedContent(content);
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        assertSame(content, entity.localize(null));
    }

    /**
     * Verifies the error to localize when the user's locale is absent.
     */
    @Test(expected = NullPointerException.class)
    public void testLocalizeWhenUserLocaleIsNull() {
        LocaleContextHolder.setLocale(null);
        assertNull(entity.localize(null));
    }

    /**
     * Verifies the failure to localize when no locale matches the user's
     * locale.
     */
    @Test
    public void testLocalizeWhenUserLocaleMismatches() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        MockLocalizedContentEntity content = new MockLocalizedContentEntity(Locale.FRENCH);
        entity.addLocalizedContent(content);
        assertNull(entity.localize(null));
    }

    /**
     * Verifies localizing using the entity's fallback locale when the user's
     * locale is not found.
     */
    @Test
    public void testLocalizeWhenUserLocaleMismatchesWithFallback() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        MockLocalizedContentEntity content = new MockLocalizedContentEntity(Locale.FRENCH);
        entity.addLocalizedContent(content);
        assertSame(content, entity.localize(Locale.FRENCH));
    }

    /**
     * Verifies the failure to localize when neither the user's locale nor the
     * entity's fallback locale matches.
     */
    @Test
    public void testLocalizeWhenUserLocaleMismatchesWithFallbackFailure() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        MockLocalizedContentEntity content = new MockLocalizedContentEntity(Locale.FRENCH);
        entity.addLocalizedContent(content);
        assertNull(entity.localize(Locale.GERMAN));
    }

}
