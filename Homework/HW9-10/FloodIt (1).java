import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import tester.Tester;
import javalib.impworld.*;
import java.awt.Color;
import java.net.URL;

import javalib.worldimages.*;

//to represent either a tile or the border
abstract class ACell {
  ACell left;
  ACell top;
  ACell right;
  ACell bottom;

  // labels the appropriate Cells as flooded at the beginning of the game
  abstract void initFlood(Color color);

  // For each cell adjacent to this cell, it will update the list of cells that
  // need
  // to be flooded if the circumstances are appropriate
  // EFFECT: adds Cells to nextTickWillFlood if that cell should be flooded
  abstract void updateFlood(Color color, ArrayList<Cell> nextTickWillFlood,
      ArrayList<Cell> flooded);

  // helper method for updateFlood, checks if this cell should be flooded,
  // and adds it to the list of cells to be flooded accordingly
  // EFFECT: adds this Cell is nextTickWillFlood if appropriate
  abstract void appendFloodedList(Color color, ArrayList<Cell> nextTickWillFlood,
      ArrayList<Cell> flooded);

}

//Represents a single square of the game area
class Cell extends ACell {
  // In logical coordinates, with the origin at the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;

  Cell(int x, int y, Color color, boolean flooded) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = new Border();
    this.top = new Border();
    this.right = new Border();
    this.bottom = new Border();
  }

  Cell(int x, int y, Color color, boolean flooded, ACell left, ACell top, ACell right,
      ACell bottom) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = left;
    this.left.right = this;
    this.top = top;
    this.top.bottom = this;
    this.right = right;
    this.right.left = this;
    this.bottom = bottom;
    this.bottom.top = this;
  }

  // produces an image of this cell
  WorldImage drawCell() {
    return new RectangleImage(25, 25, OutlineMode.SOLID, this.color);
  }

  // labels the appropriate Cells as flooded at the beginning of the game
  public void initFlood(Color color) {
    if (this.color.equals(color) && !this.flooded) {
      this.flooded = true;
      this.left.initFlood(color);
      this.top.initFlood(color);
      this.right.initFlood(color);
      this.bottom.initFlood(color);
    }
  }

  // For each cell adjacent to this cell, it will update the list of cells that
  // need
  // to be flooded if the circumstances are appropriate
  // EFFECT: adds Cells to nextTickWillFlood if that cell should be flooded
  // adds this Cell to the list of cells that have already been flooded
  void updateFlood(Color color, ArrayList<Cell> nextTickWillFlood, ArrayList<Cell> flooded) {
    if (!flooded.contains(this)) {
      this.flooded = true;
      this.color = color;
      flooded.add(this);
      this.left.appendFloodedList(color, nextTickWillFlood, flooded);
      this.top.appendFloodedList(color, nextTickWillFlood, flooded);
      this.right.appendFloodedList(color, nextTickWillFlood, flooded);
      this.bottom.appendFloodedList(color, nextTickWillFlood, flooded);
    }
  }

  // helper method for updateFlood, checks if this cell should be flooded,
  // and adds it to the list of cells to be flooded accordingly
  // EFFECT: adds this Cell is nextTickWillFlood if appropriate
  void appendFloodedList(Color color, ArrayList<Cell> nextTickWillFlood, ArrayList<Cell> flooded) {
    if (!flooded.contains(this) && this.color == color || this.flooded) {
      nextTickWillFlood.add(this);
    }
  }
}

//represents a border of the game, connected to a cell
class Border extends ACell {
  // labels the appropriate Cells as flooded at the beginning of the game
  public void initFlood(Color color) {
    // contains no code since you cannot flood a border

  }

  // For each cell adjacent to this cell, it will update the list of cells that
  // need
  // to be flooded if the circumstances are appropriate
  // EFFECT: adds Cells to nextTickWillFlood if that cell should be flooded
  void updateFlood(Color color, ArrayList<Cell> floodedList, ArrayList<Cell> flooded) {
    // contains no code since you cannot flood a border

  }

