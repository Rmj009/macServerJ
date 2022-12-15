package com.asecl.simdc.org.simdc_project.file;

import com.asecl.simdc.org.simdc_project.db.entity.MacType;
import com.asecl.simdc.org.simdc_project.db.entity.Product_MacAddress;
import com.asecl.simdc.org.simdc_project.db.mapper.MacTypeMapper;
import com.asecl.simdc.org.simdc_project.db.service.MacTypeService;
import com.asecl.simdc.org.simdc_project.file.xml.Jaxb;
import com.asecl.simdc.org.simdc_project.file.xml.JaxbList;
import com.asecl.simdc.org.simdc_project.util.UtilFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlMacProduct extends IMacProductFile {
    @Autowired
    private MacTypeService mMacTypeService;

    @Override
    public List<Product_MacAddress> read() throws JAXBException {
        super.read();
        JAXBContext jaxbContext = JAXBContext.newInstance(JaxbList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JaxbList jaxbList = (JaxbList)jaxbUnmarshaller.unmarshal(this.mInputStream);
        if(jaxbList.getJaxbList().size() == 0){
            return null;
        }

        List<Product_MacAddress> items = new ArrayList<>();
        MacType macType = null;

        if((macType = this.mMacTypeService.getByName(jaxbList.getJaxbList().get(0).getMacType())) == null){
            throw new RuntimeException("Not Found MacType : " + jaxbList.getJaxbList().get(0).getMacType());
        }

        for(Jaxb jaxb : jaxbList.getJaxbList()){
            Product_MacAddress mac = new Product_MacAddress();
            if(jaxb.getSipLicense().trim().toLowerCase().equals("null") || jaxb.getSipLicense().trim().toLowerCase().length() ==0){
                mac.setSipLicense(null);
            }else{
                mac.setSipLicense(jaxb.getSipLicense());
            }
            mac.setAddress(jaxb.getAddress());
            List<String> macstrs = UtilFunc.CheckMacAddressFormat(jaxb.getAddress());
            mac.setAddressDecimal(UtilFunc.MacStringToMacLong(macstrs));
            mac.setPO(jaxb.getPO());
            mac.setLotCode(jaxb.getLotCode());
            mac.setMacType(macType);
            items.add(mac);
        }
        return items;
    }
}
