/*
 * Copyright (c) 2013, 2015 Sacred Scripture Foundation.
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
package org.sacredscripturefoundation.commons.entity.dao;

import org.sacredscripturefoundation.commons.entity.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * This interface defines common methods to all data access objects.
 * <p>
 * Design references:
 * <ul>
 * <li><a href="https://developer.jboss.org/wiki/GenericDataAccessObjects">
 * Generic Data Access Objects</a></li>
 * <li><a href="http://dhptech.com/article/2006/10/06/java-1.5-generic-dao">Java
 * 1.5 Generic DAO</a> by Dana H. P'Simer, 2006-10-06</li>
 * <li><a href=
 * "http://www.ibm.com/developerworks/java/library/j-genericdao.html">Don't
 * repeat the DAO!"</a> by Per Mellqvist, 2006-05-12</li>
 * </ul>
 *
 * @param <T> the type of entity
 * @param <ID> the identifier of the entity
 * @author Paul Benedict
 * @since 1.0
 */
public interface Dao<T extends Entity<ID>, ID extends Serializable> {

    /**
     * Retrieves the count of all entities stored in this repository.
     *
     * @return the count
     */
    long count();

    /**
     * Forces any cached operations to be written to the repository.
     */
    void flush();

    /**
     * Retrieves the entity represented by the specified identifier.
     *
     * @param id the identifier
     * @return the entity if found; otherwise {@code null}
     * @throws NullPointerException if identifier is {@code null}
     * @see #getAll()
     */
    T get(ID id, boolean lock);

    /**
     * Generic method used to get all objects of a particular type. This is the
     * same as lookup up all rows in a table.
     *
     * @return List of populated objects
     * @see #get(Serializable, boolean)
     */
    List<T> getAll();

    /**
     * Retrieves the entity represented by the specified natural identifier. The
     * so-called "natural identifier" is an alternate unique value that has
     * business meaning in the real world. Not every entity class has a natural
     * identifier.
     * <p>
     * As of JPA 2.1, there is no native concept of a natural identifier. The
     * default implementation throws {@link UnsupportedOperationException}.
     * Vendors may have a proprietary extension; subclasses are free to override
     * this method to use them or execute a custom query.
     *
     * @param naturalId the natural identifier
     * @return the entity if found; otherwise {@code null}
     * @throws NullPointerException if identifier is {@code null}
     * @throws UnsupportedOperationException if entity has no natural identifier
     */
    T getByNaturalId(Serializable naturalId);

    /**
     * Inserts the specified entity. Updates will fail.
     *
     * @param entity the entity to insert
     * @throws NullPointerException if entity is {@code null}
     */
    void insert(T entity);

    /**
     * Performs a check against the repository to determine if the specified
     * entity is a duplicate (according to some business definition). This check
     * can assist business services that must produce a business exception when
     * a mutable natural key or other unique constraints clash with an existing
     * entity. For example, when a user updates a unique name, the update should
     * only be accepted if no other entity (other than the specified) is named
     * as such.
     * <p>
     * Implementations must guarantee the check is against the latest snapshot
     * of the repository. The check may not cause any persistence context to
     * flush. The check must support transient entities, persisted entities, and
     * detached entities.
     * <p>
     * The default implementation returns {@code false}.
     *
     * @param entity the entity to validate
     * @return {@code true} if the entity is duplicated; otherwise {@code false}
     * @throws NullPointerException if entity is {@code null}
     */
    boolean isDupe(T entity);

    /**
     * Obtains a concurrency lock from the repository for the specified entity.
     *
     * @param entity the entity to lock
     * @param optimistic {@code true} for version checking; otherwise row
     * locking
     */
    void lock(T entity, boolean optimistic);

    /**
     * Re-reads the state of the specified entity from the underlying
     * repository.
     *
     * @param entity the entity to refresh
     * @throws NullPointerException if entity is {@code null}
     */
    void refresh(T entity);

    void refresh(T entity, boolean optimistic);

    /**
     * Deletes the entity represented by the specified identifier. This method
     * is a time-saver when needing to load an entity solely for deletion.
     *
     * @param id the identifier
     * @throws NullPointerException if identifier is {@code null}
     * @see #remove(Serializable)
     */
    void remove(ID id);

    /**
     * Deletes the specified entity.
     *
     * @param entity the entity to delete
     * @throws NullPointerException if entity is {@code null}
     * @see #remove(Serializable)
     */
    void remove(T entity);

    /**
     * Updates the specified entity. Inserts will fail.
     *
     * @param entity the entity to update
     * @throws NullPointerException if entity is {@code null}
     */
    void update(T entity);

}
