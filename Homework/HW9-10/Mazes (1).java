import java.util.*;

import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//to represent a Vertex
class Vertex {
  // to represent the edges adjacent to this vertex
  Edge left;
  Edge top;
  Edge right;
  Edge bottom;

  // to represent the cartesian coordinates of this vertex
  int x;
  int y;

  boolean hasBeenProcessed;
  boolean correctPath;

  ArrayList<Vertex> neighbors;

  Vertex(int x, int y) {
    this.x = x;
    this.y = y;
    this.hasBeenProcessed = false;
    neighbors = new ArrayList<Vertex>();
    this.correctPath = false;
  }

  Vertex(Edge right, Edge bottom) {
    this.right = right;
    this.bottom = bottom;
    this.hasBeenProcessed = false;
    this.correctPath = false;
    neighbors = new ArrayList<Vertex>();
    this.correctPath = false;
  }

  Vertex(Edge left, Edge top, Edge right, Edge bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.hasBeenProcessed = false;
    this.correctPath = false;
    neighbors = new ArrayList<Vertex>();
  }

}

//to represent an edge that connects two vertices
class Edge {
  Vertex from;
  Vertex to;
  int weight;

  Edge(int weight) {
    this.weight = weight;
  }

  Edge(Vertex from, Vertex to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }
}

//to represent a display of mazes of various sizes
class MazesWorld extends World {
  int rows;
  int cols;
  Random rand;

  // to represent a 2D grid of vertices
  ArrayList<ArrayList<Vertex>> grid;

  // to represent the paths in the maze
  ArrayList<Edge> edgesInTree;

  // to represent a DIRECTED graph of edges sorted by weight
  // having an undirected graph is inefficient (and not necessary)
  ArrayList<Edge> worklist;

  HashMap<Vertex, Vertex> representatives;

  // to represent the maze's solution
  ArrayList<Vertex> path;

  int cellSize;

  MazesWorld(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    this.rand = new Random();
    this.grid = this.initGrid();
    this.edgesInTree = new ArrayList<Edge>();
    this.worklist = this.initEdges();
    this.representatives = this.initMap();
    this.kruskal();
    this.cellSize = Math.min(1600 / rows, 960 / cols);
    this.updateNeighborsList();
  }

  // used for testing "randomness"
  MazesWorld(int rows, int cols, Random rand) {
    this.rows = rows;
    this.cols = cols;
    this.rand = rand;
    this.grid = this.initGrid();
    this.edgesInTree = new ArrayList<Edge>();
    this.worklist = this.initEdges();
    this.representatives = this.initMap();
    this.kruskal();
    this.cellSize = Math.min(1600 / rows, 960 / cols);
    this.updateNeighborsList();
  }

  // initializes all the vertices in the grid
  ArrayList<ArrayList<Vertex>> initGrid() {
    ArrayList<ArrayList<Vertex>> fin = new ArrayList<ArrayList<Vertex>>();
    for (int i = 0; i < rows; i++) {
      ArrayList<Vertex> temp = new ArrayList<Vertex>();
      for (int j = 0; j < cols; j++) {
        temp.add(new Vertex(i, j));
      }
      fin.add(temp);
    }
    return fin;
  }

