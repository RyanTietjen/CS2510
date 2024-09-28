import java.util.*;

import tester.Tester;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }
  /* TEMPLATE:
   * Fields:
   * ... this.alphabet ... --ArrayList<Character>
   * ... this.code ...     --ArrayList<Character>
   * ... this.rand ...     --Random
   * 
   * Methods:
   * ... this.initEncoder() ...   --ArrayList<Character>
   * ... this.encode(String) ...  --String
   * ... this.decode(String) ...  --String
   */

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> alphabetCopy = new ArrayList<Character>(
        Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    ArrayList<Character> code = new ArrayList<Character>();
    for (int i = 0; i < 26; i++) {
      int num = rand.nextInt(26 - i);
      code.add(alphabetCopy.get(num));
      alphabetCopy.remove(num);
    }
    return code;
  }

  // produce an encoded String from the given String
  String encode(String source) {
    /* TEMPLATE:
     * Parameters:
     * ... source ... --String
     */
    return new Utils().codeAMessage(source, this.code, this.alphabet);
  }

  // produce a decoded String from the given String
  String decode(String code) {
    /* TEMPLATE:
     * Parameters:
     * ... code ... --String
     */
    return new Utils().codeAMessage(code, this.alphabet, this.code);
  }

}

// utility class to abstract methods in PermutationCode
class Utils {
  
  //encodes a message given the original character set and an encoded character set
  String codeAMessage(String message, ArrayList<Character> original, ArrayList<Character> code) {
    /* TEMPLATE:
     * Parameters:
     * ... message ...      --String
     * ... original ...     --ArrayList<Character>
     * ... code ...         --ArrayList<Character>
     */
    String str = "";
    for (int i = 0; i < message.length(); i++) {
      str += original.get(code.indexOf(message.charAt(i)));
    }
    return str;
  }
  
}

// to represent examples regarding permutation code
class ExamplesPermutations {
  ArrayList<Character> original = new ArrayList<Character>(
      Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
  ArrayList<Character> backwards = new ArrayList<Character>(
      Arrays.asList('z', 'y', 'x', 'w', 'v', 'u', 't', 's', 'r', 'q', 'p', 'o', 'n', 'm', 'l', 'k',
          'j', 'i', 'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'));
  ArrayList<Character> slightShift = new ArrayList<Character>(
      Arrays.asList('b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
          'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a'));
  ArrayList<Character> givenExample = new ArrayList<Character>(
      Arrays.asList('b', 'e', 'a', 'c', 'd', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
          'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

  // tests the method decode in the class PermutationCode
  boolean testDecode(Tester t) {
    return t.checkExpect(new PermutationCode(this.original).decode("fundies"), "fundies")
        && t.checkExpect(new PermutationCode(this.backwards).decode("fundies"), "ufmwrvh")
        && t.checkExpect(new PermutationCode(this.slightShift).decode("fundies"), "etmchdr")
        && t.checkExpect(new PermutationCode(this.givenExample).decode("abeedc"), "cabbed");
  }

  // tests the method encode in the class PermutationCode
  boolean testEncode(Tester t) {
    return t.checkExpect(new PermutationCode(this.givenExample).encode("badace"), "ebcbad")
        && t.checkExpect(new PermutationCode(this.original).encode("fundies"), "fundies")
        && t.checkExpect(new PermutationCode(this.slightShift).encode("etmchdr"), "fundies")
        && t.checkExpect(new PermutationCode(this.backwards).encode("ufmwrvh"), "fundies");
  }

  // tests the method encode in the class PermutationCode
  boolean testInitEncoder(Tester t) {
    PermutationCode ex = new PermutationCode();
    //checks that each element generated by initEncode is contained in alphabet
    // i.e. checks that the ArrayList contains all letters of the alphabet
    for (int i = 0; i < 26; i++) {
      t.checkExpect(ex.code.contains(ex.alphabet.get(i)), true);
    }
    //checks that the size of the ArrayList is the size of the alphabet
    return t.checkExpect(ex.code.size(), 26);

  }

}