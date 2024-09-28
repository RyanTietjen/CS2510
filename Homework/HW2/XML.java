import tester.Tester;

// to represent a an XML 
interface IXML {
  int contentLength();
  
  boolean hasTag(String name);
  
  boolean hasAttribute(String name);
  
  String renderAsString();
  
}

// to represent plain text
// i.e. XML text that is neither tagged nor untagged
class Plaintext implements IXML {
  String txt;
  
  Plaintext(String txt) {
    this.txt = txt;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.txt ...   -- String
   *  
   *  Methods:
   *  ... this.contentLength() ...        -- int
   *  ... this.hasTag(String) ...         -- boolean
   *  ... this.hasAttribute(String) ...   -- boolean
   *  ... this.renderAsString() ...       -- String
   *  
   */
  
  // returns the number of characters in the XML
  // excluding tags and attributes
  public int contentLength() {
    return this.txt.length();
  }
  
  // determines if the XML has a specified tag
  public boolean hasTag(String name) {
    return false;
  }
  
  //determines if the XML has a specified attribute
  public boolean hasAttribute(String name) {
    return false;
  }
  
  //produces the XML as a string
  public String renderAsString() {
    return txt;
  }
}

class Untagged implements IXML {
  ILoXML content;
  
  Untagged(ILoXML content) {
    this.content = content;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.content ...   -- ILoXML
   *  
   *  Methods:
   *  ... this.contentLength() ...        -- int
   *  ... this.hasTag(String) ...         -- boolean
   *  ... this.hasAttribute(String) ...   -- boolean
   *  ... this.renderAsString() ...       -- String
   *  
   *   Methods of fields:
   *  ... this.content.contentLength() ...        -- int
   *  ... this.content.hasTag(String) ...         -- boolean
   *  ... this.content.hasAttribute(String) ...   -- boolean
   *  ... this.content.renderAsString() ...       -- String
   *  
   */
  
  // returns the number of characters in the XML
  // excluding tags and attributes
  public int contentLength() {
    return this.content.contentLength();
  }
  
  // determines if the XML has a specified tag
  public boolean hasTag(String name) {
    return this.content.hasTag(name);
  }
  
  // determines if the XML has a specified attribute
  public boolean hasAttribute(String name) {
    return this.content.hasAttribute(name);
  }
  
  // produces the XML as a string
  public String renderAsString() {
    return content.renderAsString();
  }
  
}

class Tagged implements IXML {
  Tag tag;
  ILoXML content;
  
  Tagged(Tag tag, ILoXML content) {
    this.tag = tag;
    this.content = content;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.tag ...       -- Tag
   *  ... this.content ...   -- ILoXML
   *  
   *  Methods:
   *  ... this.contentLength() ...           -- int
   *  ... this.hasTag(String) ...            -- boolean
   *  ... this.hasAttribute(String) ...      -- boolean
   *  ... this.renderAsString() ...          -- String
   *  
   *   Methods of fields:
   *  ... this.tag.getName() ...                 -- String
   *  ... this.tag.hasAttribute(String) ...      -- boolean
   *  ... this.content.contentLength() ...       -- int
   *  ... this.content.hasTag(String) ...        -- boolean
   *  ... this.content.hasAttribute(String) ...  -- boolean
   *  ... this.content.renderAsString() ...      -- String
   *  
   *  
   */
  
  // returns the number of characters in the XML
  // excluding tags and attributes
  public int contentLength() {
    return this.content.contentLength();
  }
  
  // determines if the XML has a specified tag
  public boolean hasTag(String name) {
    return this.tag.getName().equals(name) || this.content.hasTag(name);
  }
  
  // determines if the XML has a specified attribute
  public boolean hasAttribute(String name) {
    return this.tag.hasAttribute(name) || this.content.hasAttribute(name);
  }
  
  // produces the XML as a string
  public String renderAsString() {
    return this.content.renderAsString();
  }
}

// to represent a list of XML
interface ILoXML {

  int contentLength();

  boolean hasTag(String name);
  
  boolean hasAttribute(String name);
  
  String renderAsString();
}

// to represent an empty list of XML
class MtLoXML implements ILoXML { 
  // the constructor
  MtLoXML() {
    
  }
  /* TEMPLATE:
   *  Fields:
   *  
   *  Methods:
   *  ... this.contentLength() ...           -- int
   *  ... this.hasTag(String) ...            -- boolean
   *  ... this.hasAttribute(String) ...      -- boolean
   *  ... this.renderAsString() ...          -- String
   *  
   */
  
  // returns the number of characters in the XML
  // excluding tags and attributes
  public int contentLength() {
    return 0;
  }
  
  //determines if the XML has a specified tag
  public boolean hasTag(String name) {
    return false;
  }
  
  //determines if the XML has a specified attribute
  public boolean hasAttribute(String name) {
    return false;
  }
  
  //produces the XML as a string
  public String renderAsString() {
    return "";
  }
  
}

// to represent a non-empty list of XML
class ConsLoXML implements ILoXML {
  IXML first;
  ILoXML rest;
  
