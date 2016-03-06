package Procedure;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import Dijkstra.TestDijkstraAlgorithm;
import Dijkstra.Vertex;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;

import demo.JGraphAdapterDemo;
import demo.JGraphXAdapterDemo;

import java.util.Scanner;
import java.util.Set;

public class PreClass extends JApplet {

	private static final long serialVersionUID = 2202072534703043194L;
	private static final Dimension DEFAULT_SIZE = new Dimension(930, 520);
	static HashMap<Integer, ArrayList<Integer>> totalCombinations = new HashMap<Integer, ArrayList<Integer>>();

	static int number = 0;

	private JGraphXAdapter<String, DefaultEdge> jgxAdapter;

	public static void main(String args[]) throws FileNotFoundException,
			IOException {

		System.out
				.println("Give full absolute path to static analysis output file:");
		Scanner scan = new Scanner(System.in);
		// String path = scan.nextLine();

		String fname = "NEWSUBST.CMP";
		Integer epilogiDDGraph = 0;
		int[][] CFG = CMPToCFGMatrix.convertCMPToMatrix(fname, true);
		Object[] retVal = getDDGraph(CFG);
		HashMap<Integer, Integer> mapping = (HashMap<Integer, Integer>) retVal[1];
		int[][] DDGraph = (int[][]) retVal[0];
		Object[] returngetTimesEtc = getTimesOfAlternatives(DDGraph);
		int timesOfAlternatives = (int) returngetTimesEtc[0];
		ArrayList<Pair> allpairs = (ArrayList<Pair>) returngetTimesEtc[1];
		System.out.println("\nAlternative DDGraphs/F-trees = "
				+ timesOfAlternatives);

		HashMap<String, String[][]> alternatives = new HashMap<String, String[][]>();
		for (int t = 0; t < timesOfAlternatives; t++) {
			alternatives.put("g" + t, initializeStringDDGraph(DDGraph.length));

		}

		HashMap<Integer, String> alphabet = getAlphabet();

		// find pairs
		int helper = 0;

		HashMap<Integer, Pair> sindiasmoi = new HashMap<Integer, Pair>();

		for (Pair p : allpairs) {

			sindiasmoi.put(helper, p);

			helper++;
		}
		System.out.println(sindiasmoi);
		ArrayList<Integer> zeugi = new ArrayList<Integer>();
		combinations(0, zeugi, sindiasmoi, DDGraph, helper);
		System.out.println("Total Combinations" + totalCombinations);
		Set<String> set = alternatives.keySet();
		HashMap<Integer, String[][]> matrices = new HashMap<Integer, String[][]>();
		int arithmospair = 0;
		for (String g : set) {

			ArrayList<Integer> allages = new ArrayList<Integer>(
					totalCombinations.get(arithmospair));
			for (int i = 0; i < allages.size(); i++) {// panta o arithmos ton
				// pair tha nai isos me
				// ton arithmo mesa
				int grammi = sindiasmoi.get(i).x;
				int stili = sindiasmoi.get(i).y;
				int value = allages.get(i);
				ChangeofAlternatives(alternatives.get(g), grammi, stili, value);
			}
			System.out.printf("Alternative Matrix No. %d \n", arithmospair);
			for (int grammi = 0; grammi < DDGraph.length; grammi++) {
				for (int stili = 0; stili < DDGraph[grammi].length; stili++) {
					if (DDGraph[grammi][stili] == 1) {
						alternatives.get(g)[grammi][stili] = "1";
					}
				}
			}
			Execution2(alternatives.get(g));
			matrices.put(arithmospair, alternatives.get(g));
			System.out.println();
			arithmospair++;
		}
		// epilogi ddGraph

		// HashMap<Integer, String[][]> matrices =
		// getMatrices(timesOfAlternatives, DDGraph, allpairs);

		System.out.println("EPILEXSTE ARITHMO DDGRAPH GIA ANAPARASTASI");
		System.out.println("----EISAGOGI XRISTI----");
		// apo xristi
		// Execution2(matrices.get(2));
		// panta tha xsekinaei apo to proto stoixeio
		String source = "0";
		String ForwardTree[][] = ForwardTree(matrices.get(epilogiDDGraph),
				source);
		System.out.println("------ForwardTree----------");
		Execution2(ForwardTree);
		System.out
				.println("Arithmos Teleutaiou Komvou apo ton opoio tha xsekinisei to Bachward"
						+ (ForwardTree.length - 1));
		String sinkNode = (Integer.toString(ForwardTree.length - 1));
		String BackwardTree[][] = BackWardTree(matrices.get(epilogiDDGraph),
				sinkNode);
		System.out.println("------BackwardTree----------");
		Execution2(BackwardTree);

		// anixneusi akmon pou den iparxoyn
		HashMap<Integer, ArrayList<String>> allpaths = new HashMap<Integer, ArrayList<String>>();
		allpaths = Anixneusi(matrices.get(epilogiDDGraph), ForwardTree,
				allpairs, DDGraph, BackwardTree);

		System.out.println(allpaths);// xoris mapping

		HashMap<Integer, ArrayList<String>> allFinalpaths = new HashMap<Integer, ArrayList<String>>();
		allFinalpaths = etoima(allpaths, mapping);
		System.out.println(allFinalpaths);

		visualise(DDGraph, mapping);

		// Out ela1=new Out(CFG);
	}

