package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.LoginType;
import com.asecl.simdc.org.simdc_project.db.entity.RegisterType;
import com.asecl.simdc.org.simdc_project.db.mapper.LoginTypeMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.RegisterTypeMapper;
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
class SimdcProjectLoginTypeDaoApplicationTests {

//	@Autowired
//	private LoginTypeMapper mLoginTypeMapper;
//
//	@Test
//	void insertTest() {
//		LoginType type = new LoginType();
//		type.setName("Test11");
//		mLoginTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//	}
//
//	@Test
//	void insertAndSelectAllTest() {
//		LoginType type = new LoginType();
//		type.setName("Test11");
//		mLoginTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		List<LoginType> types = this.mLoginTypeMapper.getAll();
//		for (LoginType r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndSelectAllCountTest() {
//		LoginType type = new LoginType();
//		type.setName("Test11");
//		mLoginTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		int count = this.mLoginTypeMapper.getTotalCount();
//		System.out.println("Total --> " + count);
//	}
//
//	@Test
//	void insertAndSelectByNameTest() {
//		LoginType type = new LoginType();
//		type.setName("TestGG");
//		mLoginTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		LoginType r = this.mLoginTypeMapper.getByName("TestGG");
//		System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//	}
//
//	@Test
//	void searchAndUpdateTest() {
//		LoginType type = new LoginType();
//		type.setName("Test");
//		mLoginTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		LoginType r = this.mLoginTypeMapper.getByName("Test");
//		System.out.println("before update id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		r.setName("TestGGYY");
//		this.mLoginTypeMapper.update(r);
//		System.out.println("after update id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//	}
//
//	@Test
//	void insertAndDeleteByIDTest() {
//		LoginType type = new LoginType();
//		type.setName("TestGG");
//		mLoginTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		mLoginTypeMapper.deleteByID(type.getID());
//
//		List<LoginType> types = this.mLoginTypeMapper.getAll();
//		for (LoginType r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndDeleteByNameTest() {
//		LoginType type = new LoginType();
//		type.setName("TestGG");
//		mLoginTypeMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		mLoginTypeMapper.deleteByName(type.getName());
//
//		List<LoginType> types = this.mLoginTypeMapper.getAll();
//		for (LoginType r: types) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}

}
