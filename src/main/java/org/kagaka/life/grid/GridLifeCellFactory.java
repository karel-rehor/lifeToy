package org.kagaka.life.grid;

import org.kagaka.GeneralKit;
import org.kagaka.cell.VertexCell;
import org.kagaka.cell.VertexCellFactory;
import org.kagaka.graph.Graph;
import org.kagaka.graph.GraphFactory;
import org.kagaka.graph.GraphProperties;
import org.kagaka.graph.Vertex;
import org.kagaka.graph.VertexImpl;
import org.kagaka.graph.grid.Grid;
import org.kagaka.life.cell.SimpleLifeVCell;

public class GridLifeCellFactory<T extends SimpleLifeVCell> implements GraphFactory<T> {

    @Override
    public Graph<T> createGraph(GraphProperties props) {
        
        props.hasKey("height");    
        props.hasKey("width");
        
        int height = Integer.parseInt(props.get("height"));
        int width = Integer.parseInt(props.get("width"));

        
        Grid<SimpleLifeVCell> grid = new Grid<SimpleLifeVCell>(width, height);

        int index = 0;
        
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                Vertex<SimpleLifeVCell> vertex = new VertexImpl<SimpleLifeVCell>();
             // TODO after debugging revert to random IDs
//                SimpleLifeVCell slvc = new SimpleLifeVCell(String.format("[%d,%d](%d)", i, j, index), null);
                SimpleLifeVCell slvc = new SimpleLifeVCell(GeneralKit.genHexId(GeneralKit.ID_LENGTH), null);
                VertexCellFactory.joinVertexCell((VertexCell)slvc, (Vertex)vertex);
                grid.addVertex((Vertex)vertex);
                
                index++;
            }
        }
        
        grid.joinVertices();

        return (Graph<T>) grid;

    }

}