	public static HashMap<Integer, ArrayList<String>> etoima(
			HashMap<Integer, ArrayList<String>> nomapping,
			HashMap<Integer, Integer> mapping) {

		HashMap<Integer, ArrayList<String>> ready = new HashMap<Integer, ArrayList<String>>();
		Set<Integer> keys = nomapping.keySet();
		for (Integer key : keys) {
			ArrayList<String> zeugoi = new ArrayList<String>(nomapping.get(key));
			ArrayList<String> voitheia = new ArrayList<String>();
			for (String kra : zeugoi) {

				for (Integer times : mapping.keySet()) {
					String allagi = Integer.toString(times);
					boolean find = false;
					HashMap<Integer, String> alphabet = getAlphabet();
					String etoimo = "";
					for (int intalphabet : alphabet.keySet()) {
						if (kra.substring(kra.length() - 1).equals(
								alphabet.get(intalphabet))) {

							etoimo = kra.substring(kra.length() - 1);

							if (kra.lastIndexOf(etoimo) == 1) {
								if (allagi.equals(kra.substring(0, 1))) {
									String etoimo1 = mapping.get(times)
											+ etoimo;
									voitheia.add(etoimo1);

								}
							} else if (kra.lastIndexOf(etoimo) == 2) {
								if (allagi.equals(kra.substring(0, 2))) {
									String etoimo1 = mapping.get(times)
											+ etoimo;
									voitheia.add(etoimo1);

								}
							}
						}
					}

					if (allagi.equals(kra)) {

						String etoimo1 = mapping.get(times) + etoimo;
						voitheia.add(etoimo1);

					}

				}

			}

			ready.put(key, voitheia);
		}
		return ready;
	}

