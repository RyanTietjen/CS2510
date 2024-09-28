import java.util.Comparator;

import tester.Tester;

// to represent a generic binary search tree
abstract class ABST<T> {
  Comparator<T> order;
  
  //inserts an item into this tree
  abstract ABST<T> insert(T t);
  
  //determines if an item is present in this tree
  abstract boolean present(T t);
  
  //returns the leftmost item in this tree
  abstract T getLeftmost();
  
  //helper method for getLeftmost, updates what the
  //current leftmost item is
  abstract T updateLeftmost(T that);
  
  //returns this tree absent of the leftmost items
  abstract ABST<T> getRight();
  
  //removes a given item from a tree, if possible
  abstract ABST<T> remove(T that);
  
  //determines if two BSTs are the same
  abstract boolean sameTree(ABST<T> that);
  
  //determines if two leaves are the same
  abstract boolean sameLeaf(Leaf<T> that);
  
  //determines if two nodes are the same
  abstract boolean sameNode(Node<T> that);
  
  //returns if this ABST is a leaf
  abstract boolean isLeaf();
  
  //returns if this ABST is a node
  abstract boolean isNode();
  
  //determines if this tree and another tree have the same data
  abstract boolean sameData(ABST<T> that);
  
  //helper method for sameData, determines if every item in
  //this tree can be found in that tree
  abstract boolean containsAll(ABST<T> that);
  
  //builds a list in the order of this BST
  abstract IList<T> buildList();
  
  ABST(Comparator<T> order) {
    this.order = order;
  }

}

class Leaf<T> extends ABST<T> {

  Leaf(Comparator<T> order) {
    super(order);
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.order ... -- Comparator<T>
   *  
   *  Methods:
   *  ... this.insert(T) ...            --ABST<T>
   *  ... this.present(T) ...           --boolean
   *  ... this.getLeftmost() ...        --T
   *  ... this.updateLeftmost(T) ...    --T
   *  ... this.getRight() ...           --ABST<T>
   *  ... this.remove(T) ...            --ABST<T>
   *  ... this.sameTree(ABST<T>) ...    --boolean
   *  ... this.sameLeaf(Leaf<T>) ...    --boolean
   *  ... this.sameNode(Node<T>) ...    --boolean
   *  ... this.isLeaf() ...             -- boolean
   *  ... this.isNode() ...             --boolean
   *  ... this.sameData(ABST<T>) ...    --boolean
   *  ... this.containsAll(ABST<T>) ... --boolean
   *  ... this.buildList() ...           -IList<T>
   *  
   *  Methods of Fields:
   *  ... this.order.compare(T, T) ...  --int
   *  
   */
  
  
  ABST<T> insert(T t) {
    return new Node<T>(this.order, t, new Leaf<T>(this.order), new Leaf<T>(this.order));
  }

  // returns false as nothing is present in a leaf
  boolean present(T t) {
    return false;
  }
  
  // gives an exception that indicate no item
  T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }
  
  // returns the argument T
  T updateLeftmost(T that) {
    return that;
  }
  
  // gives an exception that indicate no item
  ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }
  
  // returns this T
  ABST<T> remove(T that) {
    return this;
  }

  // determines if that tree is a Leaf and whether matches that Leaf
  boolean sameTree(ABST<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- ABST<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    if (that.isLeaf()) {
      return this.sameLeaf((Leaf<T>) that);
    }
    return false;
  }
  
  // returns a boolean indicating whether this Leaf and that Leaf as the same
  boolean sameLeaf(Leaf<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- Leaf<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    return this.order.equals(that.order);
  }
  
  // returns false as this is not a Node
  boolean sameNode(Node<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- Node<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    return false;
  }
  
  // returns true as it is a Leaf
  boolean isLeaf() {
    return true;
  }
  
  // returns false as Leaf is not a Node
  boolean isNode() {
    return false;
  }
 
  // returns true as there is no data
  boolean sameData(ABST<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- ABST<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    return true;
  }
  
  // returns true as it contains all in empty tree
  boolean containsAll(ABST<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- ABST<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    return true;
  }
  
  // returns MtList
  IList<T> buildList() {
    return new MtList<T>();
  }

}

