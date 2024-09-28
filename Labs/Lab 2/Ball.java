import tester.Tester;
import java.awt.Color;
 
class Ball {
  CartPt center;
  int radius;
  Color color;
 
  Ball(CartPt center, int radius, Color color) {
    this.center = center;
    this.radius = radius;
    this.color = color;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.center ...      -- CartPt
   *  ... this.radius ...      -- int
   *  ... this.color ...       -- Color
   *  
   *  Methods:
   *  ... this.area() ...             -- double 
   *  ... this.circumference() ...    -- double
   *  ... this.distanceTo(Ball) ...   -- double
   *  ... this.overlaps(Ball) ...     -- boolean
   *  
   *  Methods of fields:
   *  ... this.center.distance(CartPt)  -- double
   */
 
  // Returns the area of this ball
  double area() {
    return Math.PI * Math.pow(this.radius, 2);
  }
  
  // Returns the circumference of this ball
  double circumference() { 
    return Math.PI * this.radius * 2;
  }
  
  // Returns the distance between the center of this Ball and that Ball
  double distanceTo(Ball that) { 
    return this.center.distance(that.center);
  }
  
  //Determines if this Ball overlaps that ball
  boolean overlaps(Ball that) {  
    return Math.abs(this.radius - that.radius) < this.distanceTo(that) 
        && this.distanceTo(that) < this.radius + that.radius;
  }
}
 
class CartPt {
  int x;
  int y;
 
  CartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }
  /* TEMPLATE:
   *  Fields:
   *  ... this.x ...      -- int
   *  ... this.y ...      -- int
   *  
   *  Methods:
   *  ... this.distance(CartPt) -- double
   */
  
  // Determines the distance between two points
  double distance(CartPt that) {
    return Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
  }
}
 
class ExamplesBall {
  Ball b = new Ball(new CartPt(0, 0), 5, Color.BLUE);
  Ball c = new Ball(new CartPt(5,5), 5, Color.RED);
  Ball d = new Ball(new CartPt(10,0), 2, Color.ORANGE);
 
  boolean testBall(Tester t) {
    return t.checkInexact(b.area(), 78.5, 0.001) 
        && t.checkInexact(b.circumference(), 31.4, 0.001) 
        && t.checkInexact(b.distanceTo(c), 7.071, 0.001) 
        && t.checkExpect(b.overlaps(c), true) 
        && t.checkExpect(b.overlaps(d), false);
  }
}