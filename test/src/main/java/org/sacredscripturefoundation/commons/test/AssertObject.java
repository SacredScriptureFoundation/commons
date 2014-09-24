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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.sacredscripturefoundation.commons.InstanceProvider;
import org.sacredscripturefoundation.commons.InstantiationNotIntendedError;
import org.sacredscripturefoundation.commons.Provider;

/**
 * This class defines assertions common to {@link Object} testing.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public final class AssertObject {

    /**
     * Verifies the {@link Comparable#compareTo(Object)} contract for the
     * specified class.
     *
     * @param clazz the class to instantiate
     * @see #assertCompareToContract(Provider)
     */
    public static <T extends Comparable<T>> void assertCompareToContract(Class<T> clazz) {
        assertCompareToContract(new InstanceProvider<T>(clazz));
    }

    /**
     * Verifies the {@link Comparable#compareTo(Object)} contract for the type
     * bounded to the provider. For the test to succeed, the provider must act
     * as a true factory and produce new instances per invocation.
     *
     * @param <T> the type provided by the provider
     * @param provider the provider
     * @see #assertCompareToContract(Class)
     */
    public static <T extends Comparable<T>> void assertCompareToContract(Provider<T> provider) {
        T x = provider.get();
        T y = provider.get();

        // Reflexive
        assertEquals(0, x.compareTo(x));

        // Symmetric
        assertEquals(0, x.compareTo(y));
        assertEquals(0, y.compareTo(x));

        // Null
        try {
            x.compareTo(null);
            fail();
        } catch (NullPointerException e) {
            // expected
        }

        // Other class
        try {
            // FIXME Use raw type until Sun JDK is fixed
            @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
            int val = ((Comparable) x).compareTo(new Object());
            fail();
        } catch (ClassCastException e) {
            // expected
        }
    }

    /**
     * Verifies the {@link Object#equals(Object)} contract for the specified
     * class.
     *
     * @param clazz the class to instantiate
     * @see #assertEqualsContract(Provider)
     */
    public static <T> void assertEqualsContract(final Class<T> clazz) {
        assertEqualsContract(new InstanceProvider<T>(clazz));
    }

    /**
     * Verifies the {@link Object#equals(Object)} contract for the specified
     * type bounded to the provider. For the test to succeed, the provider must
     * act as a true factory and produce new instances per invocation.
     *
     * @param <T> the type provided by the provider
     * @param provider the provider
     * @see #assertEqualsContract(Class)
     */
    public static <T> void assertEqualsContract(Provider<T> provider) {
        // Obtain instances
        T x = provider.get();
        T y = provider.get();
        T z = provider.get();

        // Verify the provider acted as a true factory
        assertNotSame(x, y);
        assertNotSame(y, z);
        assertNotSame(x, z);

        assertEqualsReflexive(x);
        assertEqualsSymmetric(x, y);
        assertEqualsTransitive(x, y, z);
        assertEqualsNonNullity(x);
        assertEqualsOtherClass(x);
    }

    /**
     * Verifies for any non-null reference value <code>x</code>,
     * <code>x.equals(null)</code> should return {@code false}.
     *
     * @param x the object
     */
    private static void assertEqualsNonNullity(Object x) {
        assertFalse(x.equals(null));
    }

    /**
     * Verifies for any non-null reference values <code>x</code> and
     * <code>y</code>, <code>x.equals(y)</code> should return {@code false} if
     * <code>y</code> is considered an incompatible class for comparision by
     * <code>x</code>. This is not part of the <code>equals</code> contract, but
     * is a handy good practice when applicable.
     *
     * @param x the object
     */
    private static void assertEqualsOtherClass(Object x) {
        Object y = new Object() {
            // anonymous subclass
        };
        assertFalse(x.equals(y));
    }

    /**
     * Verififes for any non-null reference value <code>x</code>,
     * <code>x.equals(x)</code> should return {@code true}. The hash code is
     * also tested for repeatability, to verify two invocations return the same
     * hash.
     *
     * @param x the object
     */
    private static void assertEqualsReflexive(Object x) {
        assertTrue(x.equals(x));
        assertEquals(x.hashCode(), x.hashCode()); // Repeatability
    }

    /**
     * Verifies for any non-null reference values <code>x</code> and
     * <code>y</code>, <code>x.equals(y)</code> should return {@code true} if
     * and only if <code>y.equals(x)</code> returns {@code true}. The hash code
     * is also tested to verify that two equal objects have equal hashes.
     *
     * @param x the first object
     * @param y the second object
     */
    private static void assertEqualsSymmetric(Object x, Object y) {
        assertNotSame(x, y);
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));
        assertEquals(x.hashCode(), y.hashCode());
    }

    /**
     * Verifies for any non-null reference values <code>x</code>, <code>y</code>
     * , and <code>z</code>, if <code>x.equals(y)</code> returns {@code true}
     * and <code>y.equals(z)</code> returns {@code true}, then
     * <code>x.equals(z)</code> should return {@code true}. The hash code of all
     * objects are also compared for equality.
     *
     * @param x the first object
     * @param y the second object
     * @param z the third object
     */
    private static void assertEqualsTransitive(Object x, Object y, Object z) {
        assertTrue(x.equals(y));
        assertTrue(x.equals(z));
        assertTrue(y.equals(z));

        assertEquals(x.hashCode(), y.hashCode());
        assertEquals(x.hashCode(), z.hashCode());
        assertEquals(y.hashCode(), z.hashCode());
    }

    /**
     * Suppresss default constructor for noninstantiability.
     *
     * @throws InstantiationNotIntendedError always
     */
    private AssertObject() {
        throw new InstantiationNotIntendedError();
    }

}