	public static HashMap<Integer, String[][]> getMatrices(
			int timesOfAlternatives, int[][] DDGraph, ArrayList<Pair> allpairs) {
		HashMap<String, String[][]> alternatives = new HashMap<String, String[][]>();
		for (int t = 0; t < timesOfAlternatives; t++) {
			alternatives.put("g" + t, initializeStringDDGraph(DDGraph.length));

		}

		HashMap<Integer, String> alphabet = getAlphabet();

		// find pairs
		int helper = 0;

		HashMap<Integer, Pair> sindiasmoi = new HashMap<Integer, Pair>();

		for (Pair p : allpairs) {

			sindiasmoi.put(helper, p);

			helper++;
		}
		System.out.println(sindiasmoi);
		ArrayList<Integer> zeugi = new ArrayList<Integer>();
		totalCombinations.clear();
		number = 0;
		combinations(0, zeugi, sindiasmoi, DDGraph, helper);
		System.out.println("Total Combinations" + totalCombinations);
		Set<String> set = alternatives.keySet();
		HashMap<Integer, String[][]> matrices = new HashMap<Integer, String[][]>();
		int arithmospair = 0;
		for (String g : set) {

			ArrayList<Integer> allages = new ArrayList<Integer>(
					totalCombinations.get(arithmospair));
			for (int i = 0; i < allages.size(); i++) {// panta o arithmos ton
				// pair tha nai isos me
				// ton arithmo mesa
				int grammi = sindiasmoi.get(i).x;
				int stili = sindiasmoi.get(i).y;
				int value = allages.get(i);
				ChangeofAlternatives(alternatives.get(g), grammi, stili, value);
			}
			System.out.printf("Alternative Matrix No. %d \n", arithmospair);
			for (int grammi = 0; grammi < DDGraph.length; grammi++) {
				for (int stili = 0; stili < DDGraph[grammi].length; stili++) {
					if (DDGraph[grammi][stili] == 1) {
						alternatives.get(g)[grammi][stili] = "1";
					}
				}
			}
			Execution2(alternatives.get(g));
			matrices.put(arithmospair, alternatives.get(g));
			System.out.println();
			arithmospair++;
		}
		return matrices;
	}

