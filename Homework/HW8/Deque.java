import java.util.function.Predicate;

import tester.Tester;

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;
  
  abstract int sizeHelper();

  abstract ANode<T> findHelper(Predicate<T> pred);
  
  /* TEMPLATE
   * Fields:
   * ... this.next ...      --ANode<T>
   * ... this.prev ...      --ANode<T>
   * 
   * Methods:
   * ... this.sizeHelper() ...          --int
   * ... this.findHelper(Predicate<T>)  --ANode<T>
   * 
   * Methods of Fields:
   * ... this.next.sizeHelper() ...          --int
   * ... this.next.findHelper(Predicate<T>)  --ANode<T>
   * ... this.prev.sizeHelper() ...          --int
   * ... this.prev.findHelper(Predicate<T>)  --ANode<T>
   */
}

class Node<T> extends ANode<T> {
  T data;  
  
  Node(T data) {
    this.data = data;
    this.next = null;
    this.prev = null;
  }
  
  Node(T data, ANode<T> next, ANode<T> prev) {
    this.data = data;
    this.next = next;
    this.prev = prev;
    
    if (this.next == null) {
      throw new IllegalArgumentException("Given \"next\" node does not exist");
    }
    else {
      this.next.prev = this;
    }
    
    if (this.prev == null) {
      throw new IllegalArgumentException("Given \"prev\" node does not exist");
    }
    else {
      this.prev.next = this;
    }
  }
  /* TEMPLATE
   * Fields:
   * ... this.data ...      --T
   * ... this.next ...      --ANode<T>
   * ... this.prev ...      --ANode<T>
   * 
   * Methods:
   * ... this.sizeHelper() ...          --int
   * ... this.findHelper(Predicate<T>)  --ANode<T>
   * 
   * Methods of Fields:
   * ... this.next.sizeHelper() ...          --int
   * ... this.next.findHelper(Predicate<T>)  --ANode<T>
   * ... this.prev.sizeHelper() ...          --int
   * ... this.prev.findHelper(Predicate<T>)  --ANode<T>
   */

  int sizeHelper() {
    return 1 + this.next.sizeHelper();
  }

  ANode<T> findHelper(Predicate<T> pred) {
    /* TEMPLATE
     * Parameter:
     * ... pred ...   --Predicate<T>
     * 
     * Methods of parameters:
     * ... pred.test(T) ...   --boolean
     */
    if (pred.test(data)) {
      return this;
    }
    return this.next.findHelper(pred);
  }
  
}

class Sentinel<T> extends ANode<T> {
  
  Sentinel() {
    this.next = this;
    this.prev = this;
  }
  /* TEMPLATE
   * Fields:
   * ... this.next ...      --ANode<T>
   * ... this.prev ...      --ANode<T>
   * 
   * Methods:
   * ... this.size() ...                    --int
   * ... this.sizeHelper() ...              --int
   * ... this.addAtHead(T) ...              --void
   * ... this.addAtTail(T) ...              --void
   * ... this.removeFromHead() ...          --ANode<T>
   * ... this.removeFromTail() ...          --ANode<T>
   * ... this.find(Predicate<T>) ...        --ANode<T>
   * ... this.findHelper(Predicate<T>) ...  --ANode<T>
   * 
   * Methods of Fields:
   * ... this.next.sizeHelper() ...          --int
   * ... this.next.findHelper(Predicate<T>)  --ANode<T>
   * ... this.prev.sizeHelper() ...          --int
   * ... this.prev.findHelper(Predicate<T>)  --ANode<T>
   */

  int size() {
    return this.next.sizeHelper();
  }
  
  int sizeHelper() {
    return 0;
  }
  
  void addAtHead(T item) {
    /* TEMPLATE
     * Parameters:
     * ... item ...   --T
     */
    this.next = new Node<T>(item, this.next, this);
  }
  
  void addAtTail(T item) {
    /* TEMPLATE
     * Parameters:
     * ... item ...   --T
     */
    this.prev = new Node<T>(item, this, this.prev);
  }

