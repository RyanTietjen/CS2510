import tester.*;

// runs tests for the buddies problem
class ExamplesBuddies {
  ILoBuddy mt;

  // examples to represent the people
  Person ann;
  Person bob;
  Person cole;
  Person dan;
  Person ed;
  Person fay;
  Person gabi;
  Person hank;
  Person jan;
  Person kim;
  Person len;

  ILoBuddy annsBuddies;
  ILoBuddy bobsBuddies;
  ILoBuddy hanksBuddies;

  // initalizes the examples of people and their list of buddies
  void initBuddies() {
    this.mt = new MTLoBuddy();

    this.ann = new Person("Ann");
    this.bob = new Person("Bob");
    this.cole = new Person("Cole");
    this.dan = new Person("Dan");
    this.ed = new Person("Ed");
    this.fay = new Person("Fay");
    this.gabi = new Person("Gabi");
    this.hank = new Person("Hank");
    this.jan = new Person("Jan");
    this.kim = new Person("Kim");
    this.len = new Person("Len");

    // adds the list of buddies to their respective
    this.ann.addBuddy(this.bob);
    this.ann.addBuddy(this.cole);

    this.bob.addBuddy(this.ann);
    this.bob.addBuddy(this.ed);
    this.bob.addBuddy(this.hank);

    this.cole.addBuddy(this.dan);

    this.dan.addBuddy(this.cole);

    this.ed.addBuddy(this.fay);

    this.fay.addBuddy(this.ed);
    this.fay.addBuddy(this.gabi);

    this.gabi.addBuddy(this.ed);
    this.gabi.addBuddy(this.fay);

    this.jan.addBuddy(this.kim);
    this.jan.addBuddy(this.len);

    this.kim.addBuddy(this.jan);
    this.kim.addBuddy(this.len);

    this.len.addBuddy(this.jan);
    this.len.addBuddy(this.kim);

    annsBuddies = new ConsLoBuddy(this.cole, new ConsLoBuddy(this.bob, this.mt));
    bobsBuddies = new ConsLoBuddy(this.hank,
        new ConsLoBuddy(this.ed, new ConsLoBuddy(this.ann, this.mt)));
    hanksBuddies = this.mt;
  }

  // tests the method addBuddy(Person) in the class Person
  void testAddBuddy(Tester t) {
    this.initBuddies();

    // checks the people's buddies before mutation
    t.checkExpect(this.ann.buddies, annsBuddies);
    t.checkExpect(this.bob.buddies, bobsBuddies);
    t.checkExpect(this.hank.buddies, hanksBuddies);

    // check the people's buddies after mutation
    this.ann.addBuddy(this.dan);
    this.ann.addBuddy(this.fay);
    t.checkExpect(this.ann.buddies,
        new ConsLoBuddy(this.fay, new ConsLoBuddy(this.dan, annsBuddies)));

    this.bob.addBuddy(this.bob);
    t.checkExpect(this.bob.buddies, bobsBuddies);

    this.hank.addBuddy(this.ann);
    t.checkExpect(this.hank.buddies, new ConsLoBuddy(this.ann, hanksBuddies));
  }

  // tests the method hasDirectBuddy(Person) in the class Person
  void testHasDirectBuddy(Tester t) {
    this.initBuddies();

    t.checkExpect(this.ann.hasDirectBuddy(this.bob), true);
    t.checkExpect(this.ann.hasDirectBuddy(this.cole), true);
    t.checkExpect(this.ann.hasDirectBuddy(this.ed), false);
    t.checkExpect(this.hank.hasDirectBuddy(this.bob), false);
    t.checkExpect(this.jan.hasDirectBuddy(this.bob), false);
    t.checkExpect(this.jan.hasDirectBuddy(this.kim), true);
    t.checkExpect(this.bob.hasDirectBuddy(this.ed), true);
    t.checkExpect(this.bob.hasDirectBuddy(this.cole), false);
    t.checkExpect(this.ann.hasDirectBuddy(this.ann), false);
  }

  // tests the method countCommonBuddies(Person) in the class Person
  void testCountCommonBuddies(Tester t) {
    this.initBuddies();

    t.checkExpect(this.ann.countCommonBuddies(this.bob), 0);
    t.checkExpect(this.ann.countCommonBuddies(this.dan), 1);
    t.checkExpect(this.hank.countCommonBuddies(this.bob), 0);
    t.checkExpect(this.jan.countCommonBuddies(this.kim), 1);
    t.checkExpect(this.kim.countCommonBuddies(this.jan), 1);
    t.checkExpect(this.kim.countCommonBuddies(this.len), 1);
    t.checkExpect(this.ann.countCommonBuddies(this.ann), 2);
  }

  // tests the method hasExtendedBuddy(Person) in the class Person
  void testHasExtendedBuddy(Tester t) {
    this.initBuddies();

    t.checkExpect(this.ann.hasExtendedBuddy(this.ann), true);
    t.checkExpect(this.ann.hasExtendedBuddy(this.len), false);
    t.checkExpect(this.ann.hasExtendedBuddy(this.dan), true);
    t.checkExpect(this.dan.hasExtendedBuddy(this.ann), false);
    t.checkExpect(this.ann.hasExtendedBuddy(this.hank), true);
    t.checkExpect(this.hank.hasExtendedBuddy(this.ann), false);
    t.checkExpect(this.ann.hasExtendedBuddy(this.fay), true);
  }

