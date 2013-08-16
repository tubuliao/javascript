package com.isoftstone.tyw.dto.info;

public class HotWordsDTO {
    private String word;
    private int num;
    public HotWordsDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public HotWordsDTO(String word) {
        super();
        this.word = word;
    }

    public HotWordsDTO(String word, int num) {
        super();
        this.word = word;
        this.num = num;
    }

    public String getHotword() {
        return word;
    }

    public void setHotword(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    
}
