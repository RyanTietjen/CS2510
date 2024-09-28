
// Represents a non-empty List of Accounts...
class ConsLoAccount implements ILoAccount {

  Account first;
  ILoAccount rest;

  ConsLoAccount(Account first, ILoAccount rest) {
    this.first = first;
    this.rest = rest;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.first ...     --Account
   *  ... this.rest ...      --ILoAccount
   *  
   *  Methods:
   *  ... this.withdraw(int) ...                      --int
   *  ... this.deposit(int) ...                       --int
   *  ... this.removeAccount(int) ...                 --void
   *  ... this.removeHelper(int, ConsLoAccount) ...   -- void
   *  ... this.removeFirst() ...                      --ILoAccount
   */

  //EFFECT: Withdraw the given amount to the specified account
  //Return the new balance of that account
  public int withdraw(int amount, int acctNum) {
    if (this.first.accountNum == acctNum) {
      return this.first.withdraw(amount);
    }
    return this.rest.withdraw(amount, acctNum);
  }
  
  
  //EFFECT: Deposit the given funds into the given account
  //Return the new balance of that account
  public int deposit(int funds, int acctNum) {
    if (this.first.accountNum == acctNum) {
      return this.first.deposit(funds);
    }
    return this.rest.deposit(funds, acctNum);
  }

  //EFFECT: Remove the given account from this Bank
  public void remove(int acctNo) {
    this.removeHelper(acctNo, this);
  }

  //helper method for remove, connects the previous account list to the rest of this account
  public void removeHelper(int acctNo, ConsLoAccount prev) {
    if (this.first.accountNum == acctNo) {
      prev.rest = this.rest;

    }
    else {
      this.rest.removeHelper(acctNo, this);
    }
  }
  
  //helper method for remove, intended use is to remove the "sentinel" 
  public ILoAccount removeFirst() {
    return this.rest;
  }
  
    
}