  // tests the method hasExtendedBuddy(Person, ILoBuddy) in the class Person
  void testHasExtendedBuddyHelper(Tester t) {
    t.checkExpect(this.ann.hasExtendedBuddyHelper(this.ann, new MTLoBuddy()), true);
    t.checkExpect(this.ann.hasExtendedBuddyHelper(this.len, new MTLoBuddy()), false);
    t.checkExpect(this.ann.hasExtendedBuddyHelper(this.dan, new MTLoBuddy()), true);
    t.checkExpect(this.dan.hasExtendedBuddyHelper(this.ann, new MTLoBuddy()), false);
    t.checkExpect(this.dan.hasExtendedBuddyHelper(this.ann, this.annsBuddies), false);
    t.checkExpect(this.dan.hasExtendedBuddyHelper(this.ann, this.bobsBuddies), false);
    t.checkExpect(this.ann.hasExtendedBuddyHelper(this.hank, new MTLoBuddy()), true);
    t.checkExpect(this.hank.hasExtendedBuddyHelper(this.ann, new MTLoBuddy()), false);
    t.checkExpect(this.ann.hasExtendedBuddyHelper(this.fay, new MTLoBuddy()), true);

  }

  // tests the method partyCount() in the class Person
  void testPartyCount(Tester t) {
    this.initBuddies();

    t.checkExpect(this.hank.partyCount(), 1);
    t.checkExpect(this.ann.partyCount(), 8);
    t.checkExpect(this.jan.partyCount(), 3);
    t.checkExpect(this.gabi.partyCount(), 3);
    t.checkExpect(this.bob.partyCount(), 8);
    t.checkExpect(this.dan.partyCount(), 2);
    t.checkExpect(this.cole.partyCount(), 2);
  }

  // tests the method partyCountHelper(ILoBuddy) in the class Person
  void testPartyCountHelper(Tester t) {
    this.initBuddies();
    t.checkExpect(this.hank.partyCountHelper(new MTLoBuddy()), 0);
    t.checkExpect(this.ann.partyCountHelper(new MTLoBuddy()), 8);
    t.checkExpect(this.ann.partyCountHelper(this.annsBuddies), 0);
    t.checkExpect(this.ann.partyCountHelper(this.bobsBuddies), 3);
    t.checkExpect(this.jan.partyCountHelper(new MTLoBuddy()), 4);
    t.checkExpect(this.gabi.partyCountHelper(new MTLoBuddy()), 3);
    t.checkExpect(this.bob.partyCountHelper(new MTLoBuddy()), 8);
    t.checkExpect(this.dan.partyCountHelper(new MTLoBuddy()), 2);
    t.checkExpect(this.cole.partyCountHelper(new MTLoBuddy()), 2);
  }

  // tests the method contains(Person) for the classes that implement ILoBuddy
  boolean testContains(Tester t) {
    this.initBuddies();
    return t.checkExpect(this.annsBuddies.contains(this.ann), false)
        && t.checkExpect(this.annsBuddies.contains(this.bob), true)
        && t.checkExpect(this.annsBuddies.contains(this.ed), false)
        && t.checkExpect(new MTLoBuddy().contains(this.bob), false);
  }

  // tests the method countCommonBuddies(Person) for the classes that implement
  // ILoBuddy
  boolean testListCountCommonBuddies(Tester t) {
    this.initBuddies();
    return t.checkExpect(this.annsBuddies.countCommonBuddies(this.annsBuddies), 2)
        && t.checkExpect(this.annsBuddies.countCommonBuddies(this.bobsBuddies), 0)
        && t.checkExpect(this.annsBuddies.countCommonBuddies(this.hanksBuddies), 0)
        && t.checkExpect(this.hanksBuddies.countCommonBuddies(this.annsBuddies), 0);
  }

  // tests the method containsExtended(Person, ILoBuddy) for the classes that
  // implement ILoBuddy
  boolean testContainsExtended(Tester t) {
    this.initBuddies();
    return t.checkExpect(this.annsBuddies.containsExtended(this.ann, new MTLoBuddy()), true)
        && t.checkExpect(this.annsBuddies.containsExtended(this.jan, new MTLoBuddy()), false)
        && t.checkExpect(new MTLoBuddy().containsExtended(this.ann, new MTLoBuddy()), false);
  }

  // tests the method countUnique(ILoBuddy) for the classes that implement
  // ILoBuddy
  boolean testCountUnique(Tester t) {
    this.initBuddies();
    return t.checkExpect(this.annsBuddies.countUnique(new MTLoBuddy()), 8)
        && t.checkExpect(this.bobsBuddies.countUnique(new MTLoBuddy()), 8)
        && t.checkExpect(this.hanksBuddies.countUnique(new MTLoBuddy()), 0)
        && t.checkExpect(new MTLoBuddy().countUnique(new MTLoBuddy()), 0);
  }

  // tests the method merge(ILoBuddy) for the classes that implement ILoBuddy
  boolean testMerge(Tester t) {
    this.initBuddies();
    return t.checkExpect(new MTLoBuddy().merge(new MTLoBuddy()), new MTLoBuddy())
        && t.checkExpect(this.annsBuddies.merge(new MTLoBuddy()),
            new ConsLoBuddy(this.bob, new ConsLoBuddy(this.cole, new MTLoBuddy())));
  }

}