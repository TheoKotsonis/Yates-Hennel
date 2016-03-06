package Procedure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CMPToCFGMatrix {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		int[][] CFG = convertCMPToMatrix("TRIANGLE.CMP", false);


	}

	public static void Execution2(int real1[][]) {
		for (int i = 0; i < real1.length; i++) {
			System.out.print(i + ":");
			for (int j = 0; j < real1[i].length; j++) {
				//
				System.out.printf("%d ", real1[i][j]);

			}
			System.out.println();
		}
	}

	public static int[][] convertCMPToMatrix(String string, boolean print)
			throws FileNotFoundException, IOException {
		File file = new File(string);
		System.out.println(file.getAbsolutePath());
		BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
		// BufferedReader br = new BufferedReader( new FileReader(string));
		String line = "";
		int numberOfBlocks = 0;
		int[][] matrix = new int[numberOfBlocks][numberOfBlocks];
		int timer = 0;
		boolean foundBlocksNo = false;
		while ((line = br.readLine()) != null) {
			if (foundBlocksNo) {
				// minus one because the last basic unit is not supposed to have
				// entry in this section of file, as a sink node
				if (timer >= (numberOfBlocks - 1)) {
					break;
				}
			}
			if (line.contains("NUMBER OF BLOCKS")) {
				// if (print){System.out.println(line);}
				String[] temp = line.replaceAll("\\s+", "").split("=");
				numberOfBlocks = Integer.parseInt(temp[1]);
				matrix = new int[numberOfBlocks][numberOfBlocks];
				// resolves matrix dimensions and initializes it with zeros
				for (int k = 0; k < numberOfBlocks; k++) {
					for (int m = 0; m < numberOfBlocks; m++) {
						matrix[k][m] = 0;
					}
				}
				foundBlocksNo = true;
			}
			if (line.contains("CONNECTS TO BLOCKS")) {
				String[] tokens = line.split("\\s+");
				// if (print){System.out.println(line);}
				Integer bu = Integer.parseInt(tokens[2]); // starts index at 1,
															// but matrix at 0

				if (tokens.length == 7) {
					Integer outlink = Integer.parseInt(tokens[6]); // starts
																	// index at
																	// 1
					matrix[bu - 1][outlink - 1] = 1;
				} else if (tokens.length == 8) {
					Integer outlink1 = Integer.parseInt(tokens[6]); // starts
																	// index at
																	// 1
					Integer outlink2 = Integer.parseInt(tokens[7]); // starts
																	// index at
																	// 1
					matrix[bu - 1][outlink1 - 1] = 1;
					matrix[bu - 1][outlink2 - 1] = 1;
				} else {
					System.out
							.println("No basic unit can lead to more than 2 exits! Check your input files!");
					System.exit(0);
				}
				timer++;
			}
		}
		if ((timer < (numberOfBlocks - 1)) || !foundBlocksNo) {
			System.out
					.println("Incorrect .CMP file format!\nStatic analysis file must at least have a line NUMBER OF BLOCKS and a section CONNECTION DISPLAY, in this order.");
			System.exit(0);
		}
		System.out
				.println("-Software-generated CFG adjacency matrix for static analysis in file "
						+ string
						+ " (dimension:"
						+ numberOfBlocks
						+ "x"
						+ numberOfBlocks + ")-\n");
		if (print) {
			System.out.print("Basic unit	->		");
			for (int m = 0; m < numberOfBlocks; m++) {
				System.out.print("'" + (m + 1) + "'	");
			}
			System.out.println();
			for (int k = 0; k < numberOfBlocks; k++) {
				System.out.print("Basic unit " + (k + 1) + "	->		");
				for (int m = 0; m < numberOfBlocks; m++) {
					System.out.print(matrix[k][m] + "	");
				}
				System.out.println();
			}
		}
		br.close();
		return matrix;
	}
}
