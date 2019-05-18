package com.alldi.simplegameapp01;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alldi.simplegameapp01.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    int[] computerExamArray = new int[3];
    ActivityMainBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

        makeExam();

    }

    @Override
    public void bindViews() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_main);

    }
    void makeExam(){

        while (true) {

            int randomNum = (int) (Math.random() * 899 + 100);

            int[] tempNum = new int[3];
            tempNum[0] = randomNum / 100;
            tempNum[1] = randomNum / 10 % 10;
            tempNum[2] = randomNum % 10;

            boolean isDuplOk = true;

            if (tempNum[0] == tempNum[1] || tempNum[1] == tempNum[2] || tempNum[0] == tempNum[2]) {
                isDuplOk = false;
            }

            boolean isZeroOk = true;
            if (tempNum[0] == 0 || tempNum[1] == 0 || tempNum[2] == 0) {

                isZeroOk = false;
            }

            if (isDuplOk && isZeroOk) {

                computerExamArray[0] = tempNum[0];
                computerExamArray[1] = tempNum[1];
                computerExamArray[2] = tempNum[2];

                break;
            }

        }
    }
}
