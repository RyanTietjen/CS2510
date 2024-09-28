import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import javalib.worldcanvas.*;   // so we can view our images
import java.awt.Color;          // general colors (as triples of red,green,blue values)
// and predefined colors (Red, Green, Yellow, Blue, Black, White)

interface IMobile {
  //returns the total weight of the mobile
  int totalWeight();
  
  //returns the total height of the mobile
  int totalHeight();
  
  //determines if the mobile has the same torque on both sides
  boolean isBalanced();
  
  // combines this balanced mobile with another balanced mobile
  IMobile buildMobile(IMobile other, int stringLength, int strutLength);
  
  // determines the width of this mobile
  int curWidth();
  
  // determines the width of the left side of this mobile
  int leftWidth();
  
  // determines the width of the right side of this mobile
  int rightWidth();
  
  // produces an image of the mobile
  WorldImage drawMobile();
}

// represents a simple mobile
// i.e. a mobile that consists of just a vertical bar and one hanging item
class Simple implements IMobile {
  int length;
  int weight;
  Color color;
  
  //the constructor
  Simple(int length, int weight, Color color) {
    this.length = length;
    this.weight = weight;
    this.color = color;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.length ...  -- int
   *  ... this.weight ...  -- int
   *  ... this.color ...   -- color
   *  
   *  Methods:
   *  ... this.totalWeight() ...                  -- int
   *  ... this.totalHeight() ...                  -- int
   *  ... this.isBalanced() ...                   -- Boolean
   *  ... this.buildMobile(IMobile, int, int) ... -- IMobile
   *  ... this.curWidth() ...                     -- int
   *  ... this.leftWidth() ...                    -- int
   *  ... this.rightWidth() ...                   -- int
   *  ... this.drawMobile() ...                   -- WorldImage
   */

  // returns the total weight of the mobile
  public int totalWeight() {
    return weight;
  }
  
  // returns the total weight of the mobile
  public int totalHeight() {
    return this.length + this.weight / 10;
  }
  
  // determines if the mobile has the same torque on both sides
  public boolean isBalanced() {
    return true;
  }
  
  // combines this balanced mobile with another balanced mobile
  public IMobile buildMobile(IMobile other, int stringLength, int strutLength) {
    /* TEMPLATE:
     *  Parameters:
     *  ... this.other ...          -- IMobile
     *  ... this.stringLength ...   -- int
     *  ... this.strutLength ...    -- int
     *  
     *  Methods on Parameters:
     *  ... this.other.totalWeight() ...                  -- int
     *  ... this.other.totalHeight() ...                  -- int
     *  ... this.other.isBalanced() ...                   -- Boolean
     *  ... this.other.buildMobile(IMobile, int, int) ... -- IMobile
     *  ... this.other.curWidth() ...                     -- int
     *  ... this.other.leftWidth() ...                    -- int
     *  ... this.other.rightWidth() ...                   -- int
     *  ... this.other.drawMobile() ...                   -- WorldImage
     */
    int combinedWeight = this.totalWeight() + other.totalWeight();
    IMobile mobile = new Complex(stringLength,
        (strutLength * other.totalWeight()) / combinedWeight, // right side
        (strutLength * this.totalWeight()) / combinedWeight, //left side
        this, other);    
    return mobile;
  }
  
  // determines the width of this mobile
  public int curWidth() {
    return this.leftWidth() + this.rightWidth();
  }

  // determines the width of the left side of this mobile
  public int leftWidth() {
    if (weight % 20 == 0) {
      return weight / 20;
    }
    return weight / 20 + 1;
  }

  // determines the width of the right side of this mobile
  public int rightWidth() {
    if (weight % 20 == 0) {
      return weight / 20;
    }
    return weight / 20 + 1;
  }

  // produces an image of the mobile
  public WorldImage drawMobile() {
    WorldImage rectangle = new RectangleImage(this.curWidth() * 20, this.weight * 2,
        OutlineMode.SOLID, this.color);
    WorldImage pole = new RectangleImage(6, this.length * 20, OutlineMode.SOLID, Color.black);

    WorldImage finalImage = new AboveImage(pole, rectangle).movePinhole(0,
        (rectangle.getHeight() / -2 - pole.getHeight() / 2) + 3);
    return finalImage;
  }
  
}

// represents a complex mobile
// i.e. a mobile with left and right sub-mobiles
class Complex implements IMobile  {
  int length;
  int leftside;
  int rightside;
  IMobile left;
  IMobile right;
  
  Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
    this.length = length;
    this.leftside = leftside;
    this.rightside = rightside;
    this.left = left;
    this.right = right;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.lngth ...       -- int
   *  ... this.leftside ...    -- int
   *  ... this.rightside ...   -- int
   *  ... this.left ...        -- IMobile
   *  ... this.right ...       -- IMobile
   *  
   *  Methods:
   *  ... this.totalWeight() ...                  -- int
   *  ... this.totalHeight() ...                  -- int
   *  ... this.isBalanced() ...                   -- Boolean
   *  ... this.buildMobile(IMobile, int, int) ... -- IMobile
   *  ... this.curWidth() ...                     -- int
   *  ... this.leftWidth() ...                    -- int
   *  ... this.rightWidth() ...                   -- int
   *  ... this.drawMobile() ...                   -- WorldImage
   * 
   *  Methods for fields:
   *  ... this.left.totalWeight() ...                   -- int
   *  ... this.left.totalHeight() ...                   -- int
   *  ... this.left.isBalanced() ...                    -- Boolean
   *  ... this.left.buildMobile(IMobile, int, int) ...  -- IMobile
   *  ... this.left.curWidth() ...                      -- int
   *  ... this.left.leftWidth() ...                     -- int
   *  ... this.left.rightWidth() ...                    -- int
   *  ... this.left.drawMobile() ...                    -- WorldImage
   *  ... this.right.totalWeight() ...                  -- int
   *  ... this.right.totalHeight() ...                  -- int
   *  ... this.right.isBalanced() ...                   -- Boolean
   *  ... this.right.buildMobile(IMobile, int, int) ... -- IMobile
   *  ... this.right.curWidth() ...                     -- int
   *  ... this.right.leftWidth() ...                    -- int
   *  ... this.right.rightWidth() ...                   -- int
   *  ... this.right.drawMobile() ...                   -- WorldImage
   */
  
