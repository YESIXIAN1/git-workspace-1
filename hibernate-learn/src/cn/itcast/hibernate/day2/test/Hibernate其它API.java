package cn.itcast.hibernate.day2.test;

import cn.itcast.hibernate.day1.entity.Customer;
import cn.itcast.hibernate.day1.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.List;

public class Hibernate其它API {


    @Test
    /**
    *  query
    * @author      ysxian
    * @param null
    * @return
    * @exception
    * @date        2019/5/28 6:31
    */
    public void queryDemo () {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        // 1.查询所有记录
        System.out.println("查询所有记录..............");
        Query query = session.createQuery("from Customer");
        List<Customer> list = query.list();

        System.out.println(list);

        // 2.条件查询
        System.out.println("条件查询...................");
        Query query1 = session.createQuery("from Customer where cust_name = ?");
        query1.setString(0, "小叶");

        List<Customer> list1 = query1.list();

        System.out.println(list1);

        // 3. 条件查询其它形式
        System.out.println("条件查询其它形式....................");
        Query query2 = session.createQuery("from Customer where cust_name = :name and cust_phone = :phone");

        query2.setString("name", "Bill");
        query2.setString("phone", "15921024632");

        List<Customer> list2 = query2.list();

        System.out.println(list2);

        // 4. 分页查询
        System.out.println("分页查询..........................");
        Query query3 = session.createQuery("from Customer");

        query3.setFirstResult(3);
        query3.setMaxResults(5);

        List<Customer> list3 = query3.list();

        System.out.println(list3);
    }
}
