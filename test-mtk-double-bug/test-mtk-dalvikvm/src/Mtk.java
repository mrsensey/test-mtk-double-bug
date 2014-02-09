
public class Mtk {

  private static final int ITERATIONS = 100;

  private static final int DELAY = 10;

  private int mFails;

  public void test() {
    Calc calc = new Calc();

    System.out.print("\nExpected value: ");
    System.out.println(Calc.EXPECTED);
    System.out.println();
    
    for (int it = 0; it < ITERATIONS; it++) {
        double X = calc.calcX();
        sleep(DELAY);
        if (X < Calc.EXPECTED_LOW || X > Calc.EXPECTED_HIGH) {
          mFails++;
          System.err.print(it);
          System.err.print(" iteration FAILED with: ");
          System.err.println(X);
        }
    }
    System.out.println("\nFinished test with " + mFails + " fails\n");
  }

  private void sleep(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public static void main(String[] args) {
    Mtk mtk = new Mtk();
    mtk.test();
  }

}
