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
 * The <code>DuplicateEntityException</code> class is thrown when the system
 * tries to insert a new resource into the system which already exists by the
 * specified identifier.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class DuplicateEntityException extends BusinessException {

    private Object extraInformation;

    /**
     * Constructs a new exception.
     *
     * @see #DuplicateEntityException(Throwable)
     * @see #DuplicateEntityException(String, Object...)
     */
    public DuplicateEntityException() {
        super();
    }

    /**
     * Constructs a new exception with the specified message template and
     * arguments.
     *
     * @param message the message template
     * @param args the message arguments
     * @see #DuplicateEntityException()
     * @see #DuplicateEntityException(Throwable)
     */
    public DuplicateEntityException(String message, Object... args) {
        super(message, args);
    }

    /**
     * Constructs a new exception and specifies the root cause.
     *
     * @see #DuplicateEntityException()
     * @see #DuplicateEntityException(String, Object...)
     */
    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }

    /**
     * Retrieves the optional information that further indicates the reason for
     * this exception.
     *
     * @return the extra information or {@code null}
     * @see #setExtraInformation(Object)
     */
    public Object getExtraInformation() {
        return extraInformation;
    }

    /**
     * Stores the new extra information of this exception
     *
     * @param extraInformation the extra information
     * @see #getExtraInformation()
     */
    public void setExtraInformation(Object extraInformation) {
        this.extraInformation = extraInformation;
    }

}
