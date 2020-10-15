package cn.weforward.helloworld.weforward;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.weforward.common.util.BackgroundExecutor;
import cn.weforward.common.util.TaskExecutor;
import cn.weforward.framework.ApiException;
import cn.weforward.framework.WeforwardMethod;
import cn.weforward.framework.WeforwardMethods;
import cn.weforward.protocol.AsyncResponse;
import cn.weforward.protocol.doc.annotation.DocAttribute;
import cn.weforward.protocol.doc.annotation.DocMethod;
import cn.weforward.protocol.doc.annotation.DocParameter;
import cn.weforward.protocol.support.datatype.FriendlyObject;
import cn.weforward.protocol.support.datatype.SimpleDtString;

/**
 * Hello world!
 *
 */
@WeforwardMethods
public class HomeMethods {
	static final Logger _Logger = LoggerFactory.getLogger(HomeMethods.class);

	TaskExecutor m_Executor;

	public HomeMethods() {
		m_Executor = new BackgroundExecutor(2, 10, 10000);
	}

	@WeforwardMethod
	@DocMethod(description = "hello调试示范方法")
	@DocParameter(@DocAttribute(name = "name", type = String.class, description = "hello调试示范方法", example = "我是xxx"))
	public String hello(FriendlyObject params) {
		String name = params.getString("name");
		return "Hello " + name + "!";
	}

	@WeforwardMethod
	@DocMethod(description = "压测")
	@DocParameter(value = { @DocAttribute(name = "text", type = String.class, description = "大段文字", example = "测试字串"),
			@DocAttribute(name = "delay", type = int.class, description = "延时返回（单位：毫秒）", example = "1000") })
	public String pressure(FriendlyObject params, AsyncResponse asyncResponse) throws ApiException {
		int delay = params.getInt("delay", 0);
		if (0 == delay) {
			return params.getString("text");
		}

		try {
			asyncResponse.setAsync();
		} catch (IOException e) {
			throw new ApiException(ApiException.CODE_INTERNAL_ERROR, e);
		}
		Runnable worker = new Runnable() {
			@Override
			public void run() {
				asyncResponse.setServiceResult(0, "", SimpleDtString.valueOf(params.getString("text")));
				try {
					asyncResponse.complete();
				} catch (IOException e) {
					_Logger.error(e.toString(), e);
				}
			}
		};
		m_Executor.execute(worker, 0, delay);
		return null;
	}
}