  T removeFromHead() {
    if (this.next == this) {
      throw new RuntimeException("Can't remove a node from an empty list");
    }
    // casting is acceptable since the previous "if statement" checks that this.next
    // cannot be of type Sentinel
    Node<T> temp = (Node<T>) this.next;
    this.next = temp.next;
    this.next.prev = this;
    return temp.data;
  }

  T removeFromTail() {
    if (this.prev == this) {
      throw new RuntimeException("Can't remove a node from an empty list");
    }
    // casting is acceptable since the previous "if statement" checks that this.prev
    // cannot be of type Sentinel
    Node<T> temp = (Node<T>) this.prev;
    this.prev = temp.prev;
    this.prev.next = this;
    return temp.data;
  }
  
  ANode<T> find(Predicate<T> pred) {
    /* TEMPLATE
     * Parameter:
     * ... pred ...   --Predicate<T>
     * 
     * Methods of parameters:
     * ... pred.test(T) ...   --boolean
     */
    return this.next.findHelper(pred);
  }

  ANode<T> findHelper(Predicate<T> pred) {
    /* TEMPLATE
     * Parameter:
     * ... pred ...   --Predicate<T>
     * 
     * Methods of parameters:
     * ... pred.test(T) ...   --boolean
     */
    return this;
  }
  
}

class Deque<T> {
  Sentinel<T> header;
  
  Deque() {
    this.header = new Sentinel<T>();
  }
  
  Deque(Sentinel<T> header) {
    this.header = header;
  }
  /* TEMPLATE
   * Fields:
   * ... this.header ...      --Sentinel<T>
   * 
   * Methods:
   * ... this.size() ...                    --int
   * ... this.addAtHead(T) ...              --void
   * ... this.addAtTail(T) ...              --void
   * ... this.removeFromHead() ...          --ANode<T>
   * ... this.removeFromTail() ...          --ANode<T>
   * ... this.find(Predicate<T>) ...        --ANode<T>
   * 
   * Methods of Fields:
   * ... this.header.size() ...                    --int
   * ... this.header.sizeHelper() ...              --int
   * ... this.header.addAtHead(T) ...              --void
   * ... this.header.addAtTail(T) ...              --void
   * ... this.header.removeFromHead() ...          --ANode<T>
   * ... this.header.removeFromTail() ...          --ANode<T>
   * ... this.header.find(Predicate<T>) ...        --ANode<T>
   * ... this.header.findHelper(Predicate<T>) ...  --ANode<T>
   */
  
  int size() {
    return this.header.size();
  }
  
  void addAtHead(T item) {
    /* TEMPLATE
     * Parameters:
     * ... item ...   --T
     */
    this.header.addAtHead(item);
  }
  
  void addAtTail(T item) {
    /* TEMPLATE
     * Parameters:
     * ... item ...   --T
     */
    this.header.addAtTail(item);
  }
  
  T removeFromHead() {
    return this.header.removeFromHead();
  }
  
  T removeFromTail() {
    return this.header.removeFromTail();
  }
  
  ANode<T> find(Predicate<T> pred) {
    /* TEMPLATE
     * Parameter:
     * ... pred ...   --Predicate<T>
     * 
     * Methods of parameters:
     * ... pred.test(T) ...   --boolean
     */
    return this.header.find(pred);
  }
  
}

class ExamplesDeque {
  Deque<String> deque1 = new Deque<String>();
  
  Sentinel<String> initialAlphabet = new Sentinel<String>();
  Node<String> abc = new Node<String>("abc");
  Node<String> bcd = new Node<String>("bcd");
  Node<String> cde = new Node<String>("cde");
  Node<String> def = new Node<String>("def");
  Deque<String> deque2 = new Deque<String>(this.initialAlphabet);
  
  Sentinel<Integer> initialNums = new Sentinel<Integer>();
  Node<Integer> one = new Node<Integer>(1);
  Node<Integer> three = new Node<Integer>(3);
  Node<Integer> two = new Node<Integer>(2);
  Node<Integer> four = new Node<Integer>(4);
  Node<Integer> five = new Node<Integer>(5);
  Deque<Integer> deque3 = new Deque<Integer>(this.initialNums);

