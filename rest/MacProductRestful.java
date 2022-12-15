package com.asecl.simdc.org.simdc_project.rest;

//import com.asecl.simdc.org.simdc_project.db.service.ProductITService;
import com.asecl.simdc.org.simdc_project.file.XmlMacProduct;
        import com.asecl.simdc.org.simdc_project.security.LockManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RestController
@RequestMapping(value="/Product")
public class MacProductRestful {

    @Autowired
    private XmlMacProduct mMacProduct;

    @Value("${product.mac.file.path}")
    private String mFilePath;

//    @Autowired
//    private ProductITService mITService;

    @Autowired
    private LockManager mLock;

    @GetMapping(value = "/insertMac/{filename:.+}")
    @ResponseBody
    public ResponseEntity<String> insertMac(@PathVariable(value = "filename") String filename) {
//        try{
//            String fileFullPath = this.mFilePath + "/" + filename;
//            this.mMacProduct.setFilePath(fileFullPath);
//            List<Product_MacAddress> macs = this.mMacProduct.read();
//            String key = "CreateMacProduct_";
//            if(macs.size() > 0){
//                key += macs.get(0).getPID() + "_" + macs.get(0).getLotCode();
//                mLock.TryLock(key, new ILockCallback<Boolean>() {
//                    @Override
//                    public Boolean exec() {
//                        return mITService.CreateProductMacAddress(macs);
//                    }
//                });
//            }else{
//                throw new RuntimeException("Empty item error !!");
//            }
//
//            return new ResponseEntity<>("OK", HttpStatus.OK);
//        }catch(Exception ex){
//            throw new QLException(ex);
//        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
