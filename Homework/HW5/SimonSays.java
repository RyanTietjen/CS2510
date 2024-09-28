import java.awt.Color;
import java.util.Random;

import tester.Tester;
import javalib.funworld.*;
import javalib.worldimages.*;

// Represents a game of Simon Says
class SimonWorld extends World {
  // fields needed to keep track of the state of the world
  boolean generationMode;
  Random rand;
  int colorFlashed;
  ILoButton correctList;
  int counter;

  // Constructor for a game that produces the same sequence every time
  SimonWorld(boolean generationMode, Random rand, int colorFlashed, ILoButton correctList,
      int counter) {
    this.generationMode = generationMode;
    this.rand = rand;
    this.colorFlashed = colorFlashed;
    this.correctList = correctList;
    this.counter = counter;
  }

  // Constructor for a game that produces a truly random game
  SimonWorld(boolean generationMode, int colorFlashed, ILoButton correctList, int counter) {
    this.generationMode = generationMode;
    this.rand = new Random();
    this.colorFlashed = colorFlashed;
    this.correctList = correctList;
    this.counter = counter;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.generationMode ... -- boolean
   *  ... this.rand ...           -- Random
   *  ... this.colorFlashed ...   -- int
   *  ... this.correctList ...    -- ILoButton
   *  ... this.counter ...        --int
   *  
   *  Methods:
   *  ... this.makeScene() ...           --WorldScene
   *  ... this.onTick() ...              --World
   *  ... this.lastScene(String) ...     --WorldScene
   *  ... this.onMouseClicked(Posn) ...  --WorldScene
   */

  // Draw the current state of the world
  public WorldScene makeScene() {
    WorldScene background = new WorldScene(500, 500);
    WorldImage redButton = new Button(Color.red, 250, 250).drawDark();
    WorldImage greenButton = new Button(Color.green, 250, 250).drawDark();
    WorldImage yellowButton = new Button(Color.yellow, 250, 250).drawDark();
    WorldImage blueButton = new Button(Color.blue, 250, 250).drawDark();
    // Brightens one button depending on the current flashing sequence/user clicks
    if (this.colorFlashed == 0) {
      redButton = new Button(Color.red, 250, 250).drawLit();
    }
    else if (this.colorFlashed == 1) {
      greenButton = new Button(Color.green, 250, 250).drawLit();
    }
    else if (this.colorFlashed == 2) {
      yellowButton = new Button(Color.yellow, 250, 250).drawLit();
    }
    else if (this.colorFlashed == 3) {
      blueButton = new Button(Color.blue, 250, 250).drawLit();
    }
    WorldImage colors = new AboveImage(new BesideImage(redButton, greenButton),
        new BesideImage(yellowButton, blueButton));
    return background.placeImageXY(colors, 250, 250);
  }

  // handles ticking of the clock and updating the world if needed
  public World onTick() {
    // code for generating/updating the correct list of buttons
    if (this.generationMode) {
      // (re)produces the correct sequence of buttons
      if (this.counter != this.correctList.length() - 1 && this.counter > -2) {
        // if there is a light currently dim, make the next button in the sequence lit
        if (this.colorFlashed == -1) {
          return new SimonWorld(true, this.rand, this.correctList.get(counter + 1).getNumber(),
              this.correctList, this.counter + 1);
        }
        // if there is a light currently bright, make it dim on the next (this) tick
        else {
          return new SimonWorld(true, this.rand, -1, this.correctList, this.counter);
        }
      }
      // generate a new button to the end of the list
      else if (this.counter == this.correctList.length() - 1) {
        // makes sure the no buttons are lit
        if (this.colorFlashed != -1) {
          return new SimonWorld(true, this.rand, -1, this.correctList, this.counter);
        }
        // actual process of generating a new button
        // 0 = red
        // 1 = green
        // 2 = yellow
        // 3 = blue
        int num = this.rand.nextInt(4);
        if (num == 0) {
          this.correctList = this.correctList.addToEnd(new Button(Color.red, 250, 250));
        }
        else if (num == 1) {
          this.correctList = this.correctList.addToEnd(new Button(Color.green, 250, 250));
        }
        else if (num == 2) {
          this.correctList = this.correctList.addToEnd(new Button(Color.yellow, 250, 250));
        }
        else {
          this.correctList = this.correctList.addToEnd(new Button(Color.blue, 250, 250));
        }
        return new SimonWorld(true, this.rand, num, this.correctList, -10);
      }

      // necessary for flashing the final light off before switching out of generation
      // mode
      if (this.counter == -10) {
        return new SimonWorld(false, this.rand, -1, this.correctList, 0);
      }
    }
    return new SimonWorld(this.generationMode, this.rand, -1, this.correctList, this.counter);

  }

  // Returns the final scene with the given message displayed
  public WorldScene lastScene(String msg) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.msg ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.msg.concat(String) ...    -- String 
     * ... this.msg.compareTo(String) ... -- int 
     */
    WorldScene background = new WorldScene(500, 500);
    WorldImage gameOver = new TextImage(msg, 65, Color.RED);
    return background.placeImageXY(gameOver, 250, 220);
  }

