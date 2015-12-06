/*
 * Copyright (c) 2015 Sacred Scripture Foundation.
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
package org.sacredscripturefoundation.commons.entity.util;

import java.util.UUID;

/**
 * This class generates random 128-bit identifiers encoded to Base-64 strings.
 * No padding is provided. The strings are always 22-characters in length.
 * Example: "Pm12GAxSTUKE1QHalhQzmw"
 * <p>
 * The no-arg constructor uses standard Base-64 encoding. Standard encoding is
 * not safe for the web because the "{@code /}" and "{@code +}" characters
 * require URI escaping. When {@link #Base64UuidGenerator(boolean) web safety is
 * enabled}, then "{@code _}" and "{@code -}" are substituted for "{@code /}"
 * and "{@code +}", respectively.
 * <p>
 * Note: This class requires a VM with {@code sun.misc} package access. This
 * requirement can go away with JDK 8 since its java.util package provides
 * Base-64 tools.
 *
 * @author Paul Benedict
 * @since 1.0
 * @see UUID
 */
@SuppressWarnings("restriction")
public class Base64UuidGenerator implements IdGenerator {

    private final sun.misc.BASE64Encoder encoder;
    private final boolean webSafe;

    /**
     * Constructs a Base-64 generator that is not web-safe. See class
     * documentation for details.
     *
     * @see #Base64UuidGenerator(boolean)
     */
    public Base64UuidGenerator() {
        this(false);
    }

    /**
     * Constructs a Base-64 generator with support for web-safe encoding, if
     * desired. See class documentation for details.
     *
     * @param webSafe {@code true} to enable web-safe encoding; otherwise
     * {@code false}
     * @see #Base64UuidGenerator()
     */
    public Base64UuidGenerator(boolean webSafe) {
        encoder = new sun.misc.BASE64Encoder();
        this.webSafe = webSafe;
    }

    // http://stackoverflow.com/questions/772802/storing-uuid-as-base64-string
    private byte[] asByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> (8 * (7 - i)));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> (8 * (7 - i)));
        }

        return buffer;
    }

    /**
     * @see #generate0(UUID)
     */
    @Override
    public Object generate() {
        return generate0(UUID.randomUUID());
    }

    /**
     * Package-private generation of the specified UUID.
     *
     * @param uuid the UUID to use
     * @return the identifier
     * @throws NullPointerException if {@code uuid} is {@code null}
     * @see #generate()
     */
    String generate0(UUID uuid) {
        String id = encoder.encode(asByteArray(uuid)).split("=")[0];
        if (webSafe) {
            id = id.replace('/', '_').replace('+', '-');
        }
        return id;
    }

}