  //returns the total weight of the mobile
  public int totalWeight() {
    return this.left.totalWeight() + this.right.totalWeight();
  }
  
  //returns the total height of the mobile
  public int totalHeight() {
    return this.length + Math.max(this.left.totalHeight(), this.right.totalHeight());
  }
  
  // determines if the mobile has the same torque on both sides
  public boolean isBalanced() {
    return (this.left.totalWeight() * this.leftside) == (this.right.totalWeight() * rightside) 
        && this.left.isBalanced() && this.right.isBalanced();
  }
  
  //  combines this balanced mobile with another balanced mobile
  public IMobile buildMobile(IMobile other, int stringLength, int strutLength) {
    /* TEMPLATE:
     *  Parameters:
     *  ... this.other ...          -- IMobile
     *  ... this.stringLength ...   -- int
     *  ... this.strutLength ...    -- int
     *  
     *  Methods on Parameters:
     *  ... this.other.totalWeight() ...                  -- int
     *  ... this.other.totalHeight() ...                  -- int
     *  ... this.other.isBalanced() ...                   -- Boolean
     *  ... this.other.buildMobile(IMobile, int, int) ... -- IMobile
     *  ... this.other.curWidth() ...                     -- int
     *  ... this.other.leftWidth() ...                    -- int
     *  ... this.other.rightWidth() ...                   -- int
     *  ... this.other.drawMobile() ...                   -- WorldImage
     */
    int combinedWeight = this.totalWeight() + other.totalWeight();
    IMobile mobile = new Complex(stringLength,
        (strutLength * other.totalWeight()) / combinedWeight, // right side
        (strutLength * this.totalWeight()) / combinedWeight, //left side
        this, other);    
    return mobile;
  }

  // determines the width of this mobile
  public int curWidth() {
    return this.leftWidth() + this.rightWidth();
  }

