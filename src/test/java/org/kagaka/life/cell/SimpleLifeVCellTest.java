package org.kagaka.life.cell;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.kagaka.cell.VertexCell;
import org.kagaka.cell.VertexCellFactory;
import org.kagaka.graph.Vertex;
import org.kagaka.graph.VertexImpl;

class SimpleLifeVCellTest {

    
    @Test
    void simpleLifeVCellTest() {
        Vertex<VertexCell> vertex = new VertexImpl<VertexCell>();
        SimpleLifeVCell slvc = new SimpleLifeVCell(null);
        VertexCellFactory.joinVertexCell(slvc, vertex);
        assertEquals(slvc.getVertex(), vertex);
        assertEquals(vertex.get(), slvc);
        slvc.doTransform();
        slvc.getTransform().doIt();
        assertFalse(slvc.isAlive());
    }
    
    @Test
    void pairLifeVCellTest() {
        Vertex<VertexCell> v1 = new VertexImpl<VertexCell>();
        Vertex<VertexCell> v2 = new VertexImpl<VertexCell>();
        SimpleLifeVCell c1 = new SimpleLifeVCell(null);
        SimpleLifeVCell c2 = new SimpleLifeVCell(null);
        VertexCellFactory.joinVertexCell(c1, v1);
        VertexCellFactory.joinVertexCell(c2, v2);
        v1.addMutualEdge(v2);
        c1.doTransform();
        c2.doTransform();
        assertEquals(1, c1.getVertex().getEdges().size());
        assertEquals(1, c2.getVertex().getEdges().size());
    }


}
