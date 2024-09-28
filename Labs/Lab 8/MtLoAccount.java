
// Represents the empty List of Accounts
class MtLoAccount implements ILoAccount {

  MtLoAccount() {
  }
  /* TEMPLATE:
   *  Fields:
   *  
   *  Methods:
   *  ... this.withdraw(int) ...                      --int
   *  ... this.deposit(int) ...                       --int
   *  ... this.removeAccount(int) ...                 --void
   *  ... this.removeHelper(int, ConsLoAccount) ...   -- void
   *  ... this.removeFirst() ...                      --ILoAccount
   */

  // EFFECT: Withdraw the given amount to the specified account
  // Return the new balance of that account
  public int withdraw(int amount, int acctNum) {
    throw new RuntimeException("Account number " + acctNum + " is not found in this bank");
  }

  // EFFECT: Deposit the given funds into the given account
  // Return the new balance of that account
  public int deposit(int funds, int acctNum) {
    throw new RuntimeException("Account number " + acctNum + " is not found in this bank");
  }

  // EFFECT: Remove the given account from this Bank
  public void remove(int acctNo) {
    throw new RuntimeException("Account number " + acctNo + " is not found in this bank");
  }

  // helper method for remove, connects the previous account list to the rest of
  // this account
  public void removeHelper(int acctNo, ConsLoAccount prev) {
    throw new RuntimeException("Account number " + acctNo + " is not found in this bank");
  }

  // helper method for remove, intended use is to remove the "sentinel"
  public ILoAccount removeFirst() {
    return this;
  }
}
