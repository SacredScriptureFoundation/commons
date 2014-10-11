/*
 * Copyright (c) 2014 Sacred Scripture Foundation.
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
     * @see #getLocalizedContent()
     */
    void addLocalizedContent(L content);

    /**
     * Retrieves the mapping of localized content of this container. The content
     * is keyed by locale.
     *
     * @return the map (never {@code null})
     * @see #addLocalizedContent(LocaleProvider)
     */
    Map<Locale, L> getLocalizedContent();

}