package com.lsl.ssm.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lsl.ssm.pojo.User;

public interface UserMapper {
	/**
	 * 查询用户表记录数
	 * @return
	 */
	public int count();
	/**
	 * 查询用户列表
	 * @return
	 */
	public List<User> getUserList();	
	/**
	 * 查询用户列表(参数：字符串)
	 * @param userName
	 * @return
	 */
	public List<User> getUserListByUserName(String userName);
	
	/**
	 * 查询用户列表(参数：对象入参)
	 * @param user
	 * @return
	 */
	public List<User> getUserListByUser(User user);
	
	/**
	 * 查询用户列表(参数：Map)
	 * @param userMap
	 * @return
	 */
	public List<User> getUserListByMap(Map<String, String> userMap);
	
	/**
	 * 增加用户
	 * @param user
	 * @return
	 */
	public int addUser(User user);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public int modify(User user);
	
	/**
	 * 修改当前用户名密码
	 * @param id
	 * @param pwd
	 * @return
	 */
	public int updatePwd(@Param("id")Integer id, @Param("userPassword")String pwd);
}
