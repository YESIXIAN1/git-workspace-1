package cn.itcast.hibernate.day2.test;

import cn.itcast.hibernate.day1.entity.Customer;
import cn.itcast.hibernate.day1.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

public class mysql事务 {

    @Test
    public void demo() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        Customer customer = session.get(Customer.class, 7L);

        customer.setCust_name("小华");

        // 不用手动调用update就可以自动更新
//        session.update(customer);

        // 对比缓存区和快照区数据是否一致，若不一致，则更新数据库
        tx.commit();
        session.close();
    }
}
