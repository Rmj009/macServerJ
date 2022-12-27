package com.asecl.simdc.org.simdc_project.graphql.entity.customEnum;

public enum EnumMacType {
    Wifi("Wifi"),
    BT("BT");
    private final String value;
    private EnumMacType(String s) {
        value = s;
    }
    public String toString(){
        return value;
    }
}
