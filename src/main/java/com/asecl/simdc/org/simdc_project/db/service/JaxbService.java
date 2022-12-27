package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.exception.QLException;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;


@Service
public class JaxbService {


    /**
     * 將對象根據路徑轉換成xml文件
     *
     * @param obj
     * @param path
     */
    public static String convertToXml(Object obj, String path) throws IOException {
        FileWriter fw = null;
        try {
            // 利用jdk中自帶的轉換類實現
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            // 格式化xml輸出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // 將對象轉換成輸出流形式的xml
            // 創建輸出流
            fw = new FileWriter(path);
            marshaller.marshal(obj, fw);
        } catch (JAXBException e) {
            throw new QLException(e);
        } catch (IOException e) {
            throw new QLException(e);
        }
        finally {
            fw.close();
        }
        return "1";
    }

    /**
     * 將file類型的xml轉換成對象
     *
     * @param clazz
     * @param xmlPath
     * @return
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        FileReader fr = new FileReader(xmlPath);
        return unmarshaller.unmarshal(fr);
    }

   
}
