package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.mapper.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@AutoConfigureMybatis
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//不能加
//@MybatisTest
//@WebMvcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
@Transactional
public class SimdcProjectTestConfigurationDaoApplicationTests {

	@Autowired
	private TestConfigurationMapper mTestConfigurationMapper;
//
    @Autowired
    private RoleMapper mRoleMapper;

    @Autowired
    private PasswordEncoder mEncoder;

    @Autowired
    private UserMapper mUserMapper;

    @Autowired
    private CustomerMapper mCustomerMapper;


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
//
//	private Product addProduct(String Name, String Remark){
//		Product po = new Product();
//		po.setName(Name);
//		po.setRemark(Remark);
//		mProductMapper.insert(po);
//		return po;
//	}
//
////
	@Test
	public void insertTest() {
		//新增user
        User u = this.addUser("TestAdmin", "12345", "12345", "Admin");
        mUserMapper.insert(u);
        System.out.println("After insert id: "+ u.getID());

        //新增customer
		Customer cu = new Customer();
		cu.setName("customer");
		cu.setPhone("123456");
		cu.setRemark("123456");
        mCustomerMapper.insert(cu);
		System.out.println("After insert id: "+ cu.getID());

		//新增testconfiguration
        TestConfiguration tc = new TestConfiguration();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        tc.setLotCode("456789");
        //tc.setIsActive(1);
        tc.setCreatedOwner(u);
        tc.setCustomer(cu);
        mTestConfigurationMapper.insert(tc);
		List<TestConfiguration> types = this.mTestConfigurationMapper.getAll();
		for (TestConfiguration r: types) {
            System.out.println("id: "+ r.getID() + ", LotCode: " + r.getProductDevice() + ", Customer: " + r.getCustomer() + ", CreatedOwner_ID: " + r.getCreatedOwner() + ", create: " + r.getCreatedTime().toLocalDateTime());
		}
	}
//
//	@Test
//	void insertAndSelectAllTest() {
//		ICFunction type = new ICFunction();
//		type.setName("Test_name");
//		type.setRemark("Test_remark");
//		mICFunctionMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//		List<ICFunction> types = this.mICFunctionMapper.getAll();
//		for (ICFunction r: types) {
//			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", CreatedOwner_ID: " + r.getCreatedOwner_ID() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndSelectAllCountTest() {
//		ICFunction type = new ICFunction();
//		type.setName("Test_name");
//		type.setRemark("Test_remark");
//		mICFunctionMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		int count = this.mICFunctionMapper.getTotalCount();
//		System.out.println("Total --> " + count);
//	}
//
//	@Test
//	void insertAndSelectByNameTest() {
//		ICFunction type = new ICFunction();
//		type.setName("Test_name");
//		type.setRemark("Test_remark");
//		mICFunctionMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//		ICFunction r = this.mICFunctionMapper.getByName("Test_name");
//		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", CreatedOwner_ID: " + r.getCreatedOwner_ID() + ", create: " + r.getCreatedTime().toLocalDateTime());
//
//	}
//
//	@Test
//	void searchAndUpdateTest() {
//		ICFunction type = new ICFunction();
//		type.setName("Test_name");
//		type.setRemark("Test_remark");
//		mICFunctionMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		ICFunction r = this.mICFunctionMapper.getByName("Test_name");
//		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", CreatedOwner_ID: " + r.getCreatedOwner_ID() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		r.setName("TestGGYY");
//		this.mICFunctionMapper.update(r);
//		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", CreatedOwner_ID: " + r.getCreatedOwner_ID() + ", create: " + r.getCreatedTime().toLocalDateTime());
//
//	}
//
//	@Test
//	void insertAndDeleteByIDTest() {
//		ICFunction type = new ICFunction();
//		type.setName("Test_name");
//		type.setRemark("Test_remark");
//		mICFunctionMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		mICFunctionMapper.deleteByID(type.getID());
//
//		List<ICFunction> types = this.mICFunctionMapper.getAll();
//		for (ICFunction r: types) {
//			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", CreatedOwner_ID: " + r.getCreatedOwner_ID() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}
//
//	@Test
//	void insertAndDeleteByNameTest() {
//		ICFunction type = new ICFunction();
//		type.setName("Test_name");
//		type.setRemark("Test_remark");
//		mICFunctionMapper.insert(type);
//		System.out.println("After insert id: "+ type.getID());
//
//		mICFunctionMapper.deleteByName(type.getName());
//
//		List<ICFunction> types = this.mICFunctionMapper.getAll();
//		for (ICFunction r: types) {
//			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", CreatedOwner_ID: " + r.getCreatedOwner_ID() + ", create: " + r.getCreatedTime().toLocalDateTime());
//		}
//	}

}
