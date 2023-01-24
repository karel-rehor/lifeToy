package org.kagaka.influx;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InfluxConfigTest {

    @Test
    void test() {
        // fail("Not yet implemented");
        InfluxConfig conf = new InfluxConfig();
        System.out.println("DEBUG url " + conf.getUrl());
        System.out.println("DEBUG username " + conf.getUsername());
        System.out.println("DEBUG password " + conf.getPassword());
        System.out.println("DEBUG orgname " + conf.getOrgname());
        System.out.println("DEBUG bucketname " + conf.getBucketname());
        
    }

}