  // handles mouse clicks and is given the mouse location
  public SimonWorld onMouseClicked(Posn pos) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.pos ...          -- Posn
     *  
     *  Methods of Parameters:
     * ... this.pos.getX()...    -- int
     * ... this.pos.getY()...    -- int
     */
    // defaults to a non-gameplay color
    Color colorGuessed = Color.magenta;
    // ignores inputs if the game is generating colors
    if (this.generationMode) {
      return this;
    }
    // clicked red
    if (pos.x <= 250 && pos.y <= 250) {
      colorGuessed = Color.red;
      this.colorFlashed = 0;
    }
    // clicked green
    else if (pos.x >= 250 && pos.y <= 250) {
      colorGuessed = Color.green;
      this.colorFlashed = 1;
    }
    // clicked yellow
    else if (pos.x <= 250 && pos.y >= 250) {
      colorGuessed = Color.yellow;
      this.colorFlashed = 2;
    }
    // clicked blue
    else {
      colorGuessed = Color.blue;
      this.colorFlashed = 3;
    }
    // checks if the user's guess was correct
    if (this.correctList.get(this.counter).color.equals(colorGuessed)) {
      this.counter++;
    }
    // ends the game if the guess was incorrect
    else {
      this.endOfWorld("Game over!");
    }
    // enters generation mode if the user has guessed every button in this sequence
    // correctly
    if (this.counter == this.correctList.length()) {
      return new SimonWorld(true, this.rand, this.colorFlashed, this.correctList, -1);
    }
    return this;
  }
}

// Represents a list of buttons
interface ILoButton {
  // returns the number of buttons in this list
  int length();

  // adds a button to the end of this list
  ILoButton addToEnd(Button end);

  // returns a button at a given index (0-based)
  Button get(int num);

}

// Represents an empty list of buttons
class MtLoButton implements ILoButton {
  /* TEMPLATE:
   *  
   *  Methods:
   *  ... this.length() ...           --int
   *  ... this.addToEnd(Button) ...   --ILoButton
   *  ... this.get(int) ...           --Button
   */
  // returns the number of buttons in this list
  public int length() {
    return 0;
  }

  // adds a button to the end of this list
  public ILoButton addToEnd(Button end) {
    return new ConsLoButton(end, new MtLoButton());
  }

  // returns a button at a given index (0-based)
  public Button get(int num) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.num ...    -- int
     */
    throw new IndexOutOfBoundsException("Invalid index");
  }
}

// Represents a non-empty list of buttons
class ConsLoButton implements ILoButton {
  
  Button first;
  ILoButton rest;

  // the constructor
  ConsLoButton(Button first, ILoButton rest) {
    this.first = first;
    this.rest = rest;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.first ...    --Button
   *  ... this.rest ...     --ILoButton
   *  
   *  Methods:
   *  ... this.length() ...           --int
   *  ... this.addToEnd(Button) ...   --ILoButton
   *  ... this.get(int) ...           --Button
   *  
   *  Methods of fields:
   *  ... this.first.drawButton(Color) ...   --WorldImage
   *  ... this.first.drawDark() ...          --WorldImage
   *  ... this.first.drawLit() ...           --WorldImage
   *  ... this.first.getNumber() ...         --int
   */

  // returns the number of buttons in this list
  public int length() {
    return 1 + this.rest.length();
  }

  // adds a button to the end of this list
  public ILoButton addToEnd(Button end) {
    /* TEMPLATE:
     *  Parameters:
     *  ... this.end ...    --Button
     *  
     *  Methods of parameters:
     *  ... this.end.drawButton(Color) ...   --WorldImage
     *  ... this.end.drawDark() ...          --WorldImage
     *  ... this.end.drawLit() ...           --WorldImage
     *  ... this.end.getNumber() ...         --int
     */
    return new ConsLoButton(this.first, this.rest.addToEnd(end));
  }

  // returns a button at a given index (0-based)
  public Button get(int num) {
    /* TEMPLATE:
     *  Parameters:
     *  ... this.num ...    --int
     */
    if (num == 0) {
      return this.first;
    }
    return this.rest.get(num - 1);
  }
}

// Represents one of the four buttons you can click
class Button {
  Color color;
  int x;
  int y;

