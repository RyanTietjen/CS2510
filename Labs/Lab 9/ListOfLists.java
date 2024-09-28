import java.util.*;
import tester.Tester;

//to represent a list of lists
class ListOfLists<T> implements Iterable<T> {
  ArrayList<ArrayList<T>> list = new ArrayList<ArrayList<T>>();

  ListOfLists() {

  }
  /* TEMPLATE:
   * Fields:
   * ... this.list... --ArrayList<ArrayList<T>>
   * 
   * Methods:
   * ... this.addNewList() ...      --void
   * ... this.add(int, T) ...       --void
   * ... this.get(int) ...          --ArrayList<T>
   * ... this.size() ...            --int
   * ... this.iterator() ...        --Iterator<T>
   * 
   */

  // adds a new empty ArrayList<T> to the end of the list-of-lists.
  void addNewList() {
    this.list.add(new ArrayList<T>());
  }

  // adds the provided object to the end of the ArrayList<T> at the provided index
  // in the list-of-lists
  void add(int index, T object) {
    if (index < this.list.size()) {
      list.get(index).add(object);
    }
    else {
      throw new IndexOutOfBoundsException("There does not exist a list at index " + index);
    }
  }

  // returns the ArrayList<T> at the provided index in the list-of-lists
  ArrayList<T> get(int index) {
    if (index < this.list.size()) {
      return this.list.get(index);
    }
    else {
      throw new IndexOutOfBoundsException("There does not exist a list at index " + index);
    }
  }

  // returns the number of lists in this list-of-lists.
  int size() {
    return list.size();
  }

  public Iterator<T> iterator() {
    return new ListOfListsIterator<T>(this);
  }
}


class ListOfListsIterator<T> implements Iterator<T> {
  ListOfLists<T> alol;
  int lolIdx;
  int listIdx;
  
  ListOfListsIterator(ListOfLists<T> alol) {
    this.alol = alol;
    this.lolIdx = 0;
    this.listIdx = 0;
  }
  /* TEMPLATE:
   * Fields:
   * ... this.alol ...      --ListOfLists<T>
   * ... this.nextIdx ...   --int
   * ... this.listIdx ...   --int
   * 
   * Methods:
   * ... this.hasNext() ...   --boolean
   * ... this.next() ...      --T
   */

  // Does this sequence (of items in the array list) have at least one more value?
  public boolean hasNext() {
    return this.lolIdx < this.alol.size() && this.listIdx < this.alol.get(this.lolIdx).size();
  }
  
  // Get the next value in this sequence
  // EFFECT: Advance the iterator to the subsequent value
  public T next() {
    T item = this.alol.get(this.lolIdx).get(this.listIdx++);
    if (listIdx >= this.alol.get(this.lolIdx).size()) {
      this.lolIdx -= -1;
      this.listIdx = 0;
    }
    return item;
  }
  
}

class ExamplesLists {
  ListOfLists<Integer> lol = new ListOfLists<Integer>();
  
  void initData() {
    this.lol = new ListOfLists<Integer>();
    // add 3 lists
    this.lol.addNewList();
    this.lol.addNewList();
    this.lol.addNewList();

    // add elements 1,2,3 in first list
    this.lol.add(0, 1);
    this.lol.add(0, 2);
    this.lol.add(0, 3);

    // add elements 4,5,6 in second list
    this.lol.add(1, 4);
    this.lol.add(1, 5);
    this.lol.add(1, 6);

    // add elements 7,8,9 in third list
    this.lol.add(2, 7);
    this.lol.add(2, 8);
    this.lol.add(2, 9);
  }
  
 
  // this method sufficiently tests add(), addNewList() in the class ListOfLists
  // and also next() and hasNext() in the class ListOfListsIterator
  void testListOfLists(Tester t) {
    this.initData();
    // iterator should return elements in order 1,2,3,4,5,6,7,8,9
    int number = 1;
    for (Integer num : this.lol) {
      t.checkExpect(num, number);
      number = number + 1;
    }
  }
  
  //tests the method get in the class ListOfLists
  boolean testGet(Tester t) {
    this.initData();
    return t.checkExpect(this.lol.get(0), Arrays.asList(1, 2, 3))
        && t.checkExpect(this.lol.get(1), Arrays.asList(4, 5, 6))
        && t.checkExpect(this.lol.get(2), Arrays.asList(7, 8, 9))
        && t.checkException("Test get index out of bounds",
            new IndexOutOfBoundsException("There does not exist a list at index 3"),
            this.lol,
            "get",
            3);
  }
  
  //tests the method size in the class ListOfLists
  boolean testSize(Tester t) {
    this.initData();
    return t.checkExpect(this.lol.size(), 3)
        && t.checkExpect(new ListOfLists<String>().size(), 0);
  }
}