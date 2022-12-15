package com.asecl.simdc.org.simdc_project.graphql.entity.customEnum;

import com.asecl.simdc.org.simdc_project.util.Constant;

public enum EnumIncreaseType {
    NormalRotateId(Constant.EnumIncreateType_NormalRotateId),
    Normal(Constant.EnumIncreateType_Normal),
    OddAndEvenRotateId(Constant.EnumIncreateType_OddAndEvenRotateId),
    OddAndEven(Constant.EnumIncreateType_OddAndEven),
    OddRotateId(Constant.EnumIncreateType_OddRotateId),
    Odd(Constant.EnumIncreateType_Odd),
    EvenRotateId(Constant.EnumIncreateType_EvenRotateId),
    Even(Constant.EnumIncreateType_Even);
    private final String value;
    private EnumIncreaseType(String s) {
        value = s;
    }
    public String toString(){
        return value;
    }
}