	public static HashMap<Integer, ArrayList<String>> Anixneusi(String DDg[][],
			String For[][], ArrayList<Pair> allpairs, int DDGraph[][],
			String Back[][]) {
		ArrayList<Pair> zeugoi = new ArrayList<Pair>();
		for (int k = 0; k < DDg.length; k++) {// arxika na antistrafoun ta edges
			for (int m = 0; m < DDg[k].length; m++) {
				if (!For[k][m].equals(DDg[k][m])) {
					for (Pair q : allpairs) {
						if ((q.x == k) && (q.y == m)) {
							int value = DDGraph[k][m] - 1;// epeidi tha to
							// xsanapiasei meta
							while (value > 0) {
								zeugoi.add(q);
								value--;
							}
						}
					}
					Pair oe = new Pair(k, m);

					zeugoi.add(oe);

				}
			}
		}
		// menei kai o arithmos pair pou iparxoun mesa
		for (Pair p : allpairs) {
			int grammi = p.x;
			int stili = p.y;
			int arithmos = DDGraph[grammi][stili] - 1;
			if (For[grammi][stili].substring(0, 1).equals("1")) {
				while (arithmos > 0) {// se periptosi pou exei anaparastathei 2
					// fores
					Pair oe = new Pair(grammi, stili);
					zeugoi.add(oe);
					arithmos--;
				}
			}
		}

		int size = For.length;
		int[][] ForInt = new int[For.length][For.length];
		int[][] BackInt = new int[Back.length][Back.length];

		for (int k = 0; k < For.length; k++) {

			for (int m = 0; m < For[k].length; m++) {
				if (For[(k)][m].substring(0, 1).equals("1")) {
					ForInt[k][m] = 1;
				}
			}
		}
		for (int k = 0; k < Back.length; k++) {

			for (int m = 0; m < Back[k].length; m++) {
				if (Back[(k)][m].substring(0, 1).equals("1")) {
					BackInt[k][m] = 1;
				}
			}
		}

		HashMap<Integer, ArrayList<Integer>> monopatia = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, ArrayList<String>> monopatianew = new HashMap<Integer, ArrayList<String>>();

		HashMap<Integer, ArrayList<String>> monopatiaFinal1 = new HashMap<Integer, ArrayList<String>>();
		int counter = 1;
		{

			ArrayList<String> finalD = new ArrayList<String>();
			ArrayList<Integer> dijkstra = new ArrayList<Integer>();
			TestDijkstraAlgorithm paixse3 = new TestDijkstraAlgorithm(ForInt,
					0, DDGraph.length - 1, 100);// arithmos edges

			paixse3.testExcute();
			ListIterator<Vertex> iterator = TestDijkstraAlgorithm.sum()
					.listIterator();

			while (iterator.hasNext())

			{

				Vertex color = iterator.next();
				System.out.print(color.getN() + " ");
				dijkstra.add(color.getN());
			}
			String backward;
			String telos;
			for (int k = 0; k < dijkstra.size() - 1; k++) {
				int valuemesa = k + 1;

				telos = dijkstra.get(k) + "";
				backward = Back[dijkstra.get(k)][dijkstra.get(valuemesa)]
						.substring(1);
				if (backward != null) {
					telos = dijkstra.get(k) + backward;
				}
				finalD.add(telos);
				if ((k + 1) == (dijkstra.size() - 1)) {

					telos = dijkstra.get(valuemesa) + backward;
					finalD.add(telos);
				}

			}

			monopatiaFinal1.put(counter, finalD);
		}
		ArrayList<Integer> everything = new ArrayList<Integer>();
		ArrayList<String> strings = new ArrayList<String>();
		HashMap<Integer, String> alphabet = getAlphabet();
		Set<String> setZeugoi = new HashSet<String>();
		for (Pair nai : zeugoi) {// akmes pou leipoun apo to ForwardTree
			System.out.println("Oi akmes pou leipoun : " + nai.x + "-->"
					+ nai.y);

			String choice = For[nai.x][nai.y].substring(1);

			int value = DDGraph[nai.x][nai.y];
			int max = value - 1;
			if (value > 1) {
				if (choice.equals("")) {
					max = value - 1;
				} else {
					max = value;
				}
			}
			int counter1 = 0;
			String letter = null;
			while (counter1 < max) {

				letter = alphabet.get(counter1);

				String union = nai.x + "," + nai.y + "," + letter;
				System.out.println("Choice" + choice);
				if (letter.equals(choice)) {
					if (setZeugoi.contains(union)) {

						counter1++;
						setZeugoi.add(union);

					} else {
						counter1++;
						setZeugoi.add(union);

					}
				} else {
					if (setZeugoi.contains(union)) {

						counter1++;
						letter = alphabet.get(counter1);
						setZeugoi.add(union);

					} else {

						setZeugoi.add(union);
						counter1++;

						break;
					}
				}

			}

			if (nai.x != 0) {
				TestDijkstraAlgorithm paixse = new TestDijkstraAlgorithm(
						ForInt, 0, nai.x, 100);// arithmos edges
				paixse.testExcute();
				ListIterator<Vertex> iterator = TestDijkstraAlgorithm.sum()
						.listIterator();

				everything.clear();
				strings.clear();

				while (iterator.hasNext()) {

					Vertex color = iterator.next();
					everything.add(color.getN());
				}

			} else {
				everything.clear();
				strings.clear();
				everything.add(nai.x);
			}
			everything.add(nai.y);
			// edo while na koitazei
			System.out.println(everything);
			String neo;
			String forward;
			String telos = null;
			System.out.println(everything.size() - 1 + "SIZE" + everything);
			for (int i = 0; i < (everything.size() - 1); i++) {

				telos = everything.get(i) + "";
				int value1 = i + 1;

				if (!(everything.get(i) == nai.x && everything.get(value1) == nai.y)) {

					forward = For[everything.get(i)][everything.get(value1)]
							.substring(1);
					if (forward != null) {
						telos = everything.get(i) + forward;
					}

				}

				strings.add(telos);
			}
			for (int monopati : everything) {
				neo = monopati + "";
				int indexmon;
				if (monopati == nai.x && letter != null) {

					String NametoString = Integer.toString(monopati);
					indexmon = strings.indexOf(NametoString);
					strings.remove(indexmon);
					neo = monopati + letter;
					strings.add(neo);

				}

			}

			neo = nai.y + "";
			strings.add(neo);
			monopatianew.put(counter, new ArrayList<String>(strings));
			monopatia.put(counter, new ArrayList<Integer>(everything));
			counter++;
		}
		System.out.println(monopatia);
		System.out.println("----------" + monopatianew);
		int num = 2;
		Set<Integer> keys = monopatianew.keySet();
		for (Integer key : keys) {
			ArrayList<String> route = new ArrayList<String>();
			ArrayList<Integer> before = new ArrayList<Integer>();
			route = monopatianew.get(key);
			int index = (route.size() - 1);

			// dijkstra gia piso
			int value = Integer.parseInt(route.get(index).substring(0, 1));
			if (value != (DDGraph.length - 1)) {
				TestDijkstraAlgorithm paixse2 = new TestDijkstraAlgorithm(
						BackInt, value, DDGraph.length - 1, 100);// arithmos
																	// edges
				paixse2.testExcute();
				ListIterator<Vertex> iterator = TestDijkstraAlgorithm.sum()
						.listIterator();
				// Execution2(BackInt);
				route.remove(index);
				while (iterator.hasNext())

				{

					Vertex color = iterator.next();

					before.add(color.getN());
				}
				String backward;
				String telos;

				for (int k = 0; k < before.size() - 1; k++) {
					int valuemesa = k + 1;

					telos = before.get(k) + "";
					backward = Back[before.get(k)][before.get(valuemesa)]
							.substring(1);
					if (backward != null) {
						telos = before.get(k) + backward;
					}
					route.add(telos);
					if ((k + 1) == (before.size() - 1)) {

						telos = before.get(valuemesa) + backward;
						route.add(telos);
					}

				}

			}
			System.out.println();
			// monopatiaFinal.put(num, route);
			monopatiaFinal1.put(num, route);
			num++;
		}
		System.out.println();

		System.out.println();
		System.out.println(monopatiaFinal1);
		return monopatiaFinal1;

	}

