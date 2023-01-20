package org.kagaka.life.cell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.kagaka.cell.VertexCell;
import org.kagaka.cell.VertexCellImpl;
import org.kagaka.graph.Vertex;
import org.kagaka.graph.VertexImpl;

public class SimpleLifeVCell extends VertexCellImpl {
    
    // TODO make THRESHOLDs configurable
    
    private static double DEATH_THRESHOLD = 0.50;
    private static double BIRTH_THRESHOLD = 0.25;
    
    private boolean alive;
    private List<Boolean> neighborsPrevAlive;
    private double neighborAliveStatus;

    public SimpleLifeVCell(VertexImpl<VertexCell> vertex) {
        super(vertex);
        // TODO Auto-generated constructor stub
        this.alive = false;
        this.transform = () -> {
//            System.out.println("Transform Override");
            this.doTransform();
            };
        this.neighborsPrevAlive = new ArrayList<Boolean>();
        this.neighborAliveStatus = 0.0;
    }
    
    public SimpleLifeVCell(String id, VertexImpl<VertexCell> vertex) {
        super(id, vertex);
        // TODO Auto-generated constructor stub
        this.alive = false;
        this.transform = () -> {
//            System.out.println("Transform Override");
            this.doTransform();
            };
            this.neighborsPrevAlive = new ArrayList<Boolean>();    
            this.neighborAliveStatus = 0.0;
    }
    
    
    
    public static double getDEATH_THRESHOLD() {
        return DEATH_THRESHOLD;
    }

    public static void setDEATH_THRESHOLD(double dEATH_THRESHOLD) {
        DEATH_THRESHOLD = dEATH_THRESHOLD;
    }

    public static double getBIRTH_THRESHOLD() {
        return BIRTH_THRESHOLD;
    }

    public static void setBIRTH_THRESHOLD(double bIRTH_THRESHOLD) {
        BIRTH_THRESHOLD = bIRTH_THRESHOLD;
    }

    public double getNeighborAliveStatus() {
        return neighborAliveStatus;
    }

    // N.B. optimized for Grid TODO make suitable for cube or other shape as well
    public double calcNeighborAliveStatus() {
//        System.out.println("DEBUG CalcNeighborAliveStatus");
//        dumpNeighborsPrev();
        Random rand = new Random();
        int count = 0;
/*        for(Vertex<? extends VertexCell> vc : vertex.getEdges()) {
            if(((SimpleLifeVCell)vc.get()).isAlive()) {
                count++;
            };
        } */
        for(Boolean alive : neighborsPrevAlive) {
            if(alive) {
                count++;
            }
        }
        
        // For edges and corners add random states for neighbors outside grid 
        if(isAlive()) {
            for(int i = this.neighborsPrevAlive.size(); i < 4; i++) {
                //System.out.println("DEBUG randomizing cell " + i);
                count += rand.nextInt(2);
            }
        }
        
//        return((double)count/(double)(getVertex().getEdges().size()));
//        System.out.print("DEBUG count " + count + " NeighborsPrevAlive.size() " + neighborsPrevAlive.size());
        neighborAliveStatus = (double)count/4.0;
        return neighborAliveStatus;
    }

    
//    @Override
    public void doTransform() {
        // TODO Auto-generated method stub
        /*
         * Need to compare with snapshot of previous state, otherwise alive cells will metastasize during traverse;
         * */
        if(neighborsPrevAlive.size() == 0) { // first pass so just init neighbors prev
//            System.out.println("   DEBUG init neighborsPrevAlive");
            rememberPrevious();
            return;
        }

//        System.out.println("DEBUG NeighborsPrev\n===========");
//        dumpNeighborsPrev();
//        System.out.println("DEBUG ===========");
        
        
       /* if(vertex.getEdges().size() == 0) {
            System.out.println("Island");
        }
        
        if(vertex.getEdges().size() == 1) {
            System.out.println("Deadend");
        }
        
        if(vertex.getEdges().size() == 2) {
            System.out.println("Corner");
            
        }
        
        if(vertex.getEdges().size() == 3) {
            System.out.println("Side");
        } */
        
        /*
        for(Vertex<? extends VertexCell> vc : vertex.getEdges()) {
            System.out.println(vc.get());
        }*/
        
     // System.out.println("[" + getId() + "]:calcNeighborAliveStatus(): " + calcNeighborAliveStatus());
        double neighborStatus = calcNeighborAliveStatus();
        
        // cannot survive with 3 or 4 living neighbors
        // i.e. resources are depleted
        
        if(isAlive() && neighborStatus > DEATH_THRESHOLD) {
     //       System.out.println("    Killing");
            setAlive(false);
        };

        
        if((!isAlive()) && neighborStatus >= BIRTH_THRESHOLD && neighborStatus <= DEATH_THRESHOLD){ // if 1 or 2 neighbor is alive, spread life here
     //       System.out.println("   Setting Alive");
            setAlive(true);
        }
        
                
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public void rememberPrevious() {
        neighborsPrevAlive.removeAll(neighborsPrevAlive);
        for(Vertex<? extends VertexCell> vc : vertex.getEdges()) {
            neighborsPrevAlive.add(((SimpleLifeVCell)vc.get()).isAlive());
        }        
    }
    
    
    public String dumpSimpleLifeVCell() {
        StringBuffer sb = new StringBuffer();
        sb.append(getId() + ": alive: " + isAlive());
        sb.append("   edge count: " + getVertex().getEdges().size());
        ((SimpleLifeVCell)getVertex().getEdge(0).get()).setAlive(true);
        for(Vertex<? extends VertexCell> slvc : getVertex().getEdges()) {
            sb.append("   slvc: " + ((SimpleLifeVCell) slvc.get()).isAlive());
        }
        sb.append("\n");
        for(Boolean alive : neighborsPrevAlive) {
            sb.append(alive + " ");
        }

        return sb.toString();
    }
    
    public void dumpNeighborsPrev() {
        StringBuffer sb = new StringBuffer();
        sb.append("DEBUG Dump neighporsPrev[" + getId() + "]\n");
        for(Boolean alive : neighborsPrevAlive) {
            sb.append(alive + " ");
        }
        
        System.out.println(sb);
    }
    
    @Override
    public String toString() {
        return getId() + " " + isAlive() + " edges " + getVertex().getEdges().size();
    }    
  

}
