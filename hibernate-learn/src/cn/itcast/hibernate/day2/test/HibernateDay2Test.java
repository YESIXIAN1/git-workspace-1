package cn.itcast.hibernate.day2.test;

import cn.itcast.hibernate.day1.entity.Customer;
import cn.itcast.hibernate.day1.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
* @Description:    Hebernate day2 学习
* @Author:         ysxian
* @CreateDate:     2019/5/26 14:29
* @UpdateUser:     yssian
* @UpdateDate:     2019/5/26 14:29
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class HibernateDay2Test {

    @Test
    /**
    * Hibernate 持久化类三种状态
    * @author      ysxian
    * @param
    * @return
    * @exception
    * @date        2019/5/26 14:30
    */
    public void demo1 () {
        Session session = HibernateUtils.openSession();

        Transaction tx = session.beginTransaction();

        // 1. 瞬时态：没有持久化标识OID,没有被session管理
        Customer customer = new Customer();

        customer.setCust_name("小叶");

        // 2. 持久态：有OID,被session管理
        Serializable id = session.save(customer);

        tx.commit();
        session.close();

        // 3. 托管态：有OID，没有被session管理
        System.out.println(customer);
    }

    @Test
    /**
    *  测试持久化类的持久态对象具有自动更新数据库能力
    * @author      ysxian
    * @param null
    * @return
    * @exception
    * @date        2019/5/26 19:47
    */
    public void demo2() {
        Session session = HibernateUtils.openSession();

        Transaction tx = session.beginTransaction();

        Customer customer = session.get(Customer.class, 7L);

        customer.setCust_name("小明");

        // 不用手动调用update就可以自动更新
//        session.update(customer);

        // 对比缓存区和快照区数据是否一致，若不一致，则更新数据库
        tx.commit();
        session.close();
    }

    @Test
    /**
    * 一级缓存测试
    * @author      ysxian
    * @param null
    * @return
    * @exception
    * @date        2019/5/27 5:51
    */
    public void demo3() {
        Session session = HibernateUtils.openSession();

        Transaction tx = session.beginTransaction();

        // 执行了一条SQL,并将数据存入session缓存
        Customer customer1 = session.get(Customer.class, 7L);

        System.out.println(customer1);

        // 没执行SQL,查的是session缓存
        // 问题：数据库数据变了怎么办-- mysql默认事务隔离级别是可重复读，在读的时候，只允许其它事务读
        Customer customer2 = session.get(Customer.class, 7L);

        System.out.println(customer2);

        System.out.println(customer1 == customer2);

        tx.commit();
        session.close();
    }
}
