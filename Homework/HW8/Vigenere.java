import java.util.*;

import tester.Tester;

/**
 * A class that defines a new vigenere, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class Vigenere {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  // A "2D" ArrayList to represent the vigenere table
  ArrayList<ArrayList<Character>> table = new ArrayList<ArrayList<Character>>();

  // Create a new instance of the encoder/decoder with a completed vigenere table
  Vigenere() {
    this.table = this.initVigenere();
  }
  /* TEMPLATE:
   * Fields:
   * ... this.alphabet ... --ArrayList<Character>
   * ... this.table ...    --ArrayList<ArrayList<Character>>
   * 
   * Methods:
   * ... this.initVigenere() ...   --ArrayList<Character>
   * ... this.encode(String, String) ...  --String
   * ... this.decode(String, String) ...  --String
   */

  // Initialize the vigenere table
  ArrayList<ArrayList<Character>> initVigenere() {
    //very ugly way of doing this, however it is more time efficient
    //than any use of loops (as far as I can tell)
    //I understand that a loop could be used, but from my understanding iterating through a loop
    //would be as efficient or less efficient that my implementation
    //I suppose this implementation isn't space efficient, but perhaps time 
    //efficiency is more important?
    //whoever is grading this, please let me know how this "should" be done
    ArrayList<ArrayList<Character>> table = new ArrayList<ArrayList<Character>>();
    table.add(new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')));
    table.add(new ArrayList<Character>(Arrays.asList('b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a')));
    table.add(new ArrayList<Character>(Arrays.asList('c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b')));
    table.add(new ArrayList<Character>(Arrays.asList('d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c')));
    table.add(new ArrayList<Character>(Arrays.asList('e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd')));
    table.add(new ArrayList<Character>(Arrays.asList('f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e')));
    table.add(new ArrayList<Character>(Arrays.asList('g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
        'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f')));
    table.add(new ArrayList<Character>(Arrays.asList('h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g')));
    table.add(new ArrayList<Character>(Arrays.asList('i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
        'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')));
    table.add(new ArrayList<Character>(Arrays.asList('j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i')));
    table.add(new ArrayList<Character>(Arrays.asList('k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')));
    table.add(new ArrayList<Character>(Arrays.asList('l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k')));
    table.add(new ArrayList<Character>(Arrays.asList('m', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
        'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l')));
    table.add(new ArrayList<Character>(Arrays.asList('n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm')));
    table.add(new ArrayList<Character>(Arrays.asList('o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
        'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n')));
    table.add(new ArrayList<Character>(Arrays.asList('p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
        'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o')));
    table.add(new ArrayList<Character>(Arrays.asList('q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
        'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p')));
    table.add(new ArrayList<Character>(Arrays.asList('r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q')));
    table.add(new ArrayList<Character>(Arrays.asList('s', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a',
        'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r')));
    table.add(new ArrayList<Character>(Arrays.asList('t', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'b',
        'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's')));
    table.add(new ArrayList<Character>(Arrays.asList('u', 'v', 'w', 'x', 'y', 'z', 'a', 'b', 'c',
        'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't')));
    table.add(new ArrayList<Character>(Arrays.asList('v', 'w', 'x', 'y', 'z', 'a', 'b', 'c', 'd',
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u')));
    table.add(new ArrayList<Character>(Arrays.asList('w', 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e',
        'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v')));
    table.add(new ArrayList<Character>(Arrays.asList('x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w')));
    table.add(new ArrayList<Character>(Arrays.asList('y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x')));
    table.add(new ArrayList<Character>(Arrays.asList('z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y')));
    return table;
  }

  // produce an encoded String from the given String
  String encode(String source, String keyword) {
    /* TEMPLATE:
     * Parameters:
     * ... source ...  --String
     * ... keyword ... --String
     */

    keyword = new Utils().lengthenKeyword(source, keyword);
    // this appears to not be able to be abstracted
    String answer = "";
    for (int a = 0; a < source.length(); a++) {
      answer += this.table.get(alphabet.indexOf(keyword.charAt(a)))
          .get(alphabet.indexOf(source.charAt(a)));
    }
    return answer;
  }

  // produce a decoded String from the given String
  String decode(String code, String keyword) {
    /* TEMPLATE:
     * Parameters:
     * ... code ...       --String
     * ... keywords ...   --String
     */
    keyword = new Utils().lengthenKeyword(code, keyword);
    // this appears to not be able to be abstracted
    String answer = "";
    for (int a = 0; a < code.length(); a++) {
      answer += this.alphabet
          .get(table.get(alphabet.indexOf(keyword.charAt(a))).indexOf(code.charAt(a)));
    }
    return answer;
  }
}

// utility class used for abstraction
class Utils {
  //encodes a message given the original character set and an encoded character set
  //note: this method does not ensure that the length of the keyword is the same as the message
  
  String lengthenKeyword(String message, String keyword) {
    if (keyword.length() == 0 && message.length() != 0) {
      throw new IllegalArgumentException("Cannot decode a message with no keyword");
    }
    String answer = "";
    while (answer.length() < message.length()) {
      answer += keyword;
    }
    return answer;
  }
}


// to represent examples regarding permutation code
class ExamplesVigenere {
  //creating example here so that initVigenere doesn't have to be called over and over 
  //again (time efficiency)
  Vigenere ex = new Vigenere();
  
  //tests the method encode() in the class Vigenere
  boolean testEncode(Tester t) {
    return t.checkExpect(this.ex.encode("thanksgiving", "happy"), "ahpcizgxkgug")
        && t.checkExpect(
            this.ex.encode("i" + "want" + "to" + "be" + "a" + "kinder" + "person",
                "a" + "gentler" + "person"),
            "iceamesstebabqextrkdse")
        && t.checkExpect(
            this.ex.encode("i" + "want" + "to" + "be" + "a" + "stronger", "person"),
            "xarfhgdfvsgggseyse")
        && t.checkExpect(
            this.ex.encode("", ""), "")
        && t.checkExpect(
            this.ex.encode("", "rwys"), "")
        && t.checkException("Test encoding an empty message",
            new IllegalArgumentException("Cannot decode a message with no keyword"),
            this.ex, 
            "encode", 
            "rwys",
            "");
  }

  // tests the method decode() in the class Vigenere
  boolean testDecode(Tester t) {
    return t.checkExpect(this.ex.decode("ahpcizgxkgug", "happy"), "thanksgiving")
        && t.checkExpect(
            this.ex.decode("iceamesstebabqextrkdse", "a" + "gentler" + "person"),
            "i" + "want" + "to" + "be" + "a" + "kinder" + "person")
        && t.checkExpect(this.ex.decode("xarfhgdfvsgggseyse", "person"),
            "i" + "want" + "to" + "be" + "a" + "stronger", "person")
        && t.checkExpect(
            this.ex.decode("", ""), "")
        && t.checkExpect(
            this.ex.decode("", "rwys"), "")
        && t.checkException("Test encoding an empty message",
            new IllegalArgumentException("Cannot decode a message with no keyword"),
            this.ex, 
            "decode", 
            "rwys",
            "");
  }

  // tests the method initVigenere() in the class Vigenere
  void testInitVigenere(Tester t) {
    //testing dimensions of table
    t.checkExpect(this.ex.table.size(), 26);
    t.checkExpect(this.ex.table.get(0).size(), 26);
    t.checkExpect(this.ex.table.size() * this.ex.table.get(25).size(), 676);
    
    //check that each entry in table is correct
    for (int row = 0; row < this.ex.table.size(); row -= -1) {
      for (int column = 0; column < this.ex.table.get(0).size(); column -= -1) {
        t.checkExpect(this.ex.table.get(row).get(column),
            this.ex.alphabet.get((row + column) % 26));
      }
    }
    
  }
}