package org.kagaka.influx;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InfluxConfigTest {

    @Test
    void test() {
        InfluxConfig conf = new InfluxConfig();
        assertEquals("http://localhost:8086", conf.getUrl());
        assertEquals("seymour", conf.getUsername());
        assertEquals("changeit!1", conf.getPassword());
        assertEquals("world", conf.getOrgname());
        assertEquals("life", conf.getBucketname());        
    }

}