	public static <E> Integer[][] printIntMatrix(E[][] back) {
		Integer IntTree[][] = new Integer[back.length][back.length];
		for (int k = 0; k < back.length; k++) {

			for (int m = 0; m < back[k].length; m++) {
				if (((String) back[(k)][m]).substring(0, 1).equals("1")) {
					IntTree[k][m] = 1;
				}
			}
		}
		return IntTree;
	}

	public static String[][] BackWardTree(String real1[][], String source) {
		String backTree[][] = initializeStringDDGraph(real1.length);
		for (int k = 0; k < real1.length; k++) {// arxika na antistrafoun ta
												// PAIRNEI TO DDGRAPH

			for (int m = 0; m < real1[k].length; m++) {
				backTree[m][k] = real1[k][m];
			}
		}

		Queue<String> queue = new LinkedList<String>();

		boolean[] visited = new boolean[backTree.length + 1];
		visited[Integer.parseInt(source)] = true;
		queue.add(source);
		String element;
		String real[][] = initializeStringDDGraph(backTree.length);
		while (!queue.isEmpty())

		{

			element = queue.remove();

			System.out.print(element + "\t");
			for (int m = 0; m < backTree[Integer.parseInt(element)].length; m++) {
				if (backTree[Integer.parseInt(element)][m].substring(0, 1)
						.equals("1") && visited[m] == false) {
					queue.add(Integer.toString(m));
					real[m][Integer.parseInt(element)] = backTree[Integer
							.parseInt(element)][m];
					visited[m] = true;
				}
			}

			System.out.println(queue);
		}
		// Execution2(real);
		return real;

	}

