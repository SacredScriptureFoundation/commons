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

import org.sacredscripturefoundation.commons.Named;

import java.util.Objects;

/**
 * This class is the mock implementation of an {@link Named} entity.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public class NamedMockEntity extends MockEntity implements Named, Comparable<NamedMockEntity> {

    private final String name;

    public NamedMockEntity(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(NamedMockEntity o) {
        return name.compareTo(o.getName());
    }

    /**
     * Two objects are equal if their names are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NamedMockEntity) {
            return name.equals(((NamedMockEntity) obj).getName());
        }
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

}
