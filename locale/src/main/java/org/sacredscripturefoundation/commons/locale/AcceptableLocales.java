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

import org.sacredscripturefoundation.commons.Provider;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;

/**
 * This class is a holder for a list of locales which are considered
 * "acceptable" from the user.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class AcceptableLocales implements Provider<Collection<Locale>> {

    private final LinkedList<Locale> acceptableLocales;

    /**
     * Constructs a new {@code AcceptableLocales} instance.
     *
     * @param locales the whitespace-delimited list of locale codes
     */
    public AcceptableLocales(String locales) {
        acceptableLocales = new LinkedList<>();
        parseLocales(locales);
    }

    /**
     * Retrieves the collection of acceptable locales.
     *
     * @return the collection of locales; never {@code null}
     */
    @Override
    public Collection<Locale> get() {
        return acceptableLocales;
    }

    /**
     * Determines the best match for the specified locale.
     *
     * @param locale the locale to match
     * @return the best matched locale if found; otherwise {@code null}
     * @see #match(Locale, Locale)
     */
    public Locale match(Locale locale) {
        String languageCode = locale.getLanguage();
        for (Locale acceptableLocale : acceptableLocales) {
            if (acceptableLocale.getLanguage().equals(languageCode)) {
                return acceptableLocale;
            }
        }
        return null;
    }

    /**
     * Determines the best match for the specified locale, or fallbacks to the
     * default locale if not found.
     *
     * @param locale the locale to match
     * @return the best matched locale if found; otherwise the specified default
     * @see #match(Locale)
     */
    public Locale match(Locale locale, Locale defaultLocale) {
        Locale bestMatch = match(locale);
        return (bestMatch != null) ? bestMatch : defaultLocale;
    }

    /**
     * Decomposes the specified comma-delimited string of locale codes into
     * {@link Locale} instances for this object.
     *
     * @param locales the string of locale codes.
     */
    private void parseLocales(String locales) {
        String[] localeCodes = locales.split(" ");
        for (String localeCode : localeCodes) {
            Locale locale = LocaleStringUtils.parseLocaleString(localeCode);
            if (locale != null) {
                acceptableLocales.add(locale);
            }
        }
    }

}