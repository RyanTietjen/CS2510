// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
  // combine all Strings in this list into one
  String combine();

  //sorts all Strings in this list in alphabetical order, ignoring case
  ILoString sort();

  // helper method for sort, inserts a string into a given list
  ILoString insert(String s);

  // helper method that compares two strings, used in merge and sort
  boolean compareStrings(String other);

  // determines if the list is sorted
  boolean isSorted();

  // combines two lists of strings such that the items in the combined
  // list are added in an alternating manner from each list
  ILoString interleave(ILoString other);

  //adds the rest of a list to a given list
  ILoString addRest();

  // merges two sorted lists of strings into one sorted list
  ILoString merge(ILoString other);

  // reverses a given list of strings
  ILoString reverse();
  
  // helper method for reverse
  ILoString reverseHelper(String str);
  
  // determines if a list contains pairs of identical stings
  boolean isDoubledList();
  
  // helper method for isDoubledList
  boolean isDoubledListHelper(String str);
  
  // determines if this list is a palindrome
  boolean isPalindromeList();

}

// to represent an empty list of Strings
class MtLoString implements ILoString {
  // the constructor
  MtLoString() {
  }
  /*
   * TEMPLATE:
   * 
   * METHODS 
   * ... this.combine() ...                 -- String
   * ... this.sort() ...                    -- ILoString
   * ... this.insert(String) ...            -- ILoString
   * ... this.compareStrings(String) ...    -- boolean
   * ... this.isSorted() ...                -- boolean
   * ... this.interleave(ILoString) ...     -- ILoString
   * ... this.addRest() ...                 -- ILoString
   * ... this.merge(ILoString) ...          -- ILoString
   * ... this.reverse() ...                 -- ILoString
   * ... this.reverseHelper(String) ...     -- ILoString
   * ... this.isDoubled() ...               -- boolean
   * ... this.isDoubledListHelper(String) ...   -- boolean
   * ... this.isPalindromeList() ...        -- boolean
   */

  // combine all Strings in this list into one
  public String combine() {
    return "";
  }

  // sorts all Strings in this list in alphabetical order, ignoring case
  public ILoString sort() {
    return new MtLoString();
  }