  // determines the width of the left side of this mobile
  public int leftWidth() {
    return Math.max(this.leftside + this.left.leftWidth(), this.right.leftWidth() - this.rightside);
  }

  // determines the width of the right side of this mobile
  public int rightWidth() {
    return Math.max(this.rightside + this.right.rightWidth(),
        this.left.rightWidth() - this.leftside);
  }

  // produces an image of this mobile
  public WorldImage drawMobile() {
    WorldImage verticalPole = new RectangleImage(6, this.length * 60, OutlineMode.SOLID,
        Color.black).movePinhole(0, this.length * 30);
    WorldImage leftPole = new RectangleImage(this.leftside * 60, 6, OutlineMode.SOLID, Color.black)
        .movePinhole(-this.leftside * 30, 0);
    WorldImage rightPole = new RectangleImage(this.rightside * 60, 6, OutlineMode.SOLID,
        Color.black).movePinhole(this.rightside * 30, 0);

    WorldImage leftBox = new OverlayImage(leftPole, this.left.drawMobile())
        .movePinhole(this.leftside * 60, 0);

    WorldImage rightBox = new OverlayImage(rightPole, this.right.drawMobile())
        .movePinhole(-this.rightside * 60, 0);

    WorldImage hangingBoxes = new OverlayImage(leftBox, rightBox);

    WorldImage together = new OverlayImage(hangingBoxes, verticalPole).movePinhole(0,
        -this.length * 60 + 3);

    return together;
  }

}

// examples to test and represent mobiles
class ExamplesMobiles {
  // relating to exampleSimple
  IMobile exampleSimple = new Simple(2, 20, Color.BLUE);
  
  //relating to exampleComplex
  IMobile green60 = new Simple(1, 60, Color.GREEN);
  IMobile red36 = new Simple(2, 36, Color.RED);
  IMobile complex1 = new Complex(2, 5, 3, this.red36, this.green60);
  IMobile red12 = new Simple(1, 12, Color.RED);
  IMobile complex2 = new Complex(2, 8, 1, this.red12, this.complex1); 
  IMobile blue36 = new Simple(1, 36, Color.BLUE);
  
  IMobile exampleComplex = new Complex(1, 9, 3, this.blue36, this.complex2);
  
  //relating to the third example, example3
  IMobile orange56 = new Simple(1, 56, Color.ORANGE);
  
  IMobile example3 = new Complex(1, 4, 5, this.exampleComplex, this.orange56);
  
  
  // relating to a very asymmetric mobile
  IMobile asymMobile1 = new Simple(2,40, Color.green);
  IMobile asymMobile2 = new Simple(2,60, Color.magenta);
  IMobile asymMobile3 = new Simple(2,80, Color.red);
  
  IMobile asymComplex1 = new Complex(5,3,13, this.asymMobile1, this.asymMobile2);
  IMobile asymComplex2 = new Complex(2,5,3, this.asymComplex1, this.asymMobile3);
  
 
  // tests the method totalWeight for the classes that implement IMobile
  boolean testTotalWeight(Tester t) {
    return t.checkExpect(this.exampleSimple.totalWeight(), 20)
        && t.checkExpect(this.exampleComplex.totalWeight(), 144)
        && t.checkExpect(this.example3.totalWeight(), 200);
  }
  
  // tests the method totalHeight for the classes that implement IMobile
  boolean testTotalHeight(Tester t) {
    return t.checkExpect(this.exampleSimple.totalHeight(), 4)
        && t.checkExpect(this.exampleComplex.totalHeight(), 12)
        && t.checkExpect(this.example3.totalHeight(), 13);
  }
  
  // tests the method isBalanced for the classes that implement IMobile
  boolean testIsBalanced(Tester t) {
    return t.checkExpect(this.exampleSimple.isBalanced(), true)
        && t.checkExpect(this.exampleComplex.isBalanced(), true)
        && t.checkExpect(this.example3.isBalanced(), false);
  }
  
