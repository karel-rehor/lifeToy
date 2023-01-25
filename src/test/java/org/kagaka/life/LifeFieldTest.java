package org.kagaka.life;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.kagaka.graph.grid.Coords2D;

class LifeFieldTest {
    
    static final private int height = 5;
    static final private int width = 7;
    
    LifeField lifeField = new LifeField(width, height);

    @Test
    void initializationTest() {
        assertEquals(height * width, lifeField.getGrid().getVertices().size());
        assertEquals(0, lifeField.getLiveCellCount());
        assertEquals(0, lifeField.getUpdateCount());
    }
    
    @Test
    void seedTest() {
        lifeField.seed(new Coords2D[]{ new Coords2D(width/2,height/2) });
        assertEquals(1, lifeField.getLiveCellCount());

        lifeField.killCellAt(width/2, height/2);
        assertEquals(0, lifeField.getLiveCellCount());
    }
    
    @Test 
    void updateTest(){
        lifeField.seed(new Coords2D[]{ new Coords2D(height/2,width/2) });

        assertEquals(1, lifeField.getLiveCellCount());

        lifeField.update(); // updating        
        assertEquals(1, lifeField.getUpdateCount());
        assertEquals(5, lifeField.getLiveCellCount());

        lifeField.update();
        assertEquals(2, lifeField.getUpdateCount());
        assertEquals(12, lifeField.getLiveCellCount());

        lifeField.update();
        assertEquals(3, lifeField.getUpdateCount());
        assertEquals(18, lifeField.getLiveCellCount());

    }

}
