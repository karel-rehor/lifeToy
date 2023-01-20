package org.kagaka.life;

import org.apache.commons.cli.*;
import org.kagaka.graph.grid.Coords2D;
import org.kagaka.influx.LifeFluxWriter;
import org.kagaka.life.cell.SimpleLifeVCell;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.exceptions.InfluxException;

// TODO make threshold rules configurable

public class LifeRunner {
    
    static final int DEFAULT_WIDTH = 7;
    static final int DEFAULT_HEIGHT = 5;
    static final int DEFAULT_MAX_TURNS = 7;
    static final long DEFAULT_PAUSE = 3000;
    
    private int fieldWidth = DEFAULT_WIDTH;
    private int fieldHeight = DEFAULT_HEIGHT;
    private int maxTurns = DEFAULT_MAX_TURNS;
    private long pauseBetweenTurns = DEFAULT_PAUSE;
    //InfluxDBClient client;
    private boolean writeToInflux;
    
    LifeField lifeField;

    public LifeRunner() {
        super();
        this.fieldWidth = DEFAULT_WIDTH;
        this.fieldHeight = DEFAULT_HEIGHT;
        this.maxTurns = DEFAULT_MAX_TURNS;
        this.pauseBetweenTurns = DEFAULT_PAUSE;
        lifeField = new LifeField(this.fieldWidth, this.fieldHeight);
        this.writeToInflux = false;
    }

    public LifeRunner(int fieldWidth, int fieldHeight, int maxTurns, long pauseBetweenTurns) {
        super();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.maxTurns = maxTurns;
        this.pauseBetweenTurns = pauseBetweenTurns;
        lifeField = new LifeField(this.fieldWidth, this.fieldHeight);
        this.writeToInflux = false;
    }
    
    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public long getPauseBetweenTurns() {
        return pauseBetweenTurns;
    }

    public LifeField getLifeField() {
        return lifeField;
    }

    public static void main(String[] args) throws Exception{
        
//        LifeRunner runner = new LifeRunner();
        Options options = new Options();
        options.addOption("w","width", true, "width of life model field ");
        options.addOption("h","height", true, "height of life model field ");
        options.addOption("t","turns", true, "turns to play (-1 continue indefinately)");
        options.addOption("p","pause", true, "pause (in milliseconds) between turns");
        options.addOption("c","coords", false, "show coords in grid");
        options.addOption("b","birth", true, "birth threshold, number of neighboring cells alive to make current cell alive 0.01 - 0.99 (default 0.25)");
        options.addOption("d","death", true, "death threshold, number of neighboring cells alive to kill current cell 0.01 - 0.99 (default 0.50)");
        options.addOption("i","influx", false, "write data to influx");

        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null; 
        
        CommandLineParser parser = new DefaultParser();
        try {
            cmd = parser.parse(options,args);        
        }catch(UnrecognizedOptionException uoe) {
            System.out.println(uoe.getMessage());
            formatter.printHelp("LifeRunner", options);
            
            System.exit(1);            
        }
        
        if(cmd.hasOption("birth")) {
            double b = Double.parseDouble(cmd.getOptionValue("birth"));
            
            if(b < 0.01 || b > 0.99) {
                System.out.println("birth value needs to be between 0.01 and 0.99. Got " + b + " exiting");
                System.exit(1);
            }
            
            if(b >= SimpleLifeVCell.getDEATH_THRESHOLD()) {
                System.out.println("birth value " + b + " is above or equal to death value " + SimpleLifeVCell.getDEATH_THRESHOLD() + ". exiting.");
                System.exit(1);
            }
            
            SimpleLifeVCell.setBIRTH_THRESHOLD(b);

        }
        
        if(cmd.hasOption("death")) {
            double d = Double.parseDouble(cmd.getOptionValue("death"));
            
            if(d < 0.01 || d > 0.99) {
                System.out.println("death value needs to be between 0.01 and 0.99. Got " + d + " exiting");
                System.exit(1);
            }
            
            if(d <= SimpleLifeVCell.getBIRTH_THRESHOLD()) {
                System.out.println("death value " + d + " is below or equal to birth value " + SimpleLifeVCell.getBIRTH_THRESHOLD() + ". exiting.");
                System.exit(1);
            }
            
            SimpleLifeVCell.setDEATH_THRESHOLD(d);

        }
        
        
        
        LifeRunner runner = new LifeRunner(
                cmd.hasOption("width") ? Integer.parseInt(cmd.getOptionValue("width")) : DEFAULT_WIDTH,
                cmd.hasOption("height") ? Integer.parseInt(cmd.getOptionValue("height")) : DEFAULT_HEIGHT,
                cmd.hasOption("turns") ? Integer.parseInt(cmd.getOptionValue("turns")) : DEFAULT_MAX_TURNS,
                cmd.hasOption("pause") ? Long.parseLong(cmd.getOptionValue("pause")) : DEFAULT_PAUSE                        
               );
        
        if(cmd.hasOption("influx")) {
            try {
                LifeFluxWriter.connect();
                runner.writeToInflux = true;
            }catch(InfluxException ie) {
                System.err.println("WARNING: failed to connect to influx server at " + LifeFluxWriter.getConfig().getUrl());
                System.err.println("   " + ie.getMessage());
                System.err.println("    This run will not be writing data to influxdb.");
            }
        }
        
        int turnCount = 1;
        
        runner.getLifeField().seed(new Coords2D[] {new Coords2D(runner.getFieldHeight()/2, runner.getFieldWidth()/2)});
        
        while(true) {
            
            if(cmd.hasOption("coords")) {
                runner.getLifeField().dumpGridByDims();
            }else {
                runner.getLifeField().dumpGrid();
            }
            
            System.out.println(String.format("turn: %d | live cells: %d | pct alive %3.2f", 
                    turnCount, 
                    runner.getLifeField().getLiveCellCount(),
                    (runner.getLifeField().getLiveCellCount()/Double.valueOf((runner.getFieldHeight() * runner.getFieldWidth())) * 100.0)
                    ));
            
            if(runner.writeToInflux) {
                LifeFluxWriter.writeLifeField(runner.getLifeField());
            }
            
            runner.getLifeField().update();
            if(turnCount == runner.getMaxTurns()) {
                break;
            }
            turnCount++;
            Thread.sleep(runner.pauseBetweenTurns);
        }
        
        if(runner.writeToInflux) {
            LifeFluxWriter.disconnect();
        }
        
        System.out.println("Bye!");
                
    }

}
