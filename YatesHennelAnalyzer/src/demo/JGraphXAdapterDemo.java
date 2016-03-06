/* This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package demo;

import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.*;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

import java.awt.*;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;


/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs.
 * Applet based on JGraphAdapterDemo.
 *
 * @since July 9, 2013
 */
public class JGraphXAdapterDemo
extends JPanel
{


	private static final long serialVersionUID = 2202072534703043194L;
	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	private JGraphXAdapter<String, DefaultEdge> jgxAdapter;

	/**
	 * An alternative starting point for this demo, to also allow running this
	 * applet as an application.
	 *
	 * @param args ignored.
	 */
	/*public static void main(String [] args)
	{
		JGraphAdapterDemo applet = new JGraphAdapterDemo();
		applet.init();

		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}*/

	/**
	 * {@inheritDoc}
	 * @param mapping 
	 */
	public void init(int[][] gra, HashMap<Integer, Integer> mapping)
	{
		
		JFrame frame = new JFrame("JGraphT Adapter to JGraph Demo");

     // create a JGraphT graph
     		ListenableGraph<String, DefaultEdge> g =
     			new ListenableDirectedGraph<String, DefaultEdge>(
     					DefaultEdge.class);

     		// create a visualization using JGraph, via an adapter
     		jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(g);
				

		int[][] ret = gra;
/*		for (int i=0;i<7;i++){
			for (int j=0;j<7;j++){
				ret[i][j]=0;
			}
		}
		ret[0][1]=1;
		ret[0][2]=1;
		ret[1][3]=1;
		ret[1][4]=1;
		ret[2][5]=1;
		ret[2][6]=1;
*/
		
		jgxAdapter.getModel().beginUpdate();
		try {
			ArrayList<String> nodes = new ArrayList<String>();
			ArrayList<Object> obNodes = new ArrayList<Object>();
			//add nodes
			for (int i=0;i<ret.length;i++){
				nodes.add(String.valueOf((mapping.get(i))));
	//			g.addVertex(nodes.get(i));
				obNodes.add(jgxAdapter.insertVertex(getParent(), null, nodes.get(i), 0, 0, 80, 35));
			}
			
			//add edges
			for (int i=0;i<ret.length;i++){
				for (int j=0;j<ret.length;j++){
					int edges = ret[i][j];
					while (edges>0){
			//			g.addEdge(nodes.get(i),nodes.get(j));
						jgxAdapter.insertEdge(getParent(), null, nodes.get(i)+"-"+nodes.get(j), obNodes.get(i),  obNodes.get(j));
						edges--;
					}
				}
			}

		}
		
		finally {
			jgxAdapter.getModel().endUpdate();
		}
		
		mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
		
		//mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
		layout.execute(jgxAdapter.getDefaultParent());
		
		frame.add(new mxGraphComponent(jgxAdapter));
		
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
		
	}

}

//End JGraphXAdapterDemo.java
