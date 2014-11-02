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

import org.eclipse.persistence.queries.ScrollableCursor;

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

}
