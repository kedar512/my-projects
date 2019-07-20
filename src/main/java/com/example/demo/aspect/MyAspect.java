package com.example.demo.aspect;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.owasp.esapi.ESAPI;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
	
	private static final Pattern[] PATTERNS = new Pattern[]{
	        // Script fragments
	        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
	        // src='...'
	        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        // lonely script tags
	        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
	        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        // eval(...)
	        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        // expression(...)
	        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        // javascript:...
	        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
	        // vbscript:...
	        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
	        // onload(...)=...
	        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
	    };

	@Around("execution(* com.example.demo.controller.*.*(..))")
	public Object before(ProceedingJoinPoint pjp) throws Throwable {

		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Object[] args = pjp.getArgs();
		Method method = null;

		Class<?> clazz = methodSignature.getDeclaringType();

		try {
			method = clazz.getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(null != method && null != args) {
			Class<?>[] paramTypes = method.getParameterTypes();

			for (int i = 0; i < method.getParameterAnnotations().length; i++) {
				sanitizeRequest(args[i], paramTypes[i], args, i);
			}
		}
		
		return pjp.proceed(args);
	}
	
	private void sanitizeRequest(Object obj, Class<?> clazz, Object[] args, int index) {
		if(null == obj) {
			return;
		}
		
		if(clazz.equals(String.class)) {
			args[index] = stripXSS(obj.toString());
		}
	}
	
	private String stripXSS(String value) {
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            value = ESAPI.encoder().canonicalize(value);

            // Avoid null characters
            value = value.replaceAll("\0", "");

            // Remove all sections that match a pattern
            for (Pattern scriptPattern : PATTERNS){
                value = scriptPattern.matcher(value).replaceAll("");
            }
        }
        return value;
    }

}
