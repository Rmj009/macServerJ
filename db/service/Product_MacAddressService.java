package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.mapper.MacStatusMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.Product_MacAddressMapper;
import com.asecl.simdc.org.simdc_project.file.XmlMacProduct;
import com.asecl.simdc.org.simdc_project.ftp.FTPUploadUtils;
import com.asecl.simdc.org.simdc_project.graphql.entity.customEnum.EnumIncreaseType;
import com.asecl.simdc.org.simdc_project.graphql.entity.customEnum.EnumMacType;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.asecl.simdc.org.simdc_project.http.HttpService;
import com.asecl.simdc.org.simdc_project.util.Constant;
import com.asecl.simdc.org.simdc_project.util.ILockCallback;
import com.asecl.simdc.org.simdc_project.security.LockManager;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class Product_MacAddressService {
    @Autowired
    @Lazy
    private Product_MacAddressMapper mMapper;

    @Autowired
    @Lazy
    private MacStatusMapper mMacStatusMapper;

    @Autowired
    @Lazy
    private HttpService mHttpService;


    @Autowired
    @Lazy
    private SqlSessionFactory mSqlSessionFactory;

    @Autowired
    @Lazy
    private MacAddressService mMacAddressService;

    @Autowired
    @Lazy
    private MacTypeService mMacTypeService;

    @Autowired
    @Lazy
    private XmlMacProduct mMacProduct;

    @Autowired
    private LockManager mLock;

    @Autowired
    private FTPUploadUtils mFTPUploadUtils;

//    @Autowired
//    private ProductITService mITService;


    @Value("${mac.insert.sql_batch_count}")
    private long MAC_INSERT_BATCH_COUNT;

    @Value("${product.mac.file.path}")
    private String mMacFilePath;

    @Transactional
    public void insert(Product_MacAddress type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<Product_MacAddress> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public void update(Product_MacAddress type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id, long macTypeID){
        this.mMapper.deleteByID(id, macTypeID);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public InputStream GetFileStreamFromITServer(SyncPoInput model) throws FileNotFoundException, IOException, JAXBException {
       // InputStream fstream =  this.mFTPUploadUtils.getFileByFtp("/Policy.xml","D:/Policy.xml");
       // return this.mFTPUploadUtils.getFileByFtp("/Policy.xml");
        String filePath = mMacFilePath+ File.separator + model.getPo() + "_" + model.getLotcode() + ".xml";
        File xmlFile = new File(filePath);
        if(!xmlFile.exists()){
            throw new RuntimeException("Lotcode : " + model.getLotcode() + "PO : " + model.getPo() +"xml file not found !");

        }
        return new FileInputStream(xmlFile);
//        return this.mFTPUploadUtils.getFileByFtp("/Policy.xml");
    }

    public boolean CreateProductMacAddress(List<Product_MacAddress> list){
        if(list.size() == 0){
            throw new RuntimeException("CreateProductMacAddress empty item error !!");
        }

        String po = list.get(0).getPO();
        String lotCode = list.get(0).getLotCode();

        Product_MacAddress macAddress = this.mMapper.getFirstMacByLotcodeAndPO(lotCode, po);
        if(macAddress != null){
            return true;
        }

        MacStatus initStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare);

        List<List<Product_MacAddress>> macs = Lists.partition(list, (int) MAC_INSERT_BATCH_COUNT);

        for(List<Product_MacAddress> currList : macs){
            this.mMapper.fastBatchInsertById(currList, list.get(0).getMacType().getID(), initStatus.getID());
        }
        return true;
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public ResponseModel InsertMacAddressFromITFile(InputStream stream , SyncPoInput poinput) throws JAXBException {
        this.mMacProduct.setInputStream(stream);
        List<Product_MacAddress> macs = this.mMacProduct.read();
        this.mMacProduct.close();

        String key = "CreateMacProduct_";
        if(macs.size() > 0){
            key += macs.get(0).getPO() + "_" + macs.get(0).getLotCode();
            mLock.TryLock(key, new ILockCallback<Boolean>() {
                @Override
                public Boolean exec() {
                    return CreateProductMacAddress(macs);
                }
            });
        }else{
            throw new RuntimeException("Empty item error !!");
        }

//        String filePath = mMacFilePath+ File.separator + poinput.getPo() + "_" + poinput.getLotcode() + ".xml";
//        ProcessBuilder builder = new ProcessBuilder();
//
//        try
//        {
//            builder.command("/bin/bash", "-c", "chmod 644 "+filePath+"| xargs chown root "+filePath);
//            Process process = builder.start();
//
//
//        }
//        catch (Exception e)
//        {
//            throw new QLException(e);
//        }


        ResponseModel model = new ResponseModel();
        model.setResult(true);
        return model;
    }


//    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
//    public void fastBatchInsert(String eType, String Lotcode, long startMac, String pid, String SipLicense,long macTypeId){
//
//    }

//    @Transactional(rollbackFor = Exception.class)
    public List<String> _checkMacAddressFormat(String mac){
        if(mac.length() != 17){
            return null;
        }

        List<String> result = new ArrayList<String>();

        String split[] = mac.split("-");
        if(split.length != 6){
            return null;
        }

        for(int i = 0; i < 6 ; i++){
            long chkNum = Long.parseLong(split[i].trim(), 16);
            if(chkNum < 0 || chkNum > 0xff){
                return null;
            }else{
                result.add(split[i].trim());
            }
        }
        return result;
    }

//    @Transactional(rollbackFor = Exception.class)
    public boolean _checkMacTypeInput(String macTypeInput){
        boolean isExist = false;
        for(EnumMacType item: EnumMacType.values()){
            if(macTypeInput.toLowerCase().equals(item.toString().toLowerCase())){
                isExist =  true;
                break;
            }
        }
        return isExist;
    }

//    @Transactional(rollbackFor = Exception.class)
    public boolean _checkMacIncreaseTypeInput(String macIncreaseTypeInput){
        boolean isExist = false;

        for(EnumIncreaseType item: EnumIncreaseType.values()){
            if(macIncreaseTypeInput.toLowerCase().equals(item.toString().toLowerCase())){
                isExist =  true;
                break;
            }
        }
        return isExist;
    }

}
