package cn.itcast.hibernate.day1.test;

import cn.itcast.hibernate.day1.entity.Customer;
import cn.itcast.hibernate.day1.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class HibernateDemo_1 {

    @Test
    /**
    *  使用hibernate保存数据
    * @author      ysxian
    * @param null
    * @return
    * @exception
    * @date        2019/5/19 14:12
    */
    public void test() {
        // 1. 创建session对象
        Session session = HibernateUtils.openSession();
        // 2. 开启事务
        Transaction transaction = session.beginTransaction();
        // 3. 操作对象
        Customer customer = new Customer();

        customer.setCust_name("Bill");
        customer.setCust_phone("15921024632");

        session.save(customer);
        // 4. 提交事务
        transaction.commit();
        // 5. 关闭资源
        session.close();

    }
}
