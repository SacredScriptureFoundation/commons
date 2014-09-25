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

/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sacredscripturefoundation.commons.locale;

import java.util.Locale;

/**
 * This utility class provides methods utility methods for parsing locale
 * strings.
 * <p>
 * The methods are modified copies of the ones distributed in the Spring
 * Framework.
 *
 * @author Paul Benedict
 * @version $Rev: 9 $
 * @see org.springframework.util.StringUtils
 * @since 1.0
 */
abstract class LocaleStringUtils {

    /**
     * Parse the given <code>localeString</code> value into a {@link Locale}.
     * <p>
     * This is the inverse operation of {@link Locale#toString Locale's
     * toString}.
     *
     * @param localeString the locale string, following <code>Locale's</code>
     * <code>toString()</code> format ("en", "en_UK", etc); also accepts spaces
     * as separators, as an alternative to underscores
     * @return a corresponding <code>Locale</code> instance
     */
    public static Locale parseLocaleString(String localeString) {
        String[] parts = localeString.split("_");
        String language = (parts.length > 0 ? parts[0] : "");
        String country = (parts.length > 1 ? parts[1] : "");
        validateLocalePart(language);
        validateLocalePart(country);
        String variant = "";
        if (parts.length >= 2) {
            // There is definitely a variant, and it is everything after the
            // country
            // code sans the separator between the country code and the variant.
            int endIndexOfCountryCode = localeString.indexOf(country) + country.length();
            // Strip off any leading '_' and whitespace, what's left is the
            // variant.
            variant = localeString.substring(endIndexOfCountryCode).trim();
            if (variant.startsWith("_")) {
                int afterVariantIndex = variant.indexOf("_");
                if (afterVariantIndex >= 0) {
                    variant = variant.substring(afterVariantIndex);
                }
            }
        }
        return (language.length() > 0 ? new Locale(language, country, variant) : null);
    }

    /**
     * Determine the RFC 3066 compliant language tag, as used for the HTTP
     * "Accept-Language" header.
     *
     * @param locale the Locale to transform to a language tag
     * @return the RFC 3066 compliant language tag as String
     */
    public static String toLanguageTag(Locale locale) {
        StringBuilder sb = new StringBuilder(locale.getLanguage());
        if (!locale.getCountry().isEmpty()) {
            sb.append("-");
            sb.append(locale.getCountry());
        }
        return sb.toString();
    }

    private static void validateLocalePart(String localePart) {
        for (int i = 0; i < localePart.length(); i++) {
            char ch = localePart.charAt(i);
            if (ch != '_' && ch != ' ' && !Character.isLetterOrDigit(ch)) {
                throw new IllegalArgumentException("Locale part \"" + localePart + "\" contains invalid characters");
            }
        }
    }

}
