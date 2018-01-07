# MyBatis_SQLMapper

**SSM 框架学习**

二、MyBatis SQL 映射文件的配置   

*条件模糊查询表信息*   
1、单个参数
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

2、对象
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

3、Map
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

4、resultMap使用
```
	// UserMapper.xml
	<!-- 当数据库中的字段信息与对象的属性不一致时需要通过resultMap来映射,即将不同表合并成一张 -->
	<resultMap type="User" id="userList">
		<result property="id" column="id" />
		<result property="userCode" column="userCode" />
		<result property="userName" column="userName" />
		<result property="phone" column="phone" />
		<result property="birthday" column="birthday" />
		<result property="gender" column="gender" />
		<result property="userRole" column="userRole" />
		<result property="userRoleName" column="roleName" />
	</resultMap>

	<select id="getUserListByUser" resultMap="userList" parameterType="User">
		select u.*,r.roleName from smbms_user u,smbms_role r
		where u.userName like CONCAT ('%',#{userName},'%')
		and u.userRole = #{userRole} and u.userRole = r.id
	</select>

```

@Author 瞌睡虫   
@mybatis-3.2.2   
@Database: mysql 5.7.15   
@Tool: MyEclipse