	public static String[][] ForwardTree(String real1[][], String source) {
		Queue<String> queue = new LinkedList<String>();
		;
		boolean[] visited = new boolean[real1.length + 1];
		visited[Integer.parseInt(source)] = true;
		queue.add(source);
		String element;
		String real[][] = initializeStringDDGraph(real1.length);
		while (!queue.isEmpty())

		{

			element = queue.remove();

			System.out.print(element + "\t");
			for (int m = 0; m < real1[Integer.parseInt(element)].length; m++) {
				if (real1[Integer.parseInt(element)][m].substring(0, 1).equals(
						"1")
						&& visited[m] == false) {
					queue.add(Integer.toString(m));
					real[Integer.parseInt(element)][m] = real1[Integer
							.parseInt(element)][m];
					visited[m] = true;
				}
			}

			System.out.println(queue);
		}
		// Execution2(real);
		return real;
	}

	public static void ChangeofAlternatives(String real1[][], int x, int y,
			int value) {
		HashMap<Integer, String> alphabet = getAlphabet();
		real1[x][y] = "1" + alphabet.get(value);
	}

	public static void Execution2(String real1[][]) {
		for (int test = 0; test < real1[0].length; test++) // edo to array[0]
		{
			System.out.printf(" %d	", test);
		}
		System.out.println();
		for (int k = 0; k < real1.length; k++) {
			System.out.print(k + ": ");
			for (int m = 0; m < real1[k].length; m++) {
				System.out.print(real1[k][m] + "	");
			}
			System.out.println();
		}
	}

	public static void Execution2(int real1[][]) {
		for (int test = 0; test < real1[0].length; test++) // edo to array[0]
		{
			System.out.printf(" %d	", test);
		}
		System.out.println();
		for (int k = 0; k < real1.length; k++) {
			System.out.print(k + ": ");
			for (int m = 0; m < real1[k].length; m++) {
				System.out.print(real1[k][m] + "	");
			}
			System.out.println();
		}
	}

	private static void combinations(int key, ArrayList<Integer> zeugi,
			HashMap<Integer, Pair> sindiasmoi, int[][] DDGraph, int helper) {

		if (key < helper) {

			int x = sindiasmoi.get(key).getX();
			int y = sindiasmoi.get(key).getY();
			int value = DDGraph[x][y];
			for (int i = 0; i < value; i++) {
				key++;
				zeugi.add(i);
				combinations(key, zeugi, sindiasmoi, DDGraph, helper);

				if (key == helper) {

					System.out.println(zeugi);

					totalCombinations
							.put(number, new ArrayList<Integer>(zeugi));
					number++;
					int size = zeugi.size();
					zeugi.remove(size - 1);

				} else if (key < helper) {
					int size = zeugi.size() - 1;
					int index = key - 1;
					do {

						zeugi.remove(size);
						size--;

					} while (size >= index);
				}
				key--;
			}
		}

	}

