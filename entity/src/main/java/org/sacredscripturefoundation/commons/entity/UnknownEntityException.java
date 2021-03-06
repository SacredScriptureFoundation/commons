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
package org.sacredscripturefoundation.commons.entity;

import org.sacredscripturefoundation.commons.BusinessException;

/**
 * This exception is thrown when the system expects the resource to be present,
 * but it cannot be found by the specified identifier.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class UnknownEntityException extends BusinessException {

    /**
     * Constructs a new exception.
     *
     * @see #UnknownEntityException()
     * @see #UnknownEntityException(String)
     */
    public UnknownEntityException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message the message
     * @see #UnknownEntityException()
     * @see #UnknownEntityException(String, Object...)
     */
    public UnknownEntityException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified message template and
     * arguments.
     *
     * @param message the message template
     * @param args the message arguments
     * @see #UnknownEntityException()
     * @see #UnknownEntityException(String)
     */
    public UnknownEntityException(String message, Object... args) {
        super(message, args);
    }

}
