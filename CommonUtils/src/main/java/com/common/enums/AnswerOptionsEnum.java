package com.common.enums;

public enum AnswerOptionsEnum
{
    A("1","A"),
    B("2","B"),
    C("3","C"),
    D("4","D"),
    E("5","E"),
    F("6","F"),
    G("7","G"),
    H("8","H"),
    I("9","I"),
    J("10","J"),
    K("11","K"),
    L("12","L"),
    M("13","M"),
    N("14","N"),
    O("15","O"),
    P("16","P"),
    Q("17","Q"),
    R("18","R"),
    S("19","S"),
    T("20","T"),
    U("21","U"),
    V("22","V"),
    W("23","W"),
    X("24","X"),
    Y("25","Y"),
    Z("26","Z");
    
    /**
     * 索引
     */
    private String key;
    
    /**
     * 文本 
     */
    private String value;
    
    private AnswerOptionsEnum(String key,String value) {
        this.key = key;
        this.value = value;
    }
    
    public static String fomartAnswerText(String answer)
    {
        if (answer.contains(",")) {
            String[] answers = answer.split(",");

            StringBuilder builder = new StringBuilder();
            for (String key : answers) {
                String value = getValue(key);
                builder.append(value).append(",");
            }

            return builder.substring(0, builder.length() - 1);
        }

        return getValue(answer);
    }

    public static String getValue(String key) {
        String value = "";
        for(AnswerOptionsEnum optionsEnum : AnswerOptionsEnum.values()) {
            if(optionsEnum.key.equals(key)) {
                value = optionsEnum.value;
                break;
            }
        }

        return value;
    }

    public static String fomartAnswerIndex(String answer)
    {
        if (answer.contains("#")) {
            String[] answers = answer.split("#");

            StringBuilder builder = new StringBuilder();
            for (String key : answers) {
                String value = getValue(key);
                builder.append(value).append(",");
            }

            return builder.substring(0, builder.length() - 1);
        }

        return getKey(answer);
    }

    public static String getKey(String value) {
        String key = "";
        for(AnswerOptionsEnum optionsEnum : AnswerOptionsEnum.values()) {
            if(optionsEnum.value.equals(value)) {
                key = optionsEnum.key;
                break;
            }
        }

        return key;
    }
    
    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}
