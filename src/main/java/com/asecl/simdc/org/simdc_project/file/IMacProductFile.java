package com.asecl.simdc.org.simdc_project.file;

import com.asecl.simdc.org.simdc_project.db.entity.Product_MacAddress;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public abstract class IMacProductFile {

    private String filePathStr;
    private File mFile = null;
    protected InputStream mInputStream = null;

//    public File getFile() {
//        if(mFile == null){
//            this.mFile = new File(this.filePathStr);
//        }
//        return mFile;
//    }
//
//    public void setFilePath(String filePath) {
//        this.mFile = null;
//        this.filePathStr = filePath;
//    }
//
//    protected boolean fileIsExists(){
//        return this.getFile().exists() && this.getFile().isFile();
//    }
    public void setInputStream(InputStream stream){
        this.mInputStream = stream;
    }

    public List<Product_MacAddress> read() throws JAXBException {
//        if(!fileIsExists()){
//            throw new RuntimeException("FilePath : " + this.filePathStr + " is not exists !!");
//        }
        if(this.mInputStream == null){
            throw new RuntimeException("FileInputStream not input !!");
        }
        return null;
    }

    public void close(){
        try{
            if(this.mInputStream != null && this.mInputStream.available() > 0){
                this.mInputStream.close();
            }
        }catch(Exception ex){ }
    }
}
