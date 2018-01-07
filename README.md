# MyBatis_SQLMapper

**SSM 框架学习**

SQL 映射文件的配置

*条件查询表信息*   
一、单个参数
```
	// UserMapper.java
	public interface UserMapper {
		public List<User> getUserListByUserName(String userName);
	}

	// UserMapper.xml
	<!--
		根据用户名称查询用户列表(模糊查询) concat 连接字符串 resultType 返回类型， paramenterType传参类型
	-->
	<select id="getUserListByUserName" resultType="User"
		parameterType="String">
		select * from smbms_user where userName like CONCAT
		('%',#{userName},'%')
	</select>
```



@Author 瞌睡虫   
@mybatis-3.2.2   
@Database: mysql 5.7.15   
@Tool: MyEclipse