  Button(Color color, int x, int y) {
    this.color = color;
    this.x = x;
    this.y = y;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.color ...    --Color
   *  ... this.x ...        --int
   *  ... this.y ...        --int
   *  
   *  Methods:
   *  ... this.drawButton(Color) ...   --WorldImage
   *  ... this.drawDark() ...          --WorldImage
   *  ... this.drawLit() ...           --WorldImage
   *  ... this.getNumber() ...         --int
   */

  // Draw this button
  WorldImage drawButton(Color c) {
    /* TEMPLATE:
     *  Parameters:
     *  ... this.c ...    --Color
     */
    return new RectangleImage(250, 250, OutlineMode.SOLID, c);
  }

  // Draw this button dark
  WorldImage drawDark() {
    return this.drawButton(this.color.darker().darker());
  }

  // Draw this button lit
  WorldImage drawLit() {
    return this.drawButton(this.color.brighter().brighter());
  }

  // returns the number corresponding to this button's color
  // 0 = red
  // 1 = green
  // 2 = yellow
  // 3 = blue
  int getNumber() {
    if (this.color.equals(Color.red)) {
      return 0;
    }
    else if (this.color.equals(Color.green)) {
      return 1;
    }
    else if (this.color.equals(Color.yellow)) {
      return 2;
    }
    else if (this.color.equals(Color.blue)) {
      return 3;
    }
    return -1;
  }
}

// Examples
class ExamplesSimon {

  // runs the game by creating a world and calling bigBang
  boolean testSimonSays(Tester t) {
    // SimonWorld starterWorld = new SimonWorld(true, new Random(300), -1, new
    // MtLoButton(), -1);
    SimonWorld starterWorld = new SimonWorld(true, -1, new MtLoButton(), -1);
    int sceneSize = 500;
    return starterWorld.bigBang(sceneSize, sceneSize, 0.5);
  }

  ILoButton singleButton = new ConsLoButton(new Button(Color.red, 25, 25), new MtLoButton());
  ILoButton buttonList = new ConsLoButton(new Button(Color.red, 25, 25),
      new ConsLoButton(new Button(Color.yellow, 25, 25),
          new ConsLoButton(new Button(Color.blue, 25, 25), new MtLoButton())));

  // tests the methods in the classes that implement ILoButton
  // tests the method length() in the classes that implement ILoButton
  boolean testLength(Tester t) {
    return t.checkExpect(new MtLoButton().length(), 0)
        && t.checkExpect(this.singleButton.length(), 1)
        && t.checkExpect(this.buttonList.length(), 3);
  }

  ILoButton singleButtonAdded = new ConsLoButton(new Button(Color.red, 25, 25),
      new ConsLoButton(new Button(Color.blue, 25, 25), new MtLoButton()));
  ILoButton buttonListAdded = new ConsLoButton(new Button(Color.red, 25, 25),
      new ConsLoButton(new Button(Color.yellow, 25, 25),
          new ConsLoButton(new Button(Color.blue, 25, 25),
              new ConsLoButton(new Button(Color.yellow, 250, 250), new MtLoButton()))));

  // tests the method AddToEnd(ILoButton) in the classes that implement ILoButton
  boolean testAddToEnd(Tester t) {
    return t.checkExpect(new MtLoButton().addToEnd(new Button(Color.red, 25, 25)),
        this.singleButton)
        && t.checkExpect(this.singleButton.addToEnd(new Button(Color.blue, 25, 25)),
            this.singleButtonAdded)
        && t.checkExpect(this.buttonList.addToEnd(new Button(Color.yellow, 250, 250)),
            this.buttonListAdded);
  }

  // tests the method get(int) in the classes that implement ILoButton
  boolean testGet(Tester t) {
    return t.checkException(new IndexOutOfBoundsException("Invalid index"), new MtLoButton(), "get",
        1)
        && t.checkException(new IndexOutOfBoundsException("Invalid index"), this.singleButton,
            "get", 1)
        && t.checkExpect(this.singleButton.get(0), new Button(Color.red, 25, 25))
        && t.checkExpect(this.buttonList.get(0), new Button(Color.red, 25, 25))
        && t.checkExpect(this.buttonList.get(2), new Button(Color.blue, 25, 25));
  }