  // helper method for sort, inserts a string into a given list
  public ILoString insert(String s) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.s ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.s.concat(String) ...    -- String 
     * ... this.s.compareTo(String) ... -- int 
     */
    return new ConsLoString(s, this);
  }

  // helper method that compares two strings, used in merge and sort
  public boolean compareStrings(String other) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.other ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.other.concat(String) ...    -- String 
     * ... this.other.compareTo(String) ... -- int 
     */
    return true;
  }
  
  // determines if the list is sorted
  public boolean isSorted() {
    return true;
  }

  // combines two lists of strings such that the items in the combined
  // list are added in an alternating manner from each list
  public ILoString interleave(ILoString other) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.other ...          -- ILoString
     *  
     *  Methods on Parameters:
     * ... this.other.combine() ... -- String
     * ... this.other.combine() ...                 -- String
     * ... this.other.sort() ...                    -- ILoString
     * ... this.other.insert(String) ...            -- ILoString
     * ... this.other.compareStrings(String) ...    -- boolean
     * ... this.other.isSorted() ...                -- boolean
     * ... this.other.interleave(ILoString) ...     -- ILoString
     * ... this.other.addRest() ...                 -- ILoString
     * ... this.other.merge(ILoString) ...          -- ILoString
     * ... this.other.meregeHelper(String) ...      -- boolean
     * ... this.other.reverse() ...                 -- ILoString
     * ... this.other.reverseHelper(String) ...     -- ILoString
     * ... this.other.isDoubled() ...               -- boolean
     * ... this.other.isDoubledListHelper(String) ...   -- boolean
     * ... this.other.isPalindromeList() ...        -- boolean
     */
    return other.addRest();
  }

  // adds the rest of a list to a given list
  public ILoString addRest() {
    return new MtLoString();
  }

  // merges two sorted lists of strings into one sorted list
  public ILoString merge(ILoString other) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.other ...          -- ILoString
     *  
     *  Methods on Parameters:
     * ... this.other.combine() ... -- String
     * ... this.other.combine() ...                 -- String
     * ... this.other.sort() ...                    -- ILoString
     * ... this.other.insert(String) ...            -- ILoString
     * ... this.other.compareStrings(String) ...    -- boolean
     * ... this.other.isSorted() ...                -- boolean
     * ... this.other.interleave(ILoString) ...     -- ILoString
     * ... this.other.addRest() ...                 -- ILoString
     * ... this.other.merge(ILoString) ...          -- ILoString
     * ... this.other.meregeHelper(String) ...      -- boolean
     * ... this.other.reverse() ...                 -- ILoString
     * ... this.other.reverseHelper(String) ...     -- ILoString
     * ... this.other.isDoubled() ...               -- boolean
     * ... this.other.isDoubledListHelper(String) ...   -- boolean
     * ... this.other.isPalindromeList() ...        -- boolean
     */
    return other.addRest();
  }
  
  // reverses a given list of strings
  public ILoString reverse() {
    return new MtLoString();
  }
  
  // helper method for reverse
  public ILoString reverseHelper(String str) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.str ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.str.concat(String) ...    -- String 
     * ... this.str.compareTo(String) ... -- int 
     */
    return new ConsLoString(str, new MtLoString());
  }
  
  // determines if a list contains pairs of identical stings
  public boolean isDoubledList() {
    return true;
  }

  // helper method for isDoubledList
  public boolean isDoubledListHelper(String str) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.str ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.str.concat(String) ...    -- String 
     * ... this.str.compareTo(String) ... -- int 
     */
    return true;
  }
  
  // determines if a list is a palindrome
  public boolean isPalindromeList() {
    return true;
  }

}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
  String first;
  ILoString rest;

  ConsLoString(String first, ILoString rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE 
   * FIELDS: 
   * ... this.first ... -- String 
   * ... this.rest ... -- ILoString
   * 
   * METHODS 
   * ... this.combine() ...                 -- String
   * ... this.sort() ...                    -- ILoString
   * ... this.insert(String) ...            -- ILoString
   * ... this.compareStrings(String) ...    -- boolean
   * ... this.isSorted() ...                -- boolean
   * ... this.interleave(ILoString) ...     -- ILoString
   * ... this.addRest() ...                 -- ILoString
   * ... this.merge(ILoString) ...          -- ILoString
   * ... this.reverse() ...                 -- ILoString
   * ... this.reverseHelper(String) ...     -- ILoString
   * ... this.isDoubled() ...               -- boolean
   * ... this.isDoubledListHelper(String) ...   -- boolean
   * ... this.isPalindromeList() ...        -- boolean
   * 
   * METHODS FOR FIELDS 
   * ... this.first.concat(String) ... -- String 
   * ... this.first.compareTo(String) ... -- int 
   * ... this.rest.combine() ... -- String
   * ... this.rest.combine() ...                 -- String
   * ... this.rest.sort() ...                    -- ILoString
   * ... this.rest.insert(String) ...            -- ILoString
   * ... this.rest.compareStrings(String) ...    -- boolean
   * ... this.rest.isSorted() ...                -- boolean
   * ... this.rest.interleave(ILoString) ...     -- ILoString
   * ... this.rest.addRest() ...                 -- ILoString
   * ... this.rest.merge(ILoString) ...          -- ILoString
   * ... this.rest.meregeHelper(String) ...      -- boolean
   * ... this.rest.reverse() ...                 -- ILoString
   * ... this.rest.reverseHelper(String) ...     -- ILoString
   * ... this.rest.isDoubled() ...               -- boolean
   * ... this.rest.isDoubledListHelper(String) ...   -- boolean
   * ... this.rest.isPalindromeList() ...        -- boolean
   * 
   */

  // combine all Strings in this list into one
  public String combine() {
    return this.first.concat(this.rest.combine());
  }

  // sorts all Strings in this list in alphabetical order, ignoring case
  public ILoString sort() {
    return this.rest.sort().insert(this.first);
  }

  // helper method for the isSorted method, inserts a string into a list
  // if it comes next alphabetically
  public ILoString insert(String s) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.s ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.s.concat(String) ...    -- String 
     * ... this.s.compareTo(String) ... -- int 
     */
    if (this.first.toLowerCase().compareTo(s.toLowerCase()) < 0) {
      return new ConsLoString(this.first, this.rest.insert(s));
    }
    return new ConsLoString(s, this);
  }

  // helper method for isSorted and merge, compares the first item in a list to the next item
  public boolean compareStrings(String other) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.other ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.other.concat(String) ...    -- String 
     * ... this.other.compareTo(String) ... -- int 
     */
    return this.first.toLowerCase().compareTo(other.toLowerCase()) >= 0;
  }
  
  // determines if a list of strings is sorted
  public boolean isSorted() {
    return this.rest.compareStrings(this.first) && this.rest.isSorted();
  }

  // combines two lists of strings such that the items in the combined
  // list are added in an alternating manner from each list
  public ILoString interleave(ILoString other) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.other ...          -- ILoString
     *  
     *  Methods on Parameters:
     * ... this.other.combine() ... -- String
     * ... this.other.combine() ...                 -- String
     * ... this.other.sort() ...                    -- ILoString
     * ... this.other.insert(String) ...            -- ILoString
     * ... this.other.compareStrings(String) ...    -- boolean
     * ... this.other.isSorted() ...                -- boolean
     * ... this.other.interleave(ILoString) ...     -- ILoString
     * ... this.other.addRest() ...                 -- ILoString
     * ... this.other.merge(ILoString) ...          -- ILoString
     * ... this.other.meregeHelper(String) ...      -- boolean
     * ... this.other.reverse() ...                 -- ILoString
     * ... this.other.reverseHelper(String) ...     -- ILoString
     * ... this.other.isDoubled() ...               -- boolean
     * ... this.other.isDoubledListHelper(String) ...   -- boolean
     * ... this.other.isPalindromeList() ...        -- boolean
     */
    return new ConsLoString(this.first, other.interleave(this.rest));
  }

  // adds the rest of a list to a given list
  public ILoString addRest() {
    return new ConsLoString(this.first, this.rest.addRest());
  }

  // merges two sorted lists of strings into one sorted list
  public ILoString merge(ILoString other) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.other ...          -- ILoString
     *  
     *  Methods on Parameters:
     * ... this.other.combine() ... -- String
     * ... this.other.combine() ...                 -- String
     * ... this.other.sort() ...                    -- ILoString
     * ... this.other.insert(String) ...            -- ILoString
     * ... this.other.compareStrings(String) ...    -- boolean
     * ... this.other.isSorted() ...                -- boolean
     * ... this.other.interleave(ILoString) ...     -- ILoString
     * ... this.other.addRest() ...                 -- ILoString
     * ... this.other.merge(ILoString) ...          -- ILoString
     * ... this.other.meregeHelper(String) ...      -- boolean
     * ... this.other.reverse() ...                 -- ILoString
     * ... this.other.reverseHelper(String) ...     -- ILoString
     * ... this.other.isDoubled() ...               -- boolean
     * ... this.other.isDoubledListHelper(String) ...   -- boolean
     * ... this.other.isPalindromeList() ...        -- boolean
     */
    if (other.compareStrings(this.first)) {
      return new ConsLoString(this.first, this.rest.merge(other));
    }
    return other.merge(this);
  }

  // reverses a given list of strings
  public ILoString reverse() {
    return this.rest.reverse().reverseHelper(this.first);
  }

  // helper method for reverse
  public ILoString reverseHelper(String str) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.str ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.str.concat(String) ...    -- String 
     * ... this.str.compareTo(String) ... -- int 
     */
    return new ConsLoString(this.first, this.rest.reverseHelper(str));
  }

  // determines if a list contains pairs of identical stings
  public boolean isDoubledList() {
    return this.rest.isDoubledListHelper(this.first);
  }

  // helper method for isDoubledList, compares pairs of strings in a list
  public boolean isDoubledListHelper(String str) {
    /* 
     * TEMPLATE:
     *  Parameters:
     *  ... this.str ...          -- String
     *  
     *  Methods on Parameters:
     * ... this.str.concat(String) ...    -- String 
     * ... this.str.compareTo(String) ... -- int 
     */
    if (this.first.toLowerCase().compareTo(str.toLowerCase()) == 0) {
      return this.rest.isDoubledList();
    }
    return false;
  }

  // determines if a list is a palindrome
  public boolean isPalindromeList() {
    return this.reverse().interleave(this).isDoubledList();
  }
    
}

