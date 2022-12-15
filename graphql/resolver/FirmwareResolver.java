package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.service.FirmwareService;
import com.asecl.simdc.org.simdc_project.db.service.UserService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.FirmwareCreationInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.FirmwareDeleteInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.FirmwareQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.asecl.simdc.org.simdc_project.util.Constant;
import com.github.pagehelper.PageHelper;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class FirmwareResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private UserService mUserService;

    @Autowired
    private ResourceLoader mResourceLoader;

    @Autowired
    private FirmwareService mFirmwareService;

//    @Value("${fw.static.urlPath}")
//    private String mFwUrlPath;

//    把fun移動到servive start
//    private File getNewFwFile(String fileName) throws IOException{
//        File directory = mResourceLoader.getResource(Constant.FW_DIR_PATH).getFile();
//        return new File(directory, fileName);
//    }
//
//    private void deleteFwFileFromDisk(String fileName) throws IOException {
////        File fwFile = this.getNewFwFile(fileName);
////        if(fwFile.exists()){
////            fwFile.delete();
////        }
//        deleteFile(fileName);
//    }
//
//    private boolean deleteFile(String fileName) throws IOException {
//        int loop = 0;
//        boolean isOk = false;
//        do{
//            File fwFile = this.getNewFwFile(fileName);
//            if(fwFile.exists()){
//                isOk = fwFile.delete();
//            }else{
//                isOk = true;
//                break;
//            }
//            loop++;
//        }while(!isOk && loop < 5);
//        return isOk;
//    }
//
//    private void saveFwFileToDisk(Part attachmentPart, String fileName) throws IOException{
//        if(!deleteFile(fileName)){
//            throw new RuntimeException("Delete Upload File SaveName : " + fileName + " Fail !!");
//        }
//
//        File fwFile = this.getNewFwFile(fileName);
//        String fullPath = fwFile.getPath();
//        attachmentPart.write(fullPath);
//    }
//
//    private String getSaveFwMD5(String fileName) throws Exception{
//        // MD5 碼
//        String md5Code = "";
//
//        File f1 = getNewFwFile(fileName);
//
//        if( f1.exists() )
//        {
//            // 取得檔案的 MD5
//            InputStream fis =  new FileInputStream(f1);
//
//            byte[] buffer = new byte[1024];
//            java.security.MessageDigest complete = java.security.MessageDigest.getInstance("MD5");
//            int numRead;
//
//            do {
//                numRead = fis.read(buffer);
//                if (numRead > 0) {
//                    complete.update(buffer, 0, numRead);
//                }
//            } while (numRead != -1);
//
//            fis.close();
//            byte[] b = complete.digest();
//            for (int i=0; i < b.length; i++) {
//                md5Code += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
//            }
//        }
//        else
//        {
//            throw new RuntimeException("Upload File SaveName : " + fileName + " Is Not Exists !!");
//        }
//        return md5Code;
//    }
//    把fun移動到servive END

    public ResponseModel CreateFirmware(Part part, FirmwareCreationInput input, DataFetchingEnvironment env) {
        try{
            User owner = null;
            if((owner = this.mUserService.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
                throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
            }

//            暫時註解調檔案上傳功能 2020/6/18 start
//            Part attachmentPart= env.getArgument("file");
//            if(attachmentPart.getInputStream().available() == 0){
//                throw new RuntimeException("Upload File Size Is Zero Fail !!");
//            }
//            END

            if(mFirmwareService.getByNameAndVersion(input.getName(), input.getVersion()) != null){
                throw new RuntimeException("FW Name : " + input.getName() + " Version : " + input.getVersion() + " Is Already Exists !!");
            }

//            暫時註解調檔案上傳功能 2020/6/18 start
//            String currentNewFileName = input.getVersion().replace(".", "_") + "__" + input.getSaveName();
//
//            mFirmwareService.saveFwFileToDisk(attachmentPart, currentNewFileName);
//
//            String currentMD5 = mFirmwareService.getSaveFwMD5(currentNewFileName);
//
//            if(!currentMD5.toLowerCase().equals(input.getMd5().toLowerCase())){
//                throw new RuntimeException("FW MD5 Is Not Match !!");
//            }
//
//            String urlPath = Constant.FW_URL + currentNewFileName;

            Firmware fw = new Firmware();
            fw.setCreatedOwner(owner);
//            fw.setMD5(currentMD5);
            fw.setMD5("Default");
            fw.setName(input.getName());
            fw.setVersion(input.getVersion());
//            fw.setPath(urlPath);
            fw.setPath("Default");
            if(input.getRemark() != null){
                fw.setRemark(input.getRemark());
            }
//            END

            this.mFirmwareService.insert(fw);
        }catch(Exception e){
//            try{
//                this.deleteFwFileFromDisk(input.getSaveName());
//            }catch (Exception ex){ }
            throw new QLException(e);
        }

        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

    public ResponseModel DeleteFirmware(FirmwareDeleteInput input){
        try{
            Firmware f = null;
            if((f = this.mFirmwareService.getByNameAndVersion(input.getName(), input.getVersion())) == null){
                throw new RuntimeException("Firmware Name : " + input.getName() + " And Version : " + input.getVersion() + " Is Not Exists!!");
            }

            if(this.mFirmwareService.getCountByNameInTestconfiguration(input.getName(), input.getVersion()) > 0)
            {
                throw new RuntimeException("Firmware id is binding ， You need unbinding !!");
            }

            this.mFirmwareService.deleteByID(f.getID());
//            暫時註解調檔案上傳功能 2020/6/18 start
//            String fileName = f.getPath().replace(Constant.FW_URL, "");
//            this.deleteFwFileFromDisk(fileName);
//            END
        }
        catch (Exception e)
        {
            throw new QLException(e);
        }
        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

    public List<FirmwareInfo> QueryFirmware(FirmwareQueryInput input){
        List<FirmwareInfo> fw = null;
        try{
            if(input == null){
                fw = this.mFirmwareService.getAllInfo();
            }else{
                if(input.getPageNumber() > 0 && input.getPageSize() > 0){
                    PageHelper.startPage(input.getPageNumber(), input.getPageSize());
                }
                fw = this.mFirmwareService.get(input);
            }
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return fw;
    }

}
