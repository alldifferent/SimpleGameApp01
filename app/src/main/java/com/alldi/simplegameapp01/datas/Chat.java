package com.alldi.simplegameapp01.datas;

public class Chat {

    public boolean userSaid; // 유저 입력 true, 컴퓨터 입력 false
    public String message; // message 문자열 출력

    public Chat(boolean userSaid, String message) {
        this.userSaid = userSaid;
        this.message = message;
    }
}
