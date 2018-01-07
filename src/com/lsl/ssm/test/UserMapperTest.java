package com.lsl.ssm.test;

import java.util.ArrayList;
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

		for (User user : userList) {
			logger.debug("==>testGetUserListByUser userCode: "
					+ user.getUserCode() + " and userName: "
					+ user.getUserName());
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

}
