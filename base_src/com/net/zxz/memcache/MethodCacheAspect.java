package com.net.zxz.memcache;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.net.zxz.memcache.annotation.Cacheable;

public class MethodCacheAspect {
	private MemcachedManager memcachedManager;
	
	public void initialize(){
	    System.out.println("MethodCacheAspect初始化");
	}
	
	public Object executeMethod(ProceedingJoinPoint pjp) throws Throwable{
	    System.out.println("调用缓存");
		Method method = ((MethodSignature)pjp.getSignature()).getMethod();
        Method implMethod = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
        Object[] paras = pjp.getArgs();
        Cacheable cacheable = (Cacheable)implMethod.getAnnotation(com.net.zxz.memcache.annotation.Cacheable.class);
        
        //字段获取key
        String key = getKey(paras,cacheable.id());
        System.out.println(key);
        Object result = memcachedManager.get(key);
        if(result == null){
        	result = pjp.proceed();
        	memcachedManager.add(getKey(paras,cacheable.id()), result);
        }
        return result;
	}
	
	private String getKey(Object[] paras,String id){
		String key = id;
		for (Object object : paras) {
			key += object;
		}
		return key;
	}

	public void setMemcachedManager(MemcachedManager memcachedManager) {
		this.memcachedManager = memcachedManager;
	}
	
}
