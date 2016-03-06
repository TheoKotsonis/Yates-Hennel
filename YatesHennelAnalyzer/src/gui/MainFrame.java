package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;

import Procedure.CMPToCFGMatrix;
import Procedure.Pair;
import Procedure.PreClass;
import demo.JGraphXAdapterDemo;
import gui.SwingScrollBarExample.MyAdjustmentListener;

import java.awt.CardLayout;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.border.BevelBorder;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DropMode;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;

public class MainFrame extends JFrame {
	private JTextField txtCMPpath;
	private JPanel CFGmethod;
	private JPanel IntroMethod;
	private JPanel GraphVisual;
	private String txfile;
	private int row;
	private String[][] CFG;
	private int[][] intCFG;
	private int[][] DDGraph;
	private String[][] FTtree;
	private String[][] BTtree;
	private HashMap<Integer, Integer> mapping;
	private HashMap<Integer, String[][]> matrices;
	private ArrayList<Pair> allpairs;
	private JTable matrix;
	private JScrollPane CFGmatrixpane;
	private JScrollPane CFGgraphpane;
	private JScrollPane DDGpane;
	private JTable ddtable;
	private JScrollPane DDgraphpane;
	private JScrollPane DDmatrixpane;
	boolean ddflag, pathflag;
	private JScrollPane FWTgraphpane;
	private JScrollPane FTWmatrixpane;
	private JScrollPane BWTgraphpane;
	private JScrollPane BTWmatrixpane;
	protected JTable ptable;
	private JScrollPane PTHgraphpane;
	protected JScrollPane YHpane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		ddflag = true;
		pathflag = true;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1663, 704);
		// setResizable(true);
		// JScrollPane pane = new
		// JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		// JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane().setLayout(null);

		final JPanel IntroMethod = new JPanel();
		IntroMethod.setBounds(5, 12, 382, 646);
		IntroMethod.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0,
				0, 0), null, null, null));
		IntroMethod.setBackground(new Color(245, 245, 220));
		getContentPane().add(IntroMethod);
		IntroMethod.setLayout(null);

		JButton SelectButton = new JButton("Select");
		SelectButton.setFont(new Font("FreeSerif", Font.BOLD, 16));
		SelectButton.setBounds(12, 290, 100, 38);
		IntroMethod.add(SelectButton);

		txtCMPpath = new JTextField();
		txtCMPpath.setHorizontalAlignment(SwingConstants.CENTER);
		txtCMPpath.setFont(new Font("FreeSerif", Font.PLAIN, 16));
		txtCMPpath.setBounds(131, 291, 241, 38);
		IntroMethod.add(txtCMPpath);
		txtCMPpath.setColumns(10);

		JButton StartMethod = new JButton("Start the Method");
		StartMethod.setFont(new Font("FreeSerif", Font.BOLD, 16));
		StartMethod.setBounds(82, 536, 199, 38);
		IntroMethod.add(StartMethod);

		JTextArea txtrWelcomeToYates = new JTextArea();
		txtrWelcomeToYates.setEditable(false);
		txtrWelcomeToYates.setLineWrap(true);
		txtrWelcomeToYates.setFont(new Font("Dialog", Font.BOLD, 24));
		txtrWelcomeToYates.setBackground(new Color(245, 245, 220));
		txtrWelcomeToYates
				.setText("Welcome to Yates & Hennel \r\n                 Method");
		txtrWelcomeToYates.setBounds(22, 25, 334, 84);
		IntroMethod.add(txtrWelcomeToYates);

		JTextArea txtrChooseThecmp = new JTextArea();
		txtrChooseThecmp.setEditable(false);
		txtrChooseThecmp.setBackground(new Color(245, 245, 220));
		txtrChooseThecmp.setLineWrap(true);
		txtrChooseThecmp.setFont(new Font("Dialog", Font.BOLD, 18));
		txtrChooseThecmp
				.setText("        Choose the .CMP file to begin\r\n                       the method");
		txtrChooseThecmp.setBounds(12, 215, 360, 64);
		IntroMethod.add(txtrChooseThecmp);

		final JPanel GraphVisual = new JPanel();
		GraphVisual.setBounds(390, 12, 484, 646);
		GraphVisual.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0,
				0, 0), null, null, null));
		GraphVisual.setBackground(new Color(245, 245, 220));
		getContentPane().add(GraphVisual);
		GraphVisual.setLayout(null);

		final JLabel gVisual = new JLabel("Graph Visualization");
		gVisual.setHorizontalAlignment(SwingConstants.CENTER);
		gVisual.setFont(new Font("FreeSerif", Font.BOLD, 24));
		gVisual.setBounds(87, 11, 303, 49);
		GraphVisual.add(gVisual);

		final JPanel MatrixVisual = new JPanel();
		MatrixVisual.setBounds(878, 12, 480, 646);
		MatrixVisual.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(
				0, 0, 0), null, null, null));
		MatrixVisual.setBackground(new Color(245, 245, 220));
		getContentPane().add(MatrixVisual);
		MatrixVisual.setLayout(null);

		final JLabel mVisual = new JLabel("Matrix Visualization");
		mVisual.setHorizontalAlignment(SwingConstants.CENTER);
		mVisual.setFont(new Font("FreeSerif", Font.BOLD, 24));
		mVisual.setBounds(97, 11, 303, 49);
		MatrixVisual.add(mVisual);

		final JPanel CFGmethod = new JPanel();
		CFGmethod.setBounds(5, 12, 382, 646);
		CFGmethod.setLayout(null);
		CFGmethod.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0,
				0, 0), null, null, null));
		CFGmethod.setBackground(new Color(245, 245, 220));
		getContentPane().add(CFGmethod);
		CFGmethod.setVisible(false);

		JButton prevCFG = new JButton("Previous");
		prevCFG.setFont(new Font("FreeSerif", Font.BOLD, 16));
		prevCFG.setBounds(242, 536, 130, 38);
		CFGmethod.add(prevCFG);

		JButton CalcDDgraph = new JButton("DDgraphs");
		CalcDDgraph.setFont(new Font("FreeSerif", Font.BOLD, 16));
		CalcDDgraph.setBounds(12, 536, 185, 38);
		CFGmethod.add(CalcDDgraph);

		JTextArea txtCFG = new JTextArea();
		txtCFG.setEditable(false);
		txtCFG.setText("We calculate the Control Flow Graph and\r\n                 Control Flow Matrix.\r\n\r\n      Next we have to find DDgraphs");
		txtCFG.setToolTipText("");
		txtCFG.setWrapStyleWord(true);
		txtCFG.setTabSize(9);
		txtCFG.setFont(new Font("FreeSerif", Font.BOLD, 18));
		txtCFG.setBackground(new Color(245, 245, 220));
		txtCFG.setLineWrap(true);
		txtCFG.setBounds(12, 223, 360, 100);
		CFGmethod.add(txtCFG);

		JTextArea welcomeCFG = new JTextArea();
		welcomeCFG.setEditable(false);
		welcomeCFG
				.setText("Welcome to Yates & Hennel \r\n                 Method");
		welcomeCFG.setLineWrap(true);
		welcomeCFG.setFont(new Font("Dialog", Font.BOLD, 24));
		welcomeCFG.setBackground(new Color(245, 245, 220));
		welcomeCFG.setBounds(22, 25, 334, 84);
		CFGmethod.add(welcomeCFG);

		final JPanel DDGraphs = new JPanel();
		DDGraphs.setBounds(5, 12, 382, 646);
		getContentPane().add(DDGraphs);
		DDGraphs.setLayout(null);
		// DDGraphs.setLayout(new BoxLayout(DDGraphs, BoxLayout.Y_AXIS));
		DDGraphs.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0,
				0), null, null, null));
		DDGraphs.setBackground(new Color(245, 245, 220));
		DDGraphs.setVisible(false);

		JTextArea welcomeDD = new JTextArea();
		welcomeDD.setEditable(false);
		welcomeDD
				.setText("Welcome to Yates & Hennel \r\n                 Method");
		welcomeDD.setLineWrap(true);
		welcomeDD.setFont(new Font("Dialog", Font.BOLD, 24));
		welcomeDD.setBackground(new Color(245, 245, 220));
		welcomeDD.setBounds(22, 25, 334, 84);
		DDGraphs.add(welcomeDD);

		JTextArea txtDDgraph = new JTextArea();
		txtDDgraph.setWrapStyleWord(true);
		txtDDgraph.setToolTipText("");
		txtDDgraph
				.setText("            We found all the DDGraphs.\r\n              Choose one and continue");
		txtDDgraph.setTabSize(9);
		txtDDgraph.setLineWrap(true);
		txtDDgraph.setFont(new Font("FreeSerif", Font.BOLD, 18));
		txtDDgraph.setEditable(false);
		txtDDgraph.setBackground(new Color(245, 245, 220));
		txtDDgraph.setBounds(10, 221, 362, 57);
		DDGraphs.add(txtDDgraph);

		final JButton CalcFT = new JButton("Forward Tree");
		CalcFT.setFont(new Font("FreeSerif", Font.BOLD, 16));
		CalcFT.setBounds(10, 533, 185, 38);
		DDGraphs.add(CalcFT);

		final JButton prevDD = new JButton("Previous");
		prevDD.setFont(new Font("FreeSerif", Font.BOLD, 16));
		prevDD.setBounds(242, 533, 130, 38);
		DDGraphs.add(prevDD);

		final JPanel BackTreepanel = new JPanel();
		BackTreepanel.setLayout(null);
		BackTreepanel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(
				0, 0, 0), null, null, null));
		BackTreepanel.setBackground(new Color(245, 245, 220));
		BackTreepanel.setBounds(5, 12, 382, 646);
		getContentPane().add(BackTreepanel);
		BackTreepanel.setVisible(false);

		JButton prevBT = new JButton("Previous");
		prevBT.setFont(new Font("FreeSerif", Font.BOLD, 16));
		prevBT.setBounds(238, 535, 134, 38);
		BackTreepanel.add(prevBT);

		JButton CalcPaths = new JButton("All the paths");
		CalcPaths.setFont(new Font("FreeSerif", Font.BOLD, 16));
		CalcPaths.setBounds(10, 535, 184, 38);
		BackTreepanel.add(CalcPaths);

		JTextArea txtBT = new JTextArea();
		txtBT.setWrapStyleWord(true);
		txtBT.setToolTipText("");
		txtBT.setText("       We calculate the Backward Tree.\r\n        Finally we will find all the paths");
		txtBT.setTabSize(9);
		txtBT.setLineWrap(true);
		txtBT.setFont(new Font("FreeSerif", Font.BOLD, 18));
		txtBT.setEditable(false);
		txtBT.setBackground(new Color(245, 245, 220));
		txtBT.setBounds(10, 245, 362, 52);
		BackTreepanel.add(txtBT);

		JTextArea welcomeBT = new JTextArea();
		welcomeBT.setEditable(false);
		welcomeBT
				.setText("Welcome to Yates & Hennel \r\n                 Method");
		welcomeBT.setLineWrap(true);
		welcomeBT.setFont(new Font("Dialog", Font.BOLD, 24));
		welcomeBT.setBackground(new Color(245, 245, 220));
		welcomeBT.setBounds(22, 25, 334, 84);
		BackTreepanel.add(welcomeBT);

		final JPanel FTreepanel = new JPanel();
		FTreepanel.setBounds(5, 12, 382, 646);
		getContentPane().add(FTreepanel);
		FTreepanel.setLayout(null);
		FTreepanel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0,
				0, 0), null, null, null));
		FTreepanel.setBackground(new Color(245, 245, 220));
		FTreepanel.setVisible(false);

		JButton prevFT = new JButton("Previous");
		prevFT.setFont(new Font("FreeSerif", Font.BOLD, 16));
		prevFT.setBounds(238, 535, 134, 38);
		FTreepanel.add(prevFT);

		JButton calcBwt = new JButton("Backward Tree");
		calcBwt.setFont(new Font("FreeSerif", Font.BOLD, 16));
		calcBwt.setBounds(10, 535, 209, 38);
		FTreepanel.add(calcBwt);

		JTextArea txtFT = new JTextArea();
		txtFT.setWrapStyleWord(true);
		txtFT.setToolTipText("");
		txtFT.setText("       We calculate the Forward Tree.\r\nNext we have to find the Backward Tree");
		txtFT.setTabSize(9);
		txtFT.setLineWrap(true);
		txtFT.setFont(new Font("FreeSerif", Font.BOLD, 18));
		txtFT.setEditable(false);
		txtFT.setBackground(new Color(245, 245, 220));
		txtFT.setBounds(10, 245, 362, 52);
		FTreepanel.add(txtFT);

		JTextArea welcomeFT = new JTextArea();
		welcomeFT.setEditable(false);
		welcomeFT
				.setText("Welcome to Yates & Hennel \r\n                 Method");
		welcomeFT.setLineWrap(true);
		welcomeFT.setFont(new Font("Dialog", Font.BOLD, 24));
		welcomeFT.setBackground(new Color(245, 245, 220));
		welcomeFT.setBounds(22, 25, 334, 84);
		FTreepanel.add(welcomeFT);

		final JPanel YHpaths = new JPanel();
		YHpaths.setBounds(5, 12, 382, 646);
		getContentPane().add(YHpaths);
		YHpaths.setLayout(null);
		YHpaths.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0,
				0), null, null, null));
		YHpaths.setBackground(new Color(245, 245, 220));
		YHpaths.setVisible(false);

		JTextArea welcomePaths = new JTextArea();
		welcomePaths.setEditable(false);
		welcomePaths
				.setText("Welcome to Yates & Hennel \r\n                 Method");
		welcomePaths.setLineWrap(true);
		welcomePaths.setFont(new Font("Dialog", Font.BOLD, 24));
		welcomePaths.setBackground(new Color(245, 245, 220));
		welcomePaths.setBounds(22, 25, 334, 84);
		YHpaths.add(welcomePaths);

		JTextArea txtPaths = new JTextArea();
		txtPaths.setWrapStyleWord(true);
		txtPaths.setToolTipText("");
		txtPaths.setText("               We found all the paths.\r\n            Choose one and continue");
		txtPaths.setTabSize(9);
		txtPaths.setLineWrap(true);
		txtPaths.setFont(new Font("FreeSerif", Font.BOLD, 18));
		txtPaths.setEditable(false);
		txtPaths.setBackground(new Color(245, 245, 220));
		txtPaths.setBounds(10, 222, 362, 57);
		YHpaths.add(txtPaths);

		final JButton prevPaths = new JButton("Previous");
		prevPaths.setFont(new Font("FreeSerif", Font.BOLD, 16));
		prevPaths.setBounds(10, 535, 130, 38);
		YHpaths.add(prevPaths);

		final JButton resetButton = new JButton("Start again");
		resetButton.setFont(new Font("FreeSerif", Font.BOLD, 16));
		resetButton.setBounds(242, 535, 130, 38);
		YHpaths.add(resetButton);

		SelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenFile fOpen = new OpenFile();
				try {
					txfile = fOpen.pickfile();
					txtCMPpath.setText(txfile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		StartMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// HashMap<Integer,Integer> mapping = null;
				String testflag = "cfg";
				if (txfile != null && !txfile.isEmpty()) {
					CFGmethod.setVisible(true);
					IntroMethod.setVisible(false);
					DDGraphs.setVisible(false);

					try {
						intCFG = CMPToCFGMatrix
								.convertCMPToMatrix(txfile, true);
						CFG = convertmatrix(intCFG);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					GraphVisual
							.add(Graphic(GraphVisual, CFG, mapping, testflag));
					MatrixVisual.add(MTX(MatrixVisual, CFG, mapping, testflag));

				}
			}

			private String[][] convertmatrix(int[][] cfgraph) {
				// TODO Auto-generated method stub
				String[][] ret = new String[cfgraph.length][cfgraph[0].length];
				for (int i = 0; i < cfgraph.length; i++) {
					for (int j = 0; j < cfgraph[i].length; j++) {
						ret[i][j] = String.valueOf(cfgraph[i][j]);
					}
				}
				return ret;
			}
		});

		CalcDDgraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// final int[][] DDG = null;
				// final HashMap<Integer,Integer> mapping = null;
				CFGmethod.setVisible(false);
				DDGraphs.setVisible(true);
				IntroMethod.setVisible(false);

				CFGgraphpane.setVisible(false);
				CFGmatrixpane.setVisible(false);

				try {
					intCFG = CMPToCFGMatrix.convertCMPToMatrix(txfile, true);
					// CFG = convertmatrix(intCFG);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Object[] retVal = PreClass.getDDGraph(intCFG);
				mapping = (HashMap<Integer, Integer>) retVal[1];
				DDGraph = (int[][]) retVal[0];
				Object[] returngetTimesEtc = PreClass
						.getTimesOfAlternatives(DDGraph);
				int timesOfAlternatives = (int) returngetTimesEtc[0];
				allpairs = (ArrayList<Pair>) returngetTimesEtc[1];

				System.out.println(timesOfAlternatives);
				final Vector<String> rowdata = new Vector<>();
				for (int column = 1; column <= timesOfAlternatives; column++) {
					rowdata.add("DDGraph " + column);
				}

				matrices = PreClass.getMatrices(timesOfAlternatives, DDGraph,
						allpairs);

				DefaultTableModel model = new DefaultTableModel() {

					@Override
					public boolean isCellEditable(int row, int column) {
						// all cells false
						return false;
					}
				};

				model.addColumn("DDGraphs", rowdata);

				FlowLayout gvislayout = new FlowLayout();
				// layout.setHgap(10000);
				gvislayout.setVgap(18);
				gvislayout.setHgap(20);
				gvislayout.setAlignment(FlowLayout.CENTER);
				DDGraphs.setLayout(gvislayout);

				ddtable = new JTable(model);

				ddtable.setBounds(28, 298, 410, timesOfAlternatives * 16);
				ddtable.setFont(new Font("FreeSerif", Font.BOLD, 16));
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				ddtable.getColumnModel().getColumn(0)
						.setCellRenderer(centerRenderer);
				ddtable.setPreferredScrollableViewportSize(new Dimension(410,
						timesOfAlternatives * 16));
				// ddtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				ddtable.setFillsViewportHeight(true);

				DDGpane = new JScrollPane(ddtable);
				DDGpane.setPreferredSize(new Dimension(320, 140));
				// DDGpane.setSize(new Dimension(320,240),);

				ddtable.setCellSelectionEnabled(true);

				ddtable.addMouseListener(new java.awt.event.MouseAdapter() {
					@Override
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						String testflag = "ddg";
						row = ddtable.rowAtPoint(evt.getPoint());
						if (row >= 0) {

							if (ddflag) {
								GraphVisual.add(Graphic(GraphVisual,
										matrices.get(row), mapping, testflag));
								MatrixVisual.add(MTX(MatrixVisual,
										matrices.get(row), mapping, testflag));
								ddflag = false;
							} else {
								GraphVisual.remove(DDgraphpane);
								GraphVisual.add(Graphic(GraphVisual,
										matrices.get(row), mapping, testflag));
								MatrixVisual.remove(DDmatrixpane);
								MatrixVisual.add(MTX(MatrixVisual,
										matrices.get(row), mapping, testflag));
							}
							GraphVisual.revalidate();
							MatrixVisual.revalidate();

						}
					}
				});

				DDGraphs.add(DDGpane, BorderLayout.CENTER);
				CalcFT.setBounds(28, 533, 130, 38);
				CalcFT.setPreferredSize(new Dimension(199, 38));
				DDGraphs.add(CalcFT);
				prevDD.setBounds(308, 533, 130, 38);
				prevDD.setPreferredSize(new Dimension(130, 38));
				DDGraphs.add(prevDD);

			}
		});

		prevCFG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CFGmethod.setVisible(false);
				DDGraphs.setVisible(false);
				IntroMethod.setVisible(true);
				CFGgraphpane.setVisible(false);
				CFGmatrixpane.setVisible(false);
				if (!ddflag) {

					DDgraphpane.setVisible(false);
					DDmatrixpane.setVisible(false);
				}
			}
		});

		prevDD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CFGmethod.setVisible(true);
				DDGraphs.setVisible(false);
				IntroMethod.setVisible(false);
				CFGgraphpane.setVisible(true);
				CFGmatrixpane.setVisible(true);
				DDGpane.setVisible(false);
				if (!ddflag) {
					DDgraphpane.setVisible(false);
					DDmatrixpane.setVisible(false);
				}
				ddflag = true;
			}
		});

		CalcFT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!ddflag) {
					FTtree = PreClass.ForwardTree(matrices.get(row), "0");
					String testflag = "fwt";
					CFGmethod.setVisible(false);
					DDGraphs.setVisible(false);
					IntroMethod.setVisible(false);
					FTreepanel.setVisible(true);

					CFGgraphpane.setVisible(false);
					CFGmatrixpane.setVisible(false);
					DDgraphpane.setVisible(false);
					DDmatrixpane.setVisible(false);

					GraphVisual.add(Graphic(GraphVisual, FTtree, mapping,
							testflag));
					MatrixVisual.add(MTX(MatrixVisual, FTtree, mapping,
							testflag));
				}
			}
		});

		prevFT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CFGmethod.setVisible(false);
				DDGraphs.setVisible(true);
				IntroMethod.setVisible(false);
				FTreepanel.setVisible(false);
				CFGgraphpane.setVisible(false);
				CFGmatrixpane.setVisible(false);
				DDgraphpane.setVisible(true);
				DDmatrixpane.setVisible(true);
				FTWmatrixpane.setVisible(false);
				FWTgraphpane.setVisible(false);
			}
		});

		calcBwt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sinkNode = (Integer.toString(FTtree.length - 1));
				BTtree = PreClass.BackWardTree(matrices.get(row), sinkNode);
				String testflag = "bwt";
				CFGmethod.setVisible(false);
				DDGraphs.setVisible(false);
				IntroMethod.setVisible(false);
				FTreepanel.setVisible(false);
				BackTreepanel.setVisible(true);

				CFGgraphpane.setVisible(false);
				CFGmatrixpane.setVisible(false);
				DDgraphpane.setVisible(false);
				DDmatrixpane.setVisible(false);
				FTWmatrixpane.setVisible(false);
				FWTgraphpane.setVisible(false);

				GraphVisual
						.add(Graphic(GraphVisual, BTtree, mapping, testflag));
				MatrixVisual.add(MTX(MatrixVisual, BTtree, mapping, testflag));
			}
		});

		prevBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CFGmethod.setVisible(false);
				DDGraphs.setVisible(false);
				IntroMethod.setVisible(false);
				FTreepanel.setVisible(true);
				BackTreepanel.setVisible(false);
				CFGgraphpane.setVisible(false);
				CFGmatrixpane.setVisible(false);
				DDgraphpane.setVisible(false);
				DDmatrixpane.setVisible(false);
				FTWmatrixpane.setVisible(true);
				FWTgraphpane.setVisible(true);
				BWTgraphpane.setVisible(false);
				BTWmatrixpane.setVisible(false);
			}
		});

		CalcPaths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String[][] Paths = null;
				// final HashMap<Integer,Integer> mapping = null;
				CFGmethod.setVisible(false);
				DDGraphs.setVisible(false);
				IntroMethod.setVisible(false);
				FTreepanel.setVisible(false);
				BackTreepanel.setVisible(false);
				YHpaths.setVisible(true);

				CFGgraphpane.setVisible(false);
				CFGmatrixpane.setVisible(false);
				DDgraphpane.setVisible(false);
				DDmatrixpane.setVisible(false);
				FTWmatrixpane.setVisible(false);
				FWTgraphpane.setVisible(false);
				BWTgraphpane.setVisible(false);
				BTWmatrixpane.setVisible(false);

				HashMap<Integer, ArrayList<String>> allpaths = new HashMap<Integer, ArrayList<String>>();
				allpaths = PreClass.Anixneusi(matrices.get(row), FTtree,
						allpairs, DDGraph, BTtree);

				HashMap<Integer, ArrayList<String>> allFinalpaths = new HashMap<Integer, ArrayList<String>>();
				allFinalpaths = PreClass.etoima(allpaths, mapping);
				System.out.println(allFinalpaths);
				String pao[] = new String[mapping.size() + 1];
				for (int i = 1; i <= allFinalpaths.size(); i++) {
					for (String komvoi : allFinalpaths.get(i)) {
						if ((allFinalpaths.get(i).indexOf(komvoi)) == (allFinalpaths
								.get(i).size()) - 1) {
							pao[i] = pao[i] + komvoi;

							break;
						} else {
							if (pao[i] != null) {
								pao[i] = pao[i] + komvoi + "-";
							} else {
								pao[i] = komvoi + "-";
							}

						}
					}

				}

				final Vector<String> rowdata = new Vector<String>();
				for (int column = 1; column <= allFinalpaths.size(); column++) {
					rowdata.add(pao[column]);
				}
				DefaultTableModel model = new DefaultTableModel() {

					@Override
					public boolean isCellEditable(int row, int column) {

						return false;
					}
				};

				model.addColumn("All Available Paths", rowdata);

				FlowLayout gvislayout = new FlowLayout();
				// layout.setHgap(10000);
				gvislayout.setVgap(18);
				gvislayout.setHgap(20);
				gvislayout.setAlignment(FlowLayout.CENTER);
				YHpaths.setLayout(gvislayout);

				ptable = new JTable(model);

				ptable.setBounds(28, 298, 410, allFinalpaths.size() * 16);
				ptable.setFont(new Font("FreeSerif", Font.BOLD, 16));
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				ptable.getColumnModel().getColumn(0)
						.setCellRenderer(centerRenderer);
				ptable.setPreferredScrollableViewportSize(new Dimension(410,
						allFinalpaths.size() * 16));
				// ptable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				ptable.setFillsViewportHeight(true);

				YHpane = new JScrollPane(ptable);
				YHpane.setPreferredSize(new Dimension(320, 140));

				ptable.setCellSelectionEnabled(true);

				YHpaths.add(YHpane, BorderLayout.CENTER);

				YHpaths.add(YHpane, BorderLayout.CENTER);
				prevPaths.setBounds(28, 533, 185, 38);
				prevPaths.setPreferredSize(new Dimension(185, 38));
				YHpaths.add(prevPaths);
				resetButton.setBounds(308, 533, 130, 38);
				resetButton.setPreferredSize(new Dimension(130, 38));
				YHpaths.add(resetButton);
			}
		});

		prevPaths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CFGmethod.setVisible(false);
				DDGraphs.setVisible(false);
				IntroMethod.setVisible(false);
				FTreepanel.setVisible(false);
				BackTreepanel.setVisible(true);
				YHpaths.setVisible(false);
				YHpane.setVisible(false);
				CFGgraphpane.setVisible(false);
				CFGmatrixpane.setVisible(false);
				DDgraphpane.setVisible(false);
				DDmatrixpane.setVisible(false);
				FTWmatrixpane.setVisible(false);
				FWTgraphpane.setVisible(false);
				BWTgraphpane.setVisible(true);
				BTWmatrixpane.setVisible(true);
				// pathflag = true;
			}
		});

		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CFGmethod.setVisible(false);
				DDGraphs.setVisible(false);
				IntroMethod.setVisible(true);
				FTreepanel.setVisible(false);
				BackTreepanel.setVisible(false);
				YHpaths.setVisible(false);
				DDGpane.setVisible(false);
				CFGgraphpane.setVisible(false);
				CFGmatrixpane.setVisible(false);
				DDgraphpane.setVisible(false);
				DDmatrixpane.setVisible(false);
				FTWmatrixpane.setVisible(false);
				FWTgraphpane.setVisible(false);
				BWTgraphpane.setVisible(false);
				BTWmatrixpane.setVisible(false);
				YHpane.setVisible(false);
				ddflag = true;
				// pathflag = true;
			}
		});

	}

	protected JScrollPane Graphic(JPanel GraphVisual, String[][] graph,
			HashMap<Integer, Integer> mapping, String testflag) {

		FlowLayout gvislayout = new FlowLayout();
		// layout.setHgap(10000);
		gvislayout.setVgap(18);
		gvislayout.setHgap(100);
		gvislayout.setAlignment(FlowLayout.CENTER);
		GraphVisual.setLayout(gvislayout);

		ListenableGraph<String, DefaultEdge> g = new ListenableDirectedGraph<String, DefaultEdge>(
				DefaultEdge.class);

		// create a visualization using JGraph, via an adapter
		JGraphXAdapter<String, DefaultEdge> jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(
				g);

		jgxAdapter.getModel().beginUpdate();

		try {
			ArrayList<String> nodes = new ArrayList<String>();
			ArrayList<Object> obNodes = new ArrayList<Object>();
			// add nodes
			for (int i = 0; i < graph.length; i++) {
				if (testflag.equals("cfg")) {
					nodes.add(String.valueOf(i + 1));
				} else
					nodes.add(String.valueOf((mapping.get(i))));
				// g.addVertex(nodes.get(i));
				obNodes.add(jgxAdapter.insertVertex(getParent(), null,
						nodes.get(i), 0, 0, 80, 35));
			}

			// add edges
			if (testflag.equals("cfg")) {
				for (int i = 0; i < graph.length; i++) {
					for (int j = 0; j < graph.length; j++) {
						int edges = Integer.parseInt(graph[i][j]);
						while (edges > 0) {
							// g.addEdge(nodes.get(i),nodes.get(j));
							jgxAdapter.insertEdge(getParent(), null,
									nodes.get(i) + "-" + nodes.get(j),
									obNodes.get(i), obNodes.get(j));
							edges--;
						}
					}
				}
			} else {

				for (int i = 0; i < graph.length; i++) {
					for (int j = 0; j < graph.length; j++) {
						String valpricEdge = new String("");
						String strpricEdge = new String("");

						char[] ch = new char[graph[i][j].length()];
						for (int count = 0; count < graph[i][j].length(); count++) {
							ch[count] = graph[i][j].charAt(count);
							if (Character.isDigit(ch[count])) {
								valpricEdge += ch[count];
							} else
								strpricEdge += ch[count];
						}
						int edges = Integer.parseInt(valpricEdge);
						while (edges > 0) {
							// g.addEdge(nodes.get(i),nodes.get(j));
							jgxAdapter.insertEdge(
									getParent(),
									null,
									nodes.get(i) + strpricEdge + "-"
											+ nodes.get(j), obNodes.get(i),
									obNodes.get(j));
							edges--;
						}
					}
				}
			}

		}

		finally {
			jgxAdapter.getModel().endUpdate();
		}

		mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);

		layout.execute(jgxAdapter.getDefaultParent());

		if (testflag.equals("cfg")) {
			System.out.println(testflag);
			CFGgraphpane = new JScrollPane(new mxGraphComponent(jgxAdapter));
			CFGgraphpane.setPreferredSize(new Dimension(460, 540));
			return CFGgraphpane;
		} else if (testflag.equals("ddg")) {
			System.out.println(testflag);
			DDgraphpane = new JScrollPane(new mxGraphComponent(jgxAdapter));
			DDgraphpane.setPreferredSize(new Dimension(460, 540));
			return DDgraphpane;

		} else if (testflag.equals("fwt")) {
			System.out.println(testflag);
			FWTgraphpane = new JScrollPane(new mxGraphComponent(jgxAdapter));
			FWTgraphpane.setPreferredSize(new Dimension(460, 540));
			return FWTgraphpane;
		} else if (testflag.equals("bwt")) {
			System.out.println(testflag);
			BWTgraphpane = new JScrollPane(new mxGraphComponent(jgxAdapter));
			BWTgraphpane.setPreferredSize(new Dimension(460, 540));
			return BWTgraphpane;
		} else {
			System.out.println(testflag);
			PTHgraphpane = new JScrollPane(new mxGraphComponent(jgxAdapter));
			PTHgraphpane.setPreferredSize(new Dimension(460, 540));
			return PTHgraphpane;
		}

	}

	protected JScrollPane MTX(JPanel MatrixVisual, String[][] graph,
			HashMap<Integer, Integer> mapping, String testflag) {

		// ----------------------Matrix
		// Visualization-------------------------------------------------------------------------------------
		Object[][] data;
		String[] columnHeaders;
		if (testflag.equals("cfg")) {
			data = new Object[graph.length][graph[0].length + 1];
			columnHeaders = new String[graph[0].length + 1];
			columnHeaders[0] = "#";
			for (int i = 0; i < graph.length; i++) {
				for (int j = 0; j < graph[0].length + 1; j++) {
					if (j == 0) {
						data[i][j] = i + 1;
					} else {
						columnHeaders[j] = String.valueOf(j);
						data[i][j] = graph[i][j - 1];
					}
				}
			}
		} else {
			data = new Object[graph.length][graph[0].length + 1];
			columnHeaders = new String[graph[0].length + 1];
			columnHeaders[0] = "#";
			for (int i = 0; i < graph.length; i++) {
				for (int j = 0; j < graph[0].length + 1; j++) {
					if (j == 0) {
						data[i][j] = mapping.get(i);
					} else {
						columnHeaders[j] = String.valueOf(mapping.get(j - 1));// String.valueOf(j);
						data[i][j] = graph[i][j - 1];
					}
				}
			}
		}

		FlowLayout mvislayout = new FlowLayout();

		mvislayout.setVgap(18);
		mvislayout.setHgap(100);
		mvislayout.setAlignment(FlowLayout.CENTER);
		MatrixVisual.setLayout(mvislayout);

		matrix = new JTable(data, columnHeaders);

		matrix.setFont(new Font("FreeSerif", Font.BOLD, 16));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < graph.length + 1; i++) {
			matrix.getColumnModel().getColumn(i)
					.setCellRenderer(centerRenderer);
		}
		matrix.getColumnModel().getColumn(0)
				.setCellRenderer(new RowHeaderRenderer());

		matrix.setPreferredScrollableViewportSize(new Dimension(550,
				(graph[0].length) * 16));

		matrix.setFillsViewportHeight(true);

		if (testflag.equals("cfg")) {
			CFGmatrixpane = new JScrollPane(matrix);
			CFGmatrixpane.setPreferredSize(new Dimension(460, 120));
			return CFGmatrixpane;
		} else if (testflag.equals("ddg")) {
			DDmatrixpane = new JScrollPane(matrix);
			DDmatrixpane.setPreferredSize(new Dimension(460, 120));
			return DDmatrixpane;
		} else if (testflag.equals("fwt")) {
			FTWmatrixpane = new JScrollPane(matrix);
			FTWmatrixpane.setPreferredSize(new Dimension(460, 120));
			return FTWmatrixpane;
		} else if (testflag.equals("bwt")) {
			BTWmatrixpane = new JScrollPane(matrix);
			BTWmatrixpane.setPreferredSize(new Dimension(460, 120));
			return BTWmatrixpane;
		} else {
			return CFGmatrixpane;
		}

	}
}