  // helper method for updateFlood, checks if this cell should be flooded,
  // and adds it to the list of cells to be flooded accordingly
  // EFFECT: adds this Cell is nextTickWillFlood if appropriate
  void appendFloodedList(Color color, ArrayList<Cell> floodedList, ArrayList<Cell> flooded) {
    // contains no code since you cannot flood a border

  }

}

//to represent a game of FloodIt
class FloodItWorld extends World {
  // All the cells of the game
  // An ArrayList of ArrayLists is used to represent a "grid."
  ArrayList<ArrayList<Cell>> board;

  // represents the current list of cells that need to be flooded
  ArrayList<Cell> floodList;

  // represents the current list of cells that have already been updated
  ArrayList<Cell> flooded;

  // number of tiles/colors on the board
  int size;
  int colors;

  int guesses;
  int maxGuesses;
  int timer;
  boolean isFlooding;
  Color curColor;
  Random rand;

  boolean gameWon;
  boolean gameLost;

  Clip clip;

  // used to play an actual game
  FloodItWorld(int size, int colors) {
    this.rand = new Random();
    this.timer = 0;
    this.initData(size, colors);
  }

  // Helper constructor, used to run the game without music
  FloodItWorld(int size, int colors, boolean dontPlayMusic) {
    this.rand = new Random();
    if (dontPlayMusic) {
      this.timer = 3;
    }
    this.initData(size, colors);
  }

  // Helper constructor, used for testing a random creation of the board
  FloodItWorld(int size, int colors, Random rand) {
    this.rand = rand;
    this.timer = 0;
    this.initData(size, colors);
  }

  // Helper constructor, used for testing a random creation of the board, and
  // without music
  FloodItWorld(int size, int colors, Random rand, Boolean dontPlayMusic) {
    this.rand = rand;
    if (dontPlayMusic) {
      this.timer = 3;
    }
    this.initData(size, colors);
  }

  // used to reduce duplicate code
  // initializes some parts of the FloodItWorld
  void initData(int size, int colors) {
    // restrictions on the size of the board
    if (size <= 2) {
      this.size = 2;
    }
    else if (size >= 26) {
      this.size = 26;
    }
    else {
      while (size % 4 != 2) {
        size++;
      }
      this.size = size;
    }
    // restrictions on the color of the board
    if (colors <= 3) {
      this.colors = 3;
    }
    else if (colors >= 8) {
      this.colors = 8;
    }
    else {
      this.colors = colors;
    }
    this.board = this.initBoard();
    this.floodList = new ArrayList<Cell>();
    this.guesses = 0;
    this.maxGuesses = (int) Math
        .ceil(Math.log(Math.pow(this.colors, this.size) / Math.log(this.colors + 1))) - 1;
    this.isFlooding = false;
    this.curColor = Color.black;
    this.flooded = new ArrayList<Cell>();
    this.gameWon = false;
    this.gameLost = false;
    try {
      this.clip = AudioSystem.getClip();
    }
    catch (LineUnavailableException e) {
      e.printStackTrace();
    }

  }

