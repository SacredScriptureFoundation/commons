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

import java.util.Locale;
import java.util.Objects;

/**
 * This class exists to associate a locale with localized display properties.
 * Because the {@link Locale} class is <code>final</code>, it is impossible to
 * subclass to supply custom translations for locales which are not bundled
 * within the JDK. JDK 6 allows SPI implementations to provide translations, and
 * this class should back such an implementation.
 *
 * @author Paul Benedict
 * @see java.util.Locale
 * @since 1.0
 */
public class LocaleDisplay implements LocaleProvider {

    private static final String MSG_DISPLAY_LANGUAGE_REQUIRED = "Display language is required";
    private static final String MSG_DISPLAY_LOCALE_REQUIRED = "Display locale is required";
    private static final String MSG_LOCALE_REQUIRED = "Locale is required";

    private final Locale locale;
    private final Locale displayLocale;
    private final String displayLanguage;

    /**
     * Constructs a new {@code LocaleDisplay}.
     *
     * @param locale the target locale
     * @oaram displayLocale the locale of the display
     * @param displayLanguage the locale's display language
     * @throws NullPointerException if any argument is {@code null}
     */
    public LocaleDisplay(Locale locale, Locale displayLocale, String displayLanguage) {
        Objects.requireNonNull(locale, MSG_LOCALE_REQUIRED);
        Objects.requireNonNull(displayLocale, MSG_DISPLAY_LOCALE_REQUIRED);
        Objects.requireNonNull(displayLanguage, MSG_DISPLAY_LANGUAGE_REQUIRED);
        this.locale = locale;
        this.displayLocale = displayLocale;
        this.displayLanguage = displayLanguage;
    }

    /**
     * @see java.util.Locale#getDisplayLanguage()
     */
    public final String getDisplayLanguage() {
        return displayLanguage;
    }

    /**
     * Retrieves the display locale.
     *
     * @return the display locale
     */
    public final Locale getDisplayLocale() {
        return displayLocale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

}
