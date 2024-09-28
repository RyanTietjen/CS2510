import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import tester.Tester;


interface ILoString {
  
}

interface IList<T> {
  IList<T> filter(Predicate<T> pred);
  
  <U> IList<U> map(Function<T,U> converter);
  
  <U> U fold(BiFunction<T,U,U> converter,U initial); 
  
}

/*
TEMPLATE:
---------
Fields:
Methods:
... this.filter(Predicate<T> pred) ...                      -- IList<T>
... this.map(Function<T, U> converter) ...                  -- IList<U>
... this.fold(BiFunction<T, U, U> converter, U initial) ... -- <U> U
Methods for Fields:
*/

class MtList<T> implements IList<T> {
  
  MtList() {}

  @Override
  //Returns an MtList<T>()
  public IList<T> filter(Predicate<T> pred) {
    // TODO Auto-generated method stub
    return new MtList<T>();
  }

  @Override
  //Returns an MtList<T>()
  public <U> IList<U> map(Function<T, U> converter) {
    // TODO Auto-generated method stub
    return new MtList<U>();
  }

  @Override
  //Returns the argument U initial
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    // TODO Auto-generated method stub
    return initial;
  }
}

/*
TEMPLATE:
---------
Fields:
... this.first ...                             -- T
... this.rest ...                              -- IList<T>
Methods:
... this.filter(Predicate<T> pred) ...                      -- IList<T>
... this.map(Function<T, U> converter) ...                  -- IList<U>
... this.fold(BiFunction<T, U, U> converter, U initial) ... -- <U> U
Methods for Fields:
*/

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;
  
  ConsList(T first,IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  @Override
  // returns an IList<T> w/o elements that match the given function
  public IList<T> filter(Predicate<T> pred) {
    // TODO Auto-generated method stub
    if (pred.test(this.first)) {
      return new ConsList<T>(this.first,this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  @Override
  // changes elements of a list without changing its length
  public <U> IList<U> map(Function<T, U> converter) {
    // TODO Auto-generated method stub
    return new ConsList<U>(converter.apply(this.first),this.rest.map(converter));
  }

  @Override
  // looks at the individual elements of list and outputs some value based on a function
  public <U> U fold(BiFunction<T, U, U> converter, U initial) {
    // TODO Auto-generated method stub
    return converter.apply(this.first, this.rest.fold(converter,initial));
  }
}

/*
TEMPLATE:
---------
Fields:
Methods:
... this.test(String t) ...                      -- boolean
Methods for Fields:
*/

class FirstLetterJ implements Predicate<String> {
  //Determines if the first character is equal to J
  public boolean test(String t) {
    return t.substring(0,1).equals("J");
  }
}

/*
TEMPLATE:
---------
Fields:
Methods:
... this.apply(String t, Integer sum) ...         -- Integer
Methods for Fields:
*/

class NumEndInEr implements BiFunction<String, Integer, Integer> {
  // returns sum + 1 if the last two characters of a string are "er" otherwise returns sum
  public Integer apply(String t, Integer sum) {
    if (t.substring(t.length() - 2, t.length()).equals("er")) {
      return 1 + sum;
    }
    return sum;
  }
}

/*
TEMPLATE:
---------
Fields:
Methods:
... this.apply(String t) ...                      -- String
Methods for Fields:
*/

class AbbreviateName implements Function<String, String> {
  //returns first 3 characters of a String
  public String apply(String t) {
    return t.substring(0,3);
  }
}
  
class ExamplesLists {
  ExamplesLists() {}
  
  IList<String> months = new ConsList<String>("January",
      new ConsList<String>("Febuary",
          new ConsList<String>("March",
              new ConsList<String>("April",
                  new ConsList<String>("May",
                      new ConsList<String>("June", new ConsList<String>("July",
                          new ConsList<String>("August", new ConsList<String>("September",
                              new ConsList<String>("October", new ConsList<String>("November",
                                  new ConsList<String>("December", new MtList<String>()))))))))))));
  
  boolean test(Tester t) {
    return t.checkExpect(months.filter(new FirstLetterJ()), new ConsList<String>("January",
        new ConsList<String>("June", new ConsList<String>("July", new MtList<String>()))))
        && t.checkExpect(months.fold(new NumEndInEr(), 0), 4)
        && t.checkExpect(months.map(new AbbreviateName()),
            new ConsList<String>("Jan",
                new ConsList<String>("Feb",
                    new ConsList<String>("Mar",
                        new ConsList<String>("Apr",
                            new ConsList<String>("May",
                                new ConsList<String>("Jun", new ConsList<String>("Jul",
                                    new ConsList<String>("Aug", new ConsList<String>("Sep",
                                        new ConsList<String>("Oct", new ConsList<String>("Nov",
                                            new ConsList<String>(
                                                "Dec", new MtList<String>())))))))))))));
  }
}