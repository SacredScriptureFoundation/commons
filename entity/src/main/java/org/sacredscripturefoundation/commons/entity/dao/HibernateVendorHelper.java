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
package org.sacredscripturefoundation.commons.entity.dao;

import org.sacredscripturefoundation.commons.Count;
import org.sacredscripturefoundation.commons.CountImpl;
import org.sacredscripturefoundation.commons.entity.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.ScrollableResults;

/**
 * This class is the implementation for functionality specific to Hibernate.
 *
 * @param <T> the entity type
 * @param <ID> the entity identifier type
 * @author Paul Benedict
 */
public class HibernateVendorHelper<T extends Entity<ID>, ID extends Serializable> implements VendorHelper<T, ID> {

    @Override
    public Count<List<T>> page(Query query, int beginRow, int endRow) {
        // Get total result count
        int total;
        ScrollableResults scroll = query.unwrap(org.hibernate.Query.class).scroll();
        try {
            scroll.last();
            total = scroll.getRowNumber();
        } finally {
            scroll.close();
        }

        // Get page of data
        query.setFirstResult(beginRow);
        query.setMaxResults(endRow - beginRow + 1);

        @SuppressWarnings("unchecked")
        List<T> results = query.getResultList();
        return new CountImpl<List<T>>(total, results);
    }

}