	private static void visualise(int[][] dDGraph,
			HashMap<Integer, Integer> mapping) {
		JGraphXAdapterDemo applet = new JGraphXAdapterDemo();
		applet.init(dDGraph, mapping);
		// applet.init();
		JFrame frame = new JFrame();
		frame.getContentPane().add(applet);
		frame.setTitle("JGraphT Adapter to JGraph Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * {@inheritDoc}
	 */
	public void init(int[][] gra) {
		// create a JGraphT graph
		ListenableGraph<String, DefaultEdge> g = new ListenableDirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		// create a visualization using JGraph, via an adapter
		jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(g);
		mxGraphComponent compo = new mxGraphComponent(jgxAdapter);

		// compo.setSize();
		getContentPane().add(compo);
		resize(DEFAULT_SIZE);

		int[][] ret = gra;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				ret[i][j] = 0;
			}
		}
		ret[0][1] = 1;
		ret[0][2] = 1;
		ret[1][3] = 1;
		ret[1][4] = 1;
		ret[2][5] = 1;
		ret[2][6] = 1;

		ArrayList<String> nodes = new ArrayList<String>();

		// add nodes
		for (int i = 0; i < ret.length; i++) {
			nodes.add(String.valueOf((i + 1)));
			g.addVertex(nodes.get(i));
		}

		// add edges
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret.length; j++) {
				int edges = ret[i][j];
				while (edges > 0) {
					g.addEdge(nodes.get(i), nodes.get(j));
					edges--;
				}
			}
		}

		// repositioning
		Random r = new Random();
		double x = 0;
		double y = 0;
		for (int i = 0; i < ret.length; i++) {
			x = r.nextDouble() * 1000 + x / 1000.0;
			y = r.nextDouble() * 500 + y / 500.0;
			// positionVertexAt(nodes.get(i), x, y);
		}

		mxFastOrganicLayout layout = new mxFastOrganicLayout(jgxAdapter);
		// mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
		layout.execute(jgxAdapter.getDefaultParent());

		// that's all there is to it!...
	}

	private static HashMap<Integer, String> getAlphabet() {
		HashMap<Integer, String> alphabet = new HashMap<Integer, String>();
		alphabet.put(0, "a");
		alphabet.put(1, "b");
		alphabet.put(2, "c");
		alphabet.put(3, "d");
		alphabet.put(4, "e");
		alphabet.put(5, "f");
		alphabet.put(6, "g");
		alphabet.put(7, "h");
		alphabet.put(8, "i");
		alphabet.put(9, "j");
		alphabet.put(10, "k");
		alphabet.put(11, "l");
		alphabet.put(12, "m");
		return alphabet;
	}

	private static String[][] initializeStringDDGraph(int length) {
		String[][] ret = new String[length][length];
		for (int k = 0; k < length; k++) {
			for (int m = 0; m < length; m++) {
				ret[k][m] = "0";
			}
		}
		return ret;
	}

	public static Object[] getTimesOfAlternatives(int[][] dDGraph) {
		Object[] returnObj = new Object[2];
		ArrayList<Pair> l = new ArrayList<Pair>();
		int ret = 1;
		for (int k = 0; k < dDGraph.length; k++) {
			for (int m = 0; m < dDGraph.length; m++) {
				if (dDGraph[k][m] > 1) {
					ret = ret * dDGraph[k][m];
					l.add(new Pair(k, m));
				}
			}
		}

		returnObj[0] = ret;
		returnObj[1] = l;
		return returnObj;
	}

	public static Object[] getDDGraph(int[][] cFG) {
		Object[] retVal = new Object[2];
		HashMap<Integer, Integer> mapToTrueNode = new HashMap<Integer, Integer>();
		Set<Integer> removed = new HashSet<Integer>();
		for (int i = 0; i < cFG.length; i++) {
			if (i == 0 || i == (cFG.length - 1)) {
				continue;
			}
			Object[] ret = retCheck(cFG[i], cFG, i);
			if (!(Boolean) ret[0]) {

				removed.add(i);
				int dest = (int) ret[1];
				// System.out.println("dest for "+(i)+" is "+dest);
				for (int k = 0; k < cFG.length; k++) {
					if (cFG[k][i] > 0) {
						// cFG[k][dest]++;
					}
				}
				int target = 0;
				for (Integer num : (ArrayList<Integer>) ret[2]) {
					cFG[num][dest] = cFG[num][dest] + cFG[num][i];

				}
				// cFG[][] = target;
				for (int k = 0; k < cFG.length; k++) {
					cFG[i][k] = -1;
					cFG[k][i] = -1;
				}
				continue;
			}
		}
		int[][] dd = new int[cFG.length - removed.size()][cFG.length
				- removed.size()];
		System.out.println("\n->nodes removed: " + removed.size()
				+ " when converting CFG to DDGraph");
		// Iterator<Integer> it = removed.iterator();
		int x = 0;
		for (int m = 0; m < cFG.length; m++) {
			if (removed.contains(m)) {
				continue;
			}
			mapToTrueNode.put(x, m + 1);// Maps an incorrect node reference to
			// the initial basic unit nodes (as
			// refd. in CFG)
			int rowIdx = m;
			int y = 0;
			for (int k = 0; k < cFG.length; k++) {
				if (removed.contains(k)) {
					continue;
				} else {
					dd[x][y] = cFG[rowIdx][k];
					y++;
				}
			}
			x++;
		}
		// printMatrix(cFG,true, mapToTrueNode);
		printMatrix(dd, false, mapToTrueNode);
		printMap(mapToTrueNode);
		retVal[0] = dd;
		retVal[1] = mapToTrueNode;
		return retVal;
	}

	private static void printMap(HashMap<Integer, Integer> mapToTrueNode) {
		Iterator<Entry<Integer, Integer>> it = mapToTrueNode.entrySet()
				.iterator();
		System.out.println("-------------------------------------");
		System.out
				.println("\nMapping from DDGraph index positions to basic unit number\n");
		while (it.hasNext()) {
			Entry<Integer, Integer> e = it.next();
			System.out.println(e.getKey() + " => " + e.getValue());
		}
	}

	private static void printMatrix(int[][] cFG, boolean isCFG,
			HashMap<Integer, Integer> mapToTrueNode) {
		if (isCFG) {
			System.out.println("--------------------------------");
			int numberOfBlocks = cFG.length;
			System.out.print("Basic unit	->	");
			for (int m = 0; m < numberOfBlocks; m++) {
				System.out.print("'" + (m + 1) + "'	");
			}
			System.out.println();
			for (int k = 0; k < numberOfBlocks; k++) {
				System.out.print("Basic unit " + (k + 1) + "	->	");
				for (int m = 0; m < numberOfBlocks; m++) {
					System.out.print(cFG[k][m] + "	");
				}
				System.out.println();
			}
		} else {
			System.out
					.println("\n------------DDGraph from CFG above--------------------");
			int numberOfBlocks = cFG.length;
			System.out.print("Basic unit	->	");
			for (int m = 0; m < numberOfBlocks; m++) {
				System.out.print("'" + (mapToTrueNode.get(m)) + "'	");
			}
			System.out.println();
			for (int k = 0; k < numberOfBlocks; k++) {
				System.out.print("Basic unit " + (mapToTrueNode.get(k))
						+ "	->	");
				for (int m = 0; m < numberOfBlocks; m++) {
					System.out.print(cFG[k][m] + "	");
				}
				System.out.println();
			}
		}
	}

	private static Object[] retCheck(int[] is, int[][] cFG, int index) {
		// printLine(is);
		Object[] ret = new Object[3];
		ArrayList<Integer> ancestors = new ArrayList<Integer>();
		int s = 0;
		int pos = 0;
		// System.out.println();
		for (int i = 0; i < is.length; i++) {
			//
			// System.out.println(i);
			if (is[i] > 0) {
				s++;
				pos = i;
				// System.out.println(pos);
			}
		}
		// System.exit(0);
		if (s == 1) {
			ancestors = getAncestorChain(index, cFG);
			// printList(ancestors);
			ret[0] = false;
			ret[1] = pos;
			ret[2] = ancestors;
			return ret;
		} else {
			ret[0] = true;
			return ret;
		}
	}

	private static void printList(ArrayList<Integer> ancestors) {
		System.out.print("ancestors: [");
		for (Integer i : ancestors) {
			System.out.print(i + ",");
		}
		System.out.print("]\n");
	}

	private static void printLine(int[] is) {
		for (int i : is) {
			System.out.print(i + ",");
		}
	}

	private static ArrayList<Integer> getAncestorChain(int index, int[][] cFG) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int j = 0; j < cFG.length; j++) {
			if (cFG[j][index] > 0) {
				ret.add(j);
			}
		}
		return ret;
	}

	public static String getFileAsString(String path) throws IOException {
		File f = new File(path);
		return new String(Files.readAllBytes(f.toPath()));
	}
}
