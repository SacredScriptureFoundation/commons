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

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.sacredscripturefoundation.commons.InstantiationNotIntendedError;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * This class defines assertions common to {@link Class} testing.
 *
 * @author Paul Benedict
 * @since 1.0
 */
public final class AssertClass {

    /**
     * Verifies the specified class throws an {@link AssertionError} on
     * instantiation.
     *
     * @param clazz the class to verify
     * @throws Exception if assertion failure
     */
    public static void assertClassNonInstantiable(Class<?> clazz) throws Exception {
        Constructor<?> ctor = clazz.getDeclaredConstructor((Class<?>[]) null);
        ctor.setAccessible(true);
        try {
            ctor.newInstance((Object[]) null);
            fail(format(MSG_CLASS_INSTANTIATED, clazz.getName()));
        } catch (InvocationTargetException e) {
            assertTrue(AssertionError.class.isInstance(e.getTargetException()));
        } catch (Throwable t) {
            fail(format(MSG_UNEXPECTED_ERROR, t.getClass().getName(), t.getMessage()));
        }
    }

    /**
     * Verifies the specified constructor has private scope.
     *
     * @param clazz the class to verify
     * @throws Exception if assertion failure
     */
    public static void assertClassPrivateConstructor(Class<?> clazz) throws Exception {
        int modifiers = clazz.getDeclaredConstructor((Class<?>[]) null).getModifiers();
        assertTrue(format(MSG_CTOR_NOT_PRIVATE, clazz.getName()), (modifiers & Modifier.PRIVATE) != 0);
    }

    /**
     * Verifies the specified class conforms to the expected definition of a
     * static utility class. The class is marked <code>final</code>, it must
     * have a private no-arg constructor, and that if invoked throws an
     * assertion error.
     *
     * @param clazz the class to verify
     * @throws Exception if assertion failure
     * @see #assertClassNonInstantiable(Class)
     * @see #assertClassPrivateConstructor(Class)
     */
    public static void assertClassStaticUtility(Class<?> clazz) throws Exception {
        assertClassPrivateConstructor(clazz);
        assertClassNonInstantiable(clazz);
        assertTrue(format(MSG_CLASS_NOT_FINAL, clazz.getName()), (clazz.getModifiers() & Modifier.FINAL) != 0);
    }

    private static final String MSG_CLASS_INSTANTIATED = "Class %s instantiated but should not";
    private static final String MSG_CLASS_NOT_FINAL = "Class %s is not final";
    private static final String MSG_CTOR_NOT_PRIVATE = "Class %s does not declare a private no-arg constructor";
    private static final String MSG_UNEXPECTED_ERROR = "Unexpected %s error caught: %s";

    /**
     * Suppresss default constructor for noninstantiability.
     *
     * @throws InstantiationNotIntendedError always
     * @see #assertClassStaticUtility(Class)
     */
    private AssertClass() {
        throw new InstantiationNotIntendedError();
    }

}