  // initializes the edges of the grid with random weights
  // EFFECT: updates the edges of each vertex
  // adds the adjacent vertices of a vertex to its list of neighbors
  ArrayList<Edge> initEdges() {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // does this vertex have a vertex to its right?
        if (j < cols - 1) {
          Edge temp = new Edge(this.grid.get(i).get(j), this.grid.get(i).get(j + 1),
              this.rand.nextInt(1000));
          this.grid.get(i).get(j).right = temp;
          edges.add(temp);
        }
        // does this vertex have a vertex to its left?
        if (j > 0) {
          this.grid.get(i).get(j).left = new Edge(this.grid.get(i).get(j),
              this.grid.get(i).get(j - 1), this.grid.get(i).get(j - 1).right.weight);
        }
        // does this vertex have a vertex below it?
        if (i < rows - 1) {
          Edge temp = new Edge(this.grid.get(i).get(j), this.grid.get(i + 1).get(j),
              this.rand.nextInt(1000));
          this.grid.get(i).get(j).bottom = temp;
          edges.add(temp);
        }
        // does this vertex have a vertex above it?
        if (i > 0) {
          this.grid.get(i).get(j).top = new Edge(this.grid.get(i).get(j),
              this.grid.get(i - 1).get(j), this.grid.get(i - 1).get(j).bottom.weight);
        }
      }
    }
    // sort the edges by weight (ascending), will be used later
    edges.sort((Edge t, Edge o) -> t.weight - o.weight);
    return edges;
  }

  // initializes the HashMap of representatives
  HashMap<Vertex, Vertex> initMap() {
    HashMap<Vertex, Vertex> temp = new HashMap<Vertex, Vertex>();
    for (int i = 0; i < this.worklist.size(); i++) {
      temp.put(this.worklist.get(i).from, this.worklist.get(i).from);
      temp.put(this.worklist.get(i).to, this.worklist.get(i).to);
    }
    return temp;
  }

  // runs kruskal's algorithm
  // EFFECT: removes nodes from this.worklist once they have been considered by
  // the algorithm
  // creates a minimum spanning tree and adds it to this.edgesInTree
  void kruskal() {
    // while there's more than one tree
    while ((this.rows * this.cols) - 1 != this.edgesInTree.size()) {
      // If find(representatives, X) equals find(representatives, Y):
      if (this.find(this.worklist.get(0).from).equals(this.find(this.worklist.get(0).to))) {
        // discard this edge (it's already connected)
        this.worklist.remove(0);
      }
      else {
        // Record this edge in edgesInTree
        // union(representatives, find(representatives, X), find(representatives, Y))
        this.representatives.put(this.find(this.worklist.get(0).from),
            this.find(this.worklist.get(0).to));
        this.edgesInTree.add(this.worklist.remove(0));
      }
    }
  }

  // finds the representative for a given vertex
  Vertex find(Vertex vert) {
    Vertex temp = this.representatives.get(vert);
    while (!this.representatives.get(temp).equals(temp)) {
      temp = this.representatives.get(temp);
    }
    return temp;
  }

  // displays the board at any given moment
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(this.rows * this.cellSize, this.cols * this.cellSize);
    Color incorrect = new Color(145, 184, 242);
    Color correct = new Color(61, 118, 204);
    // draws the attempts of solving the maze and the correct path
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        if (this.grid.get(i).get(j).correctPath) {
          background.placeImageXY(
              new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, correct),
              i * this.cellSize + (this.cellSize / 2), j * this.cellSize + (this.cellSize / 2));
        }
        else if (this.grid.get(i).get(j).hasBeenProcessed) {
          background.placeImageXY(
              new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, incorrect),
              i * this.cellSize + (this.cellSize / 2), j * this.cellSize + (this.cellSize / 2));
        }
      }
    }

    // draws squares at the start and at the target
    background.placeImageXY(
        new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, Color.green),
        (this.cellSize / 2), (this.cellSize / 2));
    background.placeImageXY(
        new RectangleImage(this.cellSize, this.cellSize, OutlineMode.SOLID, Color.red),
        (this.rows - 1) * this.cellSize + (this.cellSize / 2),
        (this.cols - 1) * this.cellSize + (this.cellSize / 2));

    // draws the walls of the maze
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        if (i < this.rows - 1 && !this.edgesInTree.contains(this.grid.get(i).get(j).bottom)) {
          background.placeImageXY(
              new RectangleImage(1, this.cellSize, OutlineMode.SOLID, Color.black),
              (i + 1) * this.cellSize, (j + 1) * this.cellSize - this.cellSize / 2);
        }
        if (j < this.cols - 1 && !this.edgesInTree.contains(this.grid.get(i).get(j).right)) {
          background.placeImageXY(
              new RectangleImage(this.cellSize, 1, OutlineMode.SOLID, Color.black),
              (i + 1) * this.cellSize - this.cellSize / 2, (j + 1) * this.cellSize);
        }
      }
    }
    return background;
  }

  // handles keyboard inputs
  // "b" - bfs
  // "d" - dfs
  // "r" - generate a new maze
  public void onKeyEvent(String key) {
    if (key.equalsIgnoreCase("b") || key.equalsIgnoreCase("d") || key.equalsIgnoreCase("r")) {
      if (this.grid.get(0).get(0).correctPath) {
        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
            this.grid.get(i).get(j).correctPath = false;
            this.grid.get(i).get(j).hasBeenProcessed = false;
          }
        }
      }
    }
    if (key.equalsIgnoreCase("b")) {
      this.path = this.solveMaze(false);
    }
    if (key.equalsIgnoreCase("d")) {
      this.path = this.solveMaze(true);
    }
    if (key.equalsIgnoreCase("r")) {
      this.rand = new Random();
      this.grid = this.initGrid();
      this.edgesInTree = new ArrayList<Edge>();
      this.worklist = this.initEdges();
      this.representatives = this.initMap();
      this.kruskal();
      this.updateNeighborsList();
      this.cellSize = Math.min(1600 / rows, 960 / cols);
    }
  }

  // performs breadth-first search or depth-first search
  ArrayList<Vertex> solveMaze(boolean dfs) {
    HashMap<Vertex, Vertex> cameFromEdge = new HashMap<Vertex, Vertex>();
    // A Queue or a Stack, depending on the algorithm
    Deque<Vertex> worklist = new ArrayDeque<Vertex>();

    // initialize the worklist to contain the starting node (top left)
    worklist.addFirst(this.grid.get(0).get(0));

    // While(the worklist is not empty)
    while (worklist.size() != 0) {
      // Node next = the next item from the worklist
      Vertex next;
      if (dfs) {
        next = worklist.removeFirst();
      }
      else {
        next = worklist.removeLast();
      }
      // If (next has already been processed)
      if (next.hasBeenProcessed) {
        // discard it
      }
      // Else If (next is the target):
      else if (next.x == this.rows - 1 && next.y == this.cols - 1) {
        // return reconstruct(cameFromEdge, next);
        next.hasBeenProcessed = true;
        return this.reconstruct(cameFromEdge, next);

      }
      else {
        next.hasBeenProcessed = true;
        // for each neighbor n of next:
        for (int i = 0; i < next.neighbors.size(); i++) {
          // add n to the worklist
          // Record the edge (next->n) in the cameFromEdge map
          worklist.addFirst(next.neighbors.get(i));
          if (!cameFromEdge.containsKey(next.neighbors.get(i))) {
            cameFromEdge.put(next.neighbors.get(i), next);
          }
        }
      }
    }
    // unreachable code
    throw new IllegalStateException("Maze unsolvable");
  }

  // reconstructs the path that solves the maze
  ArrayList<Vertex> reconstruct(HashMap<Vertex, Vertex> cameFromEdge, Vertex next) {
    ArrayList<Vertex> path = new ArrayList<>();
    Vertex current = next;
    while (cameFromEdge.containsKey(current)) {
      if (path.contains(current)) {
        cameFromEdge.remove(current);
      }
      else {
        path.add(current);
        current = cameFromEdge.get(current);
      }
    }
    path.add(current); // add the source vertex to the path
    for (int i = 0; i < path.size(); i++) {
      path.get(i).correctPath = true;
    }
    return path;
  }

  // properly updates each vertex's list of neighbors to account for the walls of
  // the maze
  // EFFECT: adds the appropriate vertices to each Vertex's neighbors
  void updateNeighborsList() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        if (i < this.rows - 1 && this.edgesInTree.contains(this.grid.get(i).get(j).bottom)) {
          this.grid.get(i).get(j).neighbors.add(this.grid.get(i + 1).get(j));
          this.grid.get(i + 1).get(j).neighbors.add(this.grid.get(i).get(j));
        }
        if (j < this.cols - 1 && this.edgesInTree.contains(this.grid.get(i).get(j).right)) {
          this.grid.get(i).get(j).neighbors.add(this.grid.get(i).get(j + 1));
          this.grid.get(i).get(j + 1).neighbors.add(this.grid.get(i).get(j));
        }
      }
    }
  }

}

