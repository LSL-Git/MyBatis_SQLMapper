package com.lsl.ssm.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.lsl.ssm.dao.user.UserMapper;
import com.lsl.ssm.pojo.User;
import com.lsl.ssm.utils.MyBatisUtils;

public class UserMapperTest {

	private Logger logger = Logger.getLogger(UserMapper.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCount() {
		System.err.println("======================testCount=========================");
		SqlSession sqlSession = null;
		int count = 0;
		try {
			sqlSession = MyBatisUtils.createSqlSession();

			// 第一种方式:调用selectOne方法执行查询操作
			// count =
			// sqlSession.selectOne("cn.smbms.dao.user.UserMapper.count");

			// 第二种方式:调用getMapper(Mapper.class)执行dao接口方法来实现对数据库的查询操作
			count = sqlSession.getMapper(UserMapper.class).count();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}

		logger.debug("==>UserDaoTest count---> " + count);
	}

	@Test
	public void testGetUserList() {
		System.err.println("======================testGetUserList=========================");
		SqlSession sqlSession = null;
		List<User> userList = new ArrayList<User>();
		try {
			sqlSession = MyBatisUtils.createSqlSession();

			// 第一种方式:调用selectList方法执行查询操作
			// userList =
			// sqlSession.selectList("cn.smbms.dao.user.UserMapper.getUserList");

			// 第二种方式:调用getMapper(Mapper.class)执行dao接口方法来实现对数据库的查询操作
			userList = sqlSession.getMapper(UserMapper.class).getUserList();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}
		for (User user : userList) {
			logger.debug("==>testGetUserList userCode: " + user.getUserCode()
					+ " and userName: " + user.getUserName() + " and BirthDay:"
					+ user.getBirthday());
		}
	}

	@Test
	public void testGetUserListByUserName() {
		System.err.println("======================testGetUserListByUserName=========================");
		SqlSession sqlSession = null;
		List<User> userList = new ArrayList<User>();
		try {
			sqlSession = MyBatisUtils.createSqlSession();

			userList = sqlSession.getMapper(UserMapper.class)
					.getUserListByUserName("赵");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}

		for (User user : userList) {
			logger.debug("==>testGetUserListByUserName userCode: "
					+ user.getUserCode() + " and userName: "
					+ user.getUserName());
		}
	}
	
	@Test
	public void testGetUserListByUser() {
		System.err.println("======================testGetUserListByUser=========================");
		SqlSession sqlSession = null;
		List<User> userList = new ArrayList<User>();
		try {
			sqlSession = MyBatisUtils.createSqlSession();
			
			User user = new User(); // 创建User对象
			user.setUserName("赵");
			user.setUserRole(3);
			
			
			userList = sqlSession.getMapper(UserMapper.class)
					.getUserListByUser(user);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}
		
		/**
		 * 若设置resultMap的自动映射级别为NONE，
		 * 那么没有进行映射匹配的属性（比如：address等）则输出为null
		 * 若不设置resultMap的自动映射级别，则不管是否进行了映射，所有的属性值均可输出
		 */
		for (User user : userList) {
			logger.debug("testGetUserList userCode: " + user.getUserCode() + 
					" and userName: " + user.getUserName() + 
					" and userRole: " + user.getUserRole() + 
					" and userRoleName: " + user.getUserRoleName() +
					" and age: " + user.getAge() +
					" and address: " + user.getAddress());
		}
	}
		
	@Test
	public void testGetUserListByMap() {
		System.err.println("======================testGetUserListByMap=========================");
		SqlSession sqlSession = null;
		List<User> userList = new ArrayList<User>();
		try {
			sqlSession = MyBatisUtils.createSqlSession();

			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("uName", "赵");
			userMap.put("uRole", "3");
			
			userList = sqlSession.getMapper(UserMapper.class)
					.getUserListByMap(userMap);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}

		for (User user : userList) {
			logger.debug("==>testGetUserListByMap userCode: "
					+ user.getUserCode() + " and userName: "
					+ user.getUserName());
		}
	}

	@Test
	public void testAddUser() {
		System.err.println("======================testAddUser=========================");
		SqlSession sqlSession = null;
		int count = 0;
		try {
			sqlSession = MyBatisUtils.createSqlSession();
			
			User user = new User();
			user.setUserCode("test003");
			user.setUserName("测试用户003");
			user.setUserPassword("1234567");
			Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1984-12-12");
			user.setBirthday(birthday);
			user.setCreationDate(new Date());
			user.setAddress("地址测试");
			user.setGender(1);
			user.setPhone("13688783697");
			user.setUserRole(3);
			user.setCreatedBy(1);
			user.setCreationDate(new Date());
			
			count = sqlSession.getMapper(UserMapper.class).addUser(user);		
			
			int i = 3/0; // 模拟异常，进行使其进行回滚
			
			sqlSession.commit(); // 必须提交，不然不会将数据保存到数据库
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			sqlSession.rollback(); // 回滚
			count = 0;
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}
		logger.debug("==>testAddUser count:" + count);
	}
	
	@Test
	public void testModify() {
		System.err.println("======================testModify=========================");
		SqlSession sqlSession = null;
		int count = 0;
		try {
			User user = new User();
			user.setId(19);
			user.setUserCode("testmodify");
			user.setUserName("测试用户修改");
			user.setUserPassword("0000001");
			Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse("1980-10-10");
			user.setBirthday(birthday);
			user.setCreationDate(new Date());
			user.setAddress("地址测试修改");
			user.setGender(2);
			user.setPhone("13600002222");
			user.setUserRole(2);
			user.setModifyBy(1);
			user.setModifyDate(new Date());
			
			sqlSession = MyBatisUtils.createSqlSession();
			count = sqlSession.getMapper(UserMapper.class).modify(user);
			
			int i = 3/0; // 模拟异常，进行使其进行回滚
			
			sqlSession.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			sqlSession.rollback();  // 回滚
			count = 0;
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}
		logger.debug("==>testModify count:" + count);
	}
	
	@Test
	public void testUpdatePwd() {
		System.err.println("======================testModify=========================");
		SqlSession sqlSession = null;
		int count = 0;
		try {
			sqlSession = MyBatisUtils.createSqlSession();
			
			count = sqlSession.getMapper(UserMapper.class).updatePwd(18, "0000003");
			
			int i = 3/0; // 模拟异常，进行使其进行回滚
			
			sqlSession.commit();			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			sqlSession.rollback();
			count = 0;
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}
		logger.debug("==>testUpdatePwd count:" + count);
	}
	
	
	@Test
	public void testDeleteUserById() {
		System.err.println("======================testDeleteUserById=========================");
		SqlSession sqlSession = null;
		int count = 0;
		try {
			sqlSession = MyBatisUtils.createSqlSession();
			
			count = sqlSession.getMapper(UserMapper.class).deleteUserById(28);
			
//			int i = 3/0; // 模拟异常，进行使其进行回滚
			
			sqlSession.commit();			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			sqlSession.rollback();
			count = 0;
		} finally {
			MyBatisUtils.closeSqlSession(sqlSession);
		}
		logger.debug("==>testDeleteUserById count:" + count);
	}
}
