package org.kagaka.influx;

import org.kagaka.GeneralKit;
import org.kagaka.cell.CellImpl;
import org.kagaka.cell.VertexCellImpl;
import org.kagaka.graph.VertexImpl;
import org.kagaka.graph.grid.Coords2D;
import org.kagaka.life.LifeField;
import org.kagaka.life.cell.SimpleLifeVCell;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.OnboardingResponse;
import com.influxdb.client.domain.Ready;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.exceptions.InfluxException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class LifeFluxWriter{
    
    public static InfluxConfig config = new InfluxConfig();
    public static InfluxDBClient client;
    
    public static InfluxDBClient connect() throws InfluxException{
                
        client = InfluxDBClientFactory.create(config.getUrl());
        if(!client.isOnboardingAllowed()) {
            client.close();
            client = InfluxDBClientFactory.create(config.getUrl(), 
                    config.getUsername(), config.getPassword().toCharArray());
//            Organization org = client.getOrganizationsApi().findOrganizations().get(0);
            config.setToken(client.getAuthorizationsApi()
                    .findAuthorizationsByUserName(config.getUsername()).get(0).getToken());
            config.setOrgId(client.getOrganizationsApi().findOrganizations().get(0).getId());
            client.close();
        }else { 
            client.close();
            System.out.println("Setting up onboarding");
            OnboardingResponse response = InfluxDBClientFactory.onBoarding(
                    config.getUrl(), 
                    config.getUsername(), 
                    config.getPassword(), 
                    config.getOrgname(), 
                    config.getBucketname());
            // orgId = response.getOrg().getId();
            config.setToken(response.getAuth().getToken());
            config.setOrgId(response.getOrg().getId());
        }
        
        client = InfluxDBClientFactory.create(
                config.getUrl(), 
                config.getToken().toCharArray(), 
                config.getOrgname(), 
                config.getBucketname());
        
        return client;        
    }
    
    public static boolean isConnected() {
        
//        System.out.println("   DEBUG checking if client is null");
        if(client == null) {
            return false;
        }
        
//        System.out.println("  DEBUG checking ping");
        if(!client.ping()) {
            return false;
        }
        
//        System.out.println("  DEBUG checking ready");
        if(client.ready().getStatus() != Ready.StatusEnum.READY) {
            return false;
        }
                
        return true;
        
    }
    
    public static void writeSimpleLifeCell(SimpleLifeVCell cell) {
        
        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        
        String lp = String.format("lifeCell,id=%s edges=%d,alive=%b", cell.getId(), cell.getVertex().getEdges().size(), cell.isAlive());

        writeApi.writeRecord( WritePrecision.NS, lp);
        
    }
 
    
    public static void writeLifeField(LifeField lf) {
        
        String format = "lifeCell,id=%s,gridId=%s,coords=%s,turn=%02d edges=%d,neighborsAlive=%f,alive=%b";
        
        List<String> lines = new ArrayList<String>();
        
        
        for(SimpleLifeVCell cell : lf.getGrid().getContents()) {
           // System.out.println("DEBUG cell " + cell);
            Coords2D coords = lf.getGrid().getVertexCoords((VertexImpl) cell.getVertex());
            lines.add(String.format(format,  
                    cell.getId(),
                    lf.getGrid().getId(),
                    coords.getX() + ":" + coords.getY(),
                    lf.getUpdateCount(),
                    cell.getVertex().getEdges().size(), 
                    cell.getNeighborAliveStatus(), cell.isAlive() 
                    ));
        }
        
    /*    for(String s : lines) {
            System.out.println("DEBUG lines " + s);
        } */
        
        WriteApiBlocking writeApi = client.getWriteApiBlocking();
        
        writeApi.writeRecords(WritePrecision.NS, lines);
        
    }
    
    public static void disconnect() {
//        System.out.println("DEBUG DISCONNECTING");
        if(isConnected()) {
            client.close();
        }
    }

    public static InfluxConfig getConfig() {
        return config;
    }
    
    

}
