package com.net.zxz.action;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("rawtypes")
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware, Preparable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String requestUrl;

    protected static final String NO_PRIVACY = "noprivacy"; // 没有权限的访问结果
    protected static final String NOT_EXIST = "notexist";// 访问资源不存在的结果
    protected static final String INDEX = "index";
    protected static final String HOME = "home";
    protected static final String TEACHER = "teacher";
    protected static final String STUDENT = "student";

    /**
     * 得到当前时间
     * 
     * @return
     */
    public Date getNowTime() {
        return Calendar.getInstance().getTime();
    }

    public long getNowTimeMillis() {
        return System.currentTimeMillis();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    @Override
    public void prepare() throws Exception {
        // 访问的URL
        requestUrl = getRequest().getServletPath();
        System.out.println("prepare执行");
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }
}
