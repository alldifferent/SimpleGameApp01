package com.alldi.simplegameapp01;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alldi.simplegameapp01.adapters.ChatAdapter;
import com.alldi.simplegameapp01.databinding.ActivityMainBinding;
import com.alldi.simplegameapp01.datas.Chat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act; // 메인액티비티 바인딩 변수

    int[] computerExamArray = new int[3]; // 741 => 7, 4, 1

    List<Chat> chatList = new ArrayList<>(); // Chat형 리스트 변수 객체생성
    ChatAdapter mChatAdapter; // ChatAdapter 변수
    int resultCount = 0; // 시도 카운트 정수형 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // onCreate 주기 실행
        bindViews(); // bindViews 함수호출
        setupEvents(); // setupEvents 함수호출
        setValues(); // setValues 함수호출
    }

    @Override
    public void setupEvents() {
        act.inputBtn.setOnClickListener(new View.OnClickListener() { // input 버튼의 클릭 이벤트 처리구간(새로운 리스트 추가 이벤트)
            @Override
            public void onClick(View v) {

                chatList.add(new Chat(true, act.userInputEdt.getText().toString())); // 리스트에 userSaid = true, message = 유저입력값을 문자열로 추가

                mChatAdapter.notifyDataSetChanged(); // ChatAdapter에 데이터 변화알림
                act.messageListView.smoothScrollToPosition(chatList.size()-1); // 리스트뷰 추가시 새로운 리스트로 화면 위치 변경
                checkStrikeAndBalls(); // checkStrikeAndBalls 함수 호출
            }
        });
    }

    void checkStrikeAndBalls() { // 유저의 입력값과 랜덤 세자리 숫자가 정답인지 판단하는 함수

        int[] userInputArray = new int[3]; // 유저가 입력한 숫자를 저장하기 위한 정수형 배열 변수
        int number = Integer.parseInt(act.userInputEdt.getText().toString()); // 문자열을 정수형으로 변환하여 정수형 변수에 저장

        userInputArray[0] = number / 100; // userInputArray 첫번째 배열에 첫번째 입력 숫자 저장
        userInputArray[1] = number / 10 % 10; // userInputArray 두번째 배열에 두번째 입력 숫자 저장
        userInputArray[2] = number % 10; // userInputArray 세번째 배열에 세번째 입력 숫자 저장

        int strikeCount = 0; // 스트라이크 카운트 정수형 변수
        int ballCount = 0; // 볼 카운트 정수형 변수

        for (int k = 0; k < 1; k++) { // 시도 횟수 증가 반복문
            for (int i = 0; i < 3; i++) { // 유저 입력 숫자 반복문
                for (int j = 0; j < 3; j++) { // 랜덤 숫자 반복문

                    if (userInputArray[i] == computerExamArray[j]) { // 유저입력값과 랜덤숫자와 비교
                        if (i == j) { // 자리가 같고 숫자도 같을 때
                            strikeCount++; // 스트라이크 1 증가
                        } else { // 숫자가 같고 자리가 다를 때
                            ballCount++; // 볼 1증가
                        }
                    }
                }
            }
            resultCount++; // 시도 횟수 1증가
        }

        final int strikeFinalCount = strikeCount; // 정수형 스트라이크 상수값 정의
        final int ballFinalCount = ballCount; // 정수형 볼 상수값 정의

        if (strikeCount == 3) { // 세자리 숫자가 위치도 같고 숫자도 같으면
//            Toast.makeText(mContext, "정답입니다! 축하합니다!", Toast.LENGTH_SHORT).show();

            chatList.add(new Chat(false, String.format("정답입니다! 축하합니다!\n총 %d번 시도했습니다.",resultCount))); // 리스트변수에 정답 출력 추가
            mChatAdapter.notifyDataSetChanged(); // ChatAdapter에 데이터 변화 알림
            act.messageListView.smoothScrollToPosition(chatList.size()-1); // 새로운 리스트 위치로 스크롤 내림

        }
        else { // 정답이 아니면
//            Toast.makeText(mContext, String.format("%dS, %dB 입니다.", strikeCount, ballCount), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() { // 딜레이 이벤트
                @Override
                public void run() {

                    chatList.add(new Chat(false, String.format("%dS, %dB 입니다.", strikeFinalCount, ballFinalCount))); // 리스트에 결과값 출력 문자열과 false 값 저장
                    mChatAdapter.notifyDataSetChanged(); //  ChatAdapter에 데이터 변화 알림
                    act.messageListView.smoothScrollToPosition(chatList.size()-1); // 새로운 리스트 위치로 스크롤 내림

                }
            }, 1000); // 1000ms 만큼 딜레이


        }

    }

    @Override
    public void setValues() {
        makeExam(); // makeExam 함수 호출

        mChatAdapter = new ChatAdapter(mContext, chatList); // ChatAdapter 객체 생성
        act.messageListView.setAdapter(mChatAdapter); // 리스트뷰에 ChatAdapter 연결

    }

    void makeExam() { // 세자리 숫자 생성함수

        while (true) {
            int randomNumber = (int) (Math.random() * 899 + 100); // 세자리 숫자 랜덤출력 정수형 변수

            int[] tempNumber = new int[3]; // 세자리 숫자를 담을 정수형 배열변수

            tempNumber[0] = randomNumber / 100; // 첫번째 숫자 가공된 값을 tempNumber 첫번째열에 저장
            tempNumber[1] = randomNumber / 10 % 10; // 두번째 숫자 가공 값을 tempNumber 두번째열에 저장
            tempNumber[2] = randomNumber % 10; // 세번째 숫자 가공 값을 tempNumber 세번째열에 저장

            boolean isDuplOk = true; // 세자리의 숫자가 중복이 나왔는지 판별하기 위한 boolean형 변수
            if (tempNumber[0] == tempNumber[1] || tempNumber[1] == tempNumber[2] || tempNumber[0] == tempNumber[2]) { // tempNumber 세자리가 모두 중복인지 아닌지 판별하는 조건식
                isDuplOk = false; // 세자리 숫자가 하나라도 중복이라면 isDuplOk에 false 값 대입
            }

            boolean isZeroOk = true; // 세자리의 숫자가 0이 나왔는지 판별하기 위한 boolean형 변수
            if (tempNumber[0] == 0 || tempNumber[1] == 0 || tempNumber[2] == 0) { // tempNumber 세자리가 모두 0인지 아닌지 판별하는 조건식
                isZeroOk = false; // 세자리 숫자가 하나라도 0이라면 isZeroOk에 false 값 대입
            }

            if (isDuplOk && isZeroOk) { // 중복과 0이 true 라면(세자리 숫자가 중복도 없고 0도 없음)
                computerExamArray[0] = tempNumber[0]; // computerExamArray 첫번째 배열에 tempNumber 첫번째 값 대입
                computerExamArray[1] = tempNumber[1]; // computerExamArray 두번째 배열에 tempNumber 두번째 값 대입
                computerExamArray[2] = tempNumber[2]; // computerExamArray 세번째 배열에 tempNumber 세번째 값 대입

                Log.d("정답 숫자", randomNumber+" 입니다."); // 정답 출력 Log

                break; // 반복문 탈출
            }

        }


    }


    @Override
    public void bindViews() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_main); // 바인딩 변수에 나타낼 메인액티비티 레이아웃 대입
    }
}