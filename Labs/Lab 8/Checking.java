
// Represents a checking account
class Checking extends Account {

  int minimum; // The minimum account balance allowed

  Checking(int accountNum, int balance, String name, int minimum) {
    super(accountNum, balance, name);
    this.minimum = minimum;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.accountNum ... --int
   *  ... this.balance ...    --int
   *  ... this.name ...       --String
   *  ... this.minimum ...    --int
   *  
   *  Methods:
   *  ... this.withdraw(int) ...      --int
   *  ... this.deposit(int) ...      --int
   */

  //EFFECT: Withdraw the given amount
  //Return the new balance
  int withdraw(int amount) {
    if (this.balance - amount < minimum) {
      throw new RuntimeException(amount + " is not available");
    }
    this.balance -= amount;
    return balance;
  }
  
  //EFFECT: Deposit the given funds into this account
  //Return the new balance
  int deposit(int funds) {
    this.balance += funds;
    return this.balance;
  }
  
}
