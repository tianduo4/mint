package com.jspgou.common.util;

import java.io.PrintStream;

public class CnToSpell {
    private char[] chartable;
    private char[] alphatable;
    private int[] table;

    public CnToSpell() {
        this.chartable =
                new char[]{
                        '啊', 33453, '擦', '搭', 34558, '发', '噶', '哈', '哈',
                        '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
                        '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'};

        this.alphatable =
                new char[]{
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                        's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        this.table = new int[27];

        for (int i = 0; i < 27; i++)
            this.table[i] = gbValue(this.chartable[i]);
    }

    public char charAlpha(char ch) {
        if ((ch >= 'a') && (ch <= 'z')) {
            return ch;
        }
        if ((ch >= 'A') && (ch <= 'Z'))
            return (char) (ch - 'A' + 97);
        if ((ch >= '0') && (ch <= '9'))
            return ch;
        int gb = gbValue(ch);
        if (gb < this.table[0]) {
            return '0';
        }
        for (int i = 0; i < 26; i++)
            if (match(i, gb))
                break;
        int i = 0; //TODO
        if (i >= 26) {
            return '0';
        }
        return this.alphatable[i];
    }

    public String getBeginCharacter(String SourceStr) {
        String Result = "";
        int StrLength = SourceStr.length();
        try {
            for (int i = 0; i < StrLength; i++)
                Result = Result + charAlpha(SourceStr.charAt(i));
        } catch (Exception e) {
            Result = "";
        }
        return Result;
    }

    private boolean match(int i, int gb) {
        if (gb < this.table[i])
            return false;
        int j = i + 1;

        while ((j < 26) && (this.table[j] == this.table[i])) j++;
        if (j == 26) {
            return gb <= this.table[j];
        }
        return gb < this.table[j];
    }

    private int gbValue(char ch) {
        String str = new String();
        str = str + ch;
        try {
            byte[] bytes = str.getBytes("GB2312");
            if (bytes.length < 2)
                return 0;
            return (bytes[0] << 8 & 0xFF00) + (bytes[1] &
                    0xFF);
        } catch (Exception e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        CnToSpell obj1 = new CnToSpell();
        System.out.println(obj1.getBeginCharacter("测试数据8ADGaadf"));
    }
}