// to represent examples for lists of strings
class ExamplesStrings {

  ILoString mary = new ConsLoString("Mary ", new ConsLoString("had ", new ConsLoString("a ",
      new ConsLoString("little ", new ConsLoString("lamb.", new MtLoString())))));

  // examples of sorted/unsorted lists
  ILoString marySorted = new ConsLoString("a ", new ConsLoString("had ", new ConsLoString("lamb.",
      new ConsLoString("little ", new ConsLoString("Mary ", new MtLoString())))));

  ILoString sortedAlphabet = new ConsLoString("A",
      new ConsLoString("b",
          new ConsLoString("C", new ConsLoString("d", new ConsLoString("e", new ConsLoString("f",
              new ConsLoString("G", new ConsLoString("h", new MtLoString()))))))));

  ILoString unsortedAlphabet = new ConsLoString("D",
      new ConsLoString("E",
          new ConsLoString("a", new ConsLoString("c", new ConsLoString("b", new ConsLoString("F",
              new ConsLoString("G", new ConsLoString("h", new MtLoString()))))))));

  //lists used to tests merge
  ILoString marySortedMerged = new ConsLoString("a ",
      new ConsLoString("a ",
          new ConsLoString("had ", new ConsLoString("had ", new ConsLoString("lamb.",
              new ConsLoString("lamb.", new ConsLoString("little ", new ConsLoString("little ",
                  new ConsLoString("Mary ", new ConsLoString("Mary ", new MtLoString()))))))))));

