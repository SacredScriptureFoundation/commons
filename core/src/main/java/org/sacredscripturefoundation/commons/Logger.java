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
package org.sacredscripturefoundation.commons;

/**
 * This class is a custom wrapper around Log4J 1.2 functionality to parameterize
 * messages in the vein of Log4J 2. Because the latter is so new, the library is
 * not natively built into application server environments. This is a stop-gap
 * measure; it can then be tossed away.
 * <p>
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class Logger {

    /**
     * See http://logging.apache.org/log4j/2.0/manual/messages.html
     *
     * @param message the message to format
     * @param params the message parameters (can be {@code null})
     * @return the formatted message
     */
    protected static String formatMessage(String message, Object... params) {
        // Nothing to do if no parameters
        if (params == null || params.length == 0) {
            return message;
        }

        StringBuffer buf = null; // Do not instantiate until needed
        int len = message.length();
        int pos = 0;
        int paramIndex = 0;

        // Go until the string or parameters are finished
        while ((pos < len) && (paramIndex < params.length)) {
            // If no opening brace, nothing left to do
            int lBracePos = message.indexOf('{', pos);
            if (lBracePos == -1) {
                break;
            }

            // If no closing brace or the closing brace is not adjacent, then
            // there is nothing left to do
            int rBracePos = message.indexOf('}', lBracePos);
            if ((rBracePos == -1) || (rBracePos > (lBracePos + 1))) {
                break;
            }

            // Allocate the buffer
            if (buf == null) {
                buf = new StringBuffer(message.length() + (params.length * 16));
            }

            // Append the text fragment and then the parameter
            buf.append(message.substring(pos, lBracePos));
            buf.append(params[paramIndex++]);
            pos = rBracePos + 1;
        }

        // If buffer was created, then add any potential trailing text fragment
        // before creating the final string; otherwise just return the string
        // passed in
        String retMsg;
        if (buf != null) {
            if (pos < len) {
                buf.append(message.substring(pos));
            }
            retMsg = buf.toString();
        } else {
            retMsg = message;
        }

        return retMsg;
    }

    private final org.apache.log4j.Logger log;

    /**
     * Constructs a logger for the specified class.
     *
     * @param c the class
     * @throws NullPointerException if parameter is null
     */
    public Logger(Class<?> c) {
        log = org.apache.log4j.Logger.getLogger(c);
    }

    public void debug(String message, Object... params) {
        if (log.isDebugEnabled()) {
            log.debug(formatMessage(message, params));
        }
    }

    public void debug(String message, Throwable t, Object... params) {
        if (log.isDebugEnabled()) {
            log.debug(formatMessage(message, params), t);
        }
    }

    public void error(String message, Object... params) {
        log.error(formatMessage(message, params));
    }

    public void error(String message, Throwable t, Object... params) {
        log.error(formatMessage(message, params), t);
    }

    public void fatal(String message, Object... params) {
        log.fatal(formatMessage(message, params));
    }

    public void fatal(String message, Throwable t, Object... params) {
        log.fatal(formatMessage(message, params), t);
    }

    public void info(String message, Object... params) {
        if (log.isInfoEnabled()) {
            log.info(formatMessage(message, params));
        }
    }

    public void info(String message, Throwable t, Object... params) {
        if (log.isInfoEnabled()) {
            log.info(formatMessage(message, params), t);
        }
    }

    public void trace(String message, Object... params) {
        if (log.isTraceEnabled()) {
            log.trace(formatMessage(message, params));
        }
    }

    public void trace(String message, Throwable t, Object... params) {
        if (log.isTraceEnabled()) {
            log.trace(formatMessage(message, params), t);
        }
    }

}
