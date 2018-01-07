# MyBatis_SQLMapper

**SSM 框架学习**

二、MyBatis SQL 映射文件的配置

*条件查询表信息*   
一、单个参数
```
	// UserMapper.java
	public interface UserMapper {
		public List<User> getUserListByUserName(String userName);
	}

	// UserMapper.xml
	<!--根据用户名称查询用户列表(模糊查询) concat 连接字符串 resultType 返回类型， paramenterType传参类型-->

	<select id="getUserListByUserName" resultType="User"
		parameterType="String">
		select * from smbms_user where userName like CONCAT
		('%',#{userName},'%')
	</select>
```

二、对象
```
	// UserMapper.java
	public interface UserMapper {
		public List<User> getUserListByUser(User user);
	}

	// UserMapper.xml
	<!-- 查询用户列表(参数：对象入参) -->
	<select id="getUserListByUser" resultType="User" parameterType="User">
		select * from smbms_user where userName like CONCAT
		('%',#{userName},'%') and userRole = #{userRole}
	</select>
```

三、Map
```
	// UserMapper.java
	public interface UserMapper {
		public List<User> getUserListByMap(Map<String, String> userMap);
	}

	// UserMapper.xml
	<!-- 查询用户列表(参数：Map) -->
	<select id="getUserListByMap" resultType="User" parameterType="Map">
		select * from smbms_user where userName like CONCAT
		('%',#{uName},'%') and userRole = #{uRole}
	</select>
```

@Author 瞌睡虫   
@mybatis-3.2.2   
@Database: mysql 5.7.15   
@Tool: MyEclipse
