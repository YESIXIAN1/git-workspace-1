package cn.itcast.struts.action;

import com.opensymphony.xwork2.Action;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 2019/4/29
 * TIME: 17:02
 */
public class StrutsDemo2 implements Action {
    @Override
    public String execute() throws Exception {
        System.out.println("StrutsDemo2中的execute方法执行了...");

        return null;
    }
}