class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.order ... -- Comparator<T>
   *  ... this.data ...  -- T
   *  ... this.left ...  --ABST<T>
   *  ... this.right ... --ABST<T>
   *  
   *  Methods:
   *  ... this.insert(T) ...            --ABST<T>
   *  ... this.present(T) ...           --boolean
   *  ... this.getLeftmost() ...        --T
   *  ... this.updateLeftmost(T) ...    --T
   *  ... this.getRight() ...           --ABST<T>
   *  ... this.remove(T) ...            --ABST<T>
   *  ... this.sameTree(ABST<T>) ...    --boolean
   *  ... this.sameLeaf(Leaf<T>) ...    --boolean
   *  ... this.sameNode(Node<T>) ...    --boolean
   *  ... this.isLeaf() ...             -- boolean
   *  ... this.isNode() ...             --boolean
   *  ... this.sameData(ABST<T>) ...    --boolean
   *  ... this.containsAll(ABST<T>) ... --boolean
   *  ... this.buildList() ...           -IList<T>
   *  
   *  Methods of Fields:
   *  ... this.order.compare(T, T) ...        --int
   *  ... this.left.insert(T) ...             --ABST<T>
   *  ... this.left.present(T) ...            --boolean
   *  ... this.left.getLeftmost() ...         --T
   *  ... this.left.updateLeftmost(T) ...     --T
   *  ... this.left.getRight() ...            --ABST<T>
   *  ... this.left.remove(T) ...             --ABST<T>
   *  ... this.left.sameTree(ABST<T>) ...     --boolean
   *  ... this.left.sameLeaf(Leaf<T>) ...     --boolean
   *  ... this.left.sameNode(Node<T>) ...     --boolean
   *  ... this.left.isLeaf() ...              -- boolean
   *  ... this.left.isNode() ...              --boolean
   *  ... this.left.sameData(ABST<T>) ...     --boolean
   *  ... this.left.containsAll(ABST<T>) ...  --boolean
   *  ... this.left.buildList() ...           -IList<T>
   *  ... this.right.insert(T) ...            --ABST<T>
   *  ... this.right.present(T) ...           --boolean
   *  ... this.right.getLeftmost() ...        --T
   *  ... this.right.updateLeftmost(T) ...    --T
   *  ... this.right.getRight() ...           --ABST<T>
   *  ... this.right.remove(T) ...            --ABST<T>
   *  ... this.right.sameTree(ABST<T>) ...    --boolean
   *  ... this.right.sameLeaf(Leaf<T>) ...    --boolean
   *  ... this.right.sameNode(Node<T>) ...    --boolean
   *  ... this.right.isLeaf() ...             -- boolean
   *  ... this.right.isNode() ...             --boolean
   *  ... this.right.sameData(ABST<T>) ...    --boolean
   *  ... this.right.containsAll(ABST<T>) ... --boolean
   *  ... this.right.buildList() ...           -IList<T>
   *  
   */
  
  // 
  ABST<T> insert(T t) { 
    if (this.order.compare(t, this.data) < 0) {
      return new Node<T>(this.order, this.data, this.left.insert(t), this.right);
    }
    return new Node<T>(this.order, this.data, this.left, this.right.insert(t));
  }
  
  // determines whether argument is present in the tree
  boolean present(T t) {
    return this.order.compare(this.data, t) == 0 || this.left.present(t) || this.right.present(t);
  }
  
  // returns the left most part of the tree
  T getLeftmost() {
    return this.left.updateLeftmost(this.data);
  }
  
  // changes the left most part of the tree to that
  T updateLeftmost(T that) {
    return this.getLeftmost();
  }
  
  // returns the right most part of the tree
  ABST<T> getRight() {
    if (this.order.compare(this.getLeftmost(), this.data) == 0) {
      return this.right;
    }
    return this.remove(this.getLeftmost());
  }
  
  // removes that from the tree
  ABST<T> remove(T that) {
    if (this.order.compare(this.data, that) == 0) {
      return new Leaf<T>(this.order);
    }
    return new Node<T>(this.order, this.data, this.left.remove(that), this.right.remove(that));
  }
  
  // determines if data and structure of this and that tree are the same
  boolean sameTree(ABST<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- ABST<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    if (that.isNode()) {
      return this.sameNode((Node<T>) that) 
          && this.left.sameTree(((Node<T>) that).left) 
          && this.right.sameTree(((Node<T>) that).right);
    }
    return false;
  }
  
  // returns false as this is not a Leaf
  boolean sameLeaf(Leaf<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- Leaf<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    return false;
  }
  
  //returns a boolean depending on whether this and that node are the same
  boolean sameNode(Node<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- Node<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    return this.order.compare(this.data, that.data) == 0;
  }
  
  //returns false as this is not a Leaf
  boolean isLeaf() {
    return false;
  }
  
  //returns true as this is a Node
  boolean isNode() {
    return true;
  }
  
  //returns a boolean based on whether the contains of this and that are the same
  boolean sameData(ABST<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- ABST<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    return this.containsAll(that) && that.containsAll(this); 
  }
  
  //everything in this tree is contained in that tree
  boolean containsAll(ABST<T> that) {
    /* TEMPLATE:
     *  Parameters:
     *  ... that ... -- ABST<T>
     *  
     *  Methods of Parameters:
     *  ... that.insert(T) ...            --ABST<T>
     *  ... that.present(T) ...           --boolean
     *  ... that.getLeftmost() ...        --T
     *  ... that.updateLeftmost(T) ...    --T
     *  ... that.getRight() ...           --ABST<T>
     *  ... that.remove(T) ...            --ABST<T>
     *  ... that.sameTree(ABST<T>) ...    --boolean
     *  ... that.sameLeaf(Leaf<T>) ...    --boolean
     *  ... that.sameNode(Node<T>) ...    --boolean
     *  ... that.isLeaf() ...             -- boolean
     *  ... that.isNode() ...             --boolean
     *  ... that.sameData(ABST<T>) ...    --boolean
     *  ... that.containsAll(ABST<T>) ... --boolean
     *  ... that.buildList() ...           -IList<T>
     */
    return that.present(this.data) && this.left.containsAll(that) && this.right.containsAll(that);
  }
  
  IList<T> buildList() {
    return new ConsList<T>(this.getLeftmost(), this.getRight().buildList());
  }
}

