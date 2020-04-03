import java.util.*;
import java.io.*;

public class MazeSolver {

    // 0 = wall
    // 1 = path
    // 2 = destination

    public static void main(String[] args) throws Exception {

        ArrayList<Maze> mazes = new ArrayList<Maze>();
        Maze m = new Maze();

        m.maze = readFile(m);

        // Check if there is one maze or not and print out
        mazes.add(m);
        int index = 0;
        while (index < mazes.size()) {
            if (solveMaze(mazes.get(index))) {
                for (int i = m.path.size() - 2; i >= 0; i--) {
                    System.out.println(m.path.get(i).dir);
                }
                System.out.println("Fine One path!");
            } else {
                System.out.println("No path!");
            }
            index++;
        }
    }

    private static boolean solveMaze(Maze m) {
        Position p = m.getStartIndex();
        m.path.push(p);

        while (true) {
            int y = m.path.peek().y; // row
            int x = m.path.peek().x; // column

            m.maze[y][x] = 0; // set wall or can call it visited

            // down
            if (isValid(y + 1, x, m)) {
                if (m.maze[y + 1][x] == 'D') {
                    m.path.push(new Position(y + 1, x, "South"));
                    return true;
                } else if (m.maze[y + 1][x] == '1') {
                    m.path.push(new Position(y + 1, x, "South"));
                    continue;
                }
            }

            // left
            if (isValid(y, x - 1, m)) {
                if (m.maze[y][x - 1] == 'D') {
                    m.path.push(new Position(y, x - 1, "West"));
                    return true;
                } else if (m.maze[y][x - 1] == '1') {
                    m.path.push(new Position(y, x - 1, "West"));
                    continue;
                }
            }

            // up
            if (isValid(y - 1, x, m)) {
                if (m.maze[y - 1][x] == 'D') {
                    m.path.push(new Position(y - 1, x, "North"));
                    return true;
                } else if (m.maze[y - 1][x] == '1') {
                    m.path.push(new Position(y - 1, x, "North"));
                    continue;
                }
            }

            // right
            if (isValid(y, x + 1, m)) {
                if (m.maze[y][x + 1] == 'D') {
                    m.path.push(new Position(y, x + 1, "East"));
                    return true;
                } else if (m.maze[y][x + 1] == '1') {
                    m.path.push(new Position(y, x + 1, "East"));
                    continue;
                }
            }

            m.path.pop();
            System.out.println("Moved back");
            if (m.path.size() <= 0) {
                return false;
            }
        }
    }

    public static boolean isValid(int y, int x, Maze m) { // y is row, x is column
        if (y < 0 || y >= m.maze.length || x < 0 || x >= m.maze[y].length) {
            return false;
        }
        return true;
    }

    public static char[][] readFile(Maze m) throws Exception {
        // ****1step: read file find numbers of rows and columns
        int rows = 0;
        int columns = 0;
        File file = new File("maze.txt");
        String data = new String();
        // only Use BufferReader class find rows in files
        BufferedReader reader = new BufferedReader(new FileReader("maze.txt"));
        while (reader.readLine() != null) {
            rows++;
        }

        // Use Scanner find number of character in each line
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            data = sc.nextLine();
            columns = data.length();
            break;
        }
        // ****2step make matrix represent maze
        m.maze = new char[rows][columns];
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            for (int i = 0; i < rows; i++) {
                data = scan.nextLine();
                char[] line = data.toCharArray();
                for (int j = 0; j < line.length; j++) {
                    m.maze[i][j] = (char) (line[j]);
                }
            }
        }
        System.out.println(Arrays.deepToString(m.maze));

        reader.close();
        sc.close();
        scan.close();

        return m.maze;
    }
}

class Maze {
    public char[][] maze;
    public LinkedList<Position> path = new LinkedList<Position>(); // stack
    public Position start;

    public Position getStartIndex() {
        int startX = -1;
        int startY = -1;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 'S') {
                    startX = i;
                    startY = j;
                }
            }
        }
        start = new Position(startX, startY, "S");
        return start;
    }
}

class Position {
    public int x;
    public int y;
    public String dir;
    // (y, x) y is row , x is column

    public Position(int y, int x, String dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
}
