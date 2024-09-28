import tester.*;

// Bank Account Examples and Tests
class Examples {
  Account check1;
  Account check2;
  Account check3;
  Account savings1;
  Account savings2;
  Account credit1;
  Account credit2;
  
  Bank bank1;
  Bank bank2;
  Bank singleBank;
  Bank mtBank;
  
  //examples used to test the classes that implement ILoAccount
  ILoAccount mt;
  ILoAccount bank1List;
  ILoAccount bank2List;

  Examples() { 
    reset(); 
  }

  // Initializes accounts to use for testing with effects.
  // We place inside reset() so we can "reuse" the accounts
  void reset() {

    // Initialize the account examples
    check1 = new Checking(1, 100, "First Checking Account", 20);
    check2 = new Checking(2, 1000, "Second Checking Account", 100);
    check3 = new Checking(7, 250, "Third Checking Account", 10);
    
    savings1 = new Savings(3, 200, "First Savings Account", 2.5);
    savings2 = new Savings(4, 500, "Second Savings Account", 3);
    
    credit1 = new Credit(5, 100, "First Credit Account", 500, 2.5);
    credit2 = new Credit(6, 200, "Second Credit Account", 1000, 3);
    
    bank1 = new Bank("name");
    bank1.add(check1);
    bank1.add(savings1);
    bank1.add(credit1);
    
    bank2 = new Bank("greavys");
    bank2.add(check2);
    bank2.add(savings2);
    bank2.add(credit2);
    
    singleBank = new Bank("lone");
    singleBank.add(check3);
    
    mtBank = new Bank("empty");
    
    //examples used to test the classes that implement ILoAccount
    mt = new MtLoAccount();
    
    bank1List = new ConsLoAccount(this.credit1,
        new ConsLoAccount(this.savings1, new ConsLoAccount(this.check1, new MtLoAccount())));
    
    bank2List = new ConsLoAccount(this.credit2,
        new ConsLoAccount(this.savings2, new ConsLoAccount(this.check2, new MtLoAccount())));
    
  }

  // Tests the exceptions we expect to be thrown when
  //   performing an "illegal" action.
  void testExceptions(Tester t) {
    reset();
    t.checkException("Test for invalid Checking withdraw",
        new RuntimeException("1000 is not available"),
        this.check1,
        "withdraw",
        1000);
    t.checkException("Test for invalid Savings withdraw",
        new RuntimeException("1000 is not available"),
        this.savings1,
        "withdraw",
        1000);
    t.checkException("Test for invalid Credit withdraw",
        new RuntimeException("1000 is not available"),
        this.savings1,
        "withdraw",
        1000);
    t.checkException("Test for invalid credit deposit",
        new RuntimeException("a deposit of 450 would cause the account to close"),
        this.credit1,
        "deposit",
        450);
  }
  
  //tests the method withdraw in the classes that implement Account
  void testWithdraw(Tester t) {
    reset();
    
    //check values before mutation
    t.checkExpect(this.check2.balance, 1000);
    t.checkExpect(this.savings2.balance, 500);
    t.checkExpect(this.credit2.balance, 200);
    
    this.check2.withdraw(750);
    this.savings2.withdraw(250);
    this.credit2.withdraw(50);
    
    //check values after mutation
    t.checkExpect(this.check2.balance, 250);
    t.checkExpect(this.savings2.balance, 250);
    t.checkExpect(this.credit2.balance, 250);
  }
  
  //tests the method deposit in the classes that implement Account
  void testDeposit(Tester t) {
    reset();
    
    //check values before mutation
    t.checkExpect(this.check2.balance, 1000);
    t.checkExpect(this.savings2.balance, 500);
    t.checkExpect(this.credit2.balance, 200);
    
    this.check2.deposit(750);
    this.savings2.deposit(250);
    this.credit2.deposit(50);
    
    t.checkExpect(this.check2.balance, 1750);
    t.checkExpect(this.savings2.balance, 750);
    t.checkExpect(this.credit2.balance, 150);
  }
  
  //tests the method add in the class Bank
  void testAdd(Tester t) {
    reset();
    
    //check values before mutation
    t.checkExpect(this.bank1.accounts, new ConsLoAccount(this.credit1,
        new ConsLoAccount(this.savings1, new ConsLoAccount(this.check1, new MtLoAccount()))));
    t.checkExpect(this.bank2.accounts, new ConsLoAccount(this.credit2,
        new ConsLoAccount(this.savings2, new ConsLoAccount(this.check2, new MtLoAccount()))));
    
    this.bank1.add(this.credit2);
    this.bank2.add(this.credit1);
    
    // check values after mutation
    t.checkExpect(this.bank1.accounts,
        new ConsLoAccount(this.credit2, new ConsLoAccount(this.credit1,
            new ConsLoAccount(this.savings1, new ConsLoAccount(this.check1, new MtLoAccount())))));
    t.checkExpect(this.bank2.accounts,
        new ConsLoAccount(this.credit1, new ConsLoAccount(this.credit2,
            new ConsLoAccount(this.savings2, new ConsLoAccount(this.check2, new MtLoAccount())))));
  }

