
// represents a Person with a user name and a list of buddies
class Person {

  String username;
  ILoBuddy buddies;

  Person(String username) {
    this.username = username;
    this.buddies = new MTLoBuddy();
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.username ...     --String
   *  ... this.rest ...         --ILoBuddy
   *  
   *  Methods:
   *  ... this.addBuddy(Person) ...                           --void
   *  ... this.hasDirectBuddy(Person) ...                     --boolean
   *  ... this.partyCount() ...                               --int 
   *  ... this.partyCountHelper(ILoBuddy) ...                 --int
   *  ... this.countCommonBuddies(Person) ...                 --int
   *  ... this.hasExtendedBuddy(Person) ...                   --boolean
   *  ... this.hasExtendedBuddyHelper(Person, ILoBuddy) ...   --boolean
   *  
   *  
   *  Methods of Fields:
   *  ... this.buddies.contains(Person) ...                     --boolean
   *  ... this.buddies.countCommonBuddies(ILoBuddy) ...         --int
   *  ... this.buddies.containsExtended(Person, ILoBuddy) ...   --boolean
   *  ... this.buddies.countUnique(ILoBuddy) ...                --int
   *  ... this.buddies.merge(ILoBuddy) ...                      --ILoBuddy
   */

  // EFFECT:
  // Change this person's buddy list so that it includes the given person
  void addBuddy(Person buddy) {
    /* TEMPLATE:
     * Parameters:
     * ... buddy ...   --Person
     * 
     * Methods of Parameters:
     *  ... buddy.addBuddy(Person) ...                           --void
     *  ... buddy.hasDirectBuddy(Person) ...                     --boolean
     *  ... buddy.partyCount() ...                               --int 
     *  ... buddy.partyCountHelper(ILoBuddy) ...                 --int
     *  ... buddy.countCommonBuddies(Person) ...                 --int
     *  ... buddy.hasExtendedBuddy(Person) ...                   --boolean
     *  ... buddy.hasExtendedBuddyHelper(Person, ILoBuddy) ...   --boolean
     */
    if (!this.equals(buddy)) {
      this.buddies = new ConsLoBuddy(buddy, this.buddies);
    } 
  }

  // returns true if this Person has that as a direct buddy
  boolean hasDirectBuddy(Person that) {
    /* TEMPLATE:
     * Parameters:
     * ... that ...   --Person
     * 
     * Methods of Parameters:
     *  ... that.addBuddy(Person) ...                           --void
     *  ... that.hasDirectBuddy(Person) ...                     --boolean
     *  ... that.partyCount() ...                               --int 
     *  ... that.partyCountHelper(ILoBuddy) ...                 --int
     *  ... that.countCommonBuddies(Person) ...                 --int
     *  ... that.hasExtendedBuddy(Person) ...                   --boolean
     *  ... that.hasExtendedBuddyHelper(Person, ILoBuddy) ...   --boolean
     */
    return this.buddies.contains(that);
  }

  // returns the number of people who will show up at the party
  // given by this person
  int partyCount() {
    return 1 + this.buddies.countUnique(new ConsLoBuddy(this, new MTLoBuddy()));
  }
  
  // returns the number of people who will show up at the party
  // given by this person
  int partyCountHelper(ILoBuddy accumulator) {
    /* TEMPLATE:
     * Parameters:
     * ... accumulator ... --LoBuddy
     * 
     * Methods of Parameters:
     *  ... accumulator.contains(Person) ...                    --boolean
     *  ... accumulator.countCommonBuddies(ILoBuddy) ...        --int
     *  ... accumulator.containsExtended(Person, ILoBuddy) ...  --boolean
     *  ... accumulator.countUnique(ILoBuddy) ...               --int
     *  ... accumulator.merge(ILoBuddy) ...                     --ILoBuddy
     */
    return this.buddies.countUnique(accumulator);
  }

  // returns the number of people that are direct buddies
  // of both this and that person
  int countCommonBuddies(Person that) {
    /* TEMPLATE:
     * Parameters:
     * ... that ...   --Person
     * 
     * Methods of Parameters:
     *  ... that.addBuddy(Person) ...                           --void
     *  ... that.hasDirectBuddy(Person) ...                     --boolean
     *  ... that.partyCount() ...                               --int 
     *  ... that.partyCountHelper(ILoBuddy) ...                 --int
     *  ... that.countCommonBuddies(Person) ...                 --int
     *  ... that.hasExtendedBuddy(Person) ...                   --boolean
     *  ... that.hasExtendedBuddyHelper(Person, ILoBuddy) ...   --boolean
     */
    return this.buddies.countCommonBuddies(that.buddies);
  }

  // will the given person be invited to a party
  // organized by this person?
  boolean hasExtendedBuddy(Person that) {
    /* TEMPLATE:
     * Parameters:
     * ... that ...   --Person
     * 
     * Methods of Parameters:
     *  ... that.addBuddy(Person) ...                           --void
     *  ... that.hasDirectBuddy(Person) ...                     --boolean
     *  ... that.partyCount() ...                               --int 
     *  ... that.partyCountHelper(ILoBuddy) ...                 --int
     *  ... that.countCommonBuddies(Person) ...                 --int
     *  ... that.hasExtendedBuddy(Person) ...                   --boolean
     *  ... that.hasExtendedBuddyHelper(Person, ILoBuddy) ...   --boolean
     */
    return this.buddies.containsExtended(that, new ConsLoBuddy(this, new MTLoBuddy()));
  }
  
  boolean hasExtendedBuddyHelper(Person that, ILoBuddy accumulator) {
    /* TEMPLATE:
     * Parameters:
     * ... that ...        --Person
     * ... accumulator ... --LoBuddy
     * 
     * Methods of Parameters:
     *  ... that.addBuddy(Person) ...                           --void
     *  ... that.hasDirectBuddy(Person) ...                     --boolean
     *  ... that.partyCount() ...                               --int 
     *  ... that.partyCountHelper(ILoBuddy) ...                 --int
     *  ... that.countCommonBuddies(Person) ...                 --int
     *  ... that.hasExtendedBuddy(Person) ...                   --boolean
     *  ... that.hasExtendedBuddyHelper(Person, ILoBuddy) ...   --boolean
     *  ... accumulator.contains(Person) ...                    --boolean
     *  ... accumulator.countCommonBuddies(ILoBuddy) ...        --int
     *  ... accumulator.containsExtended(Person, ILoBuddy) ...  --boolean
     *  ... accumulator.countUnique(ILoBuddy) ...               --int
     *  ... accumulator.merge(ILoBuddy) ...                     --ILoBuddy
     */
    return this.buddies.containsExtended(that, accumulator);
  }

}