// To represent a book with a title, author, and price
class Book {
  String title;
  String author;
  int price;

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.title ...  --String
   *  ... this.author ... --String
   *  ... this.price ...  --int
   *  
   *  Methods of fields:
   *  ... this.title.concat(String) ...    -- String 
   *  ... this.title.compareTo(String) ... -- int 
   *  ... this.author.concat(String) ...    -- String 
   *  ... this.author.compareTo(String) ... -- int 
   */

}

// to sort Books by title
class BooksByTitle implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.title.compareTo(b2.title); 
  }
}

// to sort Books by author
class BooksByAuthor implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.author.compareTo(b2.author); 
  }
}

// to sort Books by price
class BooksByPrice implements Comparator<Book> {
  public int compare(Book b1, Book b2) {
    return b1.price - b2.price; 
  }
}

interface IList<T> {
  
}

class MtList<T> implements IList<T> { 
  MtList() {}
  /* TEMPLATE:
   * 
   */
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.first ... --T
   *  ... this.rest...   --IList<T>
   */
}
  
// to represent examples of generic binary search trees
class ExamplesABST {
  //examples of BSTs of Books
  Leaf<Book> leafTitle = new Leaf<Book>(new BooksByTitle());
  Leaf<Book> leafAuthor = new Leaf<Book>(new BooksByAuthor());
  Leaf<Book> leafPrice = new Leaf<Book>(new BooksByPrice());
  Book theGrapesOfWrath = new Book("The Grapes of Wrath", "John Steinbeck", 20);
  Book ghostInTheWires = new Book("Ghost in the Wires", "Kevin Mitnick", 15);
  Book tkam = new Book("To Kill a Mockingbird", "Harper Lee", 60);
  Book greatGatsby = new Book("The Great Gatsby", "F. Scott Fitsgerald", 30);
  Book lordOfTheFlies = new Book("Lord of the Flies", "William Golding", 10);
  Book freeFall = new Book("Free fall", "William Golding", 10);
  Book mobyDick = new Book("Moby Dick", "Herman Melville", 25);

