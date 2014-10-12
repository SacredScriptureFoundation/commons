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
import org.sacredscripturefoundation.commons.locale.LocaleProvider;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

/**
 * This abstract superclass is a helper template for any entity whose properties
 * require dynamic localized translations. Typically, translations applies to
 * {@link String} objects but it doesn't have to be the case. A {@link Map}
 * holds the association of {@link Locale} language constants to its
 * translation.
 *
 * @param <ID> the type of primary key
 * @param <L> the type of localized content
 * @author Paul Benedict
 * @see LocalizedContentEntityTest
 * @since 1.0
 */
@MappedSuperclass
public abstract class LocalizableEntity<ID, L extends LocaleProvider> extends EntityImpl<ID> implements
        LocalizableContainer<L> {

    private static final String MSG_CONTENT_NULL = "Content is required";
    private static final String MSG_CONTENT_LOCALE_NULL = "Content's locale is required";
    private static final String MSG_USER_LOCALE_NULL = "User locale is required";

    @Override
    public final void addLocalizedContent(L content) {
        Objects.requireNonNull(content, MSG_CONTENT_NULL);
        Objects.requireNonNull(content.getLocale(), MSG_CONTENT_LOCALE_NULL);
        getLocalizedContents().put(content.getLocale(), content);
    }

    @Override
    public final L localize(Locale fallbackLocale) {
        // Try the user's locale
        Locale userLocale = Objects.requireNonNull(LocaleContextHolder.getLocale(), MSG_USER_LOCALE_NULL);
        L xlat = getLocalizedContents().get(userLocale);
        if (xlat != null) {
            return xlat;
        }

        // Try the fallback locale
        if (fallbackLocale != null) {
            xlat = getLocalizedContents().get(fallbackLocale);
            if (xlat != null) {
                return xlat;
            }
        }

        return null;
    }

}