  void initData() {
    this.deque1 = new Deque<String>();

    this.initialAlphabet = new Sentinel<String>();
    this.abc = new Node<String>("abc", initialAlphabet, initialAlphabet);
    this.bcd = new Node<String>("bcd", initialAlphabet, abc);
    this.cde = new Node<String>("cde", initialAlphabet, bcd);
    this.def = new Node<String>("def", initialAlphabet, cde);
    this.deque2 = new Deque<String>(initialAlphabet);

    this.initialNums = new Sentinel<Integer>();
    this.one = new Node<Integer>(1, initialNums, initialNums);
    this.three = new Node<Integer>(3, initialNums, one);
    this.two = new Node<Integer>(2, initialNums, three);
    this.four = new Node<Integer>(4, initialNums, two);
    this.five = new Node<Integer>(5, initialNums, four);
    this.deque3 = new Deque<Integer>(initialNums);

  }

  // tests the method size() in the class Deque
  // since this method in deque is simply a call to the same method in the classes
  // that implement ANode, this method sufficiently tests size()
  // in the classes that implment ANode
  boolean testSize(Tester t) {
    this.initData();
    return t.checkExpect(this.deque1.size(), 0) && t.checkExpect(this.deque2.size(), 4)
        && t.checkExpect(this.deque3.size(), 5);
  }

  // tests the method sizeHelper() in the class Deque
  // since this method in deque is simply a call to the same method in the classes
  // that implement ANode, this method sufficiently tests sizeHelper()
  // in the classes that implment ANode
  boolean testSizeHelper(Tester t) {
    this.initData();
    return t.checkExpect(this.deque1.header.size(), 0)
        && t.checkExpect(this.deque2.header.size(), 4)
        && t.checkExpect(this.deque3.header.size(), 5);
  }

  // tests the method addAtHead(T) in the class Deque
  // since this method in deque is simply a call to the same method in the classes
  // that implement ANode, this method sufficiently tests addAtHead(T)
  // in the classes that implment ANode
  void testAddAtHead(Tester t) {
    this.initData();

    // check values before mutation
    t.checkExpect(this.deque1, new Deque<String>());

    t.checkExpect(this.abc.prev, this.initialAlphabet);
    t.checkExpect(this.initialAlphabet.next, this.abc);

    t.checkExpect(this.one.prev, this.initialNums);
    t.checkExpect(this.initialNums.next, this.one);

    // mutate
    this.deque1.addAtHead("greavys");
    this.deque2.addAtHead("ensidiya");
    this.deque3.addAtHead(444);

    // check values after mutation
    t.checkExpect(this.deque1.header.next,
        new Node<String>("greavys", this.deque1.header.next, this.deque1.header.next));
    t.checkExpect(this.initialAlphabet.next,
        new Node<String>("ensidiya", this.abc, this.initialAlphabet));
    t.checkExpect(this.initialNums.next, new Node<Integer>(444, this.one, this.initialNums));
  }

  // tests the method addAtTail(T) in the class Deque
  // since this method in deque is simply a call to the same method in the classes
  // that implement ANode, this method sufficiently tests AddAtTail(T)
  // in the classes that implment ANode
  void testAddAtTail(Tester t) {
    this.initData();

    // check values before mutation
    t.checkExpect(this.deque1, new Deque<String>());

    t.checkExpect(this.abc.prev, this.initialAlphabet);
    t.checkExpect(this.initialAlphabet.next, this.abc);

    t.checkExpect(this.one.prev, this.initialNums);
    t.checkExpect(this.initialNums.next, this.one);

    // mutate
    this.deque1.addAtTail("greavys");
    this.deque2.addAtTail("ensidiya");
    this.deque3.addAtTail(444);

    // check values after mutation
    t.checkExpect(this.deque1.header.prev,
        new Node<String>("greavys", this.deque1.header.next, this.deque1.header.next));
    t.checkExpect(this.initialAlphabet.prev,
        new Node<String>("ensidiya", this.initialAlphabet, this.def));
    t.checkExpect(this.initialNums.prev, new Node<Integer>(444, this.initialNums, this.five));
  }

