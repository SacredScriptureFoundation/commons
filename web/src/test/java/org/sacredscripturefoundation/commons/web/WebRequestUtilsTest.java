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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.sacredscripturefoundation.commons.web.WebRequestUtils.REFERER_HEADER_NAME;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

/**
 * Unit tests for {@link WebRequestUtils}.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class WebRequestUtilsTest {

    /**
     * Verifies the extracted relative path contains the anchor.
     */
    @Test
    public void testGetRelativeRefererWithAnchor() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(REFERER_HEADER_NAME)).andReturn("http://domain.com/file#2");
        replay(request);
        assertEquals("/file#2", WebRequestUtils.getRelativeReferer(request));
    }

    /**
     * Verifies the extracted relative path for a context root.
     */
    @Test
    public void testGetRelativeRefererWithContextRoot() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(REFERER_HEADER_NAME)).andReturn("http://domain.com/app/");
        replay(request);
        assertEquals("/app/", WebRequestUtils.getRelativeReferer(request));
    }

    /**
     * Verifies the extracted relative path is {@code null} for specifying
     * {@code null}.
     */
    @Test(expected = NullPointerException.class)
    public void testGetRelativeRefererWithNull() {
        assertNull(WebRequestUtils.getRelativeReferer(null));
    }

    /**
     * Verifies the extracted relative path for a simple page.
     */
    @Test
    public void testGetRelativeRefererWithPage() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(REFERER_HEADER_NAME)).andReturn("http://domain.com/app/file");
        replay(request);
        assertEquals("/app/file", WebRequestUtils.getRelativeReferer(request));
    }

    /**
     * Verifies the extracted relative path contains the query parameter.
     */
    @Test
    public void testGetRelativeRefererWithQueryParam() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(REFERER_HEADER_NAME)).andReturn("http://domain.com/app/file?id=1");
        replay(request);
        assertEquals("/app/file?id=1", WebRequestUtils.getRelativeReferer(request));
    }

    /**
     * Verifies the extracted relative path contains the query parameter and
     * anchor.
     */
    @Test
    public void testGetRelativeRefererWithQueryParamAndAnchor() {
        HttpServletRequest request = createMock(HttpServletRequest.class);
        expect(request.getHeader(REFERER_HEADER_NAME)).andReturn("http://domain.com/app/file?id=1#2");
        replay(request);
        assertEquals("/app/file?id=1#2", WebRequestUtils.getRelativeReferer(request));
    }

}
