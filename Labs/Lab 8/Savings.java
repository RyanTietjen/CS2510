
// Represents a savings account
class Savings extends Account {

  double interest; // The interest rate

  Savings(int accountNum, int balance, String name, double interest) {
    super(accountNum, balance, name);
    this.interest = interest;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.accountNum ... --int
   *  ... this.balance ...    --int
   *  ... this.name ...       --String
   *  ... this.interest ...   --int
   *  
   *  Methods:
   *  ... this.withdraw(int) ...      --int
   *  ... this.deposit(int) ...      --int
   */
  
  //EFFECT: Withdraw the given amount
  //Return the new balance
  int withdraw(int amount) {
    if (amount > balance) {
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
