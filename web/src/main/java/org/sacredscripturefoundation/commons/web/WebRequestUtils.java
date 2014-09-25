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
package org.sacredscripturefoundation.commons.web;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

/**
 * This class contains miscellaneous utilities for dealing with HTTP requests.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public final class WebRequestUtils {

    /**
     * Constructs and returns a relative path based on the referring URL, if
     * any, with proceeding query parameters and anchor. The referer will be
     * used in redirecting the user back to the same page, and it does
     * <b>assume</b> the same host to prevent any XSS attack.
     *
     * @param request the HTTP request
     * @return the referring URL if found; otherwise {@code null}
     * @throws NullPointerException if request is {@code null}
     */
    public static String getRelativeReferer(HttpServletRequest request) {
        String referer = request.getHeader(REFERER_HEADER_NAME);
        if (referer != null) {
            URL url;
            try {
                url = new URL(referer);
            } catch (MalformedURLException e) {
                return null;
            }

            StringBuilder uriPath = new StringBuilder(url.getFile());
            if (url.getRef() != null) {
                uriPath.append("#");
                uriPath.append(url.getRef());
            }

            return uriPath.toString();
        }
        return null;
    }

    public static final String REFERER_HEADER_NAME = "Referer";

    /**
     * Uninstantiable. This is a static utility class.
     *
     * @throws AssertionError always
     */
    private WebRequestUtils() {
        throw new AssertionError();
    }

}
