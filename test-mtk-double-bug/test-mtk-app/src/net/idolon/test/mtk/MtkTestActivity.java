
package net.idolon.test.mtk;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class MtkTestActivity extends Activity {
  
  static final double A = 0.5;
  static final double B = 1;
  static final double D = 16384;
  
  static final double COMPUTED_CONST = A + B / D;

  /*
   * Main calculation where bug occurs
   */
  public double calcX() {
    double t = B;
    double X = A + t / D;
    return X;
  }

  class TestRunnable implements Runnable {

    static final double EP = 0.00000000001;

    static final double EXPECTED_LOW = COMPUTED_CONST - EP;
    
    static final double EXPECTED_HIGH = COMPUTED_CONST + EP;

    public void run() {
      for (int i = 0; i < SMALL_ITERATION; i++) {
        double A = calcX();

        if (A < EXPECTED_LOW || A > EXPECTED_HIGH) {
          mFailedInCycle = true;
          mFails++;
          mEdit.getText().append("FAILED on " + mIteration + " iteration with: " + A + '\n');
        }
        mIteration++;
      }

      if (mIteration % 5000 == 0) {
        if (mFailedInCycle) {
          mFailedInCycle = false;
        } else {
          mEdit.getText().append("passed " + mIteration + " iterations\n");
        }
      }

      if (mIteration < mIterationsCount) {
        mHandler.postDelayed(new TestRunnable(), DELAY);
      } else {
        mEdit.getText().append("\nFinished test with " + mFails + " fails");
      }
    }

  }

  public void onTestClick(View v) {
    startTest(IT_10K);
  }

  public void onTestClick100(View v) {
    startTest(IT_100K);
  }

  private void startTest(int iterationsCount) {
    Editable text = mEdit.getText();
    text.clear();
    text.append("\nStarting " + iterationsCount + " iterations test...");
    text.append("\n\nExpected result " + COMPUTED_CONST + "\n\n");
    mIteration = 0;
    mFails = 0;
    mFailedInCycle = false;
    mIterationsCount = iterationsCount;
    mHandler.postDelayed(new TestRunnable(), 100);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mHandler = new Handler(getMainLooper());
    mEdit = (EditText) findViewById(R.id.edtText1);
  }

  private static final int IT_10K = 1000;

  private static final int IT_100K = 100000;

  private static final int SMALL_ITERATION = 50;

  private static final int DELAY = 10;

  private int mIteration;

  private int mFails;

  private boolean mFailedInCycle;

  private Handler mHandler;

  private int mIterationsCount;

  private EditText mEdit;


}

