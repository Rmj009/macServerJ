package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.RegisterType;
import com.asecl.simdc.org.simdc_project.db.entity.Role;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.mapper.RegisterTypeMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.RoleMapper;
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
class SimdcProjectRegisterTypeDaoApplicationTests {

//	@Autowired
//	private RegisterTypeMapper mRegisterTypeMapper;
//
//	@Test
//	void insertTest() {
//		RegisterType type = new RegisterType();
//		type.setName("Test11");
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//		RegisterType tt = this.mRegisterTypeMapper.getByName("Test11");
//		System.out.println("After insert CreatedTime: "+ tt.getCreatedTime());
//		List<RegisterType> types = this.mRegisterTypeMapper.getAll();
//		for (RegisterType r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndSelectAllTest() {
//		RegisterType type = new RegisterType();
//		type.setName("Test11");
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		type.setCreatedTime(ts);
//		mRegisterTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		List<RegisterType> types = this.mRegisterTypeMapper.getAll();
//		for (RegisterType r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
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
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		type.setCreatedTime(ts);
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
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		type.setCreatedTime(ts);
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
