package com.atguigu.mybatis;

import com.atguigu.mybatis.entity.Product;
import com.atguigu.mybatis.entity.User;
import com.atguigu.mybatis.mapper.ProductMapper;
import com.atguigu.mybatis.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {

	//自动装配：必须保证装配的对象已经初始化并保存到spring容器中
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private ProductMapper productMapper;

	@Test
	void contextLoads() {
		//根据list 集合的方式返回对象，没有条件，直接返回
		List<User> users = userMapper.selectList(null);
		users.forEach(user -> System.out.println(user));
	}
	@Test
	void TestQuerById(){
		//根据id查询
		User user = userMapper.selectById("1352507814525820930");
		System.out.println("user = " + user);
	}
	@Test
	void TestNameIsO(){
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		//查询名字包含o
		queryWrapper.like("name", "o");
		//查询大于18岁
		queryWrapper.gt("age", 18);
		//或者id小于3
		queryWrapper.or().lt("id", 3);
		//排序，先按年龄从大到小，如果一样，则选择id进行排序
		queryWrapper.orderByDesc("age","id");
		List<User> users = userMapper.selectList(queryWrapper);
//		users.forEach(user -> System.out.println(user));
		users.forEach(System.out::println);
	}

	@Test
	void TestQueryColumns(){
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("name","age");
		List<Map<String, Object>> list = userMapper.selectMaps(queryWrapper);
		list.forEach(System.out::println);
	}

	@Test
	void TestQueryPage(){
		//分页如果要使用，必须要配置分页拦截器插件，发送到mysql请求，分页查询
		Page<User> page = new Page<>(2,2);

		//参数1；page用来携带查询分页的页码和每页的size，并接收到分页数据集合
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();

		userMapper.selectPage(page, queryWrapper);
		page.getRecords().forEach(System.out::println);
		System.out.println("总数 = " + page.getTotal());
		System.out.println("当前页 = " + page.getSize());
		System.out.println("总条数 = " + page.getCurrent());
	}
	@Test
	void AddUser(){
		userMapper.insert(new User(null,"芳芳",13,"fangfang@qq.com"));
	}
	@Test
	void UpdateTest(){
//		User user = userMapper.selectById(1);
//		user.setAge(11);
//		user.setName1("小李");
//		int i = userMapper.updateById(user);
//		System.out.println("i = " + i);

		UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
		userUpdateWrapper.set("name", "小白");
		userUpdateWrapper.eq("id", "1");
		User hh = new User(null, "嘿嘿", 10, "12@qq.com");
		userMapper.update(hh, userUpdateWrapper);
	}


	@Test
	void TestConcurrent(){
		//a查询商品信息
		Product aproduct = productMapper.selectById(1);
		//B查询商品信息
		Product bproduct = productMapper.selectById(1);
		//a修改
		aproduct.setPrice(aproduct.getPrice()+50);
		productMapper.updateById(aproduct);
		//b修改
		bproduct.setPrice(bproduct.getPrice()-30);
		//查询价格
		int i = productMapper.updateById(bproduct);
		if (i==0){
			bproduct=productMapper.selectById(1);
			bproduct.setPrice(bproduct.getPrice()-30);
			productMapper.updateById(bproduct);
		}
	}

	//物理删除
	@Test
	void DelectById(){
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", "1352507814525820930");
		userMapper.delete(queryWrapper);
	}




}
