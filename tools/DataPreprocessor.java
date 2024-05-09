package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import userdefinedtypes.Node;

public class DataPreprocessor {
	public static Node[] dataProcess(String input_filename, int N) {
		//list to contain all data entries in csv
		Node[] list = new Node[N];
		
		//for file parsing
		String line = null;
		String byComma = ",";
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(input_filename));
			
			//skip the first row with column names
			line = br.readLine();
			
			//start reading the file
			int i = 0;
			line = br.readLine();
			while (line != null) {
				list[i] = new Node();
				
				String[] info = line.split(byComma); //comma acts as separator between values
				
				list[i].setId(Integer.parseInt(info[0]));
				list[i].setDiagnosis(info[1].charAt(0));
				
				float[] tmp = new float[Node.K];
				for (int x = 2; x <= Node.K + 1; x++)
					tmp[x-2] = Float.parseFloat(info[x]);
				
				list[i].setVals(tmp);
				
				//go on to the next line
				i++;
				line = br.readLine();
			}
			
			br.close();
			
			//now that you have your list of data
			//use zscore to normalize data
			for (int dimension = 0; dimension < Node.K ; dimension++) {
				float total = 0;
				
				float[] attribute_column = new float[list.length];
				
				for (int row = 0; row < list.length; row++) {
					total += list[row].getVal(dimension);
					attribute_column[row] = list[row].getVal(dimension);
				}
				
				float mean = total / list.length;
				float std = (float) Math.sqrt(Calculator.findVar(attribute_column, mean));
				
				for (int row = 0; row < list.length; row++) {
					float new_val = (list[row].getVal(dimension) - mean) / std;
					list[row].setVal(new_val, dimension);
				}
			}
			
			//randomly shuffle the list for ML
			StdRandom.shuffle(list);
			
			//for data checking purposes
			//for (int x = 0; x < N; x++) System.out.println("Patient " + list[x]);
		}
		//in case file cant be opened
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
