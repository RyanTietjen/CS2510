import tester.Tester;

// to represent a circuit
interface ICircuit {
  int countComponents();
  
  double totalVoltage();
  
  double totalCurrent();
  
  double totalResistance();
  
  ICircuit reversePolarity();
}

// to represent a battery
class Battery implements ICircuit {
  String name;
  double voltage; // in volts
  double nominalResistance; // in ohms
  
  // the constructor
  Battery(String name, double voltage, double nominalResistance) {
    this.name = name;
    this.voltage = voltage;
    this.nominalResistance = nominalResistance;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.name ...               -- String
   *  ... this.voltage ...            -- double
   *  ... this.nominalResistance ...  -- double
   *  
   *  Methods:
   *  ... this.countComponents() ...  -- int
   *  ... this.totalVoltage() ...     -- double
   *  ... this.totalResistance() ...  -- double
   *  ... this.totalCurrent() ...     -- double
   *  ... this.reversePolarity() ...  -- ICircuit
   *  
   */
  
  // returns the number of components in this circuit
  public int countComponents() {
    return 1;
  }
  
  // returns the total voltage of this circuit
  public double totalVoltage() {
    return this.voltage;
  }
  
  // returns the total resistance of this circuit
  public double totalResistance() {
    return nominalResistance;
  }
  
  // returns the total current of this circuit
  // where the current is the ratio between the voltage
  // and the resistance
  public double totalCurrent() {
    if (nominalResistance != 0) {
      return voltage / nominalResistance;
    }
    
    return voltage;
  }
    
  // returns an identical circuit component but with the voltages reversed
  public ICircuit reversePolarity() {
    ICircuit circuit = new Battery(this.name, this.voltage * -1, this.nominalResistance);
    return circuit;
  }
}

//to represent a resistor
class Resistor implements ICircuit {
  String name;
  double resistance; // in ohms

  
  //the constructor
  Resistor(String name, double resistance) {
    this.name = name;
    this.resistance = resistance;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.name ...        -- String
   *  ... this.resistance ...  -- double
   *  
   *  Methods:
   *  ... this.countComponents() ...  -- int
   *  ... this.totalVoltage() ...     -- double
   *  ... this.totalResistance() ...  -- double
   *  ... this.totalCurrent() ...     -- double
   *  ... this.reversePolarity() ...  -- ICircuit
   */
  
  // returns the number of components in this circuit
  public int countComponents() {
    return 1;
  }
  
  //returns the total voltage of this circuit
  public double totalVoltage() {
    return 0;
  }
  
  // returns the total resistance of this circuit
  public double totalResistance() {
    return resistance;
  }
  
  // returns the total current of this circuit
  public double totalCurrent() {
    return 0;
  }
  
  // returns an identical circuit component but with the voltages reversed
  public ICircuit reversePolarity() {
    return this;
  }
}

//to represent a series
class Series implements ICircuit {
  ICircuit first;
  ICircuit second;

  // the constructor
  Series(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.first ...   -- Circuit
   *  ... this.second ...  -- Circuit
   *  
   *  Methods:
   *  ... this.countComponents() ...  -- int
   *  ... this.totalVoltage() ...     -- double
   *  ... this.totalResistance() ...  -- double
   *  ... this.totalCurrent() ...     -- double
   *  ... this.reversePolarity() ...  -- ICircuit
   *  
   *  Methods of fields:
   *  ... this.first.countComponents() ...  -- int
   *  ... this.first.totalVoltage() ...     -- double
   *  ... this.first.totalResistance() ...  -- double
   *  ... this.first.totalCurrent() ...     -- double
   *  ... this.first.reversePolarity() ...  -- ICircuit
   *  ... this.second.countComponents() ...  -- int
   *  ... this.second.totalVoltage() ...     -- double
   *  ... this.second.totalResistance() ...  -- double
   *  ... this.second.totalCurrent() ...     -- double
   *  ... this.second.reversePolarity() ...  -- ICircuit
   */
  
  // returns the number of components in this circuit
  public int countComponents() {
    return this.first.countComponents() + this.second.countComponents();
  }
  
  //returns the total voltage of this circuit
  public double totalVoltage() {
    return this.first.totalVoltage() + this.second.totalVoltage();
  }
  
  // returns the total resistance of this circuit
  public double totalResistance() {
    return this.first.totalResistance() + this.second.totalResistance();
  }
  
  // returns the total current of this circuit
  // where the current is the ratio between the voltage
  // and the resistance
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }
  
  // returns an identical circuit component but with the voltages reversed
  public ICircuit reversePolarity() {
    ICircuit circuit = new Series(this.first.reversePolarity(), this.second.reversePolarity());
    return circuit;
  }
} 

//to represent a series
class Parallel implements ICircuit {
  ICircuit first;
  ICircuit second;

  Parallel(ICircuit first, ICircuit second) {
    this.first = first;
    this.second = second;
  }
  
