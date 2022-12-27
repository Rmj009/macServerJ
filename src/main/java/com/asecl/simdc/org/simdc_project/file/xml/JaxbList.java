package com.asecl.simdc.org.simdc_project.file.xml;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "JaxbList") //XML文件中的根標識
public class JaxbList  {

    @XmlElement(name = "Jaxb")
    private List<Jaxb> JaxbList = null;

    public List<Jaxb> getJaxbList() {
        return JaxbList;
    }

    public void setEmployees(List<Jaxb> JaxbList) {
        this.JaxbList = JaxbList;
    }

}
