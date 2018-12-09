package com.taoyuan.framework.aaa.config;

import com.taoyuan.framework.common.constant.ResultCode;
import com.taoyuan.framework.common.http.TyResponse;
import net.sf.json.JSONObject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RestAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");

        TyResponse tyResponse = new TyResponse();
        tyResponse.setCode(ResultCode.USER_NOT_LOGGED_IN.getCode().toString());
        tyResponse.setMsg(ResultCode.USER_NOT_LOGGED_IN.getMsg());
        JSONObject jsonObject = JSONObject.fromObject(tyResponse);
        httpServletResponse.getWriter().write(jsonObject.toString());
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();

        return false;
    }
}
