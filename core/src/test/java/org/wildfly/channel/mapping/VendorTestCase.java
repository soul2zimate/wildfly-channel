/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2020, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.wildfly.channel.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.wildfly.channel.Vendor.Support.COMMUNITY;
import static org.wildfly.channel.Vendor.Support.SUPPORTED;
import static org.wildfly.channel.Vendor.Support.TECH_PREVIEW;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wildfly.channel.Vendor;

public class VendorTestCase {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(new YAMLFactory());

    static Vendor from(String str) throws IOException {
        return OBJECT_MAPPER.readValue(str, Vendor.class);
    }

    @Test
    public void testValidVendor() throws IOException {
        Vendor vendor = from("name: My Vendor\n" +
                "support: community");
        assertEquals("My Vendor", vendor.getName());
        assertEquals(COMMUNITY, vendor.getSupport());

        System.out.println("vendor = " + vendor);
    }

    @Test
    public void testNameIsMandatory() {
        Assertions.assertThrows(Exception.class, () -> {
            from("support: community");
        });
    }

    @Test
    public void testSupportIsMandatory() {
        Assertions.assertThrows(Exception.class, () -> {
            from("name: My Vendor");
        });
    }

    @Test
    public void testValidSupportValues() throws IOException {
        Vendor vendor = from("name: My Vendor\n" +
                "support: community");
        assertEquals(COMMUNITY, vendor.getSupport());

         vendor = from("name: My Vendor\n" +
                "support: tech-preview");
        assertEquals(TECH_PREVIEW, vendor.getSupport());

         vendor = from("name: My Vendor\n" +
                "support: supported");
        assertEquals(SUPPORTED, vendor.getSupport());
    }

    @Test
    public void testInvalidSupport() {
        Assertions.assertThrows(Exception.class, () -> {
            Vendor vendor = from("name: My Vendor\n" +
                    "support: experimental-is-not-a-valid-value");
        });
    }

}
