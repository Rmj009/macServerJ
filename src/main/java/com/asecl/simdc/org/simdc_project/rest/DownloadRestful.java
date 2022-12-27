package com.asecl.simdc.org.simdc_project.rest;

import com.asecl.simdc.org.simdc_project.db.service.JaxbService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.file.xml.Jaxb;
import com.asecl.simdc.org.simdc_project.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.asecl.simdc.org.simdc_project.db.entity.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class DownloadRestful {

    @Autowired
    private ResourceLoader mResourceLoader;

    @Autowired
    private JaxbService mJaxbService;

    @GetMapping(value = Constant.FW_URL + "{fileName:.+}")
    public ResponseEntity<Resource> GetFile(@PathVariable("fileName") String fileName) throws IOException {
        InputStream in = mResourceLoader.getResource(Constant.FW_DIR_PATH + "/" + fileName).getInputStream();
        InputStreamResource resource = new InputStreamResource(in);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\"")
            .contentLength(in.available())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource);
    }

//
//    restful get、post測試用接口 2020/9/15
//

//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    public ResponseEntity<String> findByFileName(@RequestParam(value = "filename", required = true) String filename) {
//        try{
//            String path = "C://fw/"+filename;
//            Jaxb policyObj = (Jaxb)mJaxbService.convertXmlFileToObject(Jaxb.class, path);
//            System.out.println(policyObj.toString());
//            return ResponseEntity.status(HttpStatus.OK).body("Your age is ");
//        }catch(Exception ex){
//            throw new QLException(ex);
//        }
//    }
//
    @RequestMapping(value = "/it", method = RequestMethod.POST)
    public ResponseEntity<String> findByFile(@RequestParam(value = "PO", required = true) String PO ,
                                             @RequestParam(value = "LotCode", required = true) String LotCode ,
                                             @RequestParam(value = "Count", required = true) Integer Count)
    {
        try{
            System.out.println("------------------------");
            System.out.println(PO);
            System.out.println(LotCode);
            System.out.println(Count);
            System.out.println("------------------------");
            return ResponseEntity.status(HttpStatus.OK).body("post");
        }catch(Exception ex){
            throw new QLException(ex);
        }
    }

}