  // the constructor
  ConsLoXML(IXML first, ILoXML rest) {
    this.first = first;
    this.rest = rest; 
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.first ... -- IXML
   *  ... this.rest ...  -- ILoXML
   *  
   *  Methods:
   *  ... this.contentLength() ...           -- int
   *  ... this.hasTag(String) ...            -- boolean
   *  ... this.hasAttribute(String) ...      -- boolean
   *  ... this.renderAsString() ...          -- String
   *  
   *   Methods of fields:
   *  ... this.first.contentLength() ...       -- int
   *  ... this.first.hasTag(String) ...        -- boolean
   *  ... this.first.hasAttribute(String) ...  -- boolean
   *  ... this.first.renderAsString() ...      -- String   
   *  ... this.rest.contentLength() ...       -- int
   *  ... this.rest.hasTag(String) ...        -- boolean
   *  ... this.rest.hasAttribute(String) ...  -- boolean
   *  ... this.rest.renderAsString() ...      -- String
   *  
   */
  
  // returns the number of characters in the XML
  // excluding tags and attributes
  public int contentLength() {
    return this.first.contentLength() + this.rest.contentLength();
  }
  
  //determines if the XML has a specified tag
  public boolean hasTag(String name) {
    return this.first.hasTag(name) || this.rest.hasTag(name);
  }
  
  //determines if the XML has a specified attribute
  public boolean hasAttribute(String name) {
    return this.first.hasAttribute(name) || this.rest.hasAttribute(name);
  }
  
  //produces the XML as a string
  public String renderAsString() {
    return this.first.renderAsString() + this.rest.renderAsString();
  }
}

// to represent a tag that an IXML might be tagged with
class Tag {
  String name;
  ILoAtt atts;
  
  // the constructor
  Tag(String name, ILoAtt atts) {
    this.name = name;
    this.atts = atts;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.name ... -- String
   *  ... this.atts ... -- ILoAtt
   *  
   *  Methods:
   *  ... this.hasAttribute(String) ...   -- boolean
   *  ... this.getName() ...              -- String
   *  
   *   Methods of fields:
   *  ... this.atts.hasAttribute(String) ...  -- boolean
   *  
   */
  
  // determines if this tag contains the specified attribute
  public boolean hasAttribute(String name) {
    return this.name.equals(name) || this.atts.hasAttribute(name);
  }
  
  // returns the name of the tag
  public String getName() {
    return name;
  }
}

// to represent an attribute that a tag might have
class Att {
  String name;
  String value;
  
  // the constructor
  Att(String name, String value) {
    this.name = name;
    this.value = value;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.name ...  -- String
   *  ... this.value ... -- String
   *  
   *  Methods:
   *  ... this.hasAttribute(String) ...   -- boolean
   *  
   */
  
  // determines if this attribute matches the specified name
  public boolean hasAttribute(String name) {
    return this.name.equals(name);
  }
}

// to represent a list of attributes
interface ILoAtt {

  boolean hasAttribute(String name);
  
}

// to represent an empty list of attributes
class MtLoAtt implements ILoAtt {
  
  // the constructor
  MtLoAtt() {
    
  }
  /* TEMPLATE:
   *  Methods:
   *  ... this.hasAttribute(String) ...   -- boolean
   *  
   */
  
  // determines if this list of attributes contains a specified attribute
  public boolean hasAttribute(String name) {
    return false;
  }
}

// to represent a non-empty list of attributes
class ConsLoAtt implements ILoAtt {
  Att first;
  ILoAtt rest;
  
  // the constructor
  ConsLoAtt(Att first, ILoAtt rest) {
    this.first = first;
    this.rest = rest;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.first ... -- Att
   *  ... this.rest ...  -- ILoAtt
   *  
   *  Methods:
   *  ... this.hasAttribute(String) ...   -- boolean
   *  
   *   Methods of fields:
   *  ... this.first.name ...                 -- String
   *  ... this.first.value ...                -- String
   *  ... this.rest.hasAttribute(String) ...  -- boolean
   *  
   */
  
  //determines if this list of attributes contains a specified attribute
  public boolean hasAttribute(String name) {
    return first.hasAttribute(name) || rest.hasAttribute(name);
  }
}


class ExamplesXML {
  ILoXML mtXML = new MtLoXML();
  ILoAtt mtAtt = new MtLoAtt();

  // Example 1
  IXML xml1 = new Plaintext("I am XML!");

  // Example 2
  Tag yell = new Tag("yell", mtAtt);

  IXML xml2 = new Untagged(new ConsLoXML(new Plaintext("I am "),
      (new ConsLoXML(new Tagged(yell, new ConsLoXML(new Plaintext("XML"), mtXML)),
          new ConsLoXML(new Plaintext("!"), mtXML)))));

  // Example 3
  Tag italic = new Tag("italic", mtAtt);

