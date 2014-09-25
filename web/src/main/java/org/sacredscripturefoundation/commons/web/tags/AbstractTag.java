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

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is the base for all custom tags. The goal is for custom tags to be
 * independent of any framework, although it may delegate to framework(s) for
 * method implementation. Although Struts 1.x is the main provider of the user
 * interface, extending their classes would be unwise for this goal.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public abstract class AbstractTag extends BodyTagSupport {

    /**
     * Instance logger.
     */
    protected final Logger log = LogManager.getLogger();

    /**
     * Convienence method to print the specified string to the page.
     *
     * @param s the string to write
     * @throws NullPointerException if the string is {@code null}
     * @throws JspException if an error occurs
     */
    protected final void print(String s) throws JspException {
        Objects.requireNonNull(s);
        try {
            pageContext.getOut().print(s);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    /**
     * Unlike {@link #release()} which is called to reset public properties (as
     * declared in the TLD) before the tag is re-pooled, this method is to reset
     * any <em>private</em> properties between iterations in a render.
     */
    protected void reset() {
        // do nothing
    }

    /**
     * Creates a URL by preprending the context path and then sending the URL to
     * the rewrite engine to include the session ID.
     *
     * @param subPath the context relative path
     * @return the encoded URL for this application
     * @throws NullPointerException if the path is {@code null}
     */
    protected final String rewrite(String subPath) {
        Objects.requireNonNull(subPath);
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        return response.encodeURL(request.getContextPath() + subPath);
    }

}
