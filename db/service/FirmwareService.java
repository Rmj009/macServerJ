package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.Firmware;
import com.asecl.simdc.org.simdc_project.db.entity.FirmwareInfo;
import com.asecl.simdc.org.simdc_project.db.entity.SoftwareInfo;
import com.asecl.simdc.org.simdc_project.db.mapper.FirmwareMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.FirmwareQueryInput;
import com.asecl.simdc.org.simdc_project.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FirmwareService {
    @Autowired
    private FirmwareMapper mMapper;

    @Autowired
    private ResourceLoader mResourceLoader;

    @Transactional
    public void insert(Firmware type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<Firmware> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public Firmware getByNameAndVersion(String name, String version){
        return this.mMapper.getByNameAndVersion(name, version);
    }

    @Transactional
    public void update(Firmware type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByNameAndVersion(String name, String version) { this.mMapper.deleteByNameAndVersion(name, version); }

    @Transactional
    public List<FirmwareInfo> get(FirmwareQueryInput input){
        return this.mMapper.get(input);
    }

    @Transactional
    public List<FirmwareInfo> getAllInfo(){
        return this.mMapper.getAllInfo();
    }

    @Transactional
    public int getCountByNameInTestconfiguration(String name, String version){
        return this.mMapper.getCountByNameInTestconfiguration(name, version);
    }

    @Transactional
    public File getNewFwFile(String fileName) throws IOException {
        File directory = mResourceLoader.getResource(Constant.FW_DIR_PATH).getFile();
        return new File(directory, fileName);
    }

    @Transactional
    public void deleteFwFileFromDisk(String fileName) throws IOException {
//        File fwFile = this.getNewFwFile(fileName);
//        if(fwFile.exists()){
//            fwFile.delete();
//        }
        deleteFile(fileName);
    }

    @Transactional
    public boolean deleteFile(String fileName) throws IOException {
        int loop = 0;
        boolean isOk = false;
        do{
            File fwFile = this.getNewFwFile(fileName);
            if(fwFile.exists()){
                isOk = fwFile.delete();
            }else{
                isOk = true;
                break;
            }
            loop++;
        }while(!isOk && loop < 5);
        return isOk;
    }

    @Transactional
    public void saveFwFileToDisk(Part attachmentPart, String fileName) throws IOException{
        if(!deleteFile(fileName)){
            throw new RuntimeException("Delete Upload File SaveName : " + fileName + " Fail !!");
        }

        File fwFile = this.getNewFwFile(fileName);
        String fullPath = fwFile.getPath();
        attachmentPart.write(fullPath);
    }

    @Transactional
    public String getSaveFwMD5(String fileName) throws Exception{
        // MD5 碼
        String md5Code = "";

        File f1 = getNewFwFile(fileName);

        if( f1.exists() )
        {
            // 取得檔案的 MD5
            InputStream fis =  new FileInputStream(f1);

            byte[] buffer = new byte[1024];
            java.security.MessageDigest complete = java.security.MessageDigest.getInstance("MD5");
            int numRead;

            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            fis.close();
            byte[] b = complete.digest();
            for (int i=0; i < b.length; i++) {
                md5Code += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
        }
        else
        {
            throw new RuntimeException("Upload File SaveName : " + fileName + " Is Not Exists !!");
        }
        return md5Code;
    }

    @Transactional
    public int getCountByNameAndVersoin(String name, String version){
        return this.mMapper.getCountByNameAndVersoin(name, version);
    }


}
