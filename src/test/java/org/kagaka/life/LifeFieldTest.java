package org.kagaka.life;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.kagaka.graph.grid.Coords2D;

class LifeFieldTest {
    
    static final private int height = 5;
    static final private int width = 7;
    
    LifeField lifeField = new LifeField(width, height);

    @Disabled
    @Test
    void initializationTest() {
        System.out.println("lifeGrid size " + lifeField.getGrid().getVertices().size());        
        System.out.println("lifeGrid life count " + lifeField.getLiveCellCount());
        assertEquals(height * width, lifeField.getGrid().getVertices().size());
        assertEquals(0, lifeField.getLiveCellCount());
        assertEquals(0, lifeField.getUpdateCount());
    }
    
 //   @Disabled
    @Test
    void seedTest() {
        try {
            lifeField.seed(new Coords2D[]{ new Coords2D(width/2,height/2) });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(1, lifeField.getLiveCellCount());
        lifeField.killCellAt(width/2, height/2);
        assertEquals(0, lifeField.getLiveCellCount());
    }
    
    @Test 
    void updateTest(){
        try {
            lifeField.seed(new Coords2D[]{ new Coords2D(height/2,width/2) });
            System.out.println("SEEDED\n========");
            lifeField.dumpGridByDims();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(1, lifeField.getLiveCellCount());
    //    lifeField.update(); // priming
        lifeField.dumpGridByDims();
        //lifeGrid.dumpPrevNeighbors();
        lifeField.update(); // updating        
        //lifeGrid.dumpPrevNeighbors();
        assertEquals(1, lifeField.getUpdateCount());
        System.out.println("DEBUG post update live cells " + lifeField.getLiveCellCount());
        lifeField.dumpGridByDims();
        lifeField.update();
        System.out.println("DEBUG post update live cells " + lifeField.getLiveCellCount());
        lifeField.dumpGridByDims();
        lifeField.update();
        System.out.println("DEBUG post update live cells " + lifeField.getLiveCellCount());
        lifeField.dumpGridByDims();
    }

}
