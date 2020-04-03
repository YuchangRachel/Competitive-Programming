import java.util.*;
import java.io.*;

/*
Similiar: 
Flood Fill, determine area that connected given node
Number of island/ color island
Idea:
Find all islands using BFS
Color these islands with different labels
Must Go over all map, then find largest island and convert this area's label to @
Other which are not # convert labels to &

*/

class Weather {
    // Below arrays details all 8 possible movements from a cell
    // (top, right, bottom, left and 4 diagonal moves)
    private static final int[] row = { -1, -1, -1, 0, 1, 0, 1, 1 };
    private static final int[] col = { -1, 1, 0, -1, -1, 1, 0, 1 };

    public static void main(String[] args) throws Exception {
        // user prompt
        System.out.println("Enter file for satellite map and threshold of storm: ");
        Scanner sc = new Scanner(System.in);
        String selection = sc.nextLine();
        String[] input = selection.split(" ");
        String filename = input[0];
        System.out.println(filename);
        int threshold = Integer.parseInt(input[1]);

        // ****1step: read file find numbers of rows and columns
        int rows = 0;
        int columns = 0;
        String data = new String();
        // only Use BufferReader class find rows in files
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (reader.readLine() != null) {
            rows++;
        }

        // Use Scanner find number of character in each line
        File file = new File(filename);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            data = scan.nextLine();
            columns = data.length();
            break;
        }

        // *****2step: build satellite map
        char[][] map = new char[rows][columns];
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            for (int i = 0; i < rows; i++) {
                data = scanner.nextLine();
                char[] line = data.toCharArray();
                for (int j = 0; j < line.length; j++) {
                    map[i][j] = (char) (line[j]);
                }
            }
        }
        // System.out.println(Arrays.deepToString(map));

        int M = map.length;
        int N = map[0].length;

        // stores if cell is visited or not
        boolean[][] visited = new boolean[M][N];

        int storm = 0;
        int maxStorm = Integer.MIN_VALUE;
        int maxStormX = -1;
        int maxStormY = -1;

        int changeColor = 1;

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                // start BFS from each unprocessed node and
                // increment island count
                if (map[i][j] == '.' && !visited[i][j]) {
                    int mapArea = BFS(map, visited, i, j, changeColor);
                    changeColor++;
                    if (mapArea >= threshold) {
                        storm++;
                    }

                    if (mapArea > maxStorm) {
                        maxStorm = mapArea;
                        maxStormX = i;
                        maxStormY = j;
                    }
                }
            }
        }

        System.out.println("The numer of storms: " + storm + ", The size of the largest storm: " + maxStorm
                + " largest storm X: " + maxStormX + " Y: " + maxStormY);

        // coloring map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }

        // count occurence of label if less than threshold then convert back to #
        // use map store <char, count>
        HashMap<Character, Integer> labels = new HashMap<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != '#') {
                    if (labels.containsKey(map[i][j])) {
                        labels.put(map[i][j], labels.get(map[i][j]) + 1);
                    } else {
                        labels.put(map[i][j], 1);
                    }
                }
            }
        }
        Iterator iterator = labels.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapelement = (Map.Entry) iterator.next();
            int count = (int) mapelement.getValue();
            System.out.println(mapelement.getKey() + ": " + count);
        }

        System.out.println("===================================");
        // target label of highest level of storm
        char high = map[maxStormX][maxStormY];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == high) {
                    map[i][j] = '@';
                } else if (map[i][j] != '#') {
                    if (labels.get(map[i][j]) >= threshold) {
                        map[i][j] = '&';
                    } else {
                        map[i][j] = '.';
                    }
                }
                System.out.print(map[i][j]);
            }
            System.out.println();
        }

        sc.close();
        scanner.close();
        scan.close();
        reader.close();
    }

    public static boolean isSafe(char[][] map, int y, int x, boolean[][] visited) {
        return (y >= 0) && (y < visited.length) && (x >= 0) && (x < visited[0].length)
                && (map[y][x] == '.' && !visited[y][x]);
    }

    public static int BFS(char[][] map, boolean[][] visited, int i, int j, int changeColor) {
        int areaMap = 1; // current cell counted
        // create an empty queue for each island
        Queue<Position> q = new ArrayDeque<>();
        q.add(new Position(i, j));
        map[i][j] = (char) (changeColor + '0');

        // mark source node as visited
        visited[i][j] = true;

        // run till queue is not empty
        while (!q.isEmpty()) {
            // pop front node from queue and process it
            int x = q.peek().x;
            int y = q.peek().y;
            q.poll();

            // check for all 8 possible movements from current cell
            // and enqueue each valid movement
            for (int k = 0; k < 8; k++) {
                // check neighbor isSafe or not, if true then processing
                if (isSafe(map, y + row[k], x + col[k], visited)) {
                    areaMap++;
                    map[y + row[k]][x + col[k]] = (char) (changeColor + '0');
                    visited[y + row[k]][x + col[k]] = true;
                    q.add(new Position(y + row[k], x + col[k]));
                }
            }
        }
        return areaMap;
    }

}

class Position {
    int x, y;

    public Position(int y, int x) {
        this.x = x;
        this.y = y;
    }
}
