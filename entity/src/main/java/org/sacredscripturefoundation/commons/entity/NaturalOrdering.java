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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is to be applied to any entity that has an inherent ordering
 * among a homogeneous collection. Respoitories should honor the preference when
 * possible.
 *
 * @author Paul Benedict
 * @since 1.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NaturalOrdering {

    /**
     * Determines whether the ordering as ascending or descending. The default
     * is ascending.
     *
     * @return {@code true} for ascending; {@code false} for descending.
     */
    boolean ascending() default true;

    /**
     * Determines whether the ordering is case-sensitive.
     *
     * @return {@code true} to retain case sensitivite ordering; otherwise
     * {@code false}.
     */
    boolean caseSensitive() default false;

    /**
     * Specifies the property name that a collection of homogeneous entity types
     * should be ordered about. The default is "name".
     *
     * @return the property name
     * @see org.sacredscripturefoundation.commons.Named
     */
    String property() default "name";

}
