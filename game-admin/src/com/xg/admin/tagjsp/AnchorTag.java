package com.xg.admin.tagjsp;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xg.admin.dto.model.SessionInfo;
import com.xg.admin.pojo.system.MenuData;
import com.xg.admin.service.menu.IMenuService;
import com.xg.admin.utils.Constants;

/**
 * 此标签用来控制jsp页面按钮是否显示
 * @author zhangyaping email:yapingzhang_beta@163.com
 *
 */
public class AnchorTag extends TagSupport {
	private static final long serialVersionUID = 3174234039143531070L;
	// 标签使用的时候，传入的值
	private String privilege;

	public int doStartTag() throws JspException {
		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		IMenuService menuService = (IMenuService) wac.getBean("menuService");
		SessionInfo sessionInfo = (SessionInfo) pageContext.getSession().getAttribute(Constants.SESSIONKEY);
		Set<MenuData> auths = menuService.getAuths(sessionInfo.getUserId(), MenuData.auth);
		// admin用户不需要验证权限
		if (sessionInfo.getLoginName().equals("admin") || checkAuthExit(auths, privilege)) {
			// 用户权限列表中包含访问所需权限则返回EVAL_BODY_INCLUDE，即输出标签体的内容
			return EVAL_BODY_INCLUDE;
		} else {
			//			// 否则跳过标签体，即不显示标签包含的html内容
			//			try {
			//				pageContext.getOut().write("alert('您没有访问此功能的权限！权限路径为[" + privilege + "]请联系管理员给你赋予相应权限。');");
			//			} catch (IOException e) {
			//				e.printStackTrace();
			//			}//标签的返回值  
			return SKIP_BODY;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	/**
	 * 判断用户权限是否存在
	 * @param auths
	 * @param url
	 * @return
	 */
	public boolean checkAuthExit(Set<MenuData> auths, String url) {
		for (MenuData t : auths) {
			if (url.equals(t.getUrl())) {
				return true;
			}
		}
		return false;
	}
}