  //BST ordered by title
  //                      "The Grapes of Wrath"
  //                          /                   \
  //            "Ghost in the Wires"             "To Kill a Mockingbird"
  //                 /              \                     /
  //           "Free Fall"  "Lord of the Flies"  "The Great Gatsby"
  ABST<Book> exampleByTitle = new Node<Book>(new BooksByTitle(), this.theGrapesOfWrath,
      new Node<Book>(new BooksByTitle(), this.ghostInTheWires,
          new Node<Book>(new BooksByTitle(), this.freeFall, this.leafTitle, this.leafTitle),
          new Node<Book>(new BooksByTitle(), this.lordOfTheFlies, this.leafTitle, this.leafTitle)),
      new Node<Book>(new BooksByTitle(), this.tkam,
          new Node<Book>(new BooksByTitle(), this.greatGatsby, this.leafTitle, this.leafTitle),
          this.leafTitle));

  //BST ordered by author
  //                      "John Steinbeck"
  //                                /\
  //                    "Harper Lee"   "Kevin Mitnick"
  //                     /                     \
  //            "F. Scott Fitsgerald"    "William Golding"
  //                                             \
  //                                          "William Golding"
  ABST<Book> exampleByAuthor = new Node<Book>(new BooksByAuthor(), this.theGrapesOfWrath,
      new Node<Book>(new BooksByAuthor(), this.tkam,
          new Node<Book>(new BooksByAuthor(), this.greatGatsby, this.leafAuthor, this.leafAuthor),
          this.leafAuthor),
      new Node<Book>(new BooksByAuthor(), this.ghostInTheWires, this.leafAuthor, new Node<Book>(
          new BooksByAuthor(), this.lordOfTheFlies, this.leafAuthor,
          new Node<Book>(new BooksByAuthor(), this.freeFall, this.leafAuthor, this.leafAuthor))));

  //BST ordered by price
  //                                20
  //                                /\
  //                             15   60
  //                            /     /                 
  //                           10    30
  //                            \ 
  //                             10                 
  ABST<Book> exampleByPrice = new Node<Book>(new BooksByPrice(), this.theGrapesOfWrath,
      new Node<Book>(new BooksByPrice(), this.ghostInTheWires,
          new Node<Book>(new BooksByPrice(), this.lordOfTheFlies, this.leafPrice,
              new Node<Book>(new BooksByPrice(), this.freeFall, this.leafPrice, this.leafPrice)),
          this.leafPrice),
      new Node<Book>(new BooksByPrice(), this.tkam,
          new Node<Book>(new BooksByPrice(), this.greatGatsby, this.leafPrice, this.leafPrice),
          this.leafPrice));

