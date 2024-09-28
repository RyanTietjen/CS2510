
// Represents a List of Accounts
interface ILoAccount {
  
  //EFFECT: Withdraw the given amount to the specified account
  //Return the new balance of that account
  int withdraw(int amount, int acctNum);

  //EFFECT: Deposit the given funds into this account
  //Return the new balance
  int deposit(int funds, int acctNum);

  //EFFECT: Remove the given account from this Bank
  void remove(int acctNo);
  
  //helper method for remove, connects the previous account list to the rest of this account
  void removeHelper(int acctNo, ConsLoAccount prev);
  
  //helper method for remove, intended use is to remove the "sentinel" 
  ILoAccount removeFirst();
}

