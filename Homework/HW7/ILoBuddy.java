
// represents a list of Person's buddies
interface ILoBuddy {
  // determines if a person is contained in this list of buddies
  boolean contains(Person that);

  // counts the number of common buddies in this person and that person
  int countCommonBuddies(ILoBuddy that);

  // determines if a person is an extended buddy in this list
  boolean containsExtended(Person that, ILoBuddy accumulator);

  // counts the number of buddies in this list given that a person isn't already
  // counted
  int countUnique(ILoBuddy accumulator);

  // produces a list containing all the items in each list
  ILoBuddy merge(ILoBuddy accumulator);
}
