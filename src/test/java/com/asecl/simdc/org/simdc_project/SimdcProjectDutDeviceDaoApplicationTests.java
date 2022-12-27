package com.asecl.simdc.org.simdc_project;

import com.asecl.simdc.org.simdc_project.db.entity.DutDevice;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.entity.Role;
import com.asecl.simdc.org.simdc_project.db.mapper.DutDeviceMapper;
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
class SimdcProjectDutDeviceDaoApplicationTests {


	@Autowired
	private UserMapper mUserMapper;

	@Autowired
	private DutDeviceMapper mDutDeviceMapper;

	@Autowired
	private RoleMapper mRoleMapper;

	private User addUser(String Test, String phone, String rName){
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
		u.setPassword("1212");
		u.setRole(role);
		return u;
	}

	@Test
	void insertTest() {
		//新增user
		User u = this.addUser("TestAdmin", "12345", "Admin");
		mUserMapper.insert(u);
		System.out.println("After insert id: "+ u.getID());

		DutDevice dev = new DutDevice();
		dev.setHostName("192.168.0.1");
		dev.setRemark("Test_remark");
		dev.setGroupPC("hand1");
		dev.setProductDevice("gggg");
		dev.setCreatedOwner(u);
		mDutDeviceMapper.insert(dev);
		List<DutDevice> types = this.mDutDeviceMapper.getAll();
		for (DutDevice r: types) {
			System.out.println("id: "+ r.getID() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", GroupPC: " + r.getGroupPC() + ", CreatedOwner: " + r.getCreatedOwner());
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
	@Test
	void insertAndDeleteByIDTest() {
		DutDevice dev = new DutDevice();
		dev.setHostName("192.168.0.1");
		dev.setRemark("Test_remark");
        dev.setGroupPC("hand1");
		mDutDeviceMapper.insert(dev);
		System.out.println("After insert id: "+ dev.getID());

		mDutDeviceMapper.deleteByID(dev.getID());

		List<DutDevice> types = this.mDutDeviceMapper.getAll();
        for (DutDevice r: types) {
            System.out.println("id: "+ r.getID() + ", Remark: " + r.getRemark() + ", HostName: " + r.getHostName() + ", Hand: " + r.getGroupPC() + ", CreatedOwner: " + r.getCreatedOwner());
        }
	}



}
