
// Represents a credit line account
class Credit extends Account {

  int creditLine;  // Maximum amount accessible
  double interest; // The interest rate charged
    
  Credit(int accountNum, int balance, String name, int creditLine, double interest) {
    super(accountNum, balance, name);
    this.creditLine = creditLine;
    this.interest = interest;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.accountNum ... --int
   *  ... this.balance ...    --int
   *  ... this.name ...       --String
   *  ... this.minimum ...    --int
   *  ... this.interest ...   --double
   *  
   *  Methods:
   *  ... this.withdraw(int) ...      --int
   *  ... this.deposit(int) ...      --int
   */

  //EFFECT: Withdraw the given amount
  //Return the new balance
  int withdraw(int amount) {
    if (this.creditLine < amount + this.balance) {
      throw new RuntimeException(amount + " is not available");
    }
    this.balance += amount;
    return balance;
  }
  
  //EFFECT: Deposit the given funds into this account
  //Return the new balance
  int deposit(int funds) {
    if (funds > this.balance) {
      throw new RuntimeException("a deposit of " + funds + " would cause the account to close");
    }
    this.balance -= funds;
    return this.balance;
  }
}