  // initializes the FloodIt board with random colors
  ArrayList<ArrayList<Cell>> initBoard() {
    ArrayList<ArrayList<Cell>> tempBoard = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < this.size; i++) {
      ArrayList<Cell> line = new ArrayList<Cell>();
      for (int j = 0; j < this.size; j++) {
        Color givenColor = Color.black;
        int colorGen = this.rand.nextInt(colors);
        // converts between a number and a color
        if (colorGen == 0) {
          givenColor = Color.cyan;
        }
        else if (colorGen == 1) {
          givenColor = Color.yellow;
        }
        else if (colorGen == 2) {
          givenColor = Color.magenta;
        }
        else if (colorGen == 3) {
          givenColor = Color.pink;
        }
        else if (colorGen == 4) {
          givenColor = Color.red;
        }
        else if (colorGen == 5) {
          givenColor = Color.orange;
        }
        else if (colorGen == 6) {
          givenColor = Color.green;
        }
        else if (colorGen == 7) {
          givenColor = Color.blue;
        }
        // this section ensures that each Cell is "connected" with its adjacent cells

        // first cell generated cannot have any adjacent cells
        if (i == 0 && j == 0) {
          line.add(new Cell(i, j, givenColor, false));
        }
        // in each new line, the first cell won't have a cell to its left, but will have
        // one above
        else if (j == 0) {
          line.add(new Cell(i, j, givenColor, false, new Border(), tempBoard.get(i - 1).get(0),
              new Border(), new Border()));
        }
        // in only the first line, each cell will have a cell to its left, but not above
        else if (i == 0 && j != 0) {
          line.add(new Cell(i, j, givenColor, false, line.get(j - 1), new Border(), new Border(),
              new Border()));
        }
        // in any other instance, each cell will have a cell to its left and one above
        else {
          line.add(new Cell(i, j, givenColor, false, line.get(j - 1), tempBoard.get(i - 1).get(j),
              new Border(), new Border()));
        }
      }
      tempBoard.add(line);
    }
    tempBoard.get(0).get(0).initFlood(tempBoard.get(0).get(0).color);
    return tempBoard;

  }

  // displays the board at any given moment
  public WorldScene makeScene() {
    if (this.gameWon) {
      return this.produceEndScene("You won!");
    }
    else if (this.gameLost) {
      return this.produceEndScene("You lost.");
    }
    else {
      WorldScene background = new WorldScene(750, 750);
      WorldImage colors = new EmptyImage();
      for (int i = 0; i < size; i++) {
        WorldImage currentLine = new EmptyImage();
        for (int j = 0; j < size; j++) {
          currentLine = new BesideImage(currentLine, this.board.get(i).get(j).drawCell());
        }
        colors = new AboveImage(colors, currentLine);
      }
      colors = new AboveImage(new TextImage("Guesses: " + this.guesses + "/" + this.maxGuesses
          + "   Timer: " + (this.timer / 35) + "s", 25, FontStyle.BOLD, Color.black), colors);
      background.placeImageXY(colors, 375, 375);
      return background;
    }
  }

  // handles ticking of the clock and updating the world if needed
  public void onTick() {
    this.timer++;
    if (this.timer == 2) {
      this.playMusic();
    }
    if (this.isFlooding) {

      if (this.floodList.size() != 0) {
        ArrayList<Cell> nextTickWillFlood = new ArrayList<Cell>();
        for (int i = 0; i < this.floodList.size(); i++) {
          this.floodList.get(i).updateFlood(this.curColor, nextTickWillFlood, this.flooded);
        }
        this.floodList = nextTickWillFlood;
      }
      else {
        if (this.hasWon()) {
          this.gameWon = true;
        }
        else if (this.guesses == this.maxGuesses) {
          this.gameLost = true;
        }
        this.floodList = new ArrayList<Cell>();
        this.flooded = new ArrayList<Cell>();
        this.isFlooding = false;
      }
    }
  }

  // handles mouse clicks and is given the mouse location
  public void onMouseClicked(Posn pos) {
    if (!this.isFlooding) {
      int y = ((pos.y - 388) + (25 * (this.size / 2))) / 25;
      int x = ((pos.x - 375) + (25 * (this.size / 2))) / 25;
      if (!(x < 0 || y < 0 || x >= this.size || y >= this.size)) {
        this.curColor = this.board.get(y).get(x).color;
        if (!this.curColor.equals(board.get(0).get(0).color)) {
          this.floodList.add(board.get(0).get(0));
          this.isFlooding = true;
          this.guesses++;
        }
      }
    }
  }

  // handles keyboard inputs
  // EFFECT: rests the fields to their initial states as appropriate
  public void onKeyEvent(String key) {
    if (key.equalsIgnoreCase("r")) {
      this.rand = new Random();
      this.board = this.initBoard();
      this.floodList = new ArrayList<Cell>();
      this.guesses = 0;
      this.timer = 2;
      this.isFlooding = false;
      this.curColor = Color.black;
      this.flooded = new ArrayList<Cell>();
      this.gameLost = false;
      this.gameWon = false;
    }
  }

  // determines if the player has won the game
  boolean hasWon() {
    Color neededColor = this.board.get(0).get(0).color;
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        if (!this.board.get(i).get(j).color.equals(neededColor)) {
          return false;
        }
      }
    }
    return true;
  }

  // produces the end scene when the player has either won or lost
  WorldScene produceEndScene(String msg) {
    WorldScene background = new WorldScene(750, 750);
    WorldImage gameOver = new TextImage(msg, 65, FontStyle.BOLD, Color.BLACK);
    background.placeImageXY(gameOver, 375, 325);
    return background;
  }

  // I think it should be mentioned that we clearly did not learn how to do this
  // in class.
  // I have completed projects in the past that have included music, and had
  // learned how
  // to implement this via a youtube tutorial
  // A volume warning is advised, the song tends to play on the louder side.
  // The loading delay at the start is mostly caused by this method
  // It should be noted that this method causes a lot of lag,
  // In order to "disable" the method, comment line 344
  // plays music in the background
  void playMusic() {
    try {
      // other music:
      // "https://www.vgmusic.com/music/console/nintendo/n64/Z64gerud.mid"
      URL link = new URL("https://www.vgmusic.com/new-files/MKWCoconutMall.mid");
      AudioInputStream audio = AudioSystem.getAudioInputStream(link);
      // accounts for delay at start of song
      clip = AudioSystem.getClip();
      audio.skip(200000);

      clip.open(audio);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      clip.start();
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }
}