  // tests the method insert(T) in the classes that extend ABST
  // this method is sufficiently tested
  boolean testInsert(Tester t) {
    // test by title
    return t.checkExpect(new Leaf<Book>(new BooksByTitle()).insert(this.mobyDick),
        new Node<Book>(new BooksByTitle(), this.mobyDick, this.leafTitle, this.leafTitle))
        && t.checkExpect(this.exampleByTitle.insert(this.mobyDick), new Node<Book>(
            new BooksByTitle(), this.theGrapesOfWrath,
            new Node<Book>(new BooksByTitle(), this.ghostInTheWires,
                new Node<Book>(new BooksByTitle(), this.freeFall, this.leafTitle, this.leafTitle),
                new Node<Book>(new BooksByTitle(), this.lordOfTheFlies, this.leafTitle,
                    new Node<Book>(new BooksByTitle(), this.mobyDick, this.leafTitle,
                        this.leafTitle))),
            new Node<Book>(new BooksByTitle(), this.tkam,
                new Node<Book>(new BooksByTitle(), this.greatGatsby, this.leafTitle,
                    this.leafTitle),
                this.leafTitle)))
        && t.checkExpect(this.exampleByTitle.insert(this.greatGatsby), new Node<Book>(
            new BooksByTitle(), this.theGrapesOfWrath,
            new Node<Book>(new BooksByTitle(), this.ghostInTheWires,
                new Node<Book>(new BooksByTitle(), this.freeFall, this.leafTitle, this.leafTitle),
                new Node<Book>(new BooksByTitle(), this.lordOfTheFlies, this.leafTitle,
                    this.leafTitle)),
            new Node<Book>(new BooksByTitle(), this.tkam,
                new Node<Book>(new BooksByTitle(), this.greatGatsby, this.leafTitle, new Node<Book>(
                    new BooksByTitle(), this.greatGatsby, this.leafTitle, this.leafTitle)),
                this.leafTitle)))
        // test by author
        && t.checkExpect(new Leaf<Book>(new BooksByAuthor()).insert(this.mobyDick),
            new Node<Book>(new BooksByAuthor(), this.mobyDick, this.leafAuthor, this.leafAuthor))
        && t.checkExpect(this.exampleByAuthor.insert(this.mobyDick),
            new Node<Book>(new BooksByAuthor(), this.theGrapesOfWrath,
                new Node<Book>(new BooksByAuthor(), this.tkam,
                    new Node<Book>(new BooksByAuthor(), this.greatGatsby, this.leafAuthor,
                        this.leafAuthor),
                    new Node<Book>(new BooksByAuthor(), this.mobyDick, this.leafAuthor,
                        this.leafAuthor)),
                new Node<Book>(new BooksByAuthor(), this.ghostInTheWires, this.leafAuthor,
                    new Node<Book>(new BooksByAuthor(), this.lordOfTheFlies, this.leafAuthor,
                        new Node<Book>(new BooksByAuthor(), this.freeFall, this.leafAuthor,
                            this.leafAuthor)))))
        && t.checkExpect(this.exampleByAuthor.insert(this.greatGatsby),
            new Node<Book>(new BooksByAuthor(), this.theGrapesOfWrath,
                new Node<Book>(new BooksByAuthor(), this.tkam,
                    new Node<Book>(new BooksByAuthor(), this.greatGatsby, this.leafAuthor,
                        new Node<Book>(new BooksByAuthor(), this.greatGatsby, this.leafAuthor,
                            this.leafAuthor)),
                    this.leafAuthor),
                new Node<Book>(new BooksByAuthor(), this.ghostInTheWires, this.leafAuthor,
                    new Node<Book>(new BooksByAuthor(), this.lordOfTheFlies, this.leafAuthor,
                        new Node<Book>(new BooksByAuthor(), this.freeFall, this.leafAuthor,
                            this.leafAuthor)))))
        // test by price
        && t.checkExpect(new Leaf<Book>(new BooksByPrice()).insert(this.mobyDick),
            new Node<Book>(new BooksByPrice(), this.mobyDick, this.leafPrice, this.leafPrice))
        && t.checkExpect(this.exampleByPrice.insert(this.mobyDick), new Node<Book>(
            new BooksByPrice(), this.theGrapesOfWrath,
            new Node<Book>(new BooksByPrice(), this.ghostInTheWires,
                new Node<Book>(new BooksByPrice(), this.lordOfTheFlies, this.leafPrice,
                    new Node<Book>(
                        new BooksByPrice(), this.freeFall, this.leafPrice, this.leafPrice)),
                this.leafPrice),
            new Node<Book>(new BooksByPrice(), this.tkam,
                new Node<Book>(new BooksByPrice(), this.greatGatsby,
                    new Node<Book>(new BooksByPrice(), this.mobyDick, this.leafPrice,
                        this.leafPrice),
                    this.leafPrice),
                this.leafPrice)))
        && t.checkExpect(this.exampleByPrice.insert(this.greatGatsby), new Node<Book>(
            new BooksByPrice(), this.theGrapesOfWrath,
            new Node<Book>(new BooksByPrice(), this.ghostInTheWires,
                new Node<Book>(new BooksByPrice(), this.lordOfTheFlies, this.leafPrice,
                    new Node<Book>(
                        new BooksByPrice(), this.freeFall, this.leafPrice, this.leafPrice)),
                this.leafPrice),
            new Node<Book>(new BooksByPrice(), this.tkam,
                new Node<Book>(new BooksByPrice(), this.greatGatsby, this.leafPrice, new Node<Book>(
                    new BooksByPrice(), this.greatGatsby, this.leafPrice, this.leafPrice)),
                this.leafPrice)));
  }

