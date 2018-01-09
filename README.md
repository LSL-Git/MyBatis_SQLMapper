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

2、（传入参数：对象）
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

3、（传入参数：Map）
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

5、向数据库添加信息
```
	// UserMapper.java
	public interface UserMapper {
		public int addUser(User user);
	}

	// UserMapper.xml
	<!-- 增加新用户 -->
	<insert id="addUser" parameterType="User">
		insert into smbms_user
		(userCode,userName,userPassword,gender,birthday,phone,
		address,userRole,createdBy,creationDate)
		values (#{userCode}, #{userName}, #{userPassword},#{gender},#{birthday},
		#{phone},#{address},#{userRole},#{createdBy},#{creationDate})
	</insert>
```

6、更新数据库信息1
```
	// UserMapper.java
	public interface UserMapper {
		public int modify(User user);
	}

	// UserMapper.xml
	<!-- 修改用户信息 -->
	<update id="modify" parameterType="User">
		update smbms_user set
		userCode=#{userCode},userName=#{userName},userPassword=#{userPassword},
		gender=#{gender},birthday=#{birthday},phone=#{phone},address=#{address},
		userRole=#{userRole},modifyBy=#{modifyBy},modifyDate=#{modifyDate}
		where id = #{id}
	</update>	
```

7、更新数据库信息2
```
	// UserMapper.java
	public interface UserMapper {
		public int updatePwd(@Param("id")Integer id, @Param("userPassword")String pwd);
	}

	// UserMapper.xml
	<!-- 修改当前用户密码 -->
	<update id="updatePwd">
		update smbms_user set userPassword=#{userPassword} where id = #{id}
	</update>
```

8、删除数据
```
	// UserMapper.java
	public interface UserMapper {
		public int deleteUserById(@Param("id")Integer delId);
	}

	// UserMapper.xml
	<!-- 根据用户id删除用户信息 -->
	<delete id="deleteUserById">
		delete from smbms_user where id = #{id} 
	</delete>
```

9、resultMap > association 使用
```
	// UserMapper.java
	public interface UserMapper {
		public List<User> getUserListByRoleId(@Param("userRole")Integer roleId);
	}


	// User.java
	public class User {
		// association
		private Role role; // 用户角色
		
		public Role getRole() {
			return role;
		}
		
		public void setRole(Role role) {
			this.role = role;
		}
		// association
	}

	// Role.java
	public class Role {
		private Integer id; // id
		private String roleCode; // 角色编码
		private String roleName; // 角色名称
		...
		//get{} set{}
	}

	// UserMapper.xml
	<!-- 根据用户id获取用户列表  association start -->
	<resultMap type="User" id="userRoleList">
		<id property="id" column="id" />
		<result property="userCode" column="userCode" />
		<result property="userName" column="userName" />
		<result property="userRole" column="userRole" />
		<!-- javaType=Role 对应JavaBean名 | column 对应数据库查询结果集的字段 | property 对应JavaBean的属性-->
		<association property="role" javaType="Role">
			<id property="id" column="r_id" />
			<result property="roleCode" column="roleCode" />
			<result property="roleName" column="roleName" />
		</association>
	</resultMap>

	<select id="getUserListByRoleId" parameterType="Integer"
		resultMap="userRoleList">
		select u.*,r.id as r_id,r.roleCode, r.roleName 
		from smbms_user u,smbms_role r
		where u.userRole=#{userRole} 
		and u.userRole = r.id
	</select>
	<!-- 根据用户id获取用户列表  association end -->
```

10、resultMap > association使用2 (在第9步的基础上修改)
```
	// UserMapper.java
	public interface UserMapper {
		public List<User> getUserListByRoleId2(@Param("userRole")Integer roleId);
	}


	// UserMapper.xml
	<!-- 根据用户id获取用户列表2  association start -->
	<resultMap type="User" id="userRoleList2">
		<id property="id" column="id" />
		<result property="userCode" column="userCode" />
		<result property="userName" column="userName" />
		<result property="userRole" column="userRole" />
		<association property="role" javaType="Role" resultMap="roleResult" />
	</resultMap>

	<resultMap type="Role" id="roleResult">
		<id property="id" column="r_id" />
		<result property="roleCode" column="roleCode" />
		<result property="roleName" column="roleName" />
	</resultMap>

	<select id="getUserListByRoleId2" parameterType="Integer"
		resultMap="userRoleList2">
		select u.*,r.id as r_id,r.roleCode, r.roleName
		from smbms_user u,smbms_role r
		where u.userRole=#{userRole}
		and u.userRole = r.id
	</select>
	<!-- 根据用户id获取用户列表2  association end -->
```

11、resultMap > collection使用1 
```
	// UserMapper.java
	public interface UserMapper {
		public List<User> getAddressListByUserId(@Param("id")Integer userId);
	}

	// User.java
	public class User {
		// collection
		private List<Address> addressList;
		
		public List<Address> getAddressList() {
			return addressList;
		}
		
		public void setAddressList(List<Address> addressList) {
			this.addressList = addressList;
		}
		// collection
	}

	// Role.java
	public class Address {
		...
		//get{} set{}
	}

	// UserMapper.xml
	<!-- 获取指定用户的地址列表（user表-address表： 1对多） collection start -->
	<resultMap type="User" id="userAddressList">
		<id property="id" column="id" />
		<result property="userCode" column="userCode" />
		<result property="userName" column="userName" />
		<!-- ofType 对应JavaBean -->
		<collection property="addressList" ofType="Address">
			<id property="id" column="a_id" />
			<result property="postCode" column="postCode" />
			<result property="tel" column="tel" />
			<result property="contact" column="contact" />
			<result property="addressDesc" column="addressDesc" />
		</collection>
	</resultMap>

	<select id="getAddressListByUserId" parameterType="Integer"
		resultMap="userAddressList">
		select u.*, a.id as a_id, a.contact, a.addressDesc, a.tel, a.postCode
		from smbms_user u, smbms_address a
		where u.id=a.userId and u.id=#{id}
	</select>
	<!-- 获取指定用户的地址列表（user表-address表： 1对多） collection end -->
```

12、resultMap > collection使用2 (在第11步的基础上修改) 
```
	// UserMapper.java
	public interface UserMapper {
		public List<User> getAddressListByUserId2(@Param("id")Integer userId);
	}

	// UserMapper.xml
	<!-- 获取指定用户的地址列表2（user表-address表： 1对多） collection start -->
	<resultMap type="User" id="userAddressList2">
		<id property="id" column="id" />
		<result property="userCode" column="userCode" />
		<result property="userName" column="userName" />
		<!-- ofType 对应JavaBean -->
		<collection property="addressList" ofType="Address" resultMap="addressResult"/>
	</resultMap>
	
	<resultMap type="Address" id="addressResult">
		<id property="id" column="a_id" />
		<result property="postCode" column="postCode" />
		<result property="tel" column="tel" />
		<result property="contact" column="contact" />
		<result property="addressDesc" column="addressDesc" />
	</resultMap>

	<select id="getAddressListByUserId2" parameterType="Integer"
		resultMap="userAddressList2">
		select u.*, a.id as a_id, a.contact, a.addressDesc, a.tel, a.postCode
		from smbms_user u, smbms_address a
		where u.id=a.userId and u.id=#{id}
	</select>
	<!-- 获取指定用户的地址列表2（user表-address表： 1对多） collection end -->
```


@Author 瞌睡虫   
@mybatis-3.2.2   
@Database: mysql 5.7.15   
@Tool: MyEclipse
