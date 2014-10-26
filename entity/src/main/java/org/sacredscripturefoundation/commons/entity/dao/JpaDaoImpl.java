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
package org.sacredscripturefoundation.commons.entity.dao;

import org.sacredscripturefoundation.commons.Count;
import org.sacredscripturefoundation.commons.CountImpl;
import org.sacredscripturefoundation.commons.entity.Entity;
import org.sacredscripturefoundation.commons.entity.NaturalOrdering;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.queries.ScrollableCursor;

/**
 * This class serves as the Base class for all other Daos - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.
 *
 * @param <T> the entity type
 * @param <ID> the entity identifier type
 * @author Paul Benedict
 * @see NaturalOrdering
 * @since 1.0
 */
public class JpaDaoImpl<T extends Entity<ID>, ID extends Serializable> implements Dao<T, ID> {

    private static final String MSG_NO_GENERICIZED_SUBCLASS = "Constructor requires genericized subclass";
    private static final String MSG_UNKNOWN_ID = "Unknown %s id[%s]";

    protected final Logger log = LogManager.getLogger();
    private final Class<T> entityClass;
    private final NaturalOrdering ordering;
    private EntityManager em;

    /**
     * Constructs a new JPA DAO implementation. This constructor is solely for
     * compile-time construct using subclassed generics. The entity type will be
     * inferred from the type arguments.
     */
    @SuppressWarnings("unchecked")
    protected JpaDaoImpl() {
        try {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            throw new IllegalStateException(MSG_NO_GENERICIZED_SUBCLASS, e);
        }
        ordering = entityClass.getAnnotation(NaturalOrdering.class);
    }

    /**
     * Constructs a new JPA DAO implementation for the specified entity type.
     *
     * @param entityClass the entity type
     * @throws NullPointerException if the type is {@code null}
     */
    public JpaDaoImpl(Class<T> entityClass) {
        Objects.requireNonNull(entityClass);
        this.entityClass = entityClass;
        ordering = entityClass.getAnnotation(NaturalOrdering.class);
    }

    protected final Class<T> entityClass() {
        return entityClass;
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public T get(ID id, boolean lock) {
        Objects.requireNonNull(id);
        LockModeType lockMode = lock ? LockModeType.PESSIMISTIC_WRITE : LockModeType.NONE;
        return em.find(entityClass, id, lockMode);
    }

    @Override
    public List<T> getAll() {
        TypedQuery<T> query = em.createQuery(newQuery(em.getCriteriaBuilder()));
        return query.getResultList();
    }

    @Override
    public T getByNaturalId(Serializable id, boolean required) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(entityClass);
        cq.where(builder.equal(cq.from(entityClass).get("naturalId"), id));

        T entity = em.createQuery(cq).getSingleResult();
        if ((entity == null) && required) {
            throw new EntityNotFoundException(String.format(MSG_UNKNOWN_ID, entityClass.getName(), id));
        }
        return entity;
    }

    @Override
    public void insert(T entity) {
        Objects.requireNonNull(entity);
        em.persist(entity);
    }

    @Override
    public boolean isDupe(T entity) {
        return false;
    }

    @Override
    public void lock(T entity, boolean optimistic) {
        Objects.requireNonNull(entity);
        em.lock(entity, optimistic ? LockModeType.OPTIMISTIC : LockModeType.PESSIMISTIC_READ);
    }

    /**
     * Creates a new named query for the specified name.
     *
     * @param name the name of the query
     * @return the query instance
     * @see #newQuery(CriteriaBuilder)
     */
    protected TypedQuery<T> newNamedQuery(String name) {
        return em.createNamedQuery(name, entityClass);
    }

    /**
     * Creates a new criteria rooted at the configured entity class. Default
     * ordering is automatically applied if provided.
     * <p>
     * Subclasses should override this method to provide advanced criteria
     * customization.
     *
     * @return the criteria
     * @see #newNamedQuery(String)
     */
    protected CriteriaQuery<T> newQuery(CriteriaBuilder builder) {
        CriteriaQuery<T> crit = builder.createQuery(entityClass);
        Root<T> root = crit.from(entityClass);
        if (ordering != null) {
            Path<Object> propPath = root.get(ordering.property());
            Order order = ordering.ascending() ? builder.asc(propPath) : builder.desc(propPath);
            crit.orderBy(order);
        }
        crit.select(root);
        return crit;
    }

    /**
     * Executes the specified query for pagination purposes. The page of data is
     * fetched within the specified row boundaries, and a second reporting query
     * is executed to calculate the toal number of would-be results.
     *
     * @param query the query
     * @param beginRow the beginning row index from 0
     * @param endRow the ending row index inclusive
     * @return the list of data plus its total count
     */
    protected final Count<List<T>> page(Query query, int beginRow, int endRow) {
        query.setHint("eclipselink.cursor.scrollable", true);
        ScrollableCursor cursor = (ScrollableCursor) query.getSingleResult();
        try {
            int total = cursor.size();
            cursor.absolute(beginRow);

            // FIXME Eclipse compiler bug
            // Should error but doesn't: can't convert List<Object> to List<T>
            // Does error if moved to another source file! But yet to extract
            // a test case that reproduces bug
            // List<T> results = (List<T>) cursor.next(endRow - beginRow + 1);
            @SuppressWarnings("unchecked")
            List<T> results = List.class.cast(cursor.next(endRow - beginRow + 1));
            return new CountImpl<List<T>>(total, results);
        } finally {
            cursor.close();
        }
    }

    @Override
    public void refresh(T entity) {
        Objects.requireNonNull(entity);
        em.refresh(entity);
    }

    @Override
    public void refresh(T entity, boolean optimistic) {
        Objects.requireNonNull(entity);
        em.refresh(entity, optimistic ? LockModeType.OPTIMISTIC : LockModeType.PESSIMISTIC_READ);
    }

    @Override
    public void remove(ID id) {
        Objects.requireNonNull(id);
        em.remove(get(id, true));
    }

    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        em.remove(entity);
    }

    public final void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void update(T entity) {
        Objects.requireNonNull(entity);
        em.persist(entity);
    }

}