  /* TEMPLATE:
   *  Fields:
   *  ... this.first ...   -- Circuit
   *  ... this.second ...  -- Circuit
   *  
   *  Methods:
   *  ... this.countComponents() ...  -- int
   *  ... this.totalVoltage() ...     -- double
   *  ... this.totalResistance() ...  -- double
   *  ... this.totalCurrent() ...     -- double
   *  ... this.reversePolarity() ...  -- ICircuit
   *  
   *  Methods of fields:
   *  ... this.first.countComponents() ...  -- int
   *  ... this.first.totalVoltage() ...     -- double
   *  ... this.first.totalResistance() ...  -- double
   *  ... this.first.totalCurrent() ...     -- double
   *  ... this.first.reversePolarity() ...  -- ICircuit
   *  ... this.second.countComponents() ...  -- int
   *  ... this.second.totalVoltage() ...     -- double
   *  ... this.second.totalResistance() ...  -- double
   *  ... this.second.totalCurrent() ...     -- double
   *  ... this.second.reversePolarity() ...  -- ICircuit
   */
  
  //returns the total number of components in this circuit
  public int countComponents() {
    return this.first.countComponents() + this.second.countComponents();
  }
  
  //returns the total voltage of this circuit
  public double totalVoltage() {
    return this.first.totalVoltage() + this.second.totalVoltage();
  } 
  
  //returns the total resistance of this circuit
  public double totalResistance() {
    return 1 / ((1 / this.first.totalResistance()) + (1 / this.second.totalResistance()));
  }
  
  // returns the total current of this circuit
  // where the current is the ratio between the voltage
  // and the resistance
  public double totalCurrent() {
    return this.totalVoltage() / this.totalResistance();
  }
  
  // returns an identical circuit component but with the voltages reversed
  public ICircuit reversePolarity() {
    ICircuit circuit = new Parallel(this.first.reversePolarity(), this.second.reversePolarity());
    return circuit;
  }
}


class ExamplesCircuits {
  ICircuit batteryOne = new Battery("B 1", 10, 25);
  ICircuit resistorOne = new Resistor("R 1", 100);
  ICircuit simpleSeries = new Series(this.batteryOne, this.resistorOne);

  //examples relating to the complexCircuit
  ICircuit batteryTwo = new Battery("BT 1", 10, 0);
  ICircuit batteryThree = new Battery("BT 2", 20, 0);
  
  ICircuit batterySeries = new Series(batteryTwo, batteryThree);

  ICircuit resistorTwo = new Resistor("R 2", 250);
  ICircuit resistorThree = new Resistor("R 3", 500);
  ICircuit resistorFour = new Resistor("R 4", 200);
  ICircuit resistorFive = new Resistor("R 5", 50);

  ICircuit seriesOne = new Series(this.resistorFour, this.resistorFive);

  ICircuit parallelOne = new Parallel(this.seriesOne, this.resistorOne);
  ICircuit parallelTwo = new Parallel(this.parallelOne, this.resistorTwo);
  ICircuit parallelThree = new Parallel(this.parallelTwo, this.resistorThree);

  ICircuit complexCircuit = new Series(this.batterySeries, this.parallelThree);

  // Example: A circuit with two batteries in series, two resistors in series,
  // where these series are connected in parallel
  ICircuit batteryFour = new Battery("BT 4", 20, 0);
  ICircuit batteryFive = new Battery("BT 5", 35, 0);
  ICircuit seriesThree = new Series(batteryFour, batteryFive);

  ICircuit resistorSix = new Resistor("BT 4", 200);
  ICircuit resistorSeven = new Resistor("BT 5", 350);
  ICircuit seriesFour = new Series(resistorSix, resistorSeven);

  ICircuit intermediateCircuit = new Series(seriesThree, seriesFour);
  
  //examples relating to testing reverse polarity
  ICircuit reverseBatteryOne = new Battery("B 1", -10, 25);
  ICircuit reverseSimpleSeries = new Series(this.reverseBatteryOne, this.resistorOne);
  
  ICircuit reverseBatteryTwo = new Battery("BT 1", -10, 0);
  ICircuit reverseBatteryThree = new Battery("BT 2", -20, 0);
  ICircuit reverseBatterySeries = new Series(this.reverseBatteryTwo, this.reverseBatteryThree);
  ICircuit reverseComplexCircuit = new Series(this.reverseBatterySeries, this.parallelThree);
  

  // test the method countComponents in the class Battery
  boolean testBatteryCountComponents(Tester t) {
    return t.checkExpect(batteryOne.countComponents(), 1);
  }
  
  // test the method countComponents in the class Resistor
  boolean testResistorCountComponents(Tester t) {
    return t.checkExpect(resistorOne.countComponents(), 1);
  }
  
  // test the method countComponents in the class Series
  boolean testSeriesCountComponents(Tester t) {
    return t.checkExpect(simpleSeries.countComponents(), 2)
        && t.checkExpect(complexCircuit.countComponents(), 7)
        && t.checkExpect(intermediateCircuit.countComponents(), 4);
  }
  