  IXML xml3 = new Untagged(new ConsLoXML(new Plaintext("I am "),
      (new ConsLoXML(new Tagged(yell, 
          new ConsLoXML(new Tagged(italic, new ConsLoXML(new Plaintext("X"), mtXML)), 
              new ConsLoXML(new Plaintext("ML"), mtXML))), 
          new ConsLoXML(new Plaintext("!"), mtXML)))));
  
  
  // Example 4
  Tag yellVolume = new Tag("yell",  new ConsLoAtt(new Att("volume", "30db"), mtAtt));
  
  IXML xml4 = new Untagged(new ConsLoXML(new Plaintext("I am "),
      (new ConsLoXML(new Tagged(yellVolume, 
          new ConsLoXML(new Tagged(italic, new ConsLoXML(new Plaintext("X"), mtXML)), 
              new ConsLoXML(new Plaintext("ML"), mtXML))), 
          new ConsLoXML(new Plaintext("!"), mtXML)))));
  
  // Example 5
  Tag yellVolumeDuration = new Tag("yell",  new ConsLoAtt(new Att("volume", "30db"), 
      new ConsLoAtt(new Att("duration", "5sec"), mtAtt)));
  
  IXML xml5 = new Untagged(new ConsLoXML(new Plaintext("I am "),
      (new ConsLoXML(new Tagged(yellVolumeDuration, 
          new ConsLoXML(new Tagged(italic, new ConsLoXML(new Plaintext("X"), mtXML)), 
              new ConsLoXML(new Plaintext("ML"), mtXML))), 
          new ConsLoXML(new Plaintext("!"), mtXML)))));
  
  
  // Note: since the examples provided are thorough and contain instances
  // of all types of lists, individual testing of each class is redundant 
  // test the method contentLength in the classes that implement IXML
  boolean testContentLength(Tester t) {
    return t.checkExpect(mtXML.contentLength(), 0)
        && t.checkExpect(xml1.contentLength(), 9)
        && t.checkExpect(xml2.contentLength(), 9)
        && t.checkExpect(xml3.contentLength(), 9)
        && t.checkExpect(xml4.contentLength(), 9)
        && t.checkExpect(xml5.contentLength(), 9);
  }
  
  //test the method hasTag in the classes that implement IXML
  boolean testHasTag(Tester t) {
    return t.checkExpect(mtXML.hasTag("empty"), false)
        && t.checkExpect(xml1.hasTag("yell"), false)
        && t.checkExpect(xml1.hasTag("italic"), false)
        && t.checkExpect(xml1.hasTag("empty"), false)
        && t.checkExpect(xml2.hasTag("yell"), true)
        && t.checkExpect(xml2.hasTag("italic"), false)
        && t.checkExpect(xml2.hasTag("empty"), false)
        && t.checkExpect(xml3.hasTag("yell"), true)
        && t.checkExpect(xml3.hasTag("italic"), true)
        && t.checkExpect(xml3.hasTag("empty"), false)
        && t.checkExpect(xml4.hasTag("yell"), true)
        && t.checkExpect(xml4.hasTag("italic"), true)
        && t.checkExpect(xml4.hasTag("empty"), false)
        && t.checkExpect(xml5.hasTag("yell"), true)
        && t.checkExpect(xml5.hasTag("italic"), true)
        && t.checkExpect(xml5.hasTag("empty"), false);
  }
  
  //test the method hassAttribute in the classes that implement IXML
  boolean testHasAttribute(Tester t) {
    return t.checkExpect(mtXML.hasTag("empty"), false)
        && t.checkExpect(xml1.hasAttribute("volume"), false)
        && t.checkExpect(xml1.hasAttribute("duration"), false)
        && t.checkExpect(xml1.hasAttribute("empty"), false)
        && t.checkExpect(xml2.hasAttribute("volume"), false)
        && t.checkExpect(xml2.hasAttribute("duration"), false)
        && t.checkExpect(xml2.hasAttribute("empty"), false)
        && t.checkExpect(xml3.hasAttribute("volume"), false)
        && t.checkExpect(xml3.hasAttribute("duration"), false)
        && t.checkExpect(xml3.hasAttribute("empty"), false)
        && t.checkExpect(xml4.hasAttribute("volume"), true)
        && t.checkExpect(xml4.hasAttribute("duration"), false)
        && t.checkExpect(xml4.hasAttribute("empty"), false)
        && t.checkExpect(xml5.hasAttribute("volume"), true)
        && t.checkExpect(xml5.hasAttribute("duration"), true)
        && t.checkExpect(xml5.hasAttribute("empty"), false);
  }
  
  //test the method renderAsString in the classes that implement IXML
  boolean testRenderAsString(Tester t) {
    return t.checkExpect(mtXML.renderAsString(), "")
        && t.checkExpect(xml1.renderAsString(), "I am XML!")
        && t.checkExpect(xml2.renderAsString(), "I am XML!")
        && t.checkExpect(xml3.renderAsString(), "I am XML!")
        && t.checkExpect(xml4.renderAsString(), "I am XML!")
        && t.checkExpect(xml5.renderAsString(), "I am XML!");
  }
}

