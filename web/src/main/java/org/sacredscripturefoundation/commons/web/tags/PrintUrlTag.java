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
package org.sacredscripturefoundation.commons.web.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * This tag prints the URL of the printable version of the current request. A
 * second tag will usually wrap this output and present it as a link to the
 * user.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public final class PrintUrlTag extends AbstractTag {

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String qs = request.getQueryString();

        StringBuilder printURL = new StringBuilder(request.getRequestURL());
        printURL.append("?");
        if (qs != null) {
            printURL.append(qs);
            printURL.append("&");
        }
        printURL.append("printable=true");
        print(printURL.toString());

        return SKIP_BODY;
    }

}
