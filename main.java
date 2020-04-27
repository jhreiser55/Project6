import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
public class main {
	public static void main(String args[]){
		File file = new File(args[0]);
		ArrayList<String> allNodes = new ArrayList<String>();
		int graphLength = 0;

		//gathering the Nodes from the file
		try {
			Scanner nodes = new Scanner(file);
			graphLength = nodes.nextInt();
			while(nodes.hasNextLine()){
				String[] line = nodes.nextLine().split(" ");
				allNodes.add(line[0]);
			}
			nodes.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Enter a valid file");
			System.exit(9);
		}

		//instanciating the graph
		Graph graph = new Graph(graphLength);
		for(int i = 1; i < graphLength + 1; i++){
			graph.setValue(i-1,allNodes.get(i));
		}

		//gathering the Edges for previous Nodes
		File file1 = new File(args[0]);
		try {
			Scanner edges = new Scanner(file1);
			String aaaa = edges.nextLine();
			while(edges.hasNextLine()){
				String[] line = edges.nextLine().split(" ");
				int numOfEdges = Integer.valueOf(line[1]);
				if(numOfEdges > 0){
					for(int i = 0; i < numOfEdges; i++){
						graph.insertEdge(line[0],line[i+2]);
					}
				}
			}
			edges.close();

		}
		catch(FileNotFoundException e){
			System.out.println("The computer doesnt like the edges lol.");
			System.exit(8);
		}

		//Starting the topological sorting
		//graph.printMatrix();	

		ArrayList<String> topologicalOrder = TopologicalSort(graph, file);
		if(topologicalOrder != null){
			System.out.print("Topological Order is: ");
			for(String a : topologicalOrder){
				System.out.print(a + " ");
			}
			System.out.println();	
		} else{
			System.out.println("There is no Topological Ordering for this graph");
		}

	}

	public static ArrayList<String> TopologicalSort(Graph graph, File file){
		ArrayList<String> visited = new ArrayList<String>();
		Object[] valuess = graph.getValues();
		ArrayList<String> values = new ArrayList<String>();	
		ArrayList<String> first = getStartingNode(file);
		for(String a:first){
			visited.add(a);
		}
		for(Object b: valuess){
			values.add(String.valueOf(b));
		}
		for(int i= 0; i < values.size(); i++){
			for(int j = 0; j < visited.size(); j++){
				if(values.get(i).equals(visited.get(j))){
					values.remove(values.get(i));
				}
			}
		}
		int xxx = 0;
		while(values.size() != 0 && xxx != 50){
			ArrayList<String> next = getFollowingNodes(visited,values,file);
			for(int i = 0; i < next.size(); i++){
				for(int j = 0; j < values.size(); j++){
					if(next.get(i).equals(values.get(j))){
						visited.add(next.get(i));
						values.remove(values.get(j));
					}
				}
			}
			xxx++;
		}
		if(xxx == 50){
			return null;
		}
		return visited;
	}


	public static ArrayList<String> getStartingNode(File file){
		ArrayList<String> starters = new ArrayList<String>();
		try{
			Scanner s = new Scanner(file);
			String placeholder = s.nextLine();
			while(s.hasNextLine()){
				String[] line = s.nextLine().split(" ");
				int numOfEdges = Integer.valueOf(line[1]);
				if(numOfEdges == 0){
					starters.add(line[0]);
				}
			}
			s.close();
		}catch(FileNotFoundException e){
			System.out.println("DIDNT WORK");
		}
		return starters;
	}

	public static ArrayList<String> getFollowingNodes(ArrayList<String> visited, ArrayList<String> values, File file){
		ArrayList<String> next = new ArrayList<String>();
		try{
			Scanner s = new Scanner(file);
			String placeholder = s.nextLine();
			while(s.hasNextLine()){
				String[] line = s.nextLine().split(" ");
				int numOfEdges = Integer.valueOf(line[1]);
				if(numOfEdges != 0){
					for(int i = 0; i < line.length-2; i++){
						for(int j = 0; j < visited.size(); j++){
							if(line[2+i].equals(visited.get(j))){
								next.add(line[0]);
								//System.out.println(line[2+i] + " - " + visited.get(j));
							}
						}
					}
				}
			}
			s.close();

		} catch(FileNotFoundException e){
			System.out.println("This is getting out of hand");
		}
		return next;
	}
}
