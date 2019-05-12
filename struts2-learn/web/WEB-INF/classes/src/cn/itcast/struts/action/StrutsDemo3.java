package cn.itcast.struts.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by IntelliJ IDEA.
 * User: ${ysxian}
 * Date: 2019/4/29
 * TIME: 17:09
 */
public class StrutsDemo3 extends ActionSupport {

    /**
     * 添加客户信息Action
     * @return
     */
    public String add() {
        System.out.println("addCustomerAction执行了....");

        return NONE;
    }

    /**
     * 更新客户信息Action
     * @return
     */
    public String updateCustomerAction() {
        System.out.println("updateCustomerAction执行了....");

        return NONE;
    }
}
