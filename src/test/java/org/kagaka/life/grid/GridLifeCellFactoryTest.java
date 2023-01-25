package org.kagaka.life.grid;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GridLifeCellFactoryTest {

    @Disabled("This test is just a temporary holder")
    @Test
    void test() {
        fail("Not yet implemented");
    }

    
    /*  TODO update and activate
    @Test
    void testSimpLifeVCellGrid() {
//        fail("Not yet implemented");
        int height = 3;
        int width = 5;
        
        Grid<SimpleLifeVCell> grid = GraphFactoryImpl.createSimpleLifeGrid(width, height);
        
        int hIndex = 0;
        int wIndex = 0;
        for(VertexImpl<? extends SimpleLifeVCell> vc : grid.getVertices()) {
//            System.out.println("DEBUG vc [" + hIndex + "," + wIndex + "]"  + vc);
//            System.out.println("DEBUG vc "  + vc);
            if((hIndex == 0 || hIndex == height - 1) && (wIndex == 0 || wIndex == width -1 )) {
                    //Corner
//                System.out.println("Corner " + vc.get().getId() + " " + vc.getEdges().size());
                assertEquals(2,vc.getEdges().size());
            }else if((hIndex == 0 || hIndex == height - 1) && (wIndex != 0 || wIndex != width -1 )) {
                    //Horizontal Side
//                System.out.println("H-Side " + vc.get().getId() + " " + vc.getEdges().size());                
                assertEquals(3,vc.getEdges().size());
            }else if((hIndex != 0 || hIndex != height - 1) && (wIndex == 0 || wIndex == width -1 )) {
                    // Vertical Side
//                System.out.println("V-Side " + vc.get().getId() + " " + vc.getEdges().size());
                assertEquals(3,vc.getEdges().size());
            }else{
                    //  Interior Element
//                System.out.println("Interior " + vc.get().getId() + " " + vc.getEdges().size());
                assertEquals(4,vc.getEdges().size());
            }
            
            wIndex++;
            if(wIndex == width) {
                wIndex = 0;
                hIndex++;
            }
        }

        / *
        System.out.println("==========\n\n");
        grid.dumpVertices();
        System.out.println("==========\n\n");
        grid.dumpGridByDims();
        * /
        
        / *
        for(Vertex<? extends SimpleLifeVCell> slvc : grid.getVertices()) {
            Coords2D coords = grid.getVertexCoords(slvc);
            System.out.println("[" + coords.getX() + "," + coords.getY() + "]: " + slvc.get().isAlive());
        }* /
        
        
       // System.out.println("DEBUG getVertexAt(1,2) " + grid.getVertexAt(1, 2).get());
       // System.out.println("DEBUG getVertexAt(0,1) " + grid.getVertexAt(0, 1).get());
       // System.out.println("DEBUG getVertexAt(2,3) " + grid.getVertexAt(2,3).get());
        
        ((SimpleLifeVCell)grid.getVertexAt(1,2).getEdge(0).get()).setAlive(true);

        / *
        for(Vertex<? extends SimpleLifeVCell> slvc : grid.getVertices()) {
            Coords2D coords = grid.getVertexCoords(slvc);
            System.out.println("[" + coords.getX() + "," + coords.getY() + "]: " + slvc.get().isAlive());
        }* /
        
     // Alive status calculated based on snapshot of previous state
        ((SimpleLifeVCell)grid.getVertexAt(1,2).get()).rememberPrevious();  
        assertEquals(0.25, ((SimpleLifeVCell)grid.getVertexAt(1, 2).get()).calcNeighborAliveStatus());
        grid.getVertexAt(1, 2).get().getTransform().doIt();
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                assertEquals(new Coords2D(i,j), grid.getVertexCoords(grid.getVertexAt(i, j)));
            }            
        }
        assertNull(grid.getVertexAt(0, grid.getWidth()+1));
        assertNull(grid.getVertexAt(grid.getHeight()+1, 0));
        / *
        System.out.println("DEBUG getCoords of Vertex at (2,0) : " + grid.getVertexCoords(grid.getVertexAt(2, 0)));
        System.out.println("DEBUG getCoords of Vertex at (0,1) : " + grid.getVertexCoords(grid.getVertexAt(0, 1)));
        System.out.println("DEBUG getCoords of Vertex at (1,3) : " + grid.getVertexCoords(grid.getVertexAt(1, 3)));
        System.out.println("DEBUG check equals Vertex(0,1) to Vertex(2,2) " + grid.getVertexAt(0, 1).equals(grid.getVertexAt(2, 2)));
        * /
        System.out.println("DEBUG simple life cell: \n" + grid.getVertexAt(1, 2).get().dumpSimpleLifeVCell());
    }    */    

}
