// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

  Person first;
  ILoBuddy rest;

  ConsLoBuddy(Person first, ILoBuddy rest) {
    this.first = first;
    this.rest = rest;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.first ...     --Person
   *  ... this.rest ...      --ILoBuddy
   *  
   *  Methods:
   *  ... this.contains(Person) ...                     --boolean
   *  ... this.countCommonBuddies(ILoBuddy) ...         --int
   *  ... this.containsExtended(Person, ILoBuddy) ...   --boolean
   *  ... this.countUnique(ILoBuddy) ...                --int
   *  ... this.merge(ILoBuddy) ...                      --ILoBuddy
   *  
   *  Methods of Fields:
   *  ... this.first.addBuddy(Person) ...                           --void
   *  ... this.first.hasDirectBuddy(Person) ...                     --boolean
   *  ... this.first.partyCount() ...                               --int 
   *  ... this.first.partyCountHelper(ILoBuddy) ...                 --int
   *  ... this.first.countCommonBuddies(Person) ...                 --int
   *  ... this.first.hasExtendedBuddy(Person) ...                   --boolean
   *  ... this.first.hasExtendedBuddyHelper(Person, ILoBuddy) ...   --boolean
   *  ... this.rest.contains(Person) ...                            --boolean
   *  ... this.rest.countCommonBuddies(ILoBuddy) ...                --int
   *  ... this.rest.containsExtended(Person, ILoBuddy) ...          --boolean
   *  ... this.rest.countUnique(ILoBuddy) ...                       --int
   *  ... this.rest.merge(ILoBuddy) ...                             --ILoBuddy
   */

  // determines if a person is contained in this list of buddies
  public boolean contains(Person that) {
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
    return this.first.equals(that) || this.rest.contains(that);
  }

  // counts the number of common buddies in this person and that person
  public int countCommonBuddies(ILoBuddy that) {
    /* TEMPLATE:
     * Parameters:
     * ... that ...   --ILoBuddy
     * 
     * Methods of Parameters:
     *  ... that.contains(Person) ...                            --boolean
     *  ... that.countCommonBuddies(ILoBuddy) ...                --int
     *  ... that.containsExtended(Person, ILoBuddy) ...          --boolean
     *  ... that.countUnique(ILoBuddy) ...                       --int
     *  ... that.merge(ILoBuddy) ...                             --ILoBuddy
     */
    if (that.contains(this.first)) {
      return 1 + that.countCommonBuddies(this.rest);
    }
    return 0 + that.countCommonBuddies(this.rest);
  }

  // determines if a person is an extended buddy in this list
  public boolean containsExtended(Person that, ILoBuddy accumulator) {
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
    if (accumulator.contains(this.first)) {
      return false;
    }
    return this.first.hasDirectBuddy(that)
        || this.rest.containsExtended(that, new ConsLoBuddy(this.first, accumulator))
        || this.first.hasExtendedBuddyHelper(that, new ConsLoBuddy(this.first, accumulator));
  }

  // counts the number of buddies in this list given that a person isn't already
  // counted
  public int countUnique(ILoBuddy accumulator) {
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
    if (accumulator.contains(this.first)) {
      return this.rest.countUnique(accumulator);
    }
    return 1 + this.rest.countUnique(new ConsLoBuddy(this.first, accumulator))
        + this.first.partyCountHelper(this.merge(accumulator));
  }

  // produces a list containing all the items in each list
  public ILoBuddy merge(ILoBuddy accumulator) {
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
    return this.rest.merge(new ConsLoBuddy(this.first, accumulator));
  }

}
