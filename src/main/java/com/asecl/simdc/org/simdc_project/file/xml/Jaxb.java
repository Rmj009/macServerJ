package com.asecl.simdc.org.simdc_project.file.xml;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Jaxb") //XML文件中的根標識
@XmlType(propOrder = {
        "PO",
        "Address",
        "LotCode",
        "SipLicense",
        "MacType",
}) //控制JAXB 綁定類中屬性和字段的排序
public class Jaxb implements Serializable {

    private static final long serialVersionUID = 1L;


    // PID
    private String PO;

    // Address
    private String Address;

    // LotCode
    private String LotCode;

    // SipLicense
    private String SipLicense;

    // MacType
    private String MacType;

}