  // test the method countComponents in the class Parallel
  boolean testParallelCountComponents(Tester t) {
    return t.checkExpect(parallelOne.countComponents(), 3);
  }
  
  // test the method totalVoltage in the class Battery
  boolean testBatteryTotalVoltage(Tester t) {
    return t.checkInexact(batteryOne.totalVoltage(), 10.0, 0.001);
  }
  
  // test the method totalVoltage in the class Resistor
  boolean testResistorTotalVoltage(Tester t) {
    return t.checkInexact(resistorOne.totalVoltage(), 0.0, 0.001);
  }
  
  // test the method totalVoltage in the class Series
  boolean testSeriesTotalVoltage(Tester t) {
    return t.checkInexact(simpleSeries.totalVoltage(), 10.0, 0.001)
        && t.checkInexact(complexCircuit.totalVoltage(), 30.0, 0.001)
        && t.checkInexact(intermediateCircuit.totalVoltage(), 55.0, 0.001);
  }
  
  // test the method totalVoltage in the class Parallel
  boolean testParallelTotalVoltage(Tester t) {
    return t.checkInexact(parallelOne.totalVoltage(), 0.0, 0.001); 
  }
  
  // test the method totalResistance in the class Battery
  boolean testBatteryTotalResistance(Tester t) {
    return t.checkInexact(batteryOne.totalResistance(), 25.0, 0.001)
        && t.checkInexact(batteryTwo.totalResistance(), 0.0, 0.01);
  }
  
  // test the method totalResistance in the class Resistor
  boolean testResistorTotalResistance(Tester t) {
    return t.checkInexact(resistorOne.totalResistance(), 100.0, 0.001)
        && t.checkInexact(resistorSeven.totalResistance(), 350.0, 0.001);
  }
  
  // test the method totalResistance in the class Series
  boolean testSeriesTotalResistance(Tester t) {
    return t.checkInexact(simpleSeries.totalResistance(), 125.0, 0.001)
        && t.checkInexact(complexCircuit.totalResistance(), 50.0, 0.001)
        && t.checkInexact(intermediateCircuit.totalCurrent(), 0.1, 0.001);
  }
  
  // test the method totalResistance in the class Parallel
  boolean testParallelTotalResistance(Tester t) {
    return t.checkInexact(parallelThree.totalResistance(), 50.0, 0.001) 
       && t.checkInexact(parallelTwo.totalResistance(), 55.555, 0.001);
  }
  
  // test the method totalCurrent in the class Battery
  boolean testBatteryTotalCurent(Tester t) {
    return t.checkInexact(batteryOne.totalCurrent(), .4, 0.0001)
        && t.checkInexact(batteryTwo.totalCurrent(), 10.0, 0.0001)
        && t.checkInexact(batteryThree.totalCurrent(), 20.0, 0.0001);
  }
  
  // test the method totalCurrent in the class Resistor
  boolean testResistorTotalCurent(Tester t) {
    return t.checkInexact(resistorOne.totalCurrent(), 0.0, 0.001)
        && t.checkInexact(resistorSeven.totalCurrent(), 0.0, 0.001);
  }
  
  // test the method totalCurrent in the class Series
  boolean testSeriesTotalCurent(Tester t) {
    return t.checkInexact(simpleSeries.totalCurrent(), .08, 0.0001)
        && t.checkInexact(complexCircuit.totalCurrent(), .6, 0.0001);
  }
  
  // test the method totalCurrent in the class Parallel
  boolean testParallelTotalCurent(Tester t) {
    return t.checkInexact(parallelThree.totalCurrent(), 0.0, 0.001) 
        && t.checkInexact(parallelTwo.totalCurrent(), 0.0, 0.001);
  }
  
  // test the method reversePolarity in the class Battery
  boolean testBatteryReversePolarity(Tester t) {
    return t.checkExpect(batteryOne.reversePolarity(), reverseBatteryOne);
  }
  
  // test the method reversePolarity in the class Resistor
  boolean testResistorReversePolarity(Tester t) {
    return t.checkExpect(resistorOne.reversePolarity(), resistorOne)
        && t.checkExpect(resistorSeven.reversePolarity(), resistorSeven);
  }
  
  // test the method reversePolarity in the class Series
  boolean testSeriesReversePolarity(Tester t) {
    return t.checkExpect(simpleSeries.reversePolarity(), reverseSimpleSeries)
        && t.checkExpect(complexCircuit.reversePolarity(), reverseComplexCircuit);
  }
  
  // test the method reversePolarity in the class Parallel
  boolean testParallelReversePolarity(Tester t) {
    return t.checkExpect(parallelThree.reversePolarity(), parallelThree) 
        && t.checkExpect(parallelTwo.reversePolarity(), parallelTwo);

  }  
}
