package Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;



//import Centrality.centralll;

import Dijkstra.DijkstraAlgorithm;
import Dijkstra.Edge;
import Dijkstra.Graph;
import Dijkstra.Vertex;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestDijkstraAlgorithm {

	private List<Vertex> nodes;
	private List<Edge> edges;

	int[][] pao;
	int komvospouthelo;
	int komvospoupaei;
	static ArrayList<Vertex> list = new ArrayList<Vertex>();
	int edges1;
	public TestDijkstraAlgorithm(int[][] pao1, int komvos, int komvoto, int a) {
		//Execution2(pao1);
		edges1=a;
		pao = pao1;
		komvospouthelo = komvos;
		komvospoupaei=komvoto;

	}
	
	@Test
	public void testExcute() {
		String[]edge=new String[edges1];
		for (int arithmos=0;arithmos<edges1;arithmos++)
		{
			edge[arithmos]="Edge_"+arithmos;
			
		}
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();

		for (int i = 0; i < pao.length; i++) {
			Vertex location = new Vertex("Node_" + i, "Node_" + i, i);
			nodes.add(location);
		}
		int count = 0;
		for (int i = 0; i < pao.length; i++) {
			for (int column = 0; column < pao[i].length; column++) {
				if (pao[i][column] == 1) {
					addLane(edge[count], i, column, 1);
					count++;
				}
			}

		}
		//System.out.println(nodes + "---" + edges + " ola mazi ");
		// Lets check from location Loc_1 to Loc_10
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		dijkstra.execute(nodes.get(komvospouthelo));
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(komvospoupaei));
		//System.out.println(komvospouthelo+" komvos poy eimai");
		//System.out.println(komvospoupaei+" komvos poy paei");
		//System.out.println(path+"Edo path");
		
		assertNotNull(path);
		assertTrue(path.size() > 0);
		list.clear();
		for (Vertex vertex : path) {
			list.add(vertex);
			//System.out.println(vertex);
		}

	//	System.out.println(list + "Panurgia");

	}

	public static <E> ArrayList<Vertex> sum() {
		return list;
	}

	private void addLane(String laneId, int sourceLocNo, int destLocNo, int duration) {
		Edge lane = new Edge(laneId, nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
		edges.add(lane);
	}
}