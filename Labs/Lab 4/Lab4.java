import tester.*;

// to represent a game piece in the game of 2048
interface IGamePiece {
  int getValue();
  
  IGamePiece merge(IGamePiece other);
  
  boolean isValid();
}

// to represent a base tile, i.e. one that has not been merged
class BaseTile implements IGamePiece {
  int value;
  
  // the constructor
  BaseTile(int value) {
    this.value = value;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.value ...    -- int
   *  
   *  Methods:
   *  ... this.getValue() ...     -- int
   *  ... this.merge(IGamePiece)  -- MergeTile
   *  ... this.isValid() ...      -- boolean
   *  
   */
  
  // returns the value of this game piece
  public int getValue() { 
    return this.value;
  }
  
  // merges this game piece with another game piece
  public IGamePiece merge(IGamePiece other) {
    
    /* TEMPLATE:
     *  Parameters:
     *  ... this.other ... -- IGamePiece
     *  
     *  Methods of Parameters:
     *  ... this.getValue() ...     -- int
     *  ... this.merge(IGamePiece)  -- MergeTile
     *  ... this.isValid() ...      -- boolean
     */
    return new MergeTile(this, other);
  }
  
  // determines if this game piece is a valid piece
  public boolean isValid() {
    return true;
  }
}

//to represent a tile in the game that has been merged
class MergeTile implements IGamePiece {
  IGamePiece piece1;
  IGamePiece piece2;
  
  // the constructor 
  MergeTile(IGamePiece piece1, IGamePiece piece2) {
    this.piece1 = piece1;
    this.piece2 = piece2;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.piece1 ...    -- IGamePiece
   *  ... this.piece2 ...    -- IGamePiece
   *  
   *  Methods:
   *  ... this.getValue() ...     -- int
   *  ... this.merge(IGamePiece)  -- MergeTile
   *  ... this.isValid() ...      -- boolean
   *  
   *  Methods of parameters:
   *  ... this.first.getValue() ...      -- int
   *  ... this.first.merge(IGamePiece)   -- MergeTile
   *  ... this.first.isValid() ...       -- boolean
   *  ... this.second.getValue() ...     -- int
   *  ... this.second.merge(IGamePiece)  -- MergeTile
   *  ... this.second.isValid() ...      -- boolean
   */
  
  // returns the value of this game piece
  public int getValue() {
    return this.piece1.getValue() + this.piece2.getValue();
  }
  
  // merges this game piece with another game piece
  public IGamePiece merge(IGamePiece other) {
    return new MergeTile(this, other);
    
    /* TEMPLATE:
     *  Parameters:
     *  ... this.other ... -- IGamePiece
     *  
     *  Methods of Parameters:
     *  ... this.getValue() ...     -- int
     *  ... this.merge(IGamePiece)  -- MergeTile
     *  ... this.isValid() ...      -- boolean
     */
  }
  
  // determines if this game piece is a valid piece
  public boolean isValid() {
    return this.piece1.getValue() == this.piece2.getValue();
  }
}
 
class ExamplesGamePiece {
  // base pieces
  IGamePiece baseTwo = new BaseTile(2);
  IGamePiece baseFour = new BaseTile(4);
  
  // merged pieces
  IGamePiece four = new MergeTile(new BaseTile(2), new BaseTile(2));
  IGamePiece eight = new MergeTile(this.baseFour, this.baseFour);
  IGamePiece sixteen = new MergeTile(this.eight, this.eight);
  
  // example invalid pieces
  IGamePiece invalidSix = new MergeTile(this.baseTwo, this.baseFour);
  IGamePiece invalidTwelve = new MergeTile(this.eight, this.baseFour);
  
  
  // tests the method getValue for the classes that implement IGamePiece
  boolean testGetValue(Tester t) {
    return t.checkExpect(this.baseTwo.getValue(), 2) 
        && t.checkExpect(this.baseFour.getValue(), 4) 
        && t.checkExpect(this.four.getValue(), 4)
        && t.checkExpect(this.eight.getValue(), 8)
        && t.checkExpect(this.sixteen.getValue(), 16);
  }
  
  // tests the method merge for the classes that implement IGamePiece
  boolean testMerge(Tester t) {
    return t.checkExpect(this.baseTwo.merge(this.baseTwo), this.four)
        && t.checkExpect(this.baseFour.merge(this.baseFour), this.eight)
        && t.checkExpect(this.eight.merge(this.eight), this.sixteen)
        && t.checkExpect(this.baseTwo.merge(this.baseFour), this.invalidSix)
        && t.checkExpect(this.eight.merge(this.baseFour), this.invalidTwelve);
  }

  // tests the method isValid for the classes that implement IGamePiece
  boolean testIsValid(Tester t) {
    return t.checkExpect(this.baseTwo.isValid(), true)
        && t.checkExpect(this.baseFour.isValid(), true)
        && t.checkExpect(this.four.isValid(), true)
        && t.checkExpect(this.eight.isValid(), true)
        && t.checkExpect(this.sixteen.isValid(), true)
        && t.checkExpect(this.invalidSix.isValid(), false)
        && t.checkExpect(this.invalidTwelve.isValid(), false);
  }
}