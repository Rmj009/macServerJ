package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.Role;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.mapper.RoleMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.UserMapper;
import com.asecl.simdc.org.simdc_project.security.jwt.JwtUserDetail;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMybatis
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@MybatisTest
//@WebMvcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(true)
@Transactional
public class SimdcProjectUserModelDaoApplicationTests {
    @Autowired
    private UserMapper mUserMapper;

    @Autowired
    private RoleMapper mRoleMapper;

    @Autowired
    AuthenticationManager mAuthenticationManager;

    @Autowired
    private PasswordEncoder mEncoder;

    private User addUser(String Test, String phone, String pwd, String rName){
        Role role = new Role();
        role.setName(rName+"_role");
        mRoleMapper.insert(role);
        User u = new User();
        u.setRealName(Test+"_real");
        u.setNickName(Test+"_happy");
        u.setEMail(Test+"@xxx.com");
        u.setEmployeeID(Test+"ID");
        u.setPhone(phone);
        u.setIsActived((short) 1);
        u.setDisagreeReason("");
        u.setAgreeUser(null);
        u.setDisagreeUser(null);
        u.setLastActivedTime(null);
        u.setLastDisagreeActiveTime(null);
        u.setRole(role);
        u.setPassword(mEncoder.encode(pwd.trim()));
        return u;
    }

