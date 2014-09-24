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
package org.sacredscripturefoundation.commons.test;

import static org.junit.Assert.assertFalse;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * This abstract class supports JPA integration testing with assistance of the
 * Spring Framework.
 *
 * @author Paul Benedict
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-orm.xml")
@Transactional
@TransactionConfiguration
@Ignore("For Eclipse")
public class AbstractSpringJpaIntegrationTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject
    protected EntityManager em;

    /**
     * Verifies that the specified object is a transient object as defined by
     * the persistence lifecycle of JPA.
     *
     * @param o the object to verify
     * @throws AssertionError if the object is not transient
     */
    protected final void assertTransient(final Object o) {
        assertFalse(em.contains(o));
    }

    /**
     * Convenience method to execute JQL and retrieve the expected single
     * entity. Returns {@code null} if 0 entities found.
     *
     * @param <T> the expected entity type
     * @param query the JQL statement
     * @return the entity
     * @throws IncorrectResultSizeDataAccessException if one entity is not found
     */
    @SuppressWarnings("unchecked")
    protected final <T> T findSingle(String query) {
        return (T) DataAccessUtils.singleResult(em.createNamedQuery(query).getResultList());
    }

}
