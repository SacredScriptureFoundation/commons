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

import org.sacredscripturefoundation.commons.Named;

import java.util.Comparator;
import java.util.Locale;

/**
 * This class is a comparator that orders objects first by language in the
 * user's locale and second by the objects's canonical name.
 *
 * @param <T> the localized and named entity
 * @author Paul Benedict
 * @see LocaleProvider
 * @see Named
 * @since 1.0
 */
public class LocalizedNameComparator<T extends LocaleProvider & Named> implements Comparator<T> {

    private final LocaleDisplayProvider provider;
    private final Locale userLocale;

    /**
     * Constructs a new comparator that relies on the specified locale display
     * provider. The provider supplies the display names of languages in the
     * user's current locale.
     * <p>
     * For efficiency, the user's current locale is obtained and cached during
     * construction. Changing the user's locale between construction and
     * comparision will not affect the comparator instance.
     *
     * @param provider the locale display provider
     */
    public LocalizedNameComparator(LocaleDisplayProvider provider) {
        this.provider = provider;
        userLocale = LocaleContextHolder.getLocale();
    }

    @Override
    public int compare(T o1, T o2) {
        // Return immediately for reflexive comparisions
        if (o1 == o2) {
            return 0;
        }

        // Different locales are ordered by their display names in the user's
        // current locale
        Locale locale1 = o1.getLocale();
        Locale locale2 = o2.getLocale();
        if (!locale1.equals(locale2)) {
            String language1 = provider.getLanguage(locale1.getLanguage(), userLocale);
            String language2 = provider.getLanguage(locale2.getLanguage(), userLocale);
            return language1.compareTo(language2);
        }

        // Within the same locale, simply compare the names of the objects
        String name1 = o1.getName();
        String name2 = o2.getName();
        return name1.compareTo(name2);
    }

}
