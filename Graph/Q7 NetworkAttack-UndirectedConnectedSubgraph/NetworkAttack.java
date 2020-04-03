import java.util.*;
import java.io.*;
/*
Main Idea:
1.Find all connected components in an undirected graph 
-> Find all disconnected subgraphs
2.Find a longest path of one connected subgraph 
-> Store result to list'list, find longest size of innerlist
*/

public class NetworkAttack {
    public static void main(String[] arags) throws Exception {
        List<String> cases = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("network.txt"))) {
            while (br.ready()) {
                cases.add(br.readLine());
            }
        }

        handleNetwork(cases);

    }

    public static void handleNetwork(List<String> cases) {
        int caseNo = 1;
        for (int i = 0; i < cases.size(); i++) {
            if (cases.get(i).length() == 1) {
                if (cases.get(i).equals("0")) {
                    break; // case should be not processed
                } else {
                    int nodeNum = Integer.valueOf(cases.get(i));
                    ConnectedGraph graph = new ConnectedGraph(nodeNum); // each time when see numNode, create Graph
                    int nodeIndex = 0;
                    while (nodeIndex < nodeNum) {
                        // moving i pointer go to nextline
                        i++;
                        String[] line = cases.get(i).split(" ");
                        // Test: System.out.println("len: " + line.length);
                        // for (String w : line) {
                        // System.out.print(w + " ");
                        // }
                        for (int j = 1; j <= Integer.parseInt(line[0]); j++) { // until neighbor number
                            graph.addEdge(nodeIndex, Integer.parseInt(line[j]));
                        }
                        nodeIndex++;
                    }
                    graph.connectedComponents();

                    ArrayList<ArrayList<Integer>> paths = graph.getPath();

                    ComparePrintResult(paths, caseNo);
                    caseNo++;

                }

            }
        }
    }

    public static void ComparePrintResult(ArrayList<ArrayList<Integer>> paths, int caseNo) {
        int longest = Integer.MIN_VALUE;
        for (int index = 0; index < paths.size(); index++) {
            longest = Math.max(paths.get(index).size(), longest);
        }
        System.out.println("Case " + caseNo + " : " + longest);
    }
}

class ConnectedGraph {
    /*
     * A graph is an array of adjacent nodes V means size of array / number of
     * vertices/nodes
     */
    int vertex;
    LinkedList<Integer> adjListArray[];
    ArrayList<ArrayList<Integer>> paths = new ArrayList<>();

    // constructor
    public ConnectedGraph(int vertex) {
        this.vertex = vertex;
        adjListArray = new LinkedList[vertex]; // define size of an array as number of vertices/nodes

        // create a new list for each vertex and store that's adjacent nodes
        for (int i = 0; i < vertex; i++) {
            adjListArray[i] = new LinkedList<Integer>();
        }
    }

    public void addEdge(int src, int dest) {
        // add an edge from src to dest
        adjListArray[src].add(dest);

        // since graph is undirected, and edge from dest to src
        adjListArray[dest].add(src);
    }

    public void connectedComponents() {
        // mark all vertices as not visited
        boolean[] visited = new boolean[vertex];
        for (int v = 0; v < vertex; v++) {
            if (!visited[v]) {
                // print out all reachable vertices from v
                ArrayList<Integer> items = new ArrayList<>();
                dfsgraph(v, visited, items);
                paths.add(items);
            }
        }
    }

    public void dfsgraph(int v, boolean[] visited, ArrayList<Integer> connectRoad) {
        // mark the current vertex as visited and print it out
        visited[v] = true;
        // System.out.print(v + " ");
        connectRoad.add(v);

        // recursion for all vertices and adjacent to this vertex
        for (int neighbor : adjListArray[v]) {
            if (!visited[neighbor]) {
                dfsgraph(neighbor, visited, connectRoad);
            }
        }
    }

    public ArrayList<ArrayList<Integer>> getPath() {
        return paths;
    }

}