//examples to represent and test FloodItWorld and the classes that extend ACell
class ExamplesFlood {
  FloodItWorld testBoard;
  FloodItWorld testBoardLarge;
  FloodItWorld testBoardSmall;
  FloodItWorld testBoardMedium;
  FloodItWorld testBoardMedium2;

  // initalizes the examples
  void initFlood() {
    // the field "true" indicates that it will not play music
    // remove the field to play music (make sure the other examples
    // contain the field true, otherwise music will overlap

    // size 3 rounds up to 6, 10 colors rounds down to 8.
    testBoard = new FloodItWorld(3, 10, true);
    testBoardLarge = new FloodItWorld(26, 5);
    testBoardSmall = new FloodItWorld(2, 10, new Random(2), true);
    testBoardMedium = new FloodItWorld(6, 8, new Random(2), true);
    testBoardMedium2 = new FloodItWorld(6, 8, new Random(7), true);
  }

  // runs the game by calling big bang
  void testFloodIt(Tester t) {
    this.initFlood();
    int sceneSize = 750;
    testBoardLarge.bigBang(sceneSize, sceneSize, 0.02);
  }

  // tests the method initBoard() in the class FloodItWorld
  void testInitBoard(Tester t) {
    this.initFlood();
    // tests the dimensions of the board
    t.checkExpect(this.testBoard.board.size(), 6);
    t.checkExpect(this.testBoard.board.get(0).size(), 6);

    // checks that the board contains the appropriate amount of cells in the correct
    // places
    for (int i = 0; i < this.testBoard.size; i++) {
      for (int j = 0; j < this.testBoard.size; j++) {
        t.checkExpect(this.testBoard.board.get(i).get(j).x, i);
        t.checkExpect(this.testBoard.board.get(i).get(j).y, j);
      }
    }
  }

