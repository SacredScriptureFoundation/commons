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

import java.util.Date;

/**
 * This interface defines the minimum auditing information of an object.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public interface Audited {

    /**
     * Retrieves the datetime when this object was created.
     *
     * @return the created datetime
     * @see #setCreated(Date)
     */
    Date getCreated();

    /**
     * Retrieves the datetime when this object was updated.
     *
     * @return the updated datetime
     * @see #setUpdated(Date)
     */
    Date getUpdated();

    void setCreated(Date created);

    void setUpdated(Date updated);

    void touch();

}