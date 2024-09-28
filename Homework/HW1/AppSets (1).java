// to represent a set of applications
interface IAppSet {
  
}

// to represent a folder that contains apps
class Folder implements IAppSet {
  String title;
  
  Folder(String title) {
    this.title = title;
  }
}

// to represent a set of applications
class Apps implements IAppSet {
  String appName;
  IAppSet others;
  
  Apps(String appName, IAppSet others) { 
    this.appName = appName;
    this.others = others;
  } 
}

class ExamplesSets {
  IAppSet travelFolder = new Folder("Travel");
  IAppSet foodFolder = new Folder("Food");
  
  IAppSet t1 = new Apps("Uber", this.travelFolder);
  IAppSet t2 = new Apps("mTicket", this.t1);
  IAppSet t3 = new Apps("Moovit", this.t2);
  IAppSet travelApps = new Apps("Orbitz", this.t3);


  IAppSet f1 = new Apps("Grubhub", this.foodFolder);
  IAppSet f2 = new Apps("B. Good", this.f1);
  IAppSet foodApps = new Apps("Gong Cha", this.f2);
}