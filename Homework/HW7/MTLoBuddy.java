
// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
  MTLoBuddy() {
  }

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
    return false;
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
    return 0;
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
    return false;
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
    return 0;
  }

  //produces a list containing all the items in each list
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
    return accumulator;
  }

}