  // tests the methods in the class Button
  // tests the method drawButton(Color c) in the class Button
  boolean testDrawButton(Tester t) {
    return t.checkExpect(new Button(Color.red, 250, 250).drawButton(Color.red),
        new RectangleImage(250, 250, OutlineMode.SOLID, Color.red))
        && t.checkExpect(new Button(Color.blue, 250, 250).drawButton(Color.blue),
            new RectangleImage(250, 250, OutlineMode.SOLID, Color.blue));
  }

  // tests the method drawDark() in the class Button
  boolean testDrawDark(Tester t) {
    return t.checkExpect(new Button(Color.red, 250, 250).drawDark(),
        new RectangleImage(250, 250, OutlineMode.SOLID, Color.red.darker().darker()))
        && t.checkExpect(new Button(Color.blue, 250, 250).drawDark(),
            new RectangleImage(250, 250, OutlineMode.SOLID, Color.blue.darker().darker()));
  }

  // tests the method drawLit() in the class Button
  boolean testDrawLit(Tester t) {
    return t.checkExpect(new Button(Color.red, 250, 250).drawLit(),
        new RectangleImage(250, 250, OutlineMode.SOLID, Color.red.brighter().brighter()))
        && t.checkExpect(new Button(Color.blue, 250, 250).drawLit(),
            new RectangleImage(250, 250, OutlineMode.SOLID, Color.blue.brighter().brighter()));
  }

  // tests the method getNumber in the class Button
  boolean testGetNumber(Tester t) {
    return t.checkExpect(new Button(Color.red, 250, 250).getNumber(), 0)
        && t.checkExpect(new Button(Color.green, 250, 250).getNumber(), 1)
        && t.checkExpect(new Button(Color.yellow, 250, 250).getNumber(), 2)
        && t.checkExpect(new Button(Color.blue, 250, 250).getNumber(), 3)
        && t.checkExpect(new Button(Color.black, 250, 250).getNumber(), -1);
  }

  WorldScene noneLit = new WorldScene(500, 500).placeImageXY(new AboveImage(
      new BesideImage(new Button(Color.red, 250, 250).drawDark(),
          new Button(Color.green, 250, 250).drawDark()),
      new BesideImage(new Button(Color.yellow, 250, 250).drawDark(),
          new Button(Color.blue, 250, 250).drawDark())),
      250, 250);
  WorldScene redLit = new WorldScene(500, 500).placeImageXY(new AboveImage(
      new BesideImage(new Button(Color.red, 250, 250).drawLit(),
          new Button(Color.green, 250, 250).drawDark()),
      new BesideImage(new Button(Color.yellow, 250, 250).drawDark(),
          new Button(Color.blue, 250, 250).drawDark())),
      250, 250);
  WorldScene greenLit = new WorldScene(500, 500).placeImageXY(new AboveImage(
      new BesideImage(new Button(Color.red, 250, 250).drawDark(),
          new Button(Color.green, 250, 250).drawLit()),
      new BesideImage(new Button(Color.yellow, 250, 250).drawDark(),
          new Button(Color.blue, 250, 250).drawDark())),
      250, 250);
  WorldScene yellowLit = new WorldScene(500, 500).placeImageXY(new AboveImage(
      new BesideImage(new Button(Color.red, 250, 250).drawDark(),
          new Button(Color.green, 250, 250).drawDark()),
      new BesideImage(new Button(Color.yellow, 250, 250).drawLit(),
          new Button(Color.blue, 250, 250).drawDark())),
      250, 250);
  WorldScene blueLit = new WorldScene(500, 500).placeImageXY(new AboveImage(
      new BesideImage(new Button(Color.red, 250, 250).drawDark(),
          new Button(Color.green, 250, 250).drawDark()),
      new BesideImage(new Button(Color.yellow, 250, 250).drawDark(),
          new Button(Color.blue, 250, 250).drawLit())),
      250, 250);

