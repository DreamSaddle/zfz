package com.zfz.common.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.zfz.common.util.JacksonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

public class SingleValueParameterResolver implements HandlerMethodArgumentResolver {

	private static final String JSON_REQUEST_BODY = "ZFZ_SINGLE_VALUE";

	@Override
	public boolean supportsParameter(final MethodParameter methodParameter) {
		//校验当前方法参数中是否有 SingleValue 注解标注的参数
		return methodParameter.hasParameterAnnotation(SingleValue.class);
	}

	@Override
	public Object resolveArgument(final MethodParameter methodParameter, final ModelAndViewContainer modelAndViewContainer, final NativeWebRequest nativeWebRequest, final WebDataBinderFactory webDataBinderFactory) throws Exception {
		JsonNode jsonNode = getRequestBody(nativeWebRequest);

		Object val = null;
		if (jsonNode != null) {
			//获取方法中 SingleValue 注解的 value 值
			String value = methodParameter.getParameterAnnotation(SingleValue.class).value();
			//如果没有给 SingleValue 设置 value, 那么 value 默认为参数名
			if (StringUtils.isBlank(value)) {
				value = methodParameter.getParameter().getName();
			}

			val = JacksonUtils.toObject(jsonNode.get(value), methodParameter.getParameterType());
		}
		return val;
	}

	private JsonNode getRequestBody(NativeWebRequest nativeWebRequest) {
		HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
		JsonNode jsonNode = (JsonNode) request.getAttribute(JSON_REQUEST_BODY);
		if (jsonNode == null) {
			try {
				//获取请求体 json 内容
				String jsonStr = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
				jsonNode = JacksonUtils.toJSONNode(jsonStr);
				request.setAttribute(JSON_REQUEST_BODY, jsonNode);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return jsonNode;
	}
}
