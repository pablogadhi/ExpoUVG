package com.uvg.expo.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path {
    List<Vertex> nodes;
    List<Edge> edges;
    DijkstraAlgorithm dijkstra;
    public Path(){
        

        this.nodes = new ArrayList<Vertex>();
        this.edges = new ArrayList<Edge>();


        Vertex location = new Vertex("0", "A");  // ID 0
        this.nodes.add(location);
        Vertex location2 = new Vertex("1", "B"); //
        this.nodes.add(location2);
        Vertex location3 = new Vertex("2", "C");
        this.nodes.add(location3);
        Vertex location4 = new Vertex("3", "J");
        this.nodes.add(location4);
        Vertex location5 = new Vertex("4", "H");
        this.nodes.add(location5);
        Vertex location6 = new Vertex("5", "I");
        this.nodes.add(location6);
        Vertex location7 = new Vertex("6", "II");
        this.nodes.add(location7);
        Vertex location8 = new Vertex("7", "F");
        this.nodes.add(location8);
        Vertex location9 = new Vertex("8", "E");
        this.nodes.add(location9);
        Vertex location10 = new Vertex("9", "K");
        this.nodes.add(location10);
        Vertex location11 = new Vertex("10", "G");
        this.nodes.add(location11);



        Edge lane = new Edge("1",this.nodes.get(0), this.nodes.get(6), 1 );
        this.edges.add(lane);
        Edge lane2 = new Edge("2",this.nodes.get(0), this.nodes.get(7), 1 );
        this.edges.add(lane2);
        Edge lane3 = new Edge("3",this.nodes.get(7), this.nodes.get(10), 1 );
        this.edges.add(lane3);
        Edge lane4 = new Edge("4",this.nodes.get(10), this.nodes.get(4), 1 );
        this.edges.add(lane4);
        Edge lane5 = new Edge("5",this.nodes.get(5), this.nodes.get(3), 1 );
        this.edges.add(lane5);
        Edge lane6 = new Edge("6",this.nodes.get(8), this.nodes.get(1), 1 );
        this.edges.add(lane6);
        Edge lane7 = new Edge("7",this.nodes.get(8), this.nodes.get(7), 1 );
        this.edges.add(lane7);
        Edge lane8 = new Edge("8",this.nodes.get(1), this.nodes.get(2), 1 );
        this.edges.add(lane8);
        Edge lane9 = new Edge("8",this.nodes.get(8), this.nodes.get(2), 1 );
        this.edges.add(lane9);
        Edge lane10 = new Edge("8",this.nodes.get(7), this.nodes.get(2), 1 );
        this.edges.add(lane10);
        Edge lane11 = new Edge("8",this.nodes.get(9), this.nodes.get(3), 1 );
        this.edges.add(lane11);
        Edge lane12 = new Edge("8",this.nodes.get(9), this.nodes.get(4), 1 );
        this.edges.add(lane12);
        Edge lane13 = new Edge("8",this.nodes.get(9), this.nodes.get(10), 1 );
        this.edges.add(lane13);
        Edge lane14 = new Edge("8",this.nodes.get(9), this.nodes.get(0), 1 );
        this.edges.add(lane14);


        lane = new Edge("1",this.nodes.get(6), this.nodes.get(0), 1 );
        this.edges.add(lane);
        lane2 = new Edge("2",this.nodes.get(7), this.nodes.get(0), 1 );
        this.edges.add(lane2);
        lane3 = new Edge("3",this.nodes.get(10), this.nodes.get(7), 1 );
        this.edges.add(lane3);
        lane4 = new Edge("4",this.nodes.get(4), this.nodes.get(10), 1 );
        this.edges.add(lane4);
        lane5 = new Edge("5",this.nodes.get(3), this.nodes.get(5), 1 );
        this.edges.add(lane5);
        lane6 = new Edge("6",this.nodes.get(1), this.nodes.get(8), 1 );
        this.edges.add(lane6);
        lane7 = new Edge("7",this.nodes.get(7), this.nodes.get(8), 1 );
        this.edges.add(lane7);
        lane8 = new Edge("8",this.nodes.get(2), this.nodes.get(1), 1 );
        this.edges.add(lane8);
        lane9 = new Edge("8",this.nodes.get(2), this.nodes.get(8), 1 );
        this.edges.add(lane9);
        lane10 = new Edge("8",this.nodes.get(2), this.nodes.get(7), 1 );
        this.edges.add(lane10);
        lane11 = new Edge("8",this.nodes.get(3), this.nodes.get(9), 1 );
        this.edges.add(lane11);
        lane12 = new Edge("8",this.nodes.get(4), this.nodes.get(9), 1 );
        this.edges.add(lane12);
        lane13 = new Edge("8",this.nodes.get(10), this.nodes.get(9), 1 );
        this.edges.add(lane13);
        lane14 = new Edge("8",this.nodes.get(0), this.nodes.get(9), 1 );
        this.edges.add(lane14);

        Graph graph = new Graph(this.nodes, this.edges);
        this.dijkstra = new DijkstraAlgorithm(graph);
    }

    public String findPath(String orign, String dest){
        int orgId = getId(orign);
        int desId = getId(dest);
        if (orgId==-1 || desId==-1){
            return "Error bad Origin or Destination";
        }
        this.dijkstra.execute(this.nodes.get(orgId));
        LinkedList<Vertex> path = this.dijkstra.getPath(this.nodes.get(desId));

        String result = "";
        for (Vertex vertex : path) {
            result = result.concat(vertex.getName());
        }
        return result;
    }

    private int getId(String name){
        for (Vertex vertex : nodes){
            if (vertex.getName().equals(name)){
                return Integer.parseInt(vertex.getId());
            }
        }
        return -1;
    }
}
