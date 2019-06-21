package cn.itcast.hibernate.day2.test;

import cn.itcast.hibernate.day1.entity.Customer;
import cn.itcast.hibernate.day1.utils.HibernateUtils;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import java.util.Arrays;
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

    @Test
    /**
     * criteria
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/29 9:37
     */
    public void demoOfCriteria () {
        // 1. 查询所有记录
        System.out.println("查询所有记录....................");
        Session session = HibernateUtils.openSession();

        Criteria criteria = session.createCriteria(Customer.class);

        List<Customer> list = criteria.list();

        System.out.println(list);

        // 2. 条件查询
        System.out.println("条件查询_1......................");

        Criteria criteria1 = session.createCriteria(Customer.class);

        criteria1.add(Restrictions.eq("cust_name", "小叶"));

        List<Customer> list1 = criteria1.list();

        System.out.println(list1);

        // 3. 条件查询2
        System.out.println("条件查询2....................");

        Criteria criteria2 = session.createCriteria(Customer.class);

        criteria2.add(Restrictions.eq("cust_name", "小叶"));
        criteria2.add(Restrictions.eq("cust_phone", "159"));

        List<Customer> list2 = criteria2.list();

        System.out.println(list2);

        // 4. 分页查询
        System.out.println("分页查询.........................");

        Criteria criteria3 = session.createCriteria(Customer.class);

        criteria3.setFirstResult(1);
        criteria3.setMaxResults(2);

        List<Customer> list3 = criteria3.list();

        System.out.println(list3);
    }

    @Test
    /**
     * SqlQuery
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/29 10:41
     */
    public void demoOfSqlQuery () {
        // 1. 基本查询
        System.out.println("基本查询................................");
        Session session = HibernateUtils.openSession();

        SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM CST_CUSTOMER");

        List<Object []> list = sqlQuery.list();

        for (Object [] objects: list
             ) {
            System.out.println(Arrays.toString(objects));
        }
        
        // 2. 分装到对象
        System.out.println("分装到对象.....................");

        SQLQuery sqlQuery1 = session.createSQLQuery("select * from cst_customer");
        
        sqlQuery1.addEntity(Customer.class);

        List<Customer> list1 = sqlQuery1.list();

        for (Customer customer: list1
             ) {
            System.out.println(customer);
        }

    }
    
    
}
