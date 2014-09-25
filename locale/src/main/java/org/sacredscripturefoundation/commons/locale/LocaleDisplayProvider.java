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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class provides <code>Locale</code> utilities. There is a need to display
 * many language names in the user's locale, but the JDK does not provide all
 * necessary translations. There is a private store of resource bundles which
 * contain the translations to back this class.
 *
 * @author Paul Benedict
 * @see java.util.Locale
 * @since 1.0
 */
public class LocaleDisplayProvider {

    private static final String DEFAULT_BUNDLE_LOCATION = "org/sacredscripturefoundation/commons/locale/Language";

    private final Map<String, ResourceBundle> bundles;
    private final Map<String, LocaleDisplay> displays;
    private String bundleLocation;

    /**
     * Constructs a new locale display provider.
     */
    public LocaleDisplayProvider() {
        bundles = new HashMap<String, ResourceBundle>();
        displays = new HashMap<String, LocaleDisplay>();
        bundleLocation = DEFAULT_BUNDLE_LOCATION;
    }

    /**
     * Retrieves the display instances for the language codes represented by the
     * specified locales.
     *
     * @param locales the locales
     * @param displayLocale the display locale
     * @return the displays
     * @throws MissingResourceException if the bundle cannot be loaded
     */
    public List<LocaleDisplay> get(Collection<Locale> locales, Locale displayLocale) throws MissingResourceException {
        LinkedList<String> languageCodes = new LinkedList<String>();
        for (Locale locale : locales) {
            languageCodes.add(locale.getLanguage());
        }
        return get(languageCodes, displayLocale);
    }

    /**
     * Retrieves the display instances for the specified language codes.
     *
     * @param languageCodes the two-letter ISO-639 codes
     * @param displayLocale the display locale
     * @return the displays
     * @throws MissingResourceException if the bundle cannot be loaded
     * @see #get(String, Locale)
     */
    public List<LocaleDisplay> get(List<String> languageCodes, Locale displayLocale) throws MissingResourceException {
        ArrayList<LocaleDisplay> list = new ArrayList<LocaleDisplay>(languageCodes.size());
        for (String code : languageCodes) {
            list.add(get(code, displayLocale));
        }
        return list;
    }

    /**
     * Retrieves the display in the locale for the language code. For example,
     * if the language code is "en" (English) and the display locale is "fr_FR",
     * this will return "anglais".
     *
     * @param languageCode the two-letter ISO-639 code
     * @param displayLocale the display locale
     * @return the display
     * @throws MissingResourceException if the bundle cannot be loaded
     * @see #getLanguage(String, Locale)
     */
    public LocaleDisplay get(String languageCode, Locale displayLocale) throws MissingResourceException {
        Locale locale = new Locale(languageCode);
        ResourceBundle bundle = loadBundle(displayLocale);
        return lookup(locale, displayLocale, bundle.getString(languageCode));
    }

    /**
     * Retrieves the display language in the locale for the specified code.
     *
     * @param languageCode the two-letter ISO-639 code
     * @param displayLocale the display locale
     * @return the localized text if found or {@code null}
     * @see #get(String, Locale)
     */
    public String getLanguage(String languageCode, Locale displayLocale) {
        try {
            return get(languageCode, displayLocale).getDisplayLanguage();
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * Loads into memory the locale-specific resource bundle containing language
     * names. The bundles are not always retrieved directly; they are stored in
     * a map to cache subsequent usage (this may be doublework if Java already
     * caches loaded property files).
     *
     * @param locale the locale specific bundle to load
     * @return the resource bundle
     * @throws MissingResourceException if the bundle cannot be loaded
     */
    private ResourceBundle loadBundle(Locale locale) throws MissingResourceException {
        String languageCode = locale.getLanguage();
        ResourceBundle bundle = bundles.get(languageCode);

        if (bundle == null) {
            bundle = ResourceBundle.getBundle(bundleLocation, locale);
            bundles.put(languageCode, bundle);
        }

        return bundle;
    }

    /**
     * Internal method which first consults the cache for the display.
     *
     * @param locale the target locale
     * @param displayLocale the display locale
     * @param languageCode the two-letter ISO-639 code
     * @return the display
     */
    private LocaleDisplay lookup(Locale locale, Locale displayLocale, String languageCode) {
        StringBuilder key = new StringBuilder();
        key.append(languageCode);
        key.append(":");
        key.append(displayLocale.getLanguage());

        LocaleDisplay display = displays.get(key.toString());
        if (display == null) {
            display = new LocaleDisplay(locale, displayLocale, languageCode);
            displays.put(key.toString(), display);
        }

        return display;
    }

    /**
     * Stores the location of the message resources location. If not invoked, a
     * default path is used.
     *
     * @param bundleLocation the bundle location
     * @throws NullPointerException if the location is {@code null}
     */
    public void setBundleLocation(String bundleLocation) {
        Objects.requireNonNull(bundleLocation);
        this.bundleLocation = bundleLocation;
    }

}