  // tests the method makeScene() in the class FloodItWorld
  void testMakeScene(Tester t) {
    this.initFlood();
    WorldImage testBoardImage = new AboveImage(
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan)));
    testBoardImage = new AboveImage(
        new TextImage("Guesses: 0/3   Timer: 0s", 25, FontStyle.BOLD, Color.black), testBoardImage);
    WorldScene testBoardScene = new WorldScene(750, 750);
    testBoardScene.placeImageXY(testBoardImage, 375, 375);

    WorldImage testBoardMediumImage = new AboveImage(
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.yellow),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.yellow)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta)));
    testBoardMediumImage = new AboveImage(
        new TextImage("Guesses: 0/11   Timer: 0s", 25, FontStyle.BOLD, Color.black),
        testBoardMediumImage);
    WorldScene testBoardMediumScene = new WorldScene(750, 750);
    testBoardMediumScene.placeImageXY(testBoardMediumImage, 375, 375);

    t.checkExpect(this.testBoardSmall.makeScene(), testBoardScene);
    t.checkExpect(this.testBoardMedium.makeScene(), testBoardMediumScene);
  }

  // tests the method initFlood in the class FloodItWorld
  void testInitFlood(Tester t) {
    this.initFlood();
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).flooded, false);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).flooded, false);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).flooded, false);

    t.checkExpect(this.testBoardMedium2.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(1).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(2).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(3).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(4).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(5).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(0).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(1).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(2).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(3).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(4).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(5).flooded, false);

    for (int i = 2; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        t.checkExpect(this.testBoardMedium2.board.get(i).get(j).flooded, false);
      }
    }
  }

  // this method tests the the methods OnTick(), OnMouseClicked(), and hasWon()
  // in the class FloodItWorld
  // in essence, this was done so since these methods work hand in hand with each
  // other
  // and are very dependent on each other. Hence, testing them together makes
  // sense and is more efficient.
  void testOnTickAndOnMouseClickedAndHasWon(Tester t) {
    this.initFlood();

    // test data before mutation
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.orange);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).color, Color.cyan);
    t.checkExpect(this.testBoardSmall.isFlooding, false);
    t.checkExpect(this.testBoardSmall.hasWon(), false);

    // clicks off the board, should do nothing
    this.testBoardSmall.onMouseClicked(new Posn(600, 600));
    t.checkExpect(this.testBoardSmall.isFlooding, false);

    // first click
    this.testBoardSmall.onMouseClicked(new Posn(386, 376));
    t.checkExpect(this.testBoardSmall.isFlooding, true);
    t.checkExpect(
        this.testBoardSmall.floodList.size() == 1
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(0)),
        true);

    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).color, Color.cyan);

    t.checkExpect(
        this.testBoardSmall.floodList.size() == 1
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(1)),
        true);

    this.testBoardSmall.onTick();
    this.testBoardSmall.clip.stop();
    t.checkExpect(
        this.testBoardSmall.floodList.size() == 1
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(0)),
        true);

    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.floodList.size(), 0);
    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.isFlooding, false);
    t.checkExpect(this.testBoardSmall.hasWon(), false);

    // second click
    this.testBoardSmall.onMouseClicked(new Posn(363, 401));
    t.checkExpect(
        this.testBoardSmall.floodList.size() == 1
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).color, Color.cyan);

    this.testBoardSmall.onTick();
    t.checkExpect(
        this.testBoardSmall.floodList.size() == 2
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(1))
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(1).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).color, Color.cyan);

    this.testBoardSmall.onTick();
    t.checkExpect(
        this.testBoardSmall.floodList.size() == 2
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).color, Color.cyan);

    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.floodList.size(), 0);
    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.isFlooding, false);
    t.checkExpect(this.testBoardSmall.hasWon(), false);

    // third click
    this.testBoardSmall.onMouseClicked(new Posn(391, 404));
    t.checkExpect(
        this.testBoardSmall.floodList.size() == 1
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).color, Color.cyan);

    this.testBoardSmall.onTick();
    t.checkExpect(
        this.testBoardSmall.floodList.size() == 2
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(1))
            && this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(1).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.cyan);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).color, Color.cyan);

    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.floodList.size(), 4);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.cyan);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).color, Color.cyan);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.cyan);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).color, Color.cyan);

    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.floodList.size(), 2);
    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.floodList.size(), 0);
    this.testBoardSmall.onTick();
    t.checkExpect(this.testBoardSmall.isFlooding, false);
    t.checkExpect(this.testBoardSmall.hasWon(), true);

  }

  // tests the method onKeyEvent() in the class FloodItWorld
  void testOnKeyEvent(Tester t) {
    this.initFlood();

    // test before mutation
    t.checkExpect(this.testBoardSmall.floodList, new ArrayList<Cell>());
    t.checkExpect(this.testBoardSmall.guesses, 0);
    t.checkExpect(this.testBoardSmall.timer, 3);
    t.checkExpect(this.testBoardSmall.isFlooding, false);
    t.checkExpect(this.testBoardSmall.curColor, Color.black);
    t.checkExpect(this.testBoardSmall.gameLost, false);
    t.checkExpect(this.testBoardSmall.gameWon, false);

    this.testBoardSmall.onMouseClicked(new Posn(386, 376));
    this.testBoardSmall.onTick();
    this.testBoardSmall.onTick();
    this.testBoardSmall.clip.stop();
    this.testBoardSmall.onTick();

    t.checkExpect(this.testBoardSmall.floodList, new ArrayList<Cell>());
    t.checkExpect(this.testBoardSmall.guesses, 1);
    t.checkExpect(this.testBoardSmall.timer, 6);
    t.checkExpect(this.testBoardSmall.isFlooding, true);
    t.checkExpect(this.testBoardSmall.curColor, Color.magenta);
    t.checkExpect(this.testBoardSmall.gameLost, false);
    t.checkExpect(this.testBoardSmall.gameWon, false);

    // nothing should happen
    this.testBoardSmall.onKeyEvent("k");
    t.checkExpect(this.testBoardSmall.floodList, new ArrayList<Cell>());
    t.checkExpect(this.testBoardSmall.guesses, 1);
    t.checkExpect(this.testBoardSmall.timer, 6);
    t.checkExpect(this.testBoardSmall.isFlooding, true);
    t.checkExpect(this.testBoardSmall.curColor, Color.magenta);
    t.checkExpect(this.testBoardSmall.gameLost, false);
    t.checkExpect(this.testBoardSmall.gameWon, false);

    this.testBoardSmall.onKeyEvent("r");
    t.checkExpect(this.testBoardSmall.floodList, new ArrayList<Cell>());
    t.checkExpect(this.testBoardSmall.guesses, 0);
    t.checkExpect(this.testBoardSmall.timer, 2);
    t.checkExpect(this.testBoardSmall.isFlooding, false);
    t.checkExpect(this.testBoardSmall.curColor, Color.black);
    t.checkExpect(this.testBoardSmall.gameLost, false);
    t.checkExpect(this.testBoardSmall.gameWon, false);

  }

  // tests the method playMusic() in the class FloodItWorld
  void testPlayMusic(Tester t) {
    this.initFlood();

    t.checkExpect(this.testBoardMedium2.clip.isRunning(), false);
    this.testBoardMedium2.playMusic();
    t.checkExpect(this.testBoardMedium2.clip.isOpen(), true);
    this.testBoardMedium2.clip.stop();
  }

  // tests the timer implementation in the class FloodItWorld
  void testTheTimer(Tester t) {
    this.initFlood();

    t.checkExpect(this.testBoardSmall.timer, 3);
    WorldImage testBoardImage = new AboveImage(
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan)));
    testBoardImage = new AboveImage(
        new TextImage("Guesses: 0/3   Timer: 0s", 25, FontStyle.BOLD, Color.black), testBoardImage);
    WorldScene testBoardScene = new WorldScene(750, 750);
    for (int i = 0; i < 32; i++) {
      this.testBoardSmall.onTick();
    }
    testBoardImage = new AboveImage(
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan)));
    testBoardImage = new AboveImage(
        new TextImage("Guesses: 0/3   Timer: 1s", 25, FontStyle.BOLD, Color.black), testBoardImage);
    testBoardScene = new WorldScene(750, 750);
    testBoardScene.placeImageXY(testBoardImage, 375, 375);
    t.checkExpect(this.testBoardSmall.timer, 35);
    t.checkExpect(this.testBoardSmall.makeScene(), testBoardScene);

    t.checkExpect(this.testBoardMedium.timer, 3);
    WorldImage testBoardMediumImage = new AboveImage(
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.yellow),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.yellow)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta)));
    testBoardMediumImage = new AboveImage(
        new TextImage("Guesses: 0/11   Timer: 0s", 25, FontStyle.BOLD, Color.black),
        testBoardMediumImage);
    WorldScene testBoardMediumScene = new WorldScene(750, 750);
    testBoardMediumScene.placeImageXY(testBoardMediumImage, 375, 375);
    t.checkExpect(this.testBoardMedium.makeScene(), testBoardMediumScene);
    for (int i = 0; i < 80; i++) {
      this.testBoardMedium.onTick();
    }
    t.checkExpect(this.testBoardMedium.timer, 83);
    testBoardMediumImage = new AboveImage(
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.yellow),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.yellow)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.green)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.green),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.pink),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.red),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta)));
    testBoardMediumImage = new AboveImage(
        new TextImage("Guesses: 0/11   Timer: 2s", 25, FontStyle.BOLD, Color.black),
        testBoardMediumImage);
    testBoardMediumScene = new WorldScene(750, 750);
    testBoardMediumScene.placeImageXY(testBoardMediumImage, 375, 375);
    t.checkExpect(this.testBoardMedium.makeScene(), testBoardMediumScene);
  }

  // tests the on screen score feature
  void testGuessing(Tester t) {
    this.initFlood();

    t.checkExpect(this.testBoardSmall.guesses, 0);
    this.testBoardSmall.guesses += 2;
    WorldImage testBoardImage = new AboveImage(
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta)),
        new BesideImage(new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan)));
    testBoardImage = new AboveImage(
        new TextImage("Guesses: 2/3   Timer: 0s", 25, FontStyle.BOLD, Color.black), testBoardImage);
    WorldScene testBoardScene = new WorldScene(750, 750);
    testBoardScene.placeImageXY(testBoardImage, 375, 375);
    t.checkExpect(this.testBoardSmall.makeScene(), testBoardScene);
    this.testBoardSmall.guesses++;
    this.testBoardSmall.isFlooding = true;
    this.testBoardSmall.onTick();

    t.checkExpect(this.testBoardSmall.gameLost, true);
    t.checkExpect(this.testBoardSmall.produceEndScene("You lost."),
        this.testBoardSmall.makeScene());
  }

  // tests the method produceEndScene in class FloodItWorld
  void testProduceEndScene(Tester t) {
    this.initFlood();

    WorldScene backgroundWon = new WorldScene(750, 750);
    WorldImage gameOverWon = new TextImage("You won!", 65, FontStyle.BOLD, Color.BLACK);
    backgroundWon.placeImageXY(gameOverWon, 375, 325);

    t.checkExpect(this.testBoard.produceEndScene("You won!"), backgroundWon);

    WorldScene backgroundLost = new WorldScene(750, 750);
    WorldImage gameOverLost = new TextImage("You lost.", 65, FontStyle.BOLD, Color.BLACK);
    backgroundLost.placeImageXY(gameOverLost, 375, 325);

    t.checkExpect(this.testBoard.produceEndScene("You lost."), backgroundLost);
  }

  // tests the method drawCell() in the class Cell
  void testDrawCell(Tester t) {
    this.initFlood();
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).drawCell(),
        new RectangleImage(25, 25, OutlineMode.SOLID, Color.orange));
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).drawCell(),
        new RectangleImage(25, 25, OutlineMode.SOLID, Color.magenta));
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).drawCell(),
        new RectangleImage(25, 25, OutlineMode.SOLID, Color.blue));
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).drawCell(),
        new RectangleImage(25, 25, OutlineMode.SOLID, Color.cyan));
  }

  // tests the method initFlood() in the classes that extend ACell
  void testInitFloodCell(Tester t) {
    this.initFlood();

    // unflood the board for testing purposes (creating the board floods it)
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 6; j++) {
        this.testBoardMedium2.board.get(i).get(j).flooded = false;
      }
    }
    this.testBoardMedium2.board.get(0).get(0).initFlood(Color.orange);

    t.checkExpect(this.testBoardSmall.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).flooded, false);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).flooded, false);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).flooded, false);

    t.checkExpect(this.testBoardMedium2.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(1).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(2).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(3).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(4).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(0).get(5).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(0).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(1).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(2).flooded, true);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(3).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(4).flooded, false);
    t.checkExpect(this.testBoardMedium2.board.get(1).get(5).flooded, false);

    for (int i = 2; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        t.checkExpect(this.testBoardMedium2.board.get(i).get(j).flooded, false);
      }
    }

    // unflood the board for testing purposes once again
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 6; j++) {
        this.testBoardMedium2.board.get(i).get(j).flooded = false;
      }
    }
    ACell border = this.testBoardMedium2.board.get(0).get(0).left;
    border.initFlood(Color.orange);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 6; j++) {
        t.checkExpect(this.testBoardMedium2.board.get(i).get(j).flooded, false);
      }
    }
  }

  // tests the method updateFlood() in the classes that extend ACell
  void testUpdateFlood(Tester t) {
    this.initFlood();

    // check values before mutation
    t.checkExpect(this.testBoardSmall.floodList, new ArrayList<Cell>());
    t.checkExpect(this.testBoardSmall.flooded, new ArrayList<Cell>());
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.orange);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).flooded, false);

    this.testBoardSmall.board.get(0).get(0).updateFlood(Color.magenta,
        this.testBoardSmall.floodList, this.testBoardSmall.flooded);

    t.checkExpect(this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(1)),
        true);
    t.checkExpect(this.testBoardSmall.flooded.contains(this.testBoardSmall.board.get(0).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).flooded, false);

    this.testBoardSmall.board.get(0).get(1).updateFlood(Color.magenta,
        this.testBoardSmall.floodList, this.testBoardSmall.flooded);

    t.checkExpect(this.testBoardSmall.floodList.size(), 2);
    t.checkExpect(this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(1)),
        true);
    t.checkExpect(this.testBoardSmall.flooded.size(), 2);
    t.checkExpect(this.testBoardSmall.flooded.contains(this.testBoardSmall.board.get(0).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.flooded.contains(this.testBoardSmall.board.get(0).get(1)),
        true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).flooded, false);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).flooded, false);

    // nothing should change
    ACell border = this.testBoard.board.get(0).get(0).left;
    border.updateFlood(Color.magenta, this.testBoardSmall.floodList, this.testBoardSmall.flooded);

    t.checkExpect(this.testBoardSmall.floodList.size(), 2);
    t.checkExpect(this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.floodList.contains(this.testBoardSmall.board.get(0).get(1)),
        true);
    t.checkExpect(this.testBoardSmall.flooded.size(), 2);
    t.checkExpect(this.testBoardSmall.flooded.contains(this.testBoardSmall.board.get(0).get(0)),
        true);
    t.checkExpect(this.testBoardSmall.flooded.contains(this.testBoardSmall.board.get(0).get(1)),
        true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.magenta);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).flooded, false);
    t.checkExpect(this.testBoardSmall.board.get(1).get(1).flooded, false);
  }

  // tests the method appendFloodList() in the classes that extend ACell
  void testAppendFloodList(Tester t) {
    this.initFlood();

    // check values before mutation
    t.checkExpect(this.testBoardSmall.floodList, new ArrayList<Cell>());
    t.checkExpect(this.testBoardSmall.flooded, new ArrayList<Cell>());
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).color, Color.orange);
    t.checkExpect(this.testBoardSmall.board.get(1).get(0).color, Color.blue);
    t.checkExpect(this.testBoardSmall.board.get(0).get(0).flooded, true);
    t.checkExpect(this.testBoardSmall.board.get(0).get(1).flooded, false);
    this.testBoardSmall.board.get(0).get(0).appendFloodedList(Color.magenta,
        this.testBoardSmall.floodList, this.testBoardSmall.flooded);
    t.checkExpect(this.testBoardSmall.floodList.size(), 1);
    this.testBoardSmall.board.get(0).get(1).appendFloodedList(Color.magenta,
        this.testBoardSmall.floodList, this.testBoardSmall.flooded);
    t.checkExpect(this.testBoardSmall.floodList.size(), 2);
    this.testBoardSmall.board.get(1).get(0).appendFloodedList(Color.magenta,
        this.testBoardSmall.floodList, this.testBoardSmall.flooded);
    t.checkExpect(this.testBoardSmall.floodList.size(), 2);

    ACell border = this.testBoardSmall.board.get(0).get(0).left;
    border.appendFloodedList(Color.magenta, this.testBoardSmall.floodList,
        this.testBoardSmall.flooded);
    t.checkExpect(this.testBoardSmall.floodList.size(), 2);

  }

}