  //examples relating to testing buildMobile
  IMobile example1 = new Complex(2, 5, 36, this.exampleComplex, this.exampleSimple);
  IMobile example2 = new Complex(2, 1, 10, this.example3, this.exampleSimple);
  
  // tests the method buildMobile for the classes that implement IMobile
  boolean testBuildMobile(Tester t) {
    return t.checkExpect(this.exampleComplex.buildMobile(this.exampleSimple, 2, 41), this.example1)
        && t.checkExpect(this.red12.buildMobile(this.complex1, 2, 9), this.complex2)
        && t.checkExpect(this.example3.buildMobile(this.exampleSimple, 2, 11), this.example2);
  }
  
  // tests the method curWidth for the classes that implement IMobile
  boolean testCurWidth(Tester t) {
    return t.checkExpect(this.exampleComplex.curWidth(), 21)
        && t.checkExpect(this.exampleSimple.curWidth(), 2)
        && t.checkExpect(this.example3.curWidth(), 23)
        && t.checkExpect(this.asymComplex2.curWidth(), 21);
  }
  
  // tests the method leftWidth for the classes that implement IMobile
  boolean testLeftWidth(Tester t) {
    return t.checkExpect(this.exampleComplex.leftWidth(), 11)
        && t.checkExpect(this.exampleSimple.leftWidth(), 1)
        && t.checkExpect(this.example3.leftWidth(), 15)
        && t.checkExpect(this.asymComplex2.leftWidth(), 10);
  }
  
  // tests the method rightWidth for the classes that implement IMobile
  boolean testRightWidth(Tester t) {
    return t.checkExpect(this.exampleComplex.rightWidth(), 10)
        && t.checkExpect(this.exampleSimple.rightWidth(), 1)
        && t.checkExpect(this.example3.rightWidth(), 8)
        && t.checkExpect(this.asymComplex2.rightWidth(), 11);
  }
  
  // helper method to show images produced by drawMobile
  boolean showImage(WorldImage i) {
    WorldCanvas c = new WorldCanvas(1500, 1000);
    WorldScene s = new WorldScene(1500, 1000);
    return c.drawScene(s.placeImageXY(i, 850, 250)) && c.show();
  }

  // example related to testing exampleSimple
  WorldImage simplePole = new RectangleImage(6, 40, OutlineMode.SOLID, Color.black);
  WorldImage simpleBox = new RectangleImage(40, 40, OutlineMode.SOLID, Color.blue);

  WorldImage simpleFinalImage = new AboveImage(this.simplePole, this.simpleBox).movePinhole(0, -37);

  // example related to testing exampleComplex
  WorldImage testComplexBlue36Pole = new RectangleImage(6, 20, OutlineMode.SOLID, Color.black);
  WorldImage testComplexBlue36Box = new RectangleImage(80, 72, OutlineMode.SOLID, Color.blue);
  WorldImage testComplexBlue36Final = new AboveImage(this.testComplexBlue36Pole,
      this.testComplexBlue36Box).movePinhole(0, -43);

  WorldImage testComplexRed12Pole = new RectangleImage(6, 20, OutlineMode.SOLID, Color.black);
  WorldImage testComplexRed12Box = new RectangleImage(40, 24, OutlineMode.SOLID, Color.red);
  WorldImage testComplexRed12Final = new AboveImage(this.testComplexRed12Pole,
      this.testComplexRed12Box).movePinhole(0, -19);

  WorldImage testComplexRed36Pole = new RectangleImage(6, 40, OutlineMode.SOLID, Color.black);
  WorldImage testComplexRed36Box = new RectangleImage(80, 72, OutlineMode.SOLID, Color.red);
  WorldImage testComplexRed36Final = new AboveImage(this.testComplexRed36Pole,
      this.testComplexRed36Box).movePinhole(0, -53);

  WorldImage testComplexGreen60Pole = new RectangleImage(6, 20, OutlineMode.SOLID, Color.black);
  WorldImage testComplexGreen60Box = new RectangleImage(120, 120, OutlineMode.SOLID, Color.green);
  WorldImage testComplexGreen60Final = new AboveImage(this.testComplexGreen60Pole,
      this.testComplexGreen60Box).movePinhole(0, -67);

