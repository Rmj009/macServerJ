package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.mapper.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//全部倒回去
@Transactional
public class SimdcProjectTestConfiguration_Dut_BindingDaoApplicationTests {

	@Autowired
	private UserMapper mUserMapper;

	@Autowired
	private DutDeviceMapper mDutDeviceMapper;

	@Autowired
	private TestConfiguration_DutBindingMapper mTestConfiguration_Dut_BindingMapper;

	@Autowired
	private CustomerMapper mCustomerMapper;

	@Autowired
	private RoleMapper mRoleMapper;

	@Autowired
	private TestConfigurationMapper mTestConfigurationMapper;


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
		u.setPassword("1231");
		u.setRole(role);
		return u;
	}

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
		tc.setCreatedOwner(u);
		tc.setCustomer(cu);
		mTestConfigurationMapper.insert(tc);

		DutDevice dev = new DutDevice();
		dev.setHostName("192.168.0.1");
		dev.setRemark("Test_remark");
		dev.setGroupPC("hand1");
		dev.setProductDevice("1231");
		mDutDeviceMapper.insert(dev);

		TestConfiguration_DutBinding tb = new TestConfiguration_DutBinding();
		//tb.setIsActived(1);
		tb.setDutDevice_ID(dev);
		tb.setTestConfiguration_ID(tc);
		mTestConfiguration_Dut_BindingMapper.insert(tb);
		List<TestConfiguration_DutBinding> types = this.mTestConfiguration_Dut_BindingMapper.getAll();
		for (TestConfiguration_DutBinding r: types) {
			System.out.println("id: "+ r.getID() + ", TestConfiguration_ID: " + r.getTestConfiguration_ID()+ ", DutDevice_ID: " + r.getDutDevice_ID());
//			System.out.println("id: "+ r.getID() + ", IsActived: " + r.getIsActived() + ", TestConfiguration_ID: " + r.getTestConfiguration_ID()+ ", DutDevice_ID: " + r.getDutDevice_ID());
		}
	}

//	@Test
//	void insertAndSelectAllTest() {
//		DutDevice dev = new DutDevice();
//		dev.setName("1234");
//		dev.setHostName("ase");
//		dev.setIP("192.168.0.1");
//		dev.setRemark("Test_remark");
//		mDutDeviceMapper.insert(dev);
//		System.out.println("After insert id: "+ dev.getID());
//		List<DutDevice> types = this.mDutDeviceMapper.getAll();
//		for (DutDevice r: types) {
//			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
//		}
//	}
//
//	@Test
//	void insertAndSelectAllCountTest() {
//		DutDevice dev = new DutDevice();
//		dev.setName("1234");
//		dev.setHostName("ase");
//		dev.setIP("192.168.0.1");
//		dev.setRemark("Test_remark");
//		mDutDeviceMapper.insert(dev);
//		System.out.println("After insert id: "+ dev.getID());
//
//		int count = this.mDutDeviceMapper.getTotalCount();
//		System.out.println("Total --> " + count);
//	}
//
//	@Test
//	void insertAndSelectByNameTest() {
//		DutDevice dev = new DutDevice();
//		dev.setName("1234");
//		dev.setHostName("ase");
//		dev.setIP("192.168.0.1");
//		dev.setRemark("Test_remark");
//		mDutDeviceMapper.insert(dev);
//		System.out.println("After insert id: "+ dev.getID());
//
//		DutDevice r = this.mDutDeviceMapper.getByName("1234");
//		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
//	}
//
//	@Test
//	void searchAndUpdateTest() {
//		DutDevice dev = new DutDevice();
//		dev.setName("1234");
//		dev.setHostName("ase");
//		dev.setIP("192.168.0.1");
//		dev.setRemark("Test_remark");
//		mDutDeviceMapper.insert(dev);
//		System.out.println("After insert id: "+ dev.getID());
//
//		DutDevice r = this.mDutDeviceMapper.getByName("1234");
//		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
//		r.setName("TestGGYY");
//		this.mDutDeviceMapper.update(r);
//		System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
//	}
//
//	@Test
//	void insertAndDeleteByIDTest() {
//		DutDevice dev = new DutDevice();
//		dev.setName("1234");
//		dev.setHostName("ase");
//		dev.setIP("192.168.0.1");
//		dev.setRemark("Test_remark");
//		mDutDeviceMapper.insert(dev);
//		System.out.println("After insert id: "+ dev.getID());
//
//		mDutDeviceMapper.deleteByID(dev.getID());
//
//		List<DutDevice> types = this.mDutDeviceMapper.getAll();
//		for (DutDevice r: types) {
//			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
//		}
//	}
//
//	@Test
//	void insertAndDeleteByNameTest() {
//		DutDevice dev = new DutDevice();
//		dev.setName("1234");
//		dev.setHostName("ase");
//		dev.setIP("192.168.0.1");
//		dev.setRemark("Test_remark");
//		mDutDeviceMapper.insert(dev);
//		System.out.println("After insert id: "+ dev.getID());
//
//		mDutDeviceMapper.deleteByName(dev.getName());
//
//		List<DutDevice> types = this.mDutDeviceMapper.getAll();
//		for (DutDevice r: types) {
//			System.out.println("id: "+ r.getID() + ", Name: " + r.getName() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", IP: " + r.getIP());
//		}
//	}

}
