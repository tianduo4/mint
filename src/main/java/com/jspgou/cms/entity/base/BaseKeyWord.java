package com.jspgou.cms.entity.base;

import com.jspgou.cms.entity.KeyWord;

import java.io.Serializable;

public abstract class BaseKeyWord
        implements Serializable {
    public static String REF = "KeyWord";
    public static String PROP_KEYWORD = "keyword";
    public static String PROP_TIMES = "times";
    public static String PROP_ID = "id";

    private int hashCode = -2147483648;
    private Integer id;
    private String keyword;
    private Integer times;

    public BaseKeyWord() {
        initialize();
    }

    public BaseKeyWord(Integer id) {
        setId(id);
        initialize();
    }

    public BaseKeyWord(Integer id, String keyword, Integer times) {
        setId(id);
        setKeyword(keyword);
        setTimes(times);
        initialize();
    }

    protected void initialize() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.hashCode = -2147483648;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getTimes() {
        return this.times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof KeyWord)) return false;

        KeyWord keyWord = (KeyWord) obj;
        if ((getId() == null) || (keyWord.getId() == null)) return false;
        return getId().equals(keyWord.getId());
    }

    public int hashCode() {
        if (-2147483648 == this.hashCode) {
            if (getId() == null) return super.hashCode();

            String hashStr = getClass().getName() + ":" + getId().hashCode();
            this.hashCode = hashStr.hashCode();
        }

        return this.hashCode;
    }

    public String toString() {
        return super.toString();
    }
}

