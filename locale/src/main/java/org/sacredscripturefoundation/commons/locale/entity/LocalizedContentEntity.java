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
import org.sacredscripturefoundation.commons.locale.LocaleProvider;

import java.util.Locale;

import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;

/**
 * This abstract class is a helper template to support any entity which
 * represent content translated for a specific locale.
 *
 * @param <ID> the type of primary key
 * @author Paul Benedict
 * @see LocalizableEntity
 * @since 1.0
 */
@MappedSuperclass
public abstract class LocalizedContentEntity<ID> extends EntityImpl<ID> implements LocaleProvider {

    @Convert(converter = LocaleLanguageConverter.class)
    private Locale locale;

    /**
     * Retrieves the locale of this translation. The value is typically used as
     * a dictionary key to identify the locale of the subclassed properties.
     *
     * @see #setLocale(Locale)
     */
    @Override
    public Locale getLocale() {
        return locale;
    }

    /**
     * Stores the new locale of this translation.
     *
     * @param locale the locale
     * @see #getLocale()
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return super.toString() + ",locale=" + locale;
    }

}