  // tests the methods in the class SimonWorld
  // tests the method makeScene() in the class SimonWorld
  boolean testMakeScene(Tester t) {
    return t.checkExpect(new SimonWorld(true, new Random(), -1, new MtLoButton(), 0).makeScene(),
        this.noneLit)
        && t.checkExpect(new SimonWorld(true, new Random(), 0, new MtLoButton(), 0).makeScene(),
            this.redLit)
        && t.checkExpect(new SimonWorld(true, new Random(), 1, new MtLoButton(), 0).makeScene(),
            this.greenLit)
        && t.checkExpect(new SimonWorld(true, new Random(), 2, new MtLoButton(), 0).makeScene(),
            this.yellowLit)
        && t.checkExpect(new SimonWorld(true, new Random(), 3, new MtLoButton(), 0).makeScene(),
            this.blueLit);
  }

  // tests the method makeScene() in the class SimonWorld
  boolean testOnTick(Tester t) {
    // tests for when generation mode is true
    return t.checkExpect(new SimonWorld(true, new Random(300), -1, this.buttonList, 0).onTick(),
        new SimonWorld(true, new Random(300), 2, this.buttonList, 1))
        && t.checkExpect(new SimonWorld(true, new Random(300), 2, this.buttonList, 0).onTick(),
            new SimonWorld(true, new Random(300), -1, this.buttonList, 0))
        && t.checkExpect(new SimonWorld(true, new Random(300), -1, this.buttonList, 1).onTick(),
            new SimonWorld(true, new Random(300), 3, this.buttonList, 2))
        && t.checkExpect(new SimonWorld(true, new Random(300), -1, this.buttonList, 2).onTick(),
            new SimonWorld(true, new Random(300), 2, this.buttonListAdded, -10))
        && t.checkExpect(
            new SimonWorld(true, new Random(300), 2, this.buttonListAdded, -10).onTick(),
            new SimonWorld(false, new Random(300), -1, this.buttonListAdded, 0))
        && t.checkExpect(new SimonWorld(true, new Random(300), -1, new MtLoButton(), -1).onTick(),
            new SimonWorld(true, new Random(300), 2,
                new ConsLoButton(new Button(Color.yellow, 250, 250), new MtLoButton()), -10))
        // tests for when generation mode is false
        && t.checkExpect(
            new SimonWorld(false, new Random(300), 2, this.buttonListAdded, -10).onTick(),
            new SimonWorld(false, new Random(300), -1, this.buttonListAdded, -10))
        && t.checkExpect(
            new SimonWorld(false, new Random(300), -1, this.buttonListAdded, -10).onTick(),
            new SimonWorld(false, new Random(300), -1, this.buttonListAdded, -10));
  }

  // tests the method lastScene() in the class SimonWorld
  boolean testLastScene(Tester t) {
    return t.checkExpect(
        new SimonWorld(true, new Random(300), -1, new MtLoButton(), -1).lastScene(""),
        new WorldScene(500, 500).placeImageXY(new TextImage("", 65, Color.RED), 250, 220))
        && t.checkExpect(
            new SimonWorld(true, new Random(300), -1, new MtLoButton(), -1).lastScene("Game over"),
            new WorldScene(500, 500).placeImageXY(new TextImage("Game over", 65, Color.RED), 250,
                220));
  }
  
  // tests the method onMouseClicked(Posn) in the class SimonWorld
  boolean testOnMouseClicked(Tester t) {
    return t.checkExpect(new SimonWorld(false, new Random(300), -1, this.singleButton, 0)
        .onMouseClicked(new Posn(125, 249)), 
        new SimonWorld(true, new Random(300), 0, this.singleButton, -1))
        && t.checkExpect(new SimonWorld(false, new Random(300), -1, this.singleButton, 0)
            .onMouseClicked(new Posn(325, 250)), 
            new SimonWorld(false, new Random(300), 1, this.singleButton, 0))
        && t.checkExpect(new SimonWorld(false, new Random(300), -1, this.singleButton, 0)
            .onMouseClicked(new Posn(125, 325)), 
            new SimonWorld(false, new Random(300), 2, this.singleButton, 0))
        && t.checkExpect(new SimonWorld(false, new Random(300), -1, this.singleButton, 0)
            .onMouseClicked(new Posn(325, 325)), 
            new SimonWorld(false, new Random(300), 3, this.singleButton, 0))
        //tests correct guess that would end the guessing phase
        && t.checkExpect(new SimonWorld(false, new Random(300), -1, this.singleButton, 0)
            .onMouseClicked(new Posn(125, 125)), 
            new SimonWorld(true, new Random(300), 0, this.singleButton, -1))
        && t.checkExpect(new SimonWorld(true, new Random(300), -1, this.singleButton, 0)
            .onMouseClicked(new Posn(325, 250)), 
            new SimonWorld(true, new Random(300), -1, this.singleButton, 0));
  }

}