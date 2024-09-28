import tester.Tester;

//the weight of the flour should be equal to the weight of the water
//the weight of the yeast should be equal the weight of the malt
//the weight of the salt + yeast should be 1/20th the weight of the flour

// to represent the recipe for a bagel
class BagelRecipe {
  double flour; // in oz
  double water; // in oz
  double yeast; // in oz
  double salt;  // in oz
  double malt;  // in oz

  // all values in oz
  BagelRecipe(double flour, double water, double yeast, double salt, double malt) {
    this.flour = new Utils().checkRatio(flour, water, 1, 1,
        "Invalid ratio between flour, " + flour + ", and water, " + water);
    this.water = water;

    this.yeast = new Utils().checkRatio(yeast, malt, 1, 1,
        "Invalid ratio between yeast, " + yeast + ", and malt, " + malt);
    this.malt = malt;

    this.salt = new Utils().checkRatio(salt, flour - (yeast * 20), 1, 20,
        "Invalid ratio between salt + yeast, " 
    + (salt + yeast) + ", and flour, " + flour);
  }

  // all values in oz
  BagelRecipe(double flour, double yeast) {
    this.flour = flour;
    this.yeast = yeast;
    this.water = flour;
    this.malt = yeast;
    if (yeast * 20 <= flour) {
      this.salt = (flour - (yeast * 20)) / 20;
    }
    else {
      throw new IllegalArgumentException("Impossible to create recipe with given yeast, "
          + this.yeast + ", and flour, " + this.flour + " (too much yeast/too little flour)");
    }
  }

  // flour, yeast, and salt are taken as volumes in teaspoons
  BagelRecipe(double flour, double yeast, double salt) {
    this.flour = flour * 4.25;
    this.water = this.flour;
    this.yeast = (yeast / 48) * 5;
    this.malt = this.yeast;
    this.salt = new Utils().checkRatio((salt / 48) * 10, (this.flour - this.yeast * 20), 1, 20,
        "Invalid ratio between salt + yeast, "
    + ((salt / 48) * 10 + this.yeast) + ", and flour, " + this.flour);
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.flour ...  -- double
   *  ... this.yeast ...  -- double
   *  ... this.water ...  -- double
   *  ... this.salt ...   -- double
   *  ... this.malt ...   -- double
   *  
   *  Methods:
   *  ... this.sameRecipe(BagelRecipe) ...  -- boolean
   */

  boolean sameRecipe(BagelRecipe other) {
    return (Math.abs(this.flour - other.flour) < 0.001
        && Math.abs(this.water - other.water) < 0.001
        && Math.abs(this.yeast - other.yeast) < 0.001
        && Math.abs(this.salt - other.salt) < 0.001
        && Math.abs(this.malt - other.malt) < 0.001);
  }
}

// Utility class with methods that help with BagelRecipe
class Utils {
  Utils() {
    
  }
  /* TEMPLATE:
   *  Methods:
   *  ... this.chcekRatio(double, double, int, int, String) ... -- boolean
   */
  
  double checkRatio(double firstWeight, double secondWeight, int firstRatio, int secondRatio,
      String msg) {
    /* TEMPLATE:
     *  Fields:
     *  ... this.firstWeight ...   -- double
     *  ... this.secondWeight ...  -- double
     *  ... this.firstRatio ...    -- int
     *  ... this.secondRatio ...   -- int
     *  ... this.msg ...           -- String
     *
     * METHODS FOR FIELDS:
     * ... this.msg.concat(String) ...    -- String 
     * ... this.msg.compareTo(String) ... -- int 
     */
    if (Math.abs((firstWeight * secondRatio) - (secondWeight * firstRatio)) < 0.001) {
      return firstWeight;
    }
    throw new IllegalArgumentException(msg);
  }
}

class ExamplesBagel {
  BagelRecipe exampleBagel1 = new BagelRecipe(200.0, 200.0, 4.0, 6.0, 4.0);
  BagelRecipe exampleBagel2 = new BagelRecipe(30.0, 30.0, 1.0, 0.5, 1.0);
  // BagelRecipe exampleInvalidBagel1 = new BagelRecipe(200.0, 200.0, 4.0, 7.0,
  // 4.0);

