package com.hit.lpm.recommend.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hit.lpm.recommend.model.Graph.Vertex;
import com.hit.lpm.recommend.model.Graph.Edge;
/**
 * @author vin
 */
public class TestGraph {
    public static void main(String[] args) {
        Vertex v1= new Vertex("v1");
        Vertex v2= new Vertex("v2");
        Vertex v3= new Vertex("v3");
        Vertex v4= new Vertex("v4");
        Vertex v5= new Vertex("v5");
        Vertex v6= new Vertex("v6");
        Vertex v7= new Vertex("v7");
        Vertex v8= new Vertex("v8");

        List<Vertex> verList = new LinkedList<Vertex>();
        verList.add(v1);
        verList.add(v2);
        verList.add(v3);
        verList.add(v4);
        verList.add(v5);
        verList.add(v6);
        verList.add(v7);
        verList.add(v8);

        Map<Vertex, List<Edge>> vertex_edgeList_map = new HashMap<Vertex, List<Edge>>();

        List<Edge> v1List = new LinkedList<Edge>();
        v1List.add(new Edge(v1,v2,6));
        v1List.add(new Edge(v1,v4,1));
        v1List.add(new Edge(v1,v6,50));

        List<Edge> v2List = new LinkedList<Edge>();
        v2List.add(new Edge(v2,v3,43));
        v2List.add(new Edge(v2,v4,11));
        v2List.add(new Edge(v2,v5,6));

        List<Edge> v3List = new LinkedList<Edge>();
        v3List.add(new Edge(v3,v8,8));

        List<Edge> v4List = new LinkedList<Edge>();
        v4List.add(new Edge(v4,v3,15));
        v4List.add(new Edge(v4,v5,12));

        List<Edge> v5List = new LinkedList<Edge>();
        v5List.add(new Edge(v5,v3,38));
        v5List.add(new Edge(v5,v8,13));
        v5List.add(new Edge(v5,v7,24));

        List<Edge> v6List = new LinkedList<Edge>();
        v6List.add(new Edge(v6,v5,1));
        v6List.add(new Edge(v6,v7,12));

        List<Edge> v7List = new LinkedList<Edge>();
        v7List.add(new Edge(v7,v8,20));

        vertex_edgeList_map.put(v1, v1List);
        vertex_edgeList_map.put(v2, v2List);
        vertex_edgeList_map.put(v3, v3List);
        vertex_edgeList_map.put(v4, v4List);
        vertex_edgeList_map.put(v5, v5List);
        vertex_edgeList_map.put(v6, v6List);
        vertex_edgeList_map.put(v7, v7List);


        Graph g = new Graph(verList, vertex_edgeList_map);
//		g.dijkstraTravasal(1, 5);
        g.dijkstraTravasal(0, 7);



    }
}