  ILoString maryAlphabetMerged = new ConsLoString("A", new ConsLoString("a ", new ConsLoString("b",
      new ConsLoString("C", new ConsLoString("d", new ConsLoString("e", new ConsLoString("f",
          new ConsLoString("G", new ConsLoString("h", new ConsLoString("had ", new ConsLoString(
              "lamb.",
              new ConsLoString("little ", new ConsLoString("Mary ", new MtLoString())))))))))))));

  // lists used to test isDoubled
  ILoString doubledList = new ConsLoString("d",
      new ConsLoString("d",
          new ConsLoString("c", new ConsLoString("c", new ConsLoString("b", new ConsLoString("b",
              new ConsLoString("a", new ConsLoString("a", new MtLoString()))))))));

  ILoString tripledList = new ConsLoString("b",
      new ConsLoString("b", new ConsLoString("b", new ConsLoString("a", new MtLoString()))));

  // lists used to test isPalindrome
  ILoString oddPalindromeList = new ConsLoString("this ",
      new ConsLoString("is a ", new ConsLoString("palindrome.",
          new ConsLoString("is a ", new ConsLoString("this ", new MtLoString())))));

  ILoString evenPalindromeList = new ConsLoString("this ",
      new ConsLoString("is a ", new ConsLoString("palindrome.", new ConsLoString("palindrome.",
          new ConsLoString("is a ", new ConsLoString("this ", new MtLoString()))))));

  ILoString lonelyList = new ConsLoString("only item", new MtLoString());

