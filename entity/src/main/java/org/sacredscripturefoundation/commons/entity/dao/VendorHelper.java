package org.sacredscripturefoundation.commons.entity.dao;

import org.sacredscripturefoundation.commons.Count;
import org.sacredscripturefoundation.commons.entity.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

/**
 * This interface defines an abstraction for vendor-specific functionality. If
 * JPA evolves to support such functionality, these methods should be integrated
 * into {@link Dao}.
 *
 * @param <T> the entity type
 * @param <ID> the entity identifier type
 * @author Paul Benedict
 */
public interface VendorHelper<T extends Entity<ID>, ID extends Serializable> {

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
    Count<List<T>> page(Query query, int beginRow, int endRow);

}
