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

import org.sacredscripturefoundation.commons.AbstractModel;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This abstract class is the base for all entity objects. An entity is a model
 * that is persisted to a repository (like a database or file system) and
 * identified by a unique key.
 *
 * @param <ID> the identifier (primary key) type
 * @author Paul Benedict
 * @since 1.0
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class EntityImpl<ID> extends AbstractModel implements Entity<ID>, AssignableNativeId<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    /**
     * Constructs a new entity without an identifier.
     */
    public EntityImpl() {
        // empty
    }

    /**
     * Constructs a new instance with the specified identifier.
     *
     * @param id the identifier
     * @throws NullPointerException if id is {@code null}
     */
    public EntityImpl(ID id) {
        Objects.requireNonNull(id);
        setId(id);
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public void setCreated(Date created) {
        this.created = (Date) (created != null ? created.clone() : null);
    }

    /**
     * Stores the new identifier for this entity.
     *
     * @param id the id
     * @see #getId()
     * @throws IllegalArgumentException if the identifier is numeric and less
     * than zero
     */
    @Override
    public void setId(ID id) {
        if (id instanceof Number && ((Number) id).intValue() < 0) {
            throw new IllegalArgumentException(id.toString());
        }
        this.id = id;
    }

    @Override
    public void setUpdated(Date updated) {
        this.updated = (Date) (updated != null ? updated.clone() : null);
    }

    /**
     * Prints out the identifier. For subclasses which override this method,
     * only minimum information should be appended in case this object is
     * accidentally dumped to the user.
     */
    @Override
    public String toString() {
        return "id=" + id;
    }

    @Override
    public void touch() {
        setUpdated(new Date());
    }

}
