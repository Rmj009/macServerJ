package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.Register;
import com.asecl.simdc.org.simdc_project.db.entity.RegisterType;
import com.asecl.simdc.org.simdc_project.db.entity.Role;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.mapper.RegisterMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.RegisterTypeMapper;
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
@Rollback(true)
@Transactional
class SimdcProjectRegisterDaoApplicationTests {

//	@Autowired
//	private RegisterMapper mMapper;
//
//	@Autowired
//	private UserMapper mUserMapper;
//
//	@Autowired
//	private RoleMapper mRoleMapper;
//
//	@Autowired
//	private RegisterTypeMapper mRegisterTypeMapper;
//
//	@Autowired
//	private RegisterMapper mRegisterMapper;
//
//	private User addUser(String Test, String phone, String rName){
//		Role role = new Role();
//		role.setName(rName+"_role");
//		mRoleMapper.insert(role);
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		User u = new User();
//		u.setRealName(Test);
//		u.setNickName(Test);
//		u.setEMail(Test);
//		u.setEmployeeID(Test);
//		u.setPhone(phone);
//		u.setIsActived((short) 1);
//		u.setDisagreeReason("Have a nice day !!!");
//		//把User放在USER test中
//		u.setAgreeUser(null);
//		u.setDisagreeUser(null);
//		u.setLastActivedTime(ts);
//		u.setLastDisagreeActiveTime(ts);
//		u.setRole(role);
//		u.setPassword("1111");
//		u.setSalt("gg");
//		return u;
//	}
//
//	@Test
//	void insertTest() {
//
//		User u = this.addUser("TestAdmin", "12345", "Test11");
//		mUserMapper.insert(u);
//		System.out.println("After User insert id: "+ u.getID());
//
//		RegisterType type = new RegisterType();
//		type.setName("Test11_Type_ID");
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After RegisterType insert id: "+ type.getID());
//		System.out.println("After insert RegisterType id: "+ type.getID());
//
//		Register r = new Register();
//		r.setRegisterUser(u);
//		r.setRegisterType(type);
//		mRegisterMapper.insert(r);
//		System.out.println("Register_User_ID: "+ r.getRegisterUser());
//		System.out.println("Register_Type_ID: "+ r.getRegisterType());
//	}

//	@Test
//	void updateUserTest() {
//		User u = this.addUser("TestAdmin", "12345", "Test11");
//		mUserMapper.insert(u);
//		System.out.println("After User insert id: "+ u.getID());
//
//		RegisterType type = new RegisterType();
//		type.setName("Test11_Type_ID");
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		type.setCreatedTime(ts);
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After RegisterType insert id: "+ type.getID());
//		System.out.println("After insert id: "+ type.getID());
//
//	}
//
//	@Test
//	void insertAndSelectAllCountTest() {
//		RegisterType type = new RegisterType();
//		type.setName("Test11");
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		int count = this.mRegisterTypeMapper.getTotalCount();
//		System.out.println("Total --> " + count);
//	}
//
//	@Test
//	void insertAndSelectByNameTest() {
//		RegisterType type = new RegisterType();
//		type.setName("TestGG");
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		RegisterType r = this.mRegisterTypeMapper.getByName("TestGG");
//		System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//	}
//
//	@Test
//	void searchAndUpdateTest() {
//		RegisterType type = new RegisterType();
//		type.setName("Test");
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		RegisterType r = this.mRegisterTypeMapper.getByName("Test");
//		System.out.println("before update id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		r.setName("TestGGYY");
//		this.mRegisterTypeMapper.update(r);
//		System.out.println("after update id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//	}
//
//	@Test
//	void insertAndDeleteByIDTest() {
//		RegisterType type = new RegisterType();
//		type.setName("TestGG");
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		mRegisterTypeMapper.deleteByID(type.getID());
//
//		List<RegisterType> types = this.mRegisterTypeMapper.getAll();
//		for (RegisterType r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndDeleteByNameTest() {
//		RegisterType type = new RegisterType();
//		type.setName("TestGG");
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		mRegisterTypeMapper.deleteByName(type.getName());
//
//		List<RegisterType> types = this.mRegisterTypeMapper.getAll();
//		for (RegisterType r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}

}
