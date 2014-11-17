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
package org.sacredscripturefoundation.commons;

/**
 * This abstract class is the root for all business exceptions. Business
 * exceptions do not roll back a transaction.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public abstract class BusinessException extends RuntimeException {

    /**
     * Constructs a new business exception.
     */
    public BusinessException() {
        // do nothing
    }

    /**
     * Constructs a new business exception with the specified message.
     *
     * @param message the message
     * @see #BusinessException()
     * @see #BusinessException(Throwable)
     * @see #BusinessException(String, Object...)
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message template and
     * arguments. The message template and arguments should be specified
     * according to {@link String#format(String, Object...)}.
     *
     * @param message the message template
     * @param args the message arguments
     * @see #BusinessException()
     * @see #BusinessException(String)
     * @see #BusinessException(Throwable)
     */
    public BusinessException(String message, Object... args) {
        super(String.format(message, args));
    }

    /**
     * Constructs a new business exception with the specified root cause.
     *
     * @param cause the root cause
     * @see #BusinessException()
     * @see #BusinessException(String)
     * @see #BusinessException(String, Object...)
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

}
