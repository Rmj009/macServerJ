package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.mapper.TrayTypeMapper;
import com.asecl.simdc.org.simdc_project.db.service.*;
import com.asecl.simdc.org.simdc_project.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import com.hans.grpcserver.grpc.GreeterGrpc;
import com.hans.grpcserver.grpc.HelloProto;

@Component
@Order(value=1)
public class SidmcDBInitApplication implements CommandLineRunner {

    @Value("${fw.upload.filePath}")
    private String mFwFilePath;

    @Autowired
    private ResourceLoader mResourceLoader;

    @Autowired
    private RoleService mRoleDao;

    @Autowired
    private RegisterTypeService mRegisterTypeDao;

    @Autowired
    private MacTypeService mMacTypeDao;

    @Autowired
    private TestResultStatusService mTestResultDao;

    @Autowired
    private PasswordEncoder mEncoder;

    @Autowired
    private UserService mUserDao;

    @Autowired
    private RegisterService mRegisterDao;

    @Autowired
    private TestConfigurationStatusService mTestStatusDao;

    @Autowired
    private MacStatusService mMacStatusDao;

    @Autowired
    private MacDispatchTypeService mMacDispatchTypeDao;

    @Autowired
    private TrayTypeService mTrayTypeDao;

    @Override
    public void run(String... args) throws Exception {

        this.createFwPath();

        this.addRoleToDB("Admin");
        this.addRoleToDB("FW");
        this.addRoleToDB("SW");
        this.addRoleToDB("OP");
        this.addRoleToDB("Guest");

        this.addRegisterType("PC");
        this.addRegisterType("IOS");
        this.addRegisterType("Android");
        this.addRegisterType("Linux");
        this.addRegisterType("MacOS");

        this.addTestConfigStatus(Constant.TestConfigStatus_TestPrepare);
        this.addTestConfigStatus(Constant.TestConfigStatus_Testing);
        this.addTestConfigStatus(Constant.TestConfigStatus_TestFinish);
        this.addTestConfigStatus(Constant.TestConfigStatus_TestFail);

        this.addMacType(Constant.MAC_TYPE_WIFI);
        this.addMacType(Constant.MAC_TYPE_BT);

        this.addMacStatus(Constant.MacStatus_UsePrepare);
        this.addMacStatus(Constant.MacStatus_Using);
        this.addMacStatus(Constant.MacStatus_Used);
        this.addMacStatus(Constant.MacStatus_UsedFail);

        this.addMacDispathcType(Constant.EnumIncreateType_Normal);
        this.addMacDispathcType(Constant.EnumIncreateType_NormalRotateId);
        this.addMacDispathcType(Constant.EnumIncreateType_Odd);
        this.addMacDispathcType(Constant.EnumIncreateType_OddRotateId);
        this.addMacDispathcType(Constant.EnumIncreateType_Even);
        this.addMacDispathcType(Constant.EnumIncreateType_EvenRotateId);
        this.addMacDispathcType(Constant.EnumIncreateType_OddAndEven);
        this.addMacDispathcType(Constant.EnumIncreateType_OddAndEvenRotateId);

        this.addTestResult(Constant.TestResult_Pass);
        this.addTestResult(Constant.TestResult_Fail);

        this.addTrayType(Constant.TrayType_Pass);
        this.addTrayType(Constant.TrayType_FailStart1);
        this.addTrayType(Constant.TrayType_FailStart2);
        this.addTrayType(Constant.TrayType_FailStart3);
        this.addTrayType(Constant.TrayType_FailStart4);
        this.addTrayType(Constant.TrayType_FailStart5);
        this.addTrayType(Constant.TrayType_FailStart6);
        this.addTrayType(Constant.TrayType_FailStart7);
        this.addTrayType(Constant.TrayType_FailStart8);
        this.addTrayType(Constant.TrayType_FailStart9);

        this.addNewUser("Admin", "01978", "admin01234", "Admin", "01978");
        this.addRegister("01978", "PC");
    }

    private void createFwPath() throws IOException {

        String fwDir = "file:" + mFwFilePath;
        File dir = mResourceLoader.getResource(fwDir).getFile();
        if(!dir.exists() || !dir.isDirectory()){
            if(!dir.mkdirs()){
                throw new RuntimeException("Create Fw Path: " + fwDir + "Fail !!");
            }
        }
        Constant.FW_DIR_PATH = fwDir;
    }

    private void addRoleToDB(String name){
        Role role = new Role();
        role.setName(name);
        this.mRoleDao.insert(role);
    }

    private void addRegisterType(String name){
        RegisterType type = new RegisterType();
        type.setName(name);
        this.mRegisterTypeDao.insert(type);
    }

    private void addMacType(String macType){
        MacType type = new MacType();
        type.setName(macType);
        this.mMacTypeDao.insert(type);
    }

    private void addMacDispathcType(String macType){
        MacDispatchType type = new MacDispatchType();
        type.setName(macType);
        this.mMacDispatchTypeDao.insert(type);
    }

    private void addTestResult(String result){
        TestResultStatus type = new TestResultStatus();
        type.setResult(result);
        this.mTestResultDao.insert(type);
    }

    private void addTestConfigStatus(String status){
        TestConfigurationStatus type = new TestConfigurationStatus();
        type.setName(status);
        this.mTestStatusDao.insert(type);
    }

    private void addMacStatus(String status){
        MacStatus type = new MacStatus();
        type.setName(status);
        this.mMacStatusDao.insert(type);
    }

    private void addTrayType(String typestr){
        TrayType type = new TrayType();
        type.setName(typestr);
        this.mTrayTypeDao.insert(type);
    }

    private void addNewUser(String name, String employeeID, String pwd, String role, String phone) throws Exception {
        Role roleCls = this.mRoleDao.getByName(role);
        if(roleCls == null){
            throw new Exception("createNewUser role null : " + role);
        }
        User u = new User();
        u.setRealName(name+"_real");
        u.setNickName(name+"_nick");
        u.setEMail(name + "@xxx.com");
        u.setEmployeeID(employeeID);
        u.setPhone(phone);
        u.setIsActived((short) 1);
        u.setDisagreeReason("");
        u.setAgreeUser(null);
        u.setDisagreeUser(null);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        u.setLastActivedTime(ts);
        u.setLastDisagreeActiveTime(null);
        u.setRole(roleCls);
        u.setPassword(mEncoder.encode(pwd.trim()));
        this.mUserDao.insert(u);
    }

    private void addRegister(String employeeID, String registerType) throws Exception {
        User u = this.mUserDao.getByEmployeeID(employeeID);
        if(u == null){
            throw new Exception("addRegister user getByEmployeeID null : " + employeeID);
        }

        RegisterType type = this.mRegisterTypeDao.getByName(registerType);
        if(type == null){
            throw new Exception("addRegister RegisterType getByName null : " + registerType);
        }

        Register register = new Register();
        register.setRegisterType(type);
        register.setRegisterUser(u);
        this.mRegisterDao.insert(register);
    }
}