  // Tests the method present(T) for the classes that extend ABST
  // testing complete
  boolean testPresent(Tester t) {
    return t.checkExpect(this.exampleByPrice.present(this.theGrapesOfWrath), true)
        && t.checkExpect(this.exampleByPrice.present(this.mobyDick), false)
        && t.checkExpect(this.leafAuthor.present(this.ghostInTheWires), false);
  }

  // Tests the method getLeftmost() for the classes that extend ABST
  // testing complete
  boolean testGetLeftmost(Tester t) {
    return t.checkExpect(this.exampleByTitle.getLeftmost(), this.freeFall)
        && t.checkExpect(this.exampleByAuthor.getLeftmost(), this.greatGatsby)
        && t.checkExpect(this.exampleByPrice.getLeftmost(), this.lordOfTheFlies)
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.leafTitle, "getLeftmost")
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.leafAuthor, "getLeftmost")
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.leafPrice, "getLeftmost");
  }

  // Tests the method testUpdateLeftmost(T) for the classes that extend ABST
  // testing complete
  boolean testUpdateLeftmost(Tester t) {
    return t.checkExpect(this.exampleByTitle.updateLeftmost(this.mobyDick), this.freeFall)
        && t.checkExpect(this.leafTitle.updateLeftmost(this.mobyDick), this.mobyDick);
  }

  // Tests the method getRight() for the classes that extend ABST
  // testing complete
  boolean testGetRight(Tester t) {
    return t.checkExpect(this.exampleByTitle.getLeftmost(), this.freeFall)
        && t.checkExpect(this.exampleByAuthor.getLeftmost(), this.greatGatsby)
        && t.checkExpect(this.exampleByPrice.getLeftmost(), this.lordOfTheFlies)
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.leafTitle, "getLeftmost")
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.leafAuthor, "getLeftmost")
        && t.checkException(new RuntimeException("No leftmost item of an empty tree"),
            this.leafPrice, "getLeftmost");
  }

  // Tests the method remove(T) for the classes that extend ABST
  // testing complete
  boolean testRemove(Tester t) {
    return t.checkExpect(this.leafAuthor.remove(this.freeFall), this.leafAuthor)
        && t.checkExpect(this.exampleByAuthor.remove(this.freeFall),
            new Node<Book>(new BooksByAuthor(), this.theGrapesOfWrath,
                new Node<Book>(new BooksByAuthor(), this.tkam,
                    new Node<Book>(new BooksByAuthor(), this.greatGatsby, this.leafAuthor,
                        this.leafAuthor),
                    this.leafAuthor),
                new Node<Book>(new BooksByAuthor(), this.ghostInTheWires, this.leafAuthor,
                    this.leafAuthor)))
        && t.checkExpect(this.exampleByTitle.remove(this.theGrapesOfWrath), this.leafTitle);
  }

  // Tests the method sameTree(ABST<T>) for the classes that extend ABST
  // testing complete
  boolean testSameTree(Tester t) {
    return t.checkExpect(this.exampleByPrice.sameTree(this.exampleByPrice), true)
        && t.checkExpect(this.exampleByPrice.sameTree(this.exampleByAuthor), false)
        && t.checkExpect(this.exampleByPrice.sameTree(this.leafAuthor), false);
  }

  // Tests the method sameLeaf(ABST<T>) for the classes that extend ABST
  // testing complete
  boolean testSameLeaf(Tester t) {
    return t.checkExpect(this.leafAuthor.sameLeaf(this.leafAuthor), true)
        && t.checkExpect(this.leafTitle.sameLeaf(this.leafAuthor), false);
  }

  // Tests the method sameNode(ABST<T>) for the classes that extend ABST
  // testing complete
  boolean testSameNode(Tester t) {
    return t
        .checkExpect(
            new Node<Book>(new BooksByTitle(), this.tkam, this.leafTitle, this.leafTitle).sameNode(
                new Node<Book>(new BooksByTitle(), this.tkam, this.leafTitle, this.leafTitle)),
            true)
        && t.checkExpect(
            new Node<Book>(new BooksByTitle(), this.tkam, this.leafTitle, this.leafTitle)
                .sameNode(new Node<Book>(new BooksByTitle(), this.ghostInTheWires, this.leafTitle,
                    this.leafTitle)),
            false);
  }

  // Tests the method isLeaf() for the classes that extend ABST
  // testing complete
  boolean testIsLeaf(Tester t) {
    return t.checkExpect(this.leafAuthor.isLeaf(), true)
        && t.checkExpect(this.exampleByAuthor.isLeaf(), false);
  }

  // Tests the method isNode() for the classes that extend ABST
  // testing complete
  boolean testIsNode(Tester t) {
    return t.checkExpect(this.leafAuthor.isNode(), false)
        && t.checkExpect(this.exampleByAuthor.isNode(), true);
  }

  // Tests the method sameData() for the classes that extend ABST
  // testing complete
  boolean testSameData(Tester t) {
    return t.checkExpect(this.leafAuthor.sameData(this.exampleByAuthor), true)
        && t.checkExpect(this.exampleByAuthor.sameData(this.exampleByAuthor), true)
        && t.checkExpect(this.exampleByTitle.sameData(this.exampleByTitle), true)
        && t.checkExpect(this.exampleByPrice.sameData(this.exampleByAuthor), true)
        && t.checkExpect(this.exampleByAuthor.sameData(this.leafAuthor), false);
  }

  // Tests the method containsAll() for the classes that extend ABST
  // testing complete
  boolean testContainsAll(Tester t) {
    return t.checkExpect(this.leafAuthor.containsAll(this.exampleByAuthor), true)
        && t.checkExpect(this.exampleByAuthor.containsAll(this.exampleByAuthor), true)
        && t.checkExpect(this.exampleByTitle.containsAll(this.exampleByTitle), true)
        && t.checkExpect(this.exampleByPrice.containsAll(this.exampleByAuthor), true)
        && t.checkExpect(this.exampleByAuthor.containsAll(this.leafAuthor), false);
  }

  IList<Book> titleList = new ConsList<Book>(this.freeFall,
      new ConsList<Book>(this.ghostInTheWires,
          new ConsList<Book>(this.theGrapesOfWrath, new ConsList<Book>(this.greatGatsby,
              new ConsList<Book>(this.tkam, new MtList<Book>())))));
  IList<Book> authorList = new ConsList<Book>(this.greatGatsby,
      new ConsList<Book>(this.tkam,
          new ConsList<Book>(this.theGrapesOfWrath,
              new ConsList<Book>(this.ghostInTheWires, new ConsList<Book>(this.lordOfTheFlies,
                  new ConsList<Book>(this.freeFall, new MtList<Book>()))))));
  IList<Book> priceList = new ConsList<Book>(this.lordOfTheFlies,
      new ConsList<Book>(this.ghostInTheWires,
          new ConsList<Book>(this.theGrapesOfWrath, new ConsList<Book>(this.greatGatsby,
              new ConsList<Book>(this.tkam, new MtList<Book>())))));

  // Tests the method buildList() for the classes that extend ABST
  // testing incomplete/may be incorrect
  boolean testBuildList(Tester t) {
    return t.checkExpect(this.leafAuthor.buildList(), new MtList<Book>())
        && t.checkExpect(this.exampleByTitle.buildList(), this.titleList)
        && t.checkExpect(this.exampleByAuthor.buildList(), this.authorList)
        && t.checkExpect(this.exampleByPrice.buildList(), this.priceList);
  }

}
