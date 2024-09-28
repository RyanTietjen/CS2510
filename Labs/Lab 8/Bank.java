
// Represents a Bank with list of accounts
class Bank {
    
  String name;
  ILoAccount accounts;
    
  Bank(String name) {
    this.name = name;

    // Each bank starts with no accounts
    this.accounts = new MtLoAccount();
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.name ...     --String
   *  ... this.accounts ... --ILoAccount
   *  
   *  Methods:
   *  ... this.add(Account) ...     --void
   *  ... this.withdraw(int) ...      --int
   *  ... this.deposit(int) ...      --int
   *  ... this.removeAccount(int) ... --void
   */

  //EFFECT: Add a new account to this Bank
  void add(Account acct) { 
    this.accounts = new ConsLoAccount(acct, this.accounts);
  }
  
  //EFFECT: Withdraw the given amount to the specified account
  //Return the new balance of that account
  int withdraw(int amount, int acctNum) {
    return this.accounts.withdraw(amount, acctNum);
  }
  
  //EFFECT: Deposit the given funds into the given account
  //Return the new balance of that account
  int deposit(int funds, int acctNum) {
    return this.accounts.deposit(funds, acctNum); 
  }

  //EFFECT: Remove the given account from this Bank
  void removeAccount(int acctNo) {
    //assumes account numbers cannot be negative
    this.add(new Checking(-1,0, "Sentinel", 0));
    this.accounts.remove(acctNo);
    this.accounts = this.accounts.removeFirst();
  }

}
