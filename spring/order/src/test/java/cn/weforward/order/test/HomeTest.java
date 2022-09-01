package cn.weforward.order.test;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import cn.weforward.common.util.StringUtil;
import cn.weforward.protocol.Response;
import cn.weforward.protocol.client.ServiceInvoker;
import cn.weforward.protocol.client.ServiceInvokerFactory;
import cn.weforward.protocol.support.datatype.SimpleDtObject;

/**
 * Hello world!
 *
 */
public class HomeTest {

	protected ServiceInvoker m_Invoker;

	@Before
	public void init() throws IOException {
		Properties result = new Properties();
		ClassPathResource res = new ClassPathResource("weforward-test.properties");
		Properties prop = new Properties();
		prop.load(res.getInputStream());
		for (Entry<Object, Object> e : prop.entrySet()) {
			String key = StringUtil.toString(e.getKey());
			String value = StringUtil.toString(e.getValue());
			result.put(key, value);
		}
		String preUrl = result.getProperty("weforward.apiUrl");
		String accessId = result.getProperty("weforward.service.accessId");
		String accessKey = result.getProperty("weforward.service.accessKey");
		String serviceName = result.getProperty("weforward.name");
		m_Invoker = ServiceInvokerFactory.create(serviceName, preUrl, accessId, accessKey);
	}

	private ServiceInvoker getInvoker() {
		return m_Invoker;
	}

	@Test
	public void test() {
		SimpleDtObject params = new SimpleDtObject();
		params.put("name", "World");
		Response response = getInvoker().invoke("/home/hello", params);
		System.out.println("code:" + response.getResponseCode());
		System.out.println("msg:" + response.getResponseMsg());
		System.out.println("result" + response.getServiceResult());
	}

}