  WorldImage bottomLeftPole = new RectangleImage(300, 6, OutlineMode.SOLID, Color.black)
      .movePinhole(0, 0).movePinhole(-150, 0);
  WorldImage bottomRightPole = new RectangleImage(180, 6, OutlineMode.SOLID, Color.black)
      .movePinhole(0, 0).movePinhole(90, 0);
  WorldImage bottomVertPole = new RectangleImage(6, 120, OutlineMode.SOLID, Color.black)
      .movePinhole(0, 0).movePinhole(0, 60);

  WorldImage bottomLeftMobile = new OverlayImage(this.bottomLeftPole, this.testComplexRed36Final)
      .movePinhole(300, 0);
  WorldImage bottomRightMobile = new OverlayImage(this.bottomRightPole,
      this.testComplexGreen60Final).movePinhole(-180, 0);
  WorldImage bottomBoxes = new OverlayImage(this.bottomLeftMobile, this.bottomRightMobile);
  WorldImage bottomMobile = new OverlayImage(this.bottomBoxes, this.bottomVertPole).movePinhole(0,
      -117);

  WorldImage middleLeftPole = new RectangleImage(480, 6, OutlineMode.SOLID, Color.black)
      .movePinhole(-240, 0);
  WorldImage middleRightPole = new RectangleImage(60, 6, OutlineMode.SOLID, Color.black)
      .movePinhole(30, 0);
  WorldImage middleVertPole = new RectangleImage(6, 120, OutlineMode.SOLID, Color.black)
      .movePinhole(0, 60);

  WorldImage middleLeftMobile = new OverlayImage(this.middleLeftPole, this.testComplexRed12Final)
      .movePinhole(480, 0);
  WorldImage middleRightMobile = new OverlayImage(this.middleRightPole, this.bottomMobile)
      .movePinhole(-60, 0);
  WorldImage middleBoxes = new OverlayImage(this.middleLeftMobile, this.middleRightMobile);
  WorldImage middleMobile = new OverlayImage(this.middleBoxes, this.middleVertPole).movePinhole(0,
      -117);

  WorldImage topLeftPole = new RectangleImage(540, 6, OutlineMode.SOLID, Color.black)
      .movePinhole(-270, 0);
  WorldImage topRightPole = new RectangleImage(180, 6, OutlineMode.SOLID, Color.black)
      .movePinhole(90, 0);
  WorldImage topVertPole = new RectangleImage(6, 60, OutlineMode.SOLID, Color.black).movePinhole(0,
      30);

  WorldImage topLeftMobile = new OverlayImage(this.topLeftPole, this.testComplexBlue36Final)
      .movePinhole(540, 0);
  WorldImage topRightMobile = new OverlayImage(this.topRightPole, this.middleMobile)
      .movePinhole(-180, 0);
  WorldImage topBoxes = new OverlayImage(this.topLeftMobile, this.topRightMobile);
  WorldImage topMobile = new OverlayImage(this.topBoxes, this.topVertPole).movePinhole(0, -57);

  // tests the method drawMobile for the classes that implement IMobile
  boolean testDrawMobile(Tester t) {
    return t.checkExpect(this.blue36.drawMobile(), this.testComplexBlue36Final)
        && t.checkExpect(this.red12.drawMobile(), this.testComplexRed12Final)
        && t.checkExpect(this.red36.drawMobile(), this.testComplexRed36Final)
        && t.checkExpect(this.green60.drawMobile(), this.testComplexGreen60Final)
        && t.checkExpect(this.complex1.drawMobile(), this.bottomMobile)
        && t.checkExpect(this.complex2.drawMobile(), this.middleMobile)
        && t.checkExpect(this.exampleComplex.drawMobile(), this.topMobile)
        && showImage(this.exampleComplex.drawMobile())
        && t.checkExpect(this.exampleSimple.drawMobile(), this.simpleFinalImage)
        && showImage(this.exampleSimple.drawMobile()) && showImage(this.example3.drawMobile())
        && showImage(this.asymComplex2.drawMobile());
  }
  
  
}

