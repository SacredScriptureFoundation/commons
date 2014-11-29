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

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.sacredscripturefoundation.commons.entity.MockEntity;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link JpaDaoImpl}.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class DaoEclipseLinkImplTest {

    private MockEntity entity;
    private JpaDaoImpl<MockEntity, MockEntity, Long> dao;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        entity = new MockEntity(1L);
        em = createNiceMock(EntityManager.class);
        dao = new JpaDaoImpl<MockEntity, MockEntity, Long>(MockEntity.class);
        dao.setEntityManager(em);
    }

    /**
     * Verifies the failure to use the bean constructor without subclassing.
     */
    @Test(expected = IllegalStateException.class)
    public void testConstructorWhenSubclassing() {
        new JpaDaoImpl<MockEntity, MockEntity, Long>();
    }

    /**
     * Verifies the bean constructor is able to extract the
     * {@link java.lang.reflect.ParameterizedType parameterized type} stored on
     * the subclass.
     */
    @Test
    public void testConstructorWhenSubclassIsParameterized() {
        dao = new JpaDaoImpl<MockEntity, MockEntity, Long>() {
            // do nothing
        };
        assertEquals(MockEntity.class, dao.entityClass());
    }

    /**
     * Verifies the failure to use the bean constructor that has no
     * {@link java.lang.reflect.ParameterizedType parameterized types}.
     */
    @SuppressWarnings("rawtypes")
    @Test(expected = IllegalStateException.class)
    public void testConstructorWhenSubclassIsRaw() {
        new JpaDaoImpl();
    }

    /**
     * Verifies specified entity class.
     */
    @Test
    public void testEntityClass() {
        assertEquals(MockEntity.class, dao.entityClass());
    }

    /**
     * Verifies the session was flushed.
     */
    @Test
    public void testFlush() {
        em.flush();
        replay(em);
        dao.flush();
        verify(em);
    }

    /**
     * Verifies the lock mode when retrieving the specified entity.
     */
    @Test
    public void testGetByIdLock() {
        expect(em.find(dao.entityClass(), entity.getId(), LockModeType.PESSIMISTIC_WRITE)).andReturn(entity);
        replay(em);
        assertEquals(entity, dao.get(entity.getId(), true));
        verify(em);
    }

    /**
     * Verifies there is no lock mode when retrieving the specified entity.
     */
    @Test
    public void testGetByIdNoLock() {
        expect(em.find(dao.entityClass(), entity.getId(), LockModeType.NONE)).andReturn(entity);
        replay(em);
        assertEquals(entity, dao.get(entity.getId(), false));
        verify(em);
    }

    /**
     * Verifies the failure to query with a {@code null} identifier.
     */
    @Test(expected = NullPointerException.class)
    public void testGetByIdNull() {
        dao.get(null, false);
    }

    /**
     * Verifies the session saves the specified entity.
     */
    @Test
    public void testInsert() {
        em.persist(entity);
        replay(em);
        dao.insert(entity);
        verify(em);
    }

    /**
     * Verifies the failure to insert a {@code null} entity.
     */
    @Test(expected = NullPointerException.class)
    public void testInsertNull() {
        dao.insert(null);
    }

    /**
     * Verifies the session refreshes the specified entity.
     */
    @Test
    public void testRefresh() {
        em.refresh(entity);
        replay(em);
        dao.refresh(entity);
        verify(em);
    }

    /**
     * Verifies the failure to refresh a {@code null} entity.
     */
    @Test(expected = NullPointerException.class)
    public void testRefreshNull() {
        dao.refresh(null);
    }

    /**
     * Verifies the session deletes the specified entity.
     */
    @Test
    public void testRemoveByEntity() {
        em.remove(entity);
        replay(em);
        dao.remove(entity);
        verify(em);
    }

    /**
     * Verifies the failure to remove a {@code null} entity.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveByEntityNull() {
        dao.remove((MockEntity) null);
    }

    /**
     * Verifies the entity is loaded and then removed.
     */
    @Test
    public void testRemoveById() {
        expect(em.find(MockEntity.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE)).andReturn(entity);
        replay(em);
        dao.remove(entity.getId());
        verify(em);
    }

    /**
     * Verifies the failure to remove by a {@code null} identifier.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveByIdNull() {
        dao.remove((Long) null);
    }

    /**
     * Verifies the session updates the specified entity.
     */
    @Test
    public void testUpdate() {
        em.persist(entity);
        replay(em);
        dao.update(entity);
        verify(em);
    }

    /**
     * Verifies the failure to update a {@code null} entity.
     */
    @Test(expected = NullPointerException.class)
    public void testUpdateNull() {
        dao.update(null);
    }

}
