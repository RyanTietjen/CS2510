// to represent information regarding a listing of a real estate property
class RealEstateListing {
  boolean singleFamily;
  int year;
  int squareFootage;
  int price; //price in dollars
  String city;
  
  RealEstateListing(boolean singleFamily, int year, int squareFootage, int price, String city) {
    this.singleFamily = singleFamily;
    this.year = year;
    this.squareFootage = squareFootage;
    this.price = price;
    this.city = city;
  }
}

class ExamplesListings {
  RealEstateListing bostonCondo = new RealEstateListing(false, 2010, 700, 350000, "Boston");
  RealEstateListing beachHouse = new RealEstateListing(true, 1995, 2000, 699999, "Yarmouth");
  RealEstateListing whiteHouse = new RealEstateListing(
      false, 1792, 54900, 232372, "Washington, D.C.");
}
