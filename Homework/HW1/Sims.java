// to represent a mode of behavior
interface IMode{
  
}

// to represent the mode of studying
class Study implements IMode {
  String subject;
  boolean examReview;
  
  Study(String subject, boolean examReview) {
    this.subject = subject;
    this.examReview = examReview;
  } 
}

//to represent the mode of socializing
class Socialize implements IMode {
  String description;
  int friends;
  
  Socialize(String description, int friends) {
    this.description = description;
    this.friends = friends;
  } 
}

//to represent the mode of exercising
class Exercise implements IMode {
  String name;
  boolean aerobic;
  
  Exercise(String name, boolean aerobic) {
    this.name = name;
    this.aerobic = aerobic;
  }
}

// to represent a location
interface IPlace {
  
}

// to represent the location of a classroom
class Classroom implements IPlace {
  String name;
  int roomCapacity;
  int occupantCount;
  
  Classroom(String name, int roomCapacity, int occupantCount) {
    this.name = name;
    this.roomCapacity = roomCapacity;
    this.occupantCount = occupantCount;
  }
}

//to represent the location of a gym
class Gym implements IPlace {
  String name;
  int exerciseMachines;
  int patrons;
  boolean open;

  Gym(String name, int exerciseMachines, int patrons, boolean open) {
    this.name = name;
    this.exerciseMachines = exerciseMachines;
    this.patrons = patrons;
    this.open = open;
  }
}

//to represent the location of the student center
class StudentCenter implements IPlace {
  String name;
  boolean open;

  StudentCenter(String name, boolean open) {
    this.name = name;
    this.open = open;
  }
}

//to represent a student 
class SimStudent {
  String name;
  IMode mode;
  IPlace location;
  double gpa;
  String major;
  
  SimStudent(String name, IMode mode, IPlace location, double gpa, String major) {
    this.name = name;
    this.mode = mode;
    this.location = location;
    this.gpa = gpa;
    this.major = major;
    
  }
}

class ExamplesSims {
  IPlace shillman105 = new Classroom("Shillman 105", 115, 86);
  IPlace marino = new Gym("Marino Recreation Center", 78, 47, true);
  IPlace curry = new StudentCenter("Curry Student Center", false);
  IPlace dodge50 = new Classroom("Dodge 50", 150, 100);
  IPlace marinoClosed = new Gym("Marino Recreation Center", 78, 0, false);
  IPlace richards254 = new Classroom("Richards 254", 50, 30);
  IPlace richards236 = new Classroom("Richards 236", 140, 123);
  
  IMode weightLifting = new Exercise("Weight lifting", false);
  IMode studyingForExam = new Study("Studying for an exam", true);
  IMode doingHomework = new Study("Doing homework", false);
  IMode talkingWithFriends = new Socialize("Talking with friends", 3);
  
  SimStudent student1 = new SimStudent(
      "Ryan", this.weightLifting, this.marino, 4.0, "Computer Science");
  SimStudent student2 = new SimStudent(
      "Jaston", this.studyingForExam, this.dodge50, 2.7, "Greek Studies");
  SimStudent student3 = new SimStudent(
      "Aldo", this.doingHomework, this.curry, 3.8, "Business");
  SimStudent student4 = new SimStudent(
      "Jarvis", this.talkingWithFriends, this.richards254, 3.1, "Computer Engineering");
      
}

















