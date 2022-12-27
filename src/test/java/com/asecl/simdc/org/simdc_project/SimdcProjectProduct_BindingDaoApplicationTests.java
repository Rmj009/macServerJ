//package com.asecl.simdc.org.simdc_project;
//
//import com.asecl.simdc.org.simdc_project.db.entity.*;
//import com.asecl.simdc.org.simdc_project.db.mapper.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//
//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
//@AutoConfigureMybatis
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
////@MybatisTest
////@WebMvcTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(true)
//@Transactional
//public class SimdcProjectProduct_BindingDaoApplicationTests {
//
//	@Autowired
//	private UserMapper mUserMapper;
//
//	@Autowired
//	private ProductMapper mProductMapper;
//
//	@Autowired
//	private Product_BindingMapper mProduct_BindingMapper;
//
//	@Autowired
//	private CustomerMapper mCustomerMapper;
//
//	@Autowired
//	private RoleMapper mRoleMapper;
//
//	@Autowired
//	private TestConfigurationMapper mTestConfigurationMapper;
//
//
//	private User addUser(String Test, String phone, String pwd, String rName){
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
//		return u;
//	}
//
//	@Test
//	public void insertTest() {
//		//新增user
//		User u = this.addUser("TestAdmin", "12345", "12345", "Admin");
//		mUserMapper.insert(u);
//		System.out.println("After insert id: "+ u.getID());
//
//		//新增customer
//		Customer cu = new Customer();
//		cu.setName("customer");
//		cu.setPhone("123456");
//		cu.setRemark("123456");
//		mCustomerMapper.insert(cu);
//		System.out.println("After insert id: "+ cu.getID());
//
//		//新增testconfiguration
//		TestConfiguration tc = new TestConfiguration();
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		tc.setLotCode("456789");
//		tc.setIsActive(1);
//		tc.setCreatedOwner(u);
//		tc.setCustomer(cu);
//		tc.setStartDate(ts);
//		mTestConfigurationMapper.insert(tc);
//
//		Product p = new Product();
//		p.setName("product");
//		p.setRemark("123");
//		mProductMapper.insert(p);
//
//		Product_Binding pb = new Product_Binding();
//		pb.setProduct_ID(p);
//		pb.setTestConfiguration_ID(tc);
//		mProduct_BindingMapper.insert(pb);
//		List<Product_Binding> types = this.mProduct_BindingMapper.getAll();
//		for (Product_Binding r: types) {
//			System.out.println("id: "+ r.getID() + ", Product_ID: " + r.getProduct_ID() + ", TestConfiguration_ID: " + r.getTestConfiguration_ID());
//		}
//	}
//
////	@Test
////	void insertAndSelectAllTest() {
////		DutDevice dev = new DutDevice();
////		dev.setName("1234");
////		dev.setHostName("ase");
////		dev.setIP("192.168.0.1");
////		dev.setRemark("Test_remark");
////		mDutDeviceMapper.insert(dev);
////		System.out.println("After insert id: "+ dev.getID());
////		List<DutDevice> types = this.mDutDeviceMapper.getAll();
////		for (DutDevice r: types) {
////			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
////		}
////	}
////
////	@Test
////	void insertAndSelectAllCountTest() {
////		DutDevice dev = new DutDevice();
////		dev.setName("1234");
////		dev.setHostName("ase");
////		dev.setIP("192.168.0.1");
////		dev.setRemark("Test_remark");
////		mDutDeviceMapper.insert(dev);
////		System.out.println("After insert id: "+ dev.getID());
////
////		int count = this.mDutDeviceMapper.getTotalCount();
////		System.out.println("Total --> " + count);
////	}
////
////	@Test
////	void insertAndSelectByNameTest() {
////		DutDevice dev = new DutDevice();
////		dev.setName("1234");
////		dev.setHostName("ase");
////		dev.setIP("192.168.0.1");
////		dev.setRemark("Test_remark");
////		mDutDeviceMapper.insert(dev);
////		System.out.println("After insert id: "+ dev.getID());
////
////		DutDevice r = this.mDutDeviceMapper.getByName("1234");
////		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
////	}
////
////	@Test
////	void searchAndUpdateTest() {
////		DutDevice dev = new DutDevice();
////		dev.setName("1234");
////		dev.setHostName("ase");
////		dev.setIP("192.168.0.1");
////		dev.setRemark("Test_remark");
////		mDutDeviceMapper.insert(dev);
////		System.out.println("After insert id: "+ dev.getID());
////
////		DutDevice r = this.mDutDeviceMapper.getByName("1234");
////		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
////		r.setName("TestGGYY");
////		this.mDutDeviceMapper.update(r);
////		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
////	}
////
////	@Test
////	void insertAndDeleteByIDTest() {
////		DutDevice dev = new DutDevice();
////		dev.setName("1234");
////		dev.setHostName("ase");
////		dev.setIP("192.168.0.1");
////		dev.setRemark("Test_remark");
////		mDutDeviceMapper.insert(dev);
////		System.out.println("After insert id: "+ dev.getID());
////
////		mDutDeviceMapper.deleteByID(dev.getID());
////
////		List<DutDevice> types = this.mDutDeviceMapper.getAll();
////		for (DutDevice r: types) {
////			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
////		}
////	}
////
////	@Test
////	void insertAndDeleteByNameTest() {
////		DutDevice dev = new DutDevice();
////		dev.setName("1234");
////		dev.setHostName("ase");
////		dev.setIP("192.168.0.1");
////		dev.setRemark("Test_remark");
////		mDutDeviceMapper.insert(dev);
////		System.out.println("After insert id: "+ dev.getID());
////
////		mDutDeviceMapper.deleteByName(dev.getName());
////
////		List<DutDevice> types = this.mDutDeviceMapper.getAll();
////		for (DutDevice r: types) {
////			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
////		}
////	}
//
//}