  //tests the method withdraw in the class Bank
  void testBankWithdraw(Tester t) {
    reset();
    
    //check values before mutation
    //all these accounts are in the Bank bank2
    t.checkExpect(this.check2.balance, 1000);
    t.checkExpect(this.savings2.balance, 500);
    t.checkExpect(this.credit2.balance, 200);
    
    t.checkExpect(this.bank2.withdraw(750, 2), 250);
    t.checkExpect(this.bank2.withdraw(250, 4), 250);
    t.checkExpect(this.bank2.withdraw(50, 6), 250);
    
    //check values after mutation
    t.checkExpect(this.check2.balance, 250);
    t.checkExpect(this.savings2.balance, 250);
    t.checkExpect(this.credit2.balance, 250);
    
    //tests if the account cannot be found
    t.checkException("Test for invalid account number",
        new RuntimeException("Account number 8 is not found in this bank"),
        this.bank2,
        "withdraw",
        100,
        8);
    
    //tests if the withdraw is invalid
    t.checkException("Test for invalid Checking withdraw",
        new RuntimeException("1000 is not available"),
        this.bank1,
        "withdraw",
        1000,
        1);
    t.checkException("Test for invalid Savings withdraw",
        new RuntimeException("1000 is not available"),
        this.bank1,
        "withdraw",
        1000,
        3);
    t.checkException("Test for invalid Credit withdraw",
        new RuntimeException("1000 is not available"),
        this.bank1,
        "withdraw",
        1000,
        5);
  }
  
  //tests the method deposit in the class Bank
  void testBankDeposit(Tester t) {
    reset();
    
    //check values before mutation
    //all these accounts are in the Bank bank2
    t.checkExpect(this.check2.balance, 1000);
    t.checkExpect(this.savings2.balance, 500);
    t.checkExpect(this.credit2.balance, 200);
    
    t.checkExpect(this.bank2.deposit(750, 2), 1750);
    t.checkExpect(this.bank2.deposit(250, 4), 750);
    t.checkExpect(this.bank2.deposit(50, 6), 150);
    
    //check values after mutation
    t.checkExpect(this.check2.balance, 1750);
    t.checkExpect(this.savings2.balance, 750);
    t.checkExpect(this.credit2.balance, 150);
    
    //tests if the account cannot be found
    t.checkException("Test for invalid account number",
        new RuntimeException("Account number 8 is not found in this bank"),
        this.bank2,
        "deposit",
        100,
        8);
    
    //tests if the deposit is invalid
    t.checkException("Test for invalid Credit deposit",
        new RuntimeException("450 is not available"),
        this.bank1,
        "withdraw",
        450,
        5);
  }
  
  //tests the method removeAccount in the class Bank
  void testRemoveAccount(Tester t) {
    reset();
    
    //check values before mutation
    t.checkExpect(this.bank1.accounts, new ConsLoAccount(this.credit1,
        new ConsLoAccount(this.savings1, new ConsLoAccount(this.check1, new MtLoAccount()))));
    t.checkExpect(this.bank2.accounts, new ConsLoAccount(this.credit2,
        new ConsLoAccount(this.savings2, new ConsLoAccount(this.check2, new MtLoAccount()))));
    
    t.checkException("Test for removing an account not in the bank", 
        new RuntimeException("Account number 8 is not found in this bank"),
        this.bank1,
        "removeAccount",
        8);
    
    reset();
    
    this.bank1.removeAccount(1);
    this.bank2.removeAccount(2);
    
    //check values after mutation
    t.checkExpect(this.bank1.accounts, new ConsLoAccount(this.credit1,
        new ConsLoAccount(this.savings1, new MtLoAccount())));
    t.checkExpect(this.bank2.accounts, new ConsLoAccount(this.credit2,
        new ConsLoAccount(this.savings2, new MtLoAccount())));
    
    this.bank1.removeAccount(5);
    this.bank2.removeAccount(6);
    
    t.checkExpect(this.bank1.accounts, new ConsLoAccount(this.savings1, new MtLoAccount()));
    t.checkExpect(this.bank2.accounts, new ConsLoAccount(this.savings2, new MtLoAccount()));
    
    this.bank1.removeAccount(3);
    this.bank2.removeAccount(4);
    
    t.checkExpect(this.bank1.accounts, new MtLoAccount());
    t.checkExpect(this.bank2.accounts, new MtLoAccount());
    
    t.checkException("Test for removing an account not in the bank", 
        new RuntimeException("Account number 8 is not found in this bank"),
        this.bank1,
        "removeAccount",
        8);
  }
  

  
  //tests the method withdraw in the classes that implement ILoAccount
  void testILoAccountWithdraw(Tester t) {
    reset();
    
    t.checkException("Test for withdrawing from an empty list",
        new RuntimeException("Account number 10 is not found in this bank"),
        this.mt,
        "withdraw",
        100,
        10);
    t.checkExpect(this.bank2List.withdraw(750, 2), 250);
    t.checkExpect(this.bank2List.withdraw(250, 4), 250);
    t.checkExpect(this.bank2List.withdraw(50, 6), 250);
  }
  
  //tests the method deposit in the classes that implement ILoAccount
  void testILoAccountDeposit(Tester t) {
    reset();
    
    t.checkException("Test for withdrawing from an empty list",
        new RuntimeException("Account number 10 is not found in this bank"),
        this.mt,
        "deposit",
        100,
        10);
    
    t.checkExpect(this.bank2List.deposit(750, 2), 1750);
    t.checkExpect(this.bank2List.deposit(250, 4), 750);
    t.checkExpect(this.bank2List.deposit(50, 6), 150);
  }

  //tests the method removeFirst in the classes that implement ILoAccount
  void testRemoveFirst(Tester t) {
    reset();
    t.checkExpect(this.bank1List.removeFirst(),
        new ConsLoAccount(this.savings1, new ConsLoAccount(this.check1, new MtLoAccount())));
    t.checkExpect(this.mt.removeFirst(), this.mt);

  }
  
}

