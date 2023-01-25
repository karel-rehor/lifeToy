package org.kagaka.influx;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kagaka.cell.VertexCell;
import org.kagaka.cell.VertexCellFactory;
import org.kagaka.graph.Vertex;
import org.kagaka.graph.VertexImpl;
import org.kagaka.life.LifeField;
import org.kagaka.life.cell.SimpleLifeVCell;

import com.influxdb.exceptions.InfluxException;

class LifeFluxWriterTest {
    
    @BeforeAll
    static void setConnection() {
        // N.B. first connect sets up account if not yet ready
        LifeFluxWriter.connect();
        LifeFluxWriter.disconnect();        
    }
    
    @BeforeEach
    void connect() {
        LifeFluxWriter.connect();         
    }
    
    @AfterEach
    void disconnect() {
        LifeFluxWriter.disconnect();        
    }

    @Test
    void writeSimpleCellToInfluxTest() {
                
        Vertex<VertexCell> v1 = new VertexImpl<VertexCell>();
        Vertex<VertexCell> v2 = new VertexImpl<VertexCell>();
        SimpleLifeVCell c1 = new SimpleLifeVCell(null);
        SimpleLifeVCell c2 = new SimpleLifeVCell(null);
        VertexCellFactory.joinVertexCell(c1, v1);
        VertexCellFactory.joinVertexCell(c2, v2);
        v1.addMutualEdge(v2);

        try {
            LifeFluxWriter.writeSimpleLifeCell(c2);
            assertTrue(true, "Wrote simple test data to influx");
        }catch(InfluxException ie) {
            fail("Failed to write simple test data", ie);
        }
       
       LifeFluxWriter.writeLifeField(new LifeField(5, 4));       
                
    }
    
    @Test
    void writeFieldToInfluxTest() {
        
        try {
            LifeFluxWriter.writeLifeField(new LifeField(5, 4));
            assertTrue(true, "Wrote field data to influx");
        }catch(InfluxException ie) {
            fail("Failed to write field test data to influx", ie);            
        }
        
    }

}
