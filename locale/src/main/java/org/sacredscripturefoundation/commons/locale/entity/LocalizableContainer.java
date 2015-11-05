/*
 * Copyright (c) 2014, 2015 Sacred Scripture Foundation.
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

import org.sacredscripturefoundation.commons.locale.LocaleProvider;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * This interface defines a container that manages translated content for
 * individual locales.
 *
 * @author Paul Benedict
 * @since 1.0
 * @param <L> the localized content type
 */
public interface LocalizableContainer<L extends LocaleProvider> {

    /**
     * Adds the specified localized content to this container.
     *
     * @param content the localized content to add
     * @throws NullPointerException if either the content or its locale are
     * {@code null}
     * @see #getLocalizedContents()
     */
    void addLocalizedContent(L content);

    /**
     * Retrieves the mapping of all localized contents managed by this
     * container. The content is keyed by locale.
     *
     * @return the map (never {@code null})
     * @see #addLocalizedContent(LocaleProvider)
     * @see #supportsLocale(Locale)
     */
    Map<Locale, L> getLocalizedContents();

    /**
     * Retrieves the set of locales managed by this container.
     *
     * @return the set of locales (never {@code null}
     * @see #supportsLocale(Locale)
     */
    Set<Locale> locales();

    /**
     * Retrieves the localized content based on the specified locale.
     *
     * @param locale the locale
     * @return the content or {@code null}
     * @throws NullPointerException if {@code locale} is {@code null}
     */
    L localize(Locale locale);

    /**
     * Determines if this container has localized content for the specified
     * locale.
     *
     * @param locale the locale to find
     * @return {@code true} if supports; otherwise {@code false}
     * @see #locales()
     */
    boolean supportsLocale(Locale locale);

}