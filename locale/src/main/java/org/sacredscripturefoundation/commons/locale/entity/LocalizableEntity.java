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

import org.sacredscripturefoundation.commons.entity.EntityImpl;
import org.sacredscripturefoundation.commons.locale.LocaleContextHolder;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

/**
 * This abstract superclass is for any entity whose properties require dynamic
 * localized translations. Typically, translations applies to {@link String}
 * objects but it doesn't have to be the case. A {@link Map} holds the
 * association of {@link Locale} language constants to its translation.
 *
 * @param <ID> the type of primary key
 * @param <X> the type of the translation
 * @author Paul Benedict
 * @see TranslationEntity
 * @since 1.0
 */
@MappedSuperclass
public abstract class LocalizableEntity<ID, X extends TranslationEntity<ID>> extends EntityImpl<ID> implements
        LocalizableContainer<X> {

    private static final String MSG_XLAT_NULL = "Translation is required";
    private static final String MSG_XLAT_LOCALE_NULL = "Translation's locale is required";
    private static final String MSG_USER_LOCALE_NULL = "User locale is required";

    @Override
    public final void addTranslation(X xlat) {
        Objects.requireNonNull(xlat, MSG_XLAT_NULL);
        Objects.requireNonNull(xlat.getLocale(), MSG_XLAT_LOCALE_NULL);
        getTranslations().put(xlat.getLocale(), xlat);
    }

    /**
     * Determines and retrieves the localized translation based on the user's
     * current locale. If the translation is absent, the translation of the
     * fallback locale is attempted. If that translation is absent, {@code null}
     * is returned.
     *
     * @return the translation or {@code null}
     */
    protected final X localize(Locale fallbackLocale) {
        // Try the user's locale
        Locale userLocale = Objects.requireNonNull(LocaleContextHolder.getLocale(), MSG_USER_LOCALE_NULL);
        X xlat = getTranslations().get(userLocale);
        if (xlat != null) {
            return xlat;
        }

        // Try the fallback locale
        if (fallbackLocale != null) {
            xlat = getTranslations().get(fallbackLocale);
            if (xlat != null) {
                return xlat;
            }
        }

        return null;
    }

    /**
     * Stores the new localized translations for this localizable entity.
     *
     * @param translations the map of translations
     * @see #getTranslations()
     */
    public abstract void setTranslations(Map<Locale, X> translations);

}
