package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.Role;
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
class SimdcProjectRoleDaoApplicationTests {

//	@Autowired
//	private RoleMapper mRoleMapper;
//
//	@Test
//	void insertTest() {
//		Role role = new Role();
//		role.setName("Test11");
//		mRoleMapper.insert(role);
//		System.out.println("After insert id: "+ role.getID());
//	}
//
//	@Test
//	void insertAndSelectAllTest() {
//		Role role = new Role();
//		role.setName("Test11");
//		mRoleMapper.insert(role);
//		System.out.println("After insert id: "+ role.getID());
//
//		List<Role> roles = this.mRoleMapper.getAll();
//		for (Role r: roles) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndSelectAllCountTest() {
//		Role role = new Role();
//		role.setName("Test111");
//		mRoleMapper.insert(role);
//		System.out.println("After insert id: "+ role.getID());
//
//		int count = this.mRoleMapper.getTotalCount();
//		System.out.println("Total --> " + count);
//	}
//
//	@Test
//	void insertAndSelectByNameTest() {
//		Role role = new Role();
//		role.setName("TestGG");
//		mRoleMapper.insert(role);
//		System.out.println("After insert id: "+ role.getID());
//
//		Role r = this.mRoleMapper.getByName("TestGG");
//		System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//	}
//
//	@Test
//	void searchAndUpdateTest() {
//		Role r = new Role();
//		r.setName("Test");
//		mRoleMapper.insert(r);
//		System.out.println("After insert id: "+ r.getID());
//		Role role = this.mRoleMapper.getByName("Test");
//		role.setName("TestGGYY");
//		this.mRoleMapper.update(role);
//		System.out.println("after update id: "+ role.getID() + ", name: " + role.getName() + ", create: " + role.getCreatedTime().toLocalDateTime());
//	}
//
//	@Test
//	void insertAndDeleteByIDTest() {
//		Role role = new Role();
//		role.setName("TestGG");
//		mRoleMapper.insert(role);
//		System.out.println("After insert id: "+ role.getID());
//
//		mRoleMapper.deleteByID(role.getID());
//
//		List<Role> roles = this.mRoleMapper.getAll();
//		for (Role r: roles) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndDeleteByNameTest() {
//		Role role = new Role();
//		role.setName("TestGG");
//		mRoleMapper.insert(role);
//		System.out.println("After insert id: "+ role.getID());
//
//		mRoleMapper.deleteByName(role.getName());
//
//		List<Role> roles = this.mRoleMapper.getAll();
//		for (Role r: roles) {
//			System.out.println("id: "+ r.getID() + ", name: " + r.getName() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}

}