    private Authentication authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        return mAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Test
    public void insertAndVaildTest() {
        User u = this.addUser("TestAdmin", "12345", "12345", "Admin");
        mUserMapper.insert(u);
        System.out.println("After insert id: "+ u.getID());

        Authentication auth = null;
        try{
            auth = this.authenticate(u.getEmployeeID(), "12345");
        }catch(org.springframework.security.authentication.BadCredentialsException ex){
            System.out.println("org.springframework.security.authentication.BadCredentialsException : "+ ex.getMessage());
            return;
        }catch (Exception ex){
            System.out.println("Exception : "+ ex.getMessage());
            return;
        }

        JwtUserDetail userPrincipal = (JwtUserDetail) auth.getPrincipal();
        System.out.println("After User EmployeeID: " + userPrincipal.getUser().getEmployeeID() + ", Role : " +  userPrincipal.getUser().getRole().getName());

        int count = this.mUserMapper.getCountByEmployeeID("TestAdminID");
        System.out.println("ccc : "+ count);
    }


//
//    @Test
//    public void insertAndGetUserByNickNameTest() {
//        User u = this.addUser("TestAdmin", "12345", "Admin");
//        mUserMapper.insert(u);
//        System.out.println("After insert id: "+ u.getID());
//
//        User u2 = this.addUser("Test2", "123456", "SW");
//        u2.setAgreeUser(u);
//        u2.setLastActivedTime(new Timestamp(System.currentTimeMillis()));
//        mUserMapper.insert(u2);
//        System.out.println("After insert id2: "+ u.getID());
//
//        User u3 = this.addUser("Test3", "1234567", "SW");
//        u3.setDisagreeUser(u2);
//        u3.setLastDisagreeActiveTime(new Timestamp(System.currentTimeMillis()));
//        u3.setDisagreeReason("Test Test Test");
//        mUserMapper.insert(u3);
//        System.out.println("After insert id3: "+ u.getID());
//
//        User uu = this.mUserMapper.getByNickName("Test3_happy");
//
//        System.out.println("RealName: "+ uu.getRealName());
//
//        if(uu.getRole() != null){
//            System.out.println("Role: "+ uu.getRole().getName());
//        }else{
//            System.out.println("Role: null");
//        }
//
//        if(uu.getAgreeUser() != null){
//            System.out.println("AgreeName: " + uu.getAgreeUser().getRealName());
//        }else{
//            System.out.println("AgreeName: null");
//        }
//
//        if(uu.getAgreeUser() != null && uu.getAgreeUser().getRole() != null){
//            System.out.println("AgreeRole: " + uu.getAgreeUser().getRole().getName());
//            System.out.println("AgreeRoleCT: " + uu.getAgreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("AgreeRole: null");
//        }
//
//        if(uu.getDisagreeUser() != null){
//            System.out.println("DisAgreeName: " + uu.getDisagreeUser().getRealName());
//        }else{
//            System.out.println("DisAgreeName: null");
//        }
//
//        if(uu.getDisagreeUser() != null && uu.getDisagreeUser().getRole() != null){
//            System.out.println("DisAgreeRole: " + uu.getDisagreeUser().getRole().getName());
//            System.out.println("DisAgreeRoleCT: " + uu.getDisagreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("DisAgreeRole: null");
//        }
//    }
//
//    @Test
//    public void insertAndGetUserByEMailTest() {
//        User u = this.addUser("TestAdmin", "12345", "Admin");
//        mUserMapper.insert(u);
//        System.out.println("After insert id: "+ u.getID());
//
//        User u2 = this.addUser("Test2", "123456", "SW");
//        u2.setAgreeUser(u);
//        u2.setLastActivedTime(new Timestamp(System.currentTimeMillis()));
//        mUserMapper.insert(u2);
//        System.out.println("After insert id2: "+ u.getID());
//
//        User u3 = this.addUser("Test3", "1234567", "SW");
//        u3.setDisagreeUser(u2);
//        u3.setLastDisagreeActiveTime(new Timestamp(System.currentTimeMillis()));
//        u3.setDisagreeReason("Test Test Test");
//        mUserMapper.insert(u3);
//        System.out.println("After insert id3: "+ u.getID());
//
//        User uu = this.mUserMapper.getByEMail("Test2@xxx.com");
//
//        System.out.println("EMail: "+ uu.getEMail());
//
//        if(uu.getRole() != null){
//            System.out.println("Role: "+ uu.getRole().getName());
//        }else{
//            System.out.println("Role: null");
//        }
//
//        if(uu.getAgreeUser() != null){
//            System.out.println("AgreeName: " + uu.getAgreeUser().getRealName());
//        }else{
//            System.out.println("AgreeName: null");
//        }
//
//        if(uu.getAgreeUser() != null && uu.getAgreeUser().getRole() != null){
//            System.out.println("AgreeRole: " + uu.getAgreeUser().getRole().getName());
//            System.out.println("AgreeRoleCT: " + uu.getAgreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("AgreeRole: null");
//        }
//
//        if(uu.getDisagreeUser() != null){
//            System.out.println("DisAgreeName: " + uu.getDisagreeUser().getRealName());
//        }else{
//            System.out.println("DisAgreeName: null");
//        }
//
//        if(uu.getDisagreeUser() != null && uu.getDisagreeUser().getRole() != null){
//            System.out.println("DisAgreeRole: " + uu.getDisagreeUser().getRole().getName());
//            System.out.println("DisAgreeRoleCT: " + uu.getDisagreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("DisAgreeRole: null");
//        }
//    }
//
//    @Test
//    public void insertAndGetUserByPhoneTest() {
//        User u = this.addUser("TestAdmin", "12345", "Admin");
//        mUserMapper.insert(u);
//        System.out.println("After insert id: "+ u.getID());
//
//        User u2 = this.addUser("Test2", "123456", "SW");
//        User au = new User();
//        au.setEMail("TestAdmin");
//        u2.setAgreeUser(au);
//        u2.setLastActivedTime(new Timestamp(System.currentTimeMillis()));
//        mUserMapper.insert(u2);
//        System.out.println("After insert id2: "+ u.getID());
//
//        User u3 = this.addUser("Test3", "1234567", "SW");
//        u3.setDisagreeUser(au);
//        u3.setLastDisagreeActiveTime(new Timestamp(System.currentTimeMillis()));
//        u3.setDisagreeReason("Test Test Test");
//        mUserMapper.insert(u3);
//        System.out.println("After insert id3: "+ u.getID());
//
//        User uu = this.mUserMapper.getByPhone("123456");
//
//        System.out.println("RealName: "+ uu.getRealName());
//
//        if(uu.getRole() != null){
//            System.out.println("Role: "+ uu.getRole().getName());
//        }else{
//            System.out.println("Role: null");
//        }
//
//        if(uu.getAgreeUser() != null){
//            System.out.println("AgreeName: " + uu.getAgreeUser().getRealName());
//        }else{
//            System.out.println("AgreeName: null");
//        }
//
//        if(uu.getAgreeUser() != null && uu.getAgreeUser().getRole() != null){
//            System.out.println("AgreeRole: " + uu.getAgreeUser().getRole().getName());
//            System.out.println("AgreeRoleCT: " + uu.getAgreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("AgreeRole: null");
//        }
//
//        if(uu.getDisagreeUser() != null){
//            System.out.println("DisAgreeName: " + uu.getDisagreeUser().getRealName());
//        }else{
//            System.out.println("DisAgreeName: null");
//        }
//
//        if(uu.getDisagreeUser() != null && uu.getDisagreeUser().getRole() != null){
//            System.out.println("DisAgreeRole: " + uu.getDisagreeUser().getRole().getName());
//            System.out.println("DisAgreeRoleCT: " + uu.getDisagreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("DisAgreeRole: null");
//        }
//    }
//
//    @Test
//    public void insertAndGetUserByEmployeeIDTest() {
//        User u = this.addUser("TestAdmin", "12345", "Admin");
//        mUserMapper.insert(u);
//        System.out.println("After insert id: "+ u.getID());
//
//        User u2 = this.addUser("Test2", "123456", "SW");
//        u2.setAgreeUser(u);
//        u2.setLastActivedTime(new Timestamp(System.currentTimeMillis()));
//        mUserMapper.insert(u2);
//        System.out.println("After insert id2: "+ u.getID());
//
//        User u3 = this.addUser("Test3", "1234567", "SW");
//        u3.setDisagreeUser(u2);
//        u3.setLastDisagreeActiveTime(new Timestamp(System.currentTimeMillis()));
//        u3.setDisagreeReason("Test Test Test");
//        mUserMapper.insert(u3);
//        System.out.println("After insert id3: "+ u.getID());
//
//        User uu = this.mUserMapper.getByEmployeeID("Test2ID");
//
//        System.out.println("RealName: "+ uu.getRealName());
//
//        if(uu.getRole() != null){
//            System.out.println("Role: "+ uu.getRole().getName());
//        }else{
//            System.out.println("Role: null");
//        }
//
//        if(uu.getAgreeUser() != null){
//            System.out.println("AgreeName: " + uu.getAgreeUser().getRealName());
//        }else{
//            System.out.println("AgreeName: null");
//        }
//
//        if(uu.getAgreeUser() != null && uu.getAgreeUser().getRole() != null){
//            System.out.println("AgreeRole: " + uu.getAgreeUser().getRole().getName());
//            System.out.println("AgreeRoleCT: " + uu.getAgreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("AgreeRole: null");
//        }
//
//        if(uu.getDisagreeUser() != null){
//            System.out.println("DisAgreeName: " + uu.getDisagreeUser().getRealName());
//        }else{
//            System.out.println("DisAgreeName: null");
//        }
//
//        if(uu.getDisagreeUser() != null && uu.getDisagreeUser().getRole() != null){
//            System.out.println("DisAgreeRole: " + uu.getDisagreeUser().getRole().getName());
//            System.out.println("DisAgreeRoleCT: " + uu.getDisagreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("DisAgreeRole: null");
//        }
//    }
//
//    @Test
//    public void insertAndUpdateTest() {
//        User u = this.addUser("TestAdmin", "12345", "Admin");
//        mUserMapper.insert(u);
//        System.out.println("After insert id: "+ u.getID());
//
//        User uu = this.mUserMapper.getByEmployeeID("TestAdminID");
//
//        System.out.println("After Insert  -->  ");
//        System.out.println("RealName: "+ uu.getRealName());
//
//        if(uu.getRole() != null){
//            System.out.println("Role: "+ uu.getRole().getName());
//        }else{
//            System.out.println("Role: null");
//        }
//
//        if(uu.getAgreeUser() != null){
//            System.out.println("AgreeName: " + uu.getAgreeUser().getRealName());
//        }else{
//            System.out.println("AgreeName: null");
//        }
//
//        if(uu.getAgreeUser() != null && uu.getAgreeUser().getRole() != null){
//            System.out.println("AgreeRole: " + uu.getAgreeUser().getRole().getName());
//            System.out.println("AgreeRoleCT: " + uu.getAgreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("AgreeRole: null");
//        }
//
//        if(uu.getDisagreeUser() != null){
//            System.out.println("DisAgreeName: " + uu.getDisagreeUser().getRealName());
//        }else{
//            System.out.println("DisAgreeName: null");
//        }
//
//        if(uu.getDisagreeUser() != null && uu.getDisagreeUser().getRole() != null){
//            System.out.println("DisAgreeRole: " + uu.getDisagreeUser().getRole().getName());
//            System.out.println("DisAgreeRoleCT: " + uu.getDisagreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("DisAgreeRole: null");
//        }
//
//        uu.setRealName("GGYY");
//        Role newRole = this.mRoleMapper.getByName("FW");
//        uu.setRole(newRole);
//        this.mUserMapper.update(uu);
//
//        uu = this.mUserMapper.getByEmployeeID("TestAdminID");
//
//        System.out.println("After Update  -->  ");
//        System.out.println("RealName: "+ uu.getRealName());
//
//        if(uu.getRole() != null){
//            System.out.println("Role: "+ uu.getRole().getName());
//        }else{
//            System.out.println("Role: null");
//        }
//
//        if(uu.getAgreeUser() != null){
//            System.out.println("AgreeName: " + uu.getAgreeUser().getRealName());
//        }else{
//            System.out.println("AgreeName: null");
//        }
//
//        if(uu.getAgreeUser() != null && uu.getAgreeUser().getRole() != null){
//            System.out.println("AgreeRole: " + uu.getAgreeUser().getRole().getName());
//            System.out.println("AgreeRoleCT: " + uu.getAgreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("AgreeRole: null");
//        }
//
//        if(uu.getDisagreeUser() != null){
//            System.out.println("DisAgreeName: " + uu.getDisagreeUser().getRealName());
//        }else{
//            System.out.println("DisAgreeName: null");
//        }
//
//        if(uu.getDisagreeUser() != null && uu.getDisagreeUser().getRole() != null){
//            System.out.println("DisAgreeRole: " + uu.getDisagreeUser().getRole().getName());
//            System.out.println("DisAgreeRoleCT: " + uu.getDisagreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("DisAgreeRole: null");
//        }
//
//    }
//
//    @Test
//    public void insertAndDeleteTest() {
//        User u = this.addUser("TestAdmin", "12345", "Admin");
//        mUserMapper.insert(u);
//        System.out.println("After insert id: "+ u.getID());
//        int count = this.mUserMapper.getTotalCount();
//        System.out.println("After Insert Total Count -->  " + count);
//
//        User uu = this.mUserMapper.getByEmployeeID("TestAdminID");
//        this.mUserMapper.deleteByID(uu.getID());
//        count = this.mUserMapper.getTotalCount();
//        System.out.println("After Delete Total Count -->  " + count);
//    }
//
//    @Test
//    public void insertAndDeleteRoleTest() {
//        User u = this.addUser("TestAdmin", "12345", "Admin");
//        mUserMapper.insert(u);
//        User uu = this.mUserMapper.getByEmployeeID("TestAdminID");
//
//        System.out.println("After Insert  -->  ");
//        System.out.println("RealName: "+ uu.getRealName());
//
//        if(uu.getRole() != null){
//            System.out.println("Role: "+ uu.getRole().getName());
//        }else{
//            System.out.println("Role: null");
//        }
//
//        if(uu.getAgreeUser() != null){
//            System.out.println("AgreeName: " + uu.getAgreeUser().getRealName());
//        }else{
//            System.out.println("AgreeName: null");
//        }
//
//        if(uu.getAgreeUser() != null && uu.getAgreeUser().getRole() != null){
//            System.out.println("AgreeRole: " + uu.getAgreeUser().getRole().getName());
//            System.out.println("AgreeRoleCT: " + uu.getAgreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("AgreeRole: null");
//        }
//
//        if(uu.getDisagreeUser() != null){
//            System.out.println("DisAgreeName: " + uu.getDisagreeUser().getRealName());
//        }else{
//            System.out.println("DisAgreeName: null");
//        }
//
//        if(uu.getDisagreeUser() != null && uu.getDisagreeUser().getRole() != null){
//            System.out.println("DisAgreeRole: " + uu.getDisagreeUser().getRole().getName());
//            System.out.println("DisAgreeRoleCT: " + uu.getDisagreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("DisAgreeRole: null");
//        }
//
//        this.mRoleMapper.deleteByName("Admin_role");
//
//        uu = this.mUserMapper.getByEmployeeID("TestAdminID");
//        this.mUserMapper.deleteByID(uu.getID());
//        System.out.println("After Delete Role  -->  ");
//        System.out.println("RealName: "+ uu.getRealName());
//
//        if(uu.getRole() != null){
//            System.out.println("Role: "+ uu.getRole().getName());
//        }else{
//            System.out.println("Role: null");
//        }
//
//        if(uu.getAgreeUser() != null){
//            System.out.println("AgreeName: " + uu.getAgreeUser().getRealName());
//        }else{
//            System.out.println("AgreeName: null");
//        }
//
//        if(uu.getAgreeUser() != null && uu.getAgreeUser().getRole() != null){
//            System.out.println("AgreeRole: " + uu.getAgreeUser().getRole().getName());
//            System.out.println("AgreeRoleCT: " + uu.getAgreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("AgreeRole: null");
//        }
//
//        if(uu.getDisagreeUser() != null){
//            System.out.println("DisAgreeName: " + uu.getDisagreeUser().getRealName());
//        }else{
//            System.out.println("DisAgreeName: null");
//        }
//
//        if(uu.getDisagreeUser() != null && uu.getDisagreeUser().getRole() != null){
//            System.out.println("DisAgreeRole: " + uu.getDisagreeUser().getRole().getName());
//            System.out.println("DisAgreeRoleCT: " + uu.getDisagreeUser().getRole().getCreatedTime().toString());
//        }else{
//            System.out.println("DisAgreeRole: null");
//        }
//    }
}