//to represent examples and tests relating to the maze
class ExamplesMaze {
  MazesWorld maxMaze;
  MazesWorld smallMaze;
  MazesWorld medMaze;
  MazesWorld superSmallMaze;

  // initializes the examples
  void initData() {
    maxMaze = new MazesWorld(100, 60);
    smallMaze = new MazesWorld(5, 5, new Random(100));
    medMaze = new MazesWorld(16, 10);
    superSmallMaze = new MazesWorld(3, 3, new Random(2));
  }

  // runs the game by calling big bang
  void testMaze(Tester t) {
    this.initData();
    this.maxMaze.bigBang(1610, 970, 0.5);
  }

  // tests the method initGrid in the class MazesWorld
  void testInitGrid(Tester t) {
    this.initData();
    // checks that the dimensions are correct
    t.checkExpect(this.smallMaze.grid.size(), 5);
    t.checkExpect(this.smallMaze.grid.get(0).size(), 5);

    // checks that each vertex contains the correct information about its location
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        t.checkExpect(this.smallMaze.grid.get(i).get(j).x, i);
        t.checkExpect(this.smallMaze.grid.get(i).get(j).y, j);
      }
    }
  }

  // tests the method initEdges in the class MazesWorld
  void testInitEdges(Tester t) {
    this.initData();
    // need to recall the method since the worklist gets modified later on
    this.smallMaze.worklist = this.smallMaze.initEdges();

    // checks that the size of the worklist is correct
    t.checkExpect(this.smallMaze.worklist.size(), 40);

    // checks that the weights are sorted properly
    for (int i = 1; i < 40; i++) {
      t.checkExpect(
          this.smallMaze.worklist.get(i).weight >= this.smallMaze.worklist.get(i - 1).weight, true);
    }
    t.checkExpect(this.smallMaze.grid.get(1).get(1).right.weight,
        this.smallMaze.grid.get(1).get(2).left.weight);
    t.checkExpect(this.smallMaze.grid.get(1).get(1).left.weight,
        this.smallMaze.grid.get(1).get(0).right.weight);
    t.checkExpect(this.smallMaze.grid.get(1).get(1).top.weight,
        this.smallMaze.grid.get(0).get(1).bottom.weight);
    t.checkExpect(this.smallMaze.grid.get(1).get(1).bottom.weight,
        this.smallMaze.grid.get(2).get(1).top.weight);
  }

  // tests the method initMap() in the class MazesWorld
  void testInitMap(Tester t) {
    this.initData();
    this.smallMaze.worklist = this.smallMaze.initEdges();
    this.smallMaze.representatives = this.smallMaze.initMap();
    t.checkExpect(this.smallMaze.representatives.size(), 25);
    for (int i = 0; i < this.smallMaze.worklist.size(); i++) {
      t.checkExpect(this.smallMaze.representatives.get(this.smallMaze.worklist.get(i).from),
          this.smallMaze.worklist.get(i).from);
    }
  }

  // tests the method find() in the class MazesWorld
  void testFind(Tester t) {
    this.initData();

    t.checkExpect(this.smallMaze.find(this.smallMaze.grid.get(0).get(0)),
        this.smallMaze.grid.get(4).get(4));
    t.checkExpect(this.smallMaze.find(this.smallMaze.grid.get(1).get(0)),
        this.smallMaze.grid.get(4).get(4));
    t.checkExpect(this.smallMaze.find(this.smallMaze.grid.get(2).get(0)),
        this.smallMaze.grid.get(4).get(4));
    t.checkExpect(this.smallMaze.find(this.smallMaze.grid.get(3).get(0)),
        this.smallMaze.grid.get(4).get(4));
    t.checkExpect(this.smallMaze.find(this.smallMaze.grid.get(4).get(0)),
        this.smallMaze.grid.get(4).get(4));
    t.checkExpect(this.smallMaze.find(this.smallMaze.grid.get(4).get(1)),
        this.smallMaze.grid.get(4).get(4));
    t.checkExpect(this.smallMaze.find(this.smallMaze.grid.get(4).get(2)),
        this.smallMaze.grid.get(4).get(4));
    t.checkExpect(this.smallMaze.find(this.smallMaze.grid.get(4).get(3)),
        this.smallMaze.grid.get(4).get(4));
  }

  // tests the method kruskals() in the class MazesWorld
  void testKruskals(Tester t) {
    this.initData();

    // check dimensions
    t.checkExpect(this.smallMaze.worklist.size(), 4);
    t.checkExpect(this.smallMaze.edgesInTree.size(), 24);

    // verifies that a path can be made from the start to the end
    t.checkExpect(this.smallMaze.edgesInTree.get(5).from.x, 0);
    t.checkExpect(this.smallMaze.edgesInTree.get(5).from.y, 0);
    t.checkExpect(this.smallMaze.edgesInTree.get(5).to.x, 1);
    t.checkExpect(this.smallMaze.edgesInTree.get(5).to.y, 0);

    t.checkExpect(this.smallMaze.edgesInTree.get(20).from.x, 1);
    t.checkExpect(this.smallMaze.edgesInTree.get(20).from.y, 0);
    t.checkExpect(this.smallMaze.edgesInTree.get(20).to.x, 2);
    t.checkExpect(this.smallMaze.edgesInTree.get(20).to.y, 0);

    t.checkExpect(this.smallMaze.edgesInTree.get(3).from.x, 2);
    t.checkExpect(this.smallMaze.edgesInTree.get(3).from.y, 0);
    t.checkExpect(this.smallMaze.edgesInTree.get(3).to.x, 3);
    t.checkExpect(this.smallMaze.edgesInTree.get(3).to.y, 0);

    t.checkExpect(this.smallMaze.edgesInTree.get(2).from.x, 3);
    t.checkExpect(this.smallMaze.edgesInTree.get(2).from.y, 0);
    t.checkExpect(this.smallMaze.edgesInTree.get(2).to.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(2).to.y, 0);

    t.checkExpect(this.smallMaze.edgesInTree.get(17).from.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(17).from.y, 0);
    t.checkExpect(this.smallMaze.edgesInTree.get(17).to.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(17).to.y, 1);

    t.checkExpect(this.smallMaze.edgesInTree.get(15).from.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(15).from.y, 1);
    t.checkExpect(this.smallMaze.edgesInTree.get(15).to.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(15).to.y, 2);

    t.checkExpect(this.smallMaze.edgesInTree.get(18).from.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(18).from.y, 2);
    t.checkExpect(this.smallMaze.edgesInTree.get(18).to.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(18).to.y, 3);

    t.checkExpect(this.smallMaze.edgesInTree.get(13).from.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(13).from.y, 3);
    t.checkExpect(this.smallMaze.edgesInTree.get(13).to.x, 4);
    t.checkExpect(this.smallMaze.edgesInTree.get(13).to.y, 4);
  }

  // tests the method makeScene in the class MazesWorld
  void testMakeScene(Tester t) {
    this.initData();
    WorldImage vertWallSS = new RectangleImage(1, 320, OutlineMode.SOLID, Color.black);
    WorldImage horizWallSS = new RectangleImage(320, 1, OutlineMode.SOLID, Color.black);
    WorldScene superSmallScene = new WorldScene(960, 960);
    superSmallScene.placeImageXY(new RectangleImage(320, 320, OutlineMode.SOLID, Color.green), 160,
        160);
    superSmallScene.placeImageXY(new RectangleImage(320, 320, OutlineMode.SOLID, Color.red), 800,
        800);
    superSmallScene.placeImageXY(vertWallSS, 320, 160);
    superSmallScene.placeImageXY(vertWallSS, 640, 160);
    superSmallScene.placeImageXY(vertWallSS, 640, 480);
    superSmallScene.placeImageXY(horizWallSS, 480, 640);

    t.checkExpect(this.superSmallMaze.makeScene(), superSmallScene);

    WorldImage vertWallS = new RectangleImage(1, 192, OutlineMode.SOLID, Color.black);
    WorldImage horizWallS = new RectangleImage(192, 1, OutlineMode.SOLID, Color.black);
    WorldScene smallScene = new WorldScene(960, 960);
    smallScene.placeImageXY(new RectangleImage(192, 192, OutlineMode.SOLID, Color.green), 96, 96);
    smallScene.placeImageXY(new RectangleImage(192, 192, OutlineMode.SOLID, Color.red), 864, 864);
    smallScene.placeImageXY(horizWallS, 96, 192);
    smallScene.placeImageXY(vertWallS, 192, 288);
    smallScene.placeImageXY(vertWallS, 192, 480);
    smallScene.placeImageXY(vertWallS, 192, 864);
    smallScene.placeImageXY(horizWallS, 288, 192);
    smallScene.placeImageXY(horizWallS, 288, 384);
    smallScene.placeImageXY(horizWallS, 480, 192);
    smallScene.placeImageXY(horizWallS, 480, 384);
    smallScene.placeImageXY(vertWallS, 576, 480);
    smallScene.placeImageXY(horizWallS, 480, 576);
    smallScene.placeImageXY(horizWallS, 480, 768);
    smallScene.placeImageXY(vertWallS, 576, 864);
    smallScene.placeImageXY(vertWallS, 768, 288);
    smallScene.placeImageXY(horizWallS, 672, 384);
    smallScene.placeImageXY(horizWallS, 672, 576);
    smallScene.placeImageXY(horizWallS, 672, 768);
    t.checkExpect(this.smallMaze.makeScene(), smallScene);
  }

  // tests the method onKeyEvent in the class MazesWorld
  void testOnKeyEvent(Tester t) {
    this.initData();
    this.superSmallMaze.onKeyEvent("b");
    t.checkExpect(this.superSmallMaze.grid.get(0).get(0).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(0).get(1).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(0).get(2).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(1).get(2).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(2).get(2).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(0).get(1).hasBeenProcessed, true);
    t.checkExpect(this.superSmallMaze.grid.get(1).get(1).hasBeenProcessed, true);

    this.superSmallMaze.onKeyEvent("d");
    t.checkExpect(this.superSmallMaze.grid.get(0).get(0).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(0).get(1).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(0).get(2).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(1).get(2).correctPath, true);
    t.checkExpect(this.superSmallMaze.grid.get(2).get(2).correctPath, true);

    this.superSmallMaze.onKeyEvent("r");
    t.checkExpect(this.superSmallMaze.grid.get(0).get(0).correctPath, false);
  }

  // tests the method solveMaze in the class MazesWorld
  void testSolveMaze(Tester t) {
    this.initData();
    ArrayList<Vertex> temp = this.superSmallMaze.solveMaze(true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(0).get(0)), true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(0).get(1)), true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(0).get(2)), true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(1).get(2)), true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(2).get(2)), true);
  }

  // tests the method reconstruct in the class MazesWorld
  void testReconstruct(Tester t) {
    this.initData();
    HashMap<Vertex, Vertex> tempMap = new HashMap<Vertex, Vertex>();
    tempMap.put(this.superSmallMaze.grid.get(2).get(2), this.superSmallMaze.grid.get(1).get(2));
    tempMap.put(this.superSmallMaze.grid.get(1).get(2), this.superSmallMaze.grid.get(0).get(2));
    tempMap.put(this.superSmallMaze.grid.get(0).get(2), this.superSmallMaze.grid.get(0).get(1));
    tempMap.put(this.superSmallMaze.grid.get(0).get(1), this.superSmallMaze.grid.get(0).get(0));
    ArrayList<Vertex> temp = this.superSmallMaze.reconstruct(tempMap,
        this.superSmallMaze.grid.get(2).get(2));
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(0).get(0)), true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(0).get(1)), true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(0).get(2)), true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(1).get(2)), true);
    t.checkExpect(temp.contains(this.superSmallMaze.grid.get(2).get(2)), true);
  }

  // tests the method updateNeighborsList in the class MazesWorld
  void testUpdateNeighborsList(Tester t) {
    this.initData();
    t.checkExpect(this.superSmallMaze.grid.get(0).get(0).neighbors
        .contains(this.superSmallMaze.grid.get(0).get(1)), true);
    t.checkExpect(this.superSmallMaze.grid.get(0).get(0).neighbors
        .contains(this.superSmallMaze.grid.get(1).get(0)), false);
    t.checkExpect(this.superSmallMaze.grid.get(2).get(2).neighbors
        .contains(this.superSmallMaze.grid.get(2).get(1)), true);
    t.checkExpect(this.superSmallMaze.grid.get(2).get(2).neighbors
        .contains(this.superSmallMaze.grid.get(1).get(2)), true);
  }
}