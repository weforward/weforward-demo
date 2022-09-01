package cn.weforward.springbootdemo.config;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import cn.weforward.data.mybatisplus.persister.MybatisPlusPersisterFactory;

@Configuration
@MapperScan("cn.weforward.springbootdemo.mapper")
public class MybatisConfig {
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	@Bean
	public MybatisPlusPersisterFactory persisterFactory(SqlSessionTemplate template) {
		MybatisPlusPersisterFactory factory = new MybatisPlusPersisterFactory(template);
		factory.setServerId("x00ff");
		return factory;
	}
}