  // tests the method removeFromHead() in the class Deque
  // since this method in deque is simply a call to the same method in the classes
  // that implement ANode, this method sufficiently tests removeFromHead()
  // in the classes that implment ANode
  void testRemoveFromHead(Tester t) {
    this.initData();

    // check values before mutation
    t.checkExpect(this.deque1, new Deque<String>());

    t.checkExpect(this.abc.prev, this.initialAlphabet);
    t.checkExpect(this.initialAlphabet.next, this.abc);

    t.checkExpect(this.one.prev, this.initialNums);
    t.checkExpect(this.initialNums.next, this.one);

    // mutate
    t.checkException("Test removing a node from an empty deque",
        new RuntimeException("Can't remove a node from an empty list"), this.deque1,
        "removeFromHead");
    t.checkExpect(this.deque2.removeFromHead(), "abc");
    t.checkExpect(this.deque3.removeFromHead(), 1);

    // check values after mutation
    t.checkExpect(this.initialAlphabet.next, this.bcd);
    t.checkExpect(this.initialNums.next, this.three);
    t.checkExpect(this.deque2.size(), 3);
    t.checkExpect(this.deque3.size(), 4);
  }

  // tests the method removeFromTail() in the class Deque
  // since this method in deque is simply a call to the same method in the classes
  // that implement ANode, this method sufficiently tests removeFromTail()
  // in the classes that implment ANode
  void testRemoveFromTail(Tester t) {
    this.initData();

    // check values before mutation
    t.checkExpect(this.deque1, new Deque<String>());

    t.checkExpect(this.abc.prev, this.initialAlphabet);
    t.checkExpect(this.initialAlphabet.next, this.abc);

    t.checkExpect(this.one.prev, this.initialNums);
    t.checkExpect(this.initialNums.next, this.one);

    // mutate
    t.checkException("Test removing a node from an empty deque",
        new RuntimeException("Can't remove a node from an empty list"), this.deque1,
        "removeFromTail");
    t.checkExpect(this.deque2.removeFromTail(), "def");
    t.checkExpect(this.deque3.removeFromTail(), 5);

    // check values after mutation
    t.checkExpect(this.initialAlphabet.prev, this.cde);
    t.checkExpect(this.initialNums.prev, this.four);
    t.checkExpect(this.deque2.size(), 3);
    t.checkExpect(this.deque3.size(), 4);
  }

  // tests the method find(Predicate<T>) in the class Deque
  // since this method in deque is simply a call to the same method in the classes
  // that implement ANode, this method sufficiently tests find(Predicate<T>)
  // in the classes that implment ANode
  void testFind(Tester t) {
    this.initData();

    // used to test find returning the first element it finds
    this.deque2.addAtTail("bcd");
    this.deque3.addAtTail(2);

    t.checkExpect(this.deque1.find(abc -> abc.equals("abc")), this.deque1.header);
    t.checkExpect(this.deque2.find(bcd -> bcd.equals("bcd")), this.bcd);
    t.checkExpect(this.deque3.find(two -> two == 2), this.two);
  }

  // tests the method findHelper(Predicate<T>) in the class Deque
  // since this method in deque is simply a call to the same method in the classes
  // that implement ANode, this method sufficiently tests findHelper(Predicate<T>)
  // in the classes that implment ANode
  void testFindHelper(Tester t) {
    this.initData();

    // used to test find returning the first element it finds
    this.deque2.addAtTail("bcd");
    this.deque3.addAtTail(2);

    t.checkExpect(this.deque1.header.findHelper(abc -> abc.equals("abc")), this.deque1.header);
    t.checkExpect(this.deque2.find(bcd -> bcd.equals("bcd")), this.bcd);
    t.checkExpect(this.deque3.find(two -> two == 2), this.two);
  }

}