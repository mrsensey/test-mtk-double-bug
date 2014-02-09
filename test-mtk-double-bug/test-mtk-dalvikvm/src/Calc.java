
public class Calc {

  static final double A = 0.5;
  static final double B = 1;
  static final double D = 16384;

  static final double EXPECTED = A + B / D;

  static final double EP = 1e-12;

  static final double EXPECTED_LOW = EXPECTED - EP;

  static final double EXPECTED_HIGH = EXPECTED + EP;

  /*
   * Main calculation where bug occurs
   */
  public double calcX() {
    double t = B;
    double X = A + t / D;
    return X;
  }

}
