package com.game.admin.controllers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import com.game.admin.controllers.interceptor.LoginRequired;
import com.game.admin.dto.model.SessionInfo;
import com.game.admin.utils.Constants;

public class LoginRequredInterceptor extends ControllerInterceptorAdapter {
	public LoginRequredInterceptor() {
		setPriority(900);//设置一个数字表示拦截优先级，当有多个拦截器时，要精准控制，数字小的内层，大的在外层，在最外层的before方法最先执行，大家都执行完后它的after才最后执行
	}

	//    // 覆盖这个方法返回一个注解类，使得只有注解了该annotation的方法才会被起作用(注解在控制器类或方法上均有效)
	//    // 还有一个相反功能的方法：getDenyAnnotationClass，表示注解了某个annotatioin后，拦截器不要拦截他
	//    @Override
	//    protected Class<? extends Annotation> getRequiredAnnotationClass() {
	//        return LoginRequired.class;// 这是一个注解，只有标过的controller才会接受这个拦截器的洗礼。
	//    }

	@Override
	protected Class<? extends Annotation> getDenyAnnotationClass() {
		return LoginRequired.class;
	}

	@Override
	protected Object before(Invocation inv) throws Exception {//在controller执行前执行
		SessionInfo session = (SessionInfo) inv.getRequest().getSession().getAttribute(Constants.SESSIONKEY);
		// 如果当前没有登录就返回"r:/lib/login"表示重定向到http://host:port/lib/login页面
		if (session == null) {
			// 没有返回true或null，表示要中断整个处理流程，即不再继续调用其他拦截器以及最终的控制器
			//            inv.getResponse().sendRedirect(inv.getRequest().getContextPath()+"/index.jsp");
			//            return "";
			return "r:" + inv.getRequest().getContextPath() + "/index.jsp";
		}
		// 返回true或null，表示继续整个流程
		return null;
	}

	@Override
	protected Object after(Invocation inv, Object instruction) throws Exception {//在controller执行中（后）执行，如果一个返回抛出了异常，则不会进来
		return super.after(inv, instruction);
	}

	@Override
	public void afterCompletion(final Invocation inv, Throwable ex) throws Exception {//在controller执行后执行，不论是否异常，都会进来
		// TODO ....
	}

	@Override
	protected boolean isForAction(Method actionMethod, Class<?> controllerClazz) {//定义满足某条件的才会被拦截
		return true;
	}
}