  BagelRecipe exampleBagel3 = new BagelRecipe(405.0, 20.0);
  BagelRecipe exampleBagel4 = new BagelRecipe(60.0, 2.45);
  // BagelRecipe exampleInvalidBagel2 = new BagelRecipe(399.0, 20.0);

  BagelRecipe exampleBagel5 = new BagelRecipe(0.941, 0.48, 0.72);
  BagelRecipe exampleBagel6 = new BagelRecipe(1.8824, 2.88, 0.48);
  // BagelRecipe exampleInvalidBagel3 = new BagelRecipe(93.0, 1.0, 2.0);

  // tests the first constructor in BagelRecipe, i.e. the one that takes in 5
  // ingredients in oz
  boolean testFirstConstructor(Tester t) {
    return t.checkConstructorNoException("test first constructor for exceptions", "BagelRecipe",
        200.0, 200.0, 4.0, 6.0, 4.0)
        && t.checkConstructorNoException("test first constructor for exceptions", "BagelRecipe",
            30.0, 30.0, 1.0, 0.5, 1.0)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "Invalid ratio between salt + yeast, 11.0, and flour, 200.0"),
            "BagelRecipe", 200.0, 200.0, 4.0, 7.0, 4.0);
  }

  // tests the second constructor in BagelRecipe, i.e. the one that takes in 2
  // ingredients in oz
  boolean testSecondConstructor(Tester t) {
    return t.checkConstructorNoException("test second constructor for exceptions", "BagelRecipe",
        405.0, 20.0)
        && t.checkConstructorNoException("test second constructor for exceptions", "BagelRecipe",
            60.0, 2.45)
        && t.checkConstructorException(
            new IllegalArgumentException("Impossible to create recipe with given yeast, 20.0,"
                + "and flour, 399.0 (too much yeast/too little flour)"),
            "BagelRecipe", 399.0, 20.0)
        && t.checkExpect(exampleBagel3, new BagelRecipe(405, 405, 20, 0.25, 20))
        && t.checkExpect(exampleBagel4, new BagelRecipe(60, 60, 2.45, 0.55, 2.45));
  }

  // tests the third constructor in BagelRecipe, i.e. the one that takes in 3
  // ingredients as volumes
  boolean testThirdConstructor(Tester t) {
    return t.checkConstructorNoException("test third constructor for exceptions", "BagelRecipe",
        0.941, 0.48, 0.72)
        && t.checkConstructorNoException("test third constructor for exceptions", "BagelRecipe",
            1.8824, 2.88, 0.48)
        && t.checkConstructorException(
            new IllegalArgumentException(
                "Invalid ratio between salt + yeast, 0.5208333333333333, and flour, 395.25"),
            "BagelRecipe", 93.0, 1.0, 2.0)
        && t.checkExpect(exampleBagel5, new BagelRecipe(4, 4, 0.05, 0.15, 0.05))
        && t.checkExpect(exampleBagel6, new BagelRecipe(8, 8, 0.3, 0.1, 0.3));
  }

  // tests the method sameRecipe for the class BagelRecipe
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(exampleBagel1.sameRecipe(exampleBagel2), false)
        && t.checkExpect(exampleBagel1.sameRecipe(new BagelRecipe(200, 200, 4, 6, 4)), true)
        && t.checkExpect(exampleBagel2.sameRecipe(new BagelRecipe(30.0, 30.0, 1.0, 0.5, 1.0)), true)
        && t.checkExpect(exampleBagel3.sameRecipe(new BagelRecipe(405, 405, 20, 0.25, 20)), true)
        && t.checkExpect(exampleBagel4.sameRecipe(new BagelRecipe(60, 60, 2.45, 0.55, 2.45)), true)
        && t.checkExpect(exampleBagel5.sameRecipe(new BagelRecipe(4, 4, 0.05, 0.15, 0.05)), true)
        && t.checkExpect(exampleBagel6.sameRecipe(new BagelRecipe(8, 8, 0.3, 0.1, 0.3)), true);
  }

  // tests the helper method checkRatio
  boolean testCheckRatio(Tester t) {
    return t.checkExpect(new Utils().checkRatio(1, 1, 1, 1, "test"), 1.0)
        && t.checkExpect(new Utils().checkRatio(1, 20, 1, 20, "test"), 1.0)
        && t.checkExpect(new Utils().checkRatio(10, 20, 1, 2, "test"), 10.0);
  }
}