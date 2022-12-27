package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.Login;
import com.asecl.simdc.org.simdc_project.db.entity.LoginType;
import com.asecl.simdc.org.simdc_project.db.entity.Role;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.mapper.LoginMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.LoginTypeMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.RoleMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.UserMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.util.List;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//全部倒回去
@Rollback(true)
@Transactional
class SimdcProjectLoginDaoApplicationTests {

//	@Autowired
//	private LoginMapper mLoginMapper;
//
//	@Autowired
//	private LoginTypeMapper mLoginTypeMapper;
//
//	@Autowired
//	private RoleMapper mRoleMapper;
//
//	@Autowired
//	private UserMapper mUserMapper;
//
//	private User addUser(String Test, String phone, String rName){
//		Role role = new Role();
//		role.setName(rName+"_role");
//		mRoleMapper.insert(role);
//		User u = new User();
//		u.setRealName(Test+"_real");
//		u.setNickName(Test+"_happy");
//		u.setEMail(Test+"@xxx.com");
//		u.setEmployeeID(Test+"ID");
//		u.setPhone(phone);
//		u.setIsActived((short) 1);
//		u.setDisagreeReason("");
//		u.setAgreeUser(null);
//		u.setDisagreeUser(null);
//		u.setLastActivedTime(null);
//		u.setLastDisagreeActiveTime(null);
//		u.setRole(role);
//		u.setPassword("1111");
//		u.setSalt("gg");
//		return u;
//	}
//
//	@Test
//	void insertTest() {
//		//新增user
//		User u = this.addUser("TestAdmin", "12345", "Admin");
//		mUserMapper.insert(u);
//		System.out.println("After insert id: "+ u.getID());
//		//新增logintype
//		LoginType lt = new LoginType();
//		lt.setName("Test11");
//		mLoginTypeMapper.insert(lt);
//		System.out.println("After insert id: "+ lt.getID());
//		//寫入login
//		Login type = new Login();
//		type.setJwtToken("Test11");
//		type.setLoginUser_ID(u.getID());
//		type.setLoginType_ID(lt.getID());
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		type.setLastModifyTime(ts);
//		mLoginMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//		List<Login> types = this.mLoginMapper.getAll();
//		for (Login r: types) {
//			System.out.println("id: "+ r.getID() + ", JwtToken: " + r.getJwtToken() + ", LastModify: " + r.getLastModifyTime().toLocalDateTime() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndSelectAllTest() {
//		Login type = new Login();
//		type.setJwtToken("Test11");
//		//增加LastModifytime的單元測試
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		type.setLastModifyTime(ts);
//		mLoginMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		List<Login> types = this.mLoginMapper.getAll();
//		for (Login r: types) {
//			System.out.println("id: "+ r.getID() + ", JwtToken: " + r.getJwtToken() + ", LastModify: " + r.getLastModifyTime().toLocalDateTime() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndSelectAllCountTest() {
//		Login type = new Login();
//		type.setJwtToken("Test11");
//		mLoginMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		int count = this.mLoginMapper.getTotalCount();
//		System.out.println("Total --> " + count);
//	}
//
//	@Test
//	void insertAndSelectByNameTest() {
//		Login type = new Login();
//		type.setJwtToken("TestGG");
//		mLoginMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		Login r = this.mLoginMapper.getByJwtToken("TestGG");
//		System.out.println("id: "+ r.getID() + ", JwtToken: " + r.getJwtToken() + ", create: " + r.getCreatedTime().toLocalDateTime());
//	}
//
//	@Test
//	void searchAndUpdateTest() {
//		Login type = new Login();
//		type.setJwtToken("Test");
//		mLoginMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		Login r = this.mLoginMapper.getByJwtToken("Test");
//		System.out.println("before update id: "+ r.getID() + ", name: " + r.getJwtToken() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		r.setJwtToken("TestGGYY");
//		this.mLoginMapper.update(r);
//		System.out.println("after update id: "+ r.getID() + ", name: " + r.getJwtToken() + ", create: " + r.getCreatedTime().toLocalDateTime());
//	}
//
//	@Test
//	void insertAndDeleteByIDTest() {
//		Login type = new Login();
//		type.setJwtToken("TestGG");
//		mLoginMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		mLoginMapper.deleteByID(type.getID());
//
//		List<Login> types = this.mLoginMapper.getAll();
//		for (Login r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getJwtToken() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndDeleteByNameTest() {
//		Login type = new Login();
//		type.setJwtToken("TestGG");
//		mLoginMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		mLoginMapper.deleteByJwtToken(type.getJwtToken());
//
//		List<Login> types = this.mLoginMapper.getAll();
//		for (Login r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getJwtToken() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}

}
