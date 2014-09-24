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
package org.sacredscripturefoundation.commons;

import java.util.Collection;
import java.util.Objects;

/**
 * This class represents a generic container for an object and its associated
 * count value (like from a <code>COUNT(*)</code> SQL function).
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class CountImpl<T> implements Count<T> {

    /**
     * Calculates the total from the collection of counts.
     *
     * @param <T> any type
     * @param counts the collection
     * @return the total
     */
    public static <T> int total(Collection<Count<T>> counts) {
        int total = 0;
        for (Count<T> count : counts) {
            total += count.getCount();
        }
        return total;
    }

    private static final String MSG_COUNT_NEGATIVE = "Count cannot be negative: %d";

    private static final String MSG_COUNTED_OBJECT_NULL = "Counted object cannot be null";

    private int count;
    private T counted;

    /**
     * Constructs a new count.
     */
    public CountImpl() {
        // empty
    }

    /**
     * Constructs a new count for the specified object.
     *
     * @param count the count
     * @param counted the counted object
     * @throws IllegalArgumentException if count is negative
     * @throws NullPointerException if counted is {@code null}
     * @see #setCount(int)
     * @see #setCounted(Object)
     */
    public CountImpl(int count, T counted) {
        setCount(count);
        setCounted(counted);
    }

    /**
     * @see #setCount(int)
     */
    @Override
    public final int getCount() {
        return count;
    }

    /**
     * This implementation can only return {@code null} until
     * {@link #setCounted(Object)} is sucessfully invoked.
     *
     * @return {@inheritDoc}
     * @see #setCounted(Object)
     */
    @Override
    public final T getCounted() {
        return counted;
    }

    /**
     * Stores the new count.
     *
     * @param count the count
     * @throws IllegalArgumentException if count is negative
     * @see #getCount()
     * @see #setCounted(Object)
     */
    public final void setCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException(String.format(MSG_COUNT_NEGATIVE, count));
        }
        this.count = count;
    }

    /**
     * Stores the new object of this count.
     *
     * @param counted the counted object
     * @throws NullPointerException if counted is {@code null}
     * @see #getCounted()
     * @see #setCount(int)
     */
    public final void setCounted(T counted) {
        Objects.requireNonNull(counted, MSG_COUNTED_OBJECT_NULL);
        this.counted = counted;
    }

}