  // test the method combine for the lists of Strings
  boolean testCombine(Tester t) {
    return t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
  }

  // test the method sort for the classes that implement ILoString
  boolean testSort(Tester t) {
    return t.checkExpect(this.mary.sort(), this.marySorted)
        && t.checkExpect(new MtLoString().sort(), new MtLoString())
        && t.checkExpect(this.lonelyList.sort(), this.lonelyList);
  }

  // tests the helper method insert
  boolean testInsert(Tester t) {
    return t.checkExpect(new MtLoString().insert("hello"),
        new ConsLoString("hello", new MtLoString()))
        && t.checkExpect(this.lonelyList.insert("a"),
            new ConsLoString("a", new ConsLoString("only item", new MtLoString())))
        && t.checkExpect(this.lonelyList.insert("z"),
            new ConsLoString("only item", new ConsLoString("z", new MtLoString())));

  }

  // tests the helper method compareStrings
  boolean testCompareStrings(Tester t) {
    return t.checkExpect(new MtLoString().compareStrings("test"), true)
        && t.checkExpect(new ConsLoString("test", new MtLoString()).compareStrings("test"), true)
        && t.checkExpect(new ConsLoString("greavys", new MtLoString()).compareStrings("test"),
            false)
        && t.checkExpect(this.mary.compareStrings("Mary"), true);
  }

  // test the method isSorted for the classes that implement ILoString
  boolean testIsSorted(Tester t) {
    return t.checkExpect(this.sortedAlphabet.isSorted(), true)
        && t.checkExpect(this.unsortedAlphabet.isSorted(), false)
        && t.checkExpect(this.mary.isSorted(), false)
        && t.checkExpect(this.marySorted.isSorted(), true)
        && t.checkExpect(new MtLoString().isSorted(), true)
        && t.checkExpect(this.lonelyList.isSorted(), true);
  }

  // test the method interleave for the classes that implement ILoString
  boolean testInterleave(Tester t) {
    return t.checkExpect(this.sortedAlphabet.interleave(this.mary), new ConsLoString("A",
        new ConsLoString("Mary ", new ConsLoString("b", new ConsLoString("had ",
            new ConsLoString("C", new ConsLoString("a ", new ConsLoString("d",
                new ConsLoString("little ", new ConsLoString("e", new ConsLoString("lamb.",
                    new ConsLoString("f",
                        new ConsLoString("G", new ConsLoString("h", new MtLoString()))))))))))))))
        && t.checkExpect(this.unsortedAlphabet.interleave(this.mary),
            new ConsLoString("D",
                new ConsLoString("Mary ",
                    new ConsLoString("E",
                        new ConsLoString("had ",
                            new ConsLoString("a",
                                new ConsLoString("a ", new ConsLoString("c",
                                    new ConsLoString("little ", new ConsLoString("b",
                                        new ConsLoString("lamb.", new ConsLoString("F",
                                            new ConsLoString("G",
                                                new ConsLoString("h", new MtLoString()))))))))))))))
        && t.checkExpect(this.marySorted.interleave(this.marySorted), this.marySortedMerged)
        && t.checkExpect(new MtLoString().interleave(new MtLoString()), new MtLoString())
        && t.checkExpect(new MtLoString().interleave(this.mary), this.mary)
        && t.checkExpect(this.mary.interleave(new MtLoString()), this.mary)
        && t.checkExpect(new MtLoString().interleave(this.lonelyList), this.lonelyList);
  }

  // test the helper method addRest
  boolean testAddRest(Tester t) {
    return t.checkExpect(new MtLoString().addRest(), new MtLoString())
        && t.checkExpect(this.lonelyList.addRest(), this.lonelyList)
        && t.checkExpect(this.mary.addRest(), this.mary);
  }

