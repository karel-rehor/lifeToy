package org.kagaka.influx;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.kagaka.cell.VertexCellImpl;
import org.kagaka.cell.VertexCellFactory;
import org.kagaka.graph.VertexImpl;
import org.kagaka.life.LifeField;
import org.kagaka.life.cell.SimpleLifeVCell;

class LifeFluxWriterTest {

    @Test
    void writeLifeFieldToInfluxTest() {
        System.out.println("DEBUG before connect LifeFluxWriter.isConnected() " + LifeFluxWriter.isConnected());
        try {
            LifeFluxWriter.connect();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unable to connect to db at " + LifeFluxWriter.getConfig().getUrl());            
        }
        System.out.println("DEBUG after connect  LifeFluxWriter.isConnected() " + LifeFluxWriter.isConnected());
        LifeFluxWriter.disconnect();
        System.out.println("DEBUG after disconnect LifeFluxWriter.isConnected() " + LifeFluxWriter.isConnected()); 
        
                
     /*   Vertex<VertexCell> v1 = new Vertex<VertexCell>();
        Vertex<VertexCell> v2 = new Vertex<VertexCell>();
        SimpleLifeVCell c1 = new SimpleLifeVCell(null);
        SimpleLifeVCell c2 = new SimpleLifeVCell(null);
        VertexCellFactory.joinVertexCell(c1, v1);
        VertexCellFactory.joinVertexCell(c2, v2);
        v1.addMutualEdge(v2);
        
       LifeFluxWriter.connect();
 
       LifeFluxWriter.writeSimpleLifeCell(c2); */
        
       try {           
        LifeFluxWriter.connect();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       
       LifeFluxWriter.writeLifeField(new LifeField(5, 4));
       
       LifeFluxWriter.disconnect();
                
    }

}
