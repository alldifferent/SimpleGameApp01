package com.alldi.simplegameapp01.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alldi.simplegameapp01.R;
import com.alldi.simplegameapp01.datas.Chat;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> { // ArrayAdapter 상속 클래스

    Context mContext; // Context 멤버변수
    List<Chat> mList; // 리스트 멤버변수
    LayoutInflater inf; // LayoutInflater 멤버변수

    public ChatAdapter(Context context, List<Chat> list) { // ChatAdapter 함수 Context와 list 입력받음
        super(context, R.layout.chat_list_item, list); // chat_list_item 레이아웃

        mContext = context; // 입력받은 context 멤버변수에 저장
        mList = list; // 입력받은 list 멤버변수에 저장
        inf = LayoutInflater.from(mContext); // chat_list_item 레이아웃에 쓰여있는 뷰를 뷰객체로 만듬

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // View 불러오는 함수
        View row = convertView; // convertView를 row에 저장

        if (row == null) { // row가 비어있다면
            row = inf.inflate(R.layout.chat_list_item, null); // 비어있는 row 뷰에 chat_list_item 레이아웃 생성
        }

        Chat data = mList.get(position); // 리스트 위치에 있는 데이터를 Chat클래스 data변수에 저장

        FrameLayout userMsgFrameLayout = row.findViewById(R.id.userMsgFrameLayout); // chat_list_item 레이아웃에 있는 userMsgFrameLayout 연결
        FrameLayout computerMsgFrameLayout = row.findViewById(R.id.computerMsgFrameLayout); // chat_list_item 레이아웃에 있는 computerMsgFrameLayout 연결
        TextView userMessageTxt = row.findViewById(R.id.userMessageTxt); // chat_list_item 레이아웃에 있는 userMessageTxt 연결
        TextView computerMessageTxt = row.findViewById(R.id.computerMessageTxt); // chat_list_item 레이아웃에 있는 computerMessageTxt 연결


        if (data.userSaid) { // 유저가 말한다면
            userMsgFrameLayout.setVisibility(View.VISIBLE); // 유저 프레임아웃 보여주기
            computerMsgFrameLayout.setVisibility(View.GONE); // 컴퓨터 프레임아웃 없애기

            userMessageTxt.setText(data.message); // 유저가 입력한 숫자 userMessageTxt에 세팅

        }
        else { // 컴퓨터가 말한다면

            userMsgFrameLayout.setVisibility(View.GONE); // 유저 프레임아웃 없애기
            computerMsgFrameLayout.setVisibility(View.VISIBLE); // 컴퓨터 프레임아웃 보여주기

            computerMessageTxt.setText(data.message); // 컴퓨터가 출력한 숫자 computerMessageTxt에 세팅

        }


        return row; // row값 리턴
    }
}
