package cloud.weforward.user.config;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import cn.weforward.data.mybatisplus.persister.MybatisPlusPersisterFactory;

@Configuration
@MapperScan("cloud.weforward.user.mapper")
public class MybatisConfig {
	@Value("${weforward.serverid:x00ff}")
	protected String serverId;
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	@Bean
	public MybatisPlusPersisterFactory persisterFactory(SqlSessionTemplate template) {
		MybatisPlusPersisterFactory factory = new MybatisPlusPersisterFactory(template);
		factory.setServerId(serverId);
		return factory;
	}
}