  // test the method merge for the classes that implement ILoString
  boolean testMerge(Tester t) {
    return t.checkExpect(this.marySorted.merge(this.marySorted), this.marySortedMerged)
        && t.checkExpect(this.marySorted.merge(this.sortedAlphabet), this.maryAlphabetMerged)
        && t.checkExpect(new MtLoString().merge(new MtLoString()), new MtLoString())
        && t.checkExpect(new MtLoString().merge(this.lonelyList), this.lonelyList)
        && t.checkExpect(this.lonelyList.merge(new MtLoString()), this.lonelyList);
  }

  // test the method reverse for the classes that implement ILoStinrg
  boolean testReverse(Tester t) {
    return t.checkExpect(this.mary.reverse(),
        new ConsLoString("lamb.",
            new ConsLoString("little ",
                new ConsLoString("a ",
                    new ConsLoString("had ", new ConsLoString("Mary ", new MtLoString()))))))
        && t.checkExpect(doubledList.reverse(),
            new ConsLoString("a",
                new ConsLoString("a",
                    new ConsLoString("b", new ConsLoString("b", new ConsLoString("c",
                        new ConsLoString("c",
                            new ConsLoString("d", new ConsLoString("d", new MtLoString())))))))))
        && t.checkExpect(new MtLoString().reverse(), new MtLoString())
        && t.checkExpect(this.lonelyList.reverse(), this.lonelyList);
  }

  // test the helper method reverseHelper
  boolean testReverseHelper(Tester t) {
    return t.checkExpect(new MtLoString().reverseHelper("hello"),
        new ConsLoString("hello", new MtLoString()))
        && t.checkExpect(this.lonelyList.reverseHelper("greavys"),
            new ConsLoString("only item", new ConsLoString("greavys", new MtLoString())))
        && t.checkExpect(this.mary.reverseHelper("item"),
            new ConsLoString("Mary ",
                new ConsLoString("had ", new ConsLoString("a ", new ConsLoString("little ",
                    new ConsLoString("lamb.", new ConsLoString("item", new MtLoString())))))));

  }

  // test the method isDoubled for the classes that implement ILoString
  boolean testIsDoubled(Tester t) {
    return t.checkExpect(this.doubledList.isDoubledList(), true)
        && t.checkExpect(this.tripledList.isDoubledList(), false)
        && t.checkExpect(this.unsortedAlphabet.isDoubledList(), false)
        && t.checkExpect(this.marySortedMerged.isDoubledList(), true)
        && t.checkExpect(this.maryAlphabetMerged.isDoubledList(), false)
        && t.checkExpect(this.lonelyList.isDoubledList(), true)
        && t.checkExpect(new MtLoString().isDoubledList(), true);
  }

  // test the helper method isDoubledListHelper
  boolean testisDoubledListHelper(Tester t) {
    return t.checkExpect(new MtLoString().isDoubledListHelper("hello"), true)
        && t.checkExpect(this.lonelyList.isDoubledListHelper("hello"), false)
        && t.checkExpect(this.mary.isDoubledListHelper("Mary"), false)
        && t.checkExpect(this.marySortedMerged.isDoubledListHelper("extra"), false)
        && t.checkExpect(this.tripledList.isDoubledListHelper("b"), true);
  }

  // test the method isPalindrome for the classes that implement isDoubled
  boolean testIsPalindrome(Tester t) {
    return t.checkExpect(this.doubledList.isPalindromeList(), false)
        && t.checkExpect(this.tripledList.isPalindromeList(), false)
        && t.checkExpect(this.unsortedAlphabet.isPalindromeList(), false)
        && t.checkExpect(this.marySortedMerged.isPalindromeList(), false)
        && t.checkExpect(this.maryAlphabetMerged.isPalindromeList(), false)
        && t.checkExpect(this.evenPalindromeList.isPalindromeList(), true)
        && t.checkExpect(this.oddPalindromeList.isPalindromeList(), true)
        && t.checkExpect(this.lonelyList.isPalindromeList(), true)
        && t.checkExpect(new MtLoString().isDoubledList(), true);
  }

}
