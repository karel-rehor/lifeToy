package org.kagaka.life;

import java.util.Set;

import org.kagaka.graph.GraphFactoryImpl;
import org.kagaka.graph.GraphProperties;
import org.kagaka.graph.GraphPropertiesImpl;
import org.kagaka.graph.Vertex;
import org.kagaka.graph.VertexImpl;
import org.kagaka.graph.grid.Coords2D;
import org.kagaka.graph.grid.Grid;
import org.kagaka.life.cell.SimpleLifeVCell;
import org.kagaka.life.grid.GridLifeCellFactory;

public class LifeField {
    
    private final int width;
    private final int height;
    private final GraphProperties gprops;
    
    private int updateCount;
    
    private final Grid<? extends SimpleLifeVCell> grid; // = GraphFactoryVertexCell.createSimpleLifeGrid(width, height);

    public LifeField(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        gprops = new GraphPropertiesImpl();
        gprops.set("height", Integer.toString(this.height));
        gprops.set("width", Integer.toString(this.width));
        GridLifeCellFactory<? extends SimpleLifeVCell> factory = new GridLifeCellFactory<>();
        grid = (Grid<? extends SimpleLifeVCell>) factory.createGraph(gprops);
//        grid = GraphFactoryImpl.createSimpleLifeGrid(width, height);
        updateCount = 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public Grid<? extends SimpleLifeVCell> getGrid() {
        return grid;
    }
    
    public int getLiveCellCount() {
        int count = 0;
        for(Vertex<? extends SimpleLifeVCell> slvc : grid.getVertices()) {
      //      System.out.println("DEBUG slvc " + slvc.get().getId() + ": " + slvc.get().isAlive());
            if(slvc.get().isAlive()) {
                count++;
            }
        }
        return count;
    }
    
    public void zeroAll() {
        for(Vertex<? extends SimpleLifeVCell> slvc : grid.getVertices()) {
            slvc.get().setAlive(false);
        }
    }
    
    public void seed(Coords2D[] seedCells)  throws Exception {
                
        for(int i = 0; i < seedCells.length; i++) {
            if(seedCells[i].getX() > width - 1 || seedCells[i].getY() > height - 1) {
                throw new IndexOutOfBoundsException("Attempt to seed out of bounds");
            }
            
            grid.getAt(seedCells[i]).setAlive(true);
            
        }
        
        // prime remember previous
       rememberPrevious();
        
    }
    
    public void reset(Coords2D[] seedCells) throws Exception {
        zeroAll();
        seed(seedCells);
    }
    
    public void killCellAt(int x, int y) {
        grid.getAt(x, y).setAlive(false);
    }
    
    public void update() {
//        Set<Vertex<? extends SimpleLifeVCell>> allVertices;
        Set<?> allVertices;
        if(updateCount % 2 == 0) {
            allVertices = grid.depthFirstTraversal((VertexImpl)grid.getVertexAt(0,0));
        }else {
            allVertices = grid.breadthFirstTraversal((VertexImpl)grid.getVertexAt(0,0));
        }
        
        for(VertexImpl<? extends SimpleLifeVCell> vslvc : (Set<VertexImpl<? extends SimpleLifeVCell>>)allVertices) {
            vslvc.get().getTransform().doIt();
        }
        
        rememberPrevious();
        updateCount++;
    }
    
    public void rememberPrevious() {
        for(Vertex<? extends SimpleLifeVCell> vsc : grid.getVertices()) {
            vsc.get().rememberPrevious();
        }
    }
    
    // TODO - remove after debugging complete
    public void dumpGridByDims() {
        
     //   System.out.println("DEBUG dumpGridByDIms");
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("width " + width + " height " + height + " total cells " + width * height + "\n");
        
        int index = 0;
      /*  
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                String state = grid.getAt(i,j).isAlive() == true ? "*" : "_";
                sb.append("|" + i + "," + j + "(" + grid.getAt(i,j).getNeighborAliveStatus() + "}|");
                index++;            }
            sb.append("\n");
        } */
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                String state = grid.getAt(i,j).isAlive() == true ? "*" : "_";
                sb.append("|" + i + "," + j + "{" + state + "}|");
                index++;            }
            sb.append("\n");
        }
        
        
        System.out.println(sb);
        
    }
    
    public void dumpGrid() {

        StringBuffer sb = new StringBuffer();
        
        sb.append("width " + width + " height " + height + " total cells " + width * height + "\n");

        int index = 0;

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                String state = grid.getAt(i,j).isAlive() == true ? "*" : "_";
                sb.append("|" + state + "|");
                index++;            
           }
            sb.append("\n");
        }

        System.out.println(sb);
        
    }
    
    public void dumpPrevNeighbors() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                grid.getAt(i,j).dumpNeighborsPrev();
        }        
    }}

}
