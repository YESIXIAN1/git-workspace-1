package cn.itcast.hibernate.day4.test;

import cn.itcast.hibernate.day1.entity.Customer;
import cn.itcast.hibernate.day1.entity.LinkMan;
import cn.itcast.hibernate.day1.utils.HibernateUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:    hibernate day4学习
 * @Author:         yesixian
 * @CreateDate:     2019/6/6 16:20
 * @UpdateUser:     
 * @UpdateDate:     2019/6/6 16:20
 * @UpdateRemark:   修改内容
 */
public class Demo_day4_1 {
    // 一 hibernate 的检索方式----------------------

    @Test
    /**
     * 对象图导航检索
     * 根据已经加载的对象，导航到他的关联对象。利用类之间的关联关系检索对象。
     * 映射文件中配置了多对一的关联关系
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo1() {
        // 根据联系人查询客户
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();

        LinkMan linkMan = session.get(LinkMan.class, 3L);
        Customer customer = linkMan.getCustomer();

        System.out.println(customer);

        tx.commit();
    }

    @Test
    /**
     * OID 检索方式
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo2() {
        // 根据联系人查询客户
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();

        LinkMan linkMan = session.get(LinkMan.class, 3L);
        LinkMan linkManLoad = session.load(LinkMan.class, 10L);

        System.out.println(linkMan);
        System.out.println(linkManLoad);

        tx.commit();
    }

    @Test
    /**
     * HQL 检索方式
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo3() {
        // 根据联系人查询客户
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();

        // 1 基本查询
        /*Query query = session.createQuery("from Customer");
        List<Customer> list = query.list();*/

        // 2. 起别名 查询
//        Query query = session.createQuery("from Customer c");
        Query query = session.createQuery("select c from Customer c");
        List<Customer> list = query.list();

        for (Customer cus: list
             ) {
            System.out.println(cus);
        }

        tx.commit();
    }

    @Test
    /**
     * 排序 检索方式
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo4() {
        // 根据联系人查询客户
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();


        Query query = session.createQuery("from Customer order by cust_id desc");
        List<Customer> list = query.list();

        for (Customer cus: list
             ) {
            System.out.println(cus);
        }

        tx.commit();
    }
    @Test
    /**
     * 条件查询 检索方式
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo5() {
        // 根据联系人查询客户
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();

        // 1. 按位置绑定参数
        /*Query query = session.createQuery("from Customer where cust_name = ?");
//        query.setParameter(0, "小叶");
        query.setString(0, "小叶");*/

        // 2. 按名称绑定参数
        Query query1 = session.createQuery("from Customer where cust_name = :name");
        query1.setParameter("name", "小刘");

        List<Customer> list = query1.list();

        for (Customer cus: list
             ) {
            System.out.println(cus);
        }

        Customer customer = (Customer) query1.uniqueResult();

        tx.commit();
    }

    @Test
    /**
     * 分页查询 检索方式
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo6() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();

        Query query1 = session.createQuery("from Customer order by cust_id asc");
        query1.setFirstResult(5);
        query1.setMaxResults(5);

        List<Customer> list = query1.list();

        for (Customer cus: list
             ) {
            System.out.println(cus);
        }

        tx.commit();
    }

    @Test
    /**
     * 统计查询 检索方式
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo7() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();

        Query query1 = session.createQuery("select count(1) from Customer");

        Long c = (Long) query1.uniqueResult();

        System.out.println("员工人数：" + c);
        tx.commit();
    }

    @Test
    /**
     * 投影检索 检索方式
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo8() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();

        // 2. 投影查询一列
        /*Query query1 = session.createQuery("select cust_name from Customer");
        List<String> nameList = query1.list();
        for (String name : nameList) {
            System.out.println(name);
        }*/

        // 3. 投影查询多列
        /*Query query = session.createQuery("select cust_id,cust_name from Customer");
        List<Object[]> list = query.list();
        for (Object[] objects :list) {
            System.out.println(Arrays.toString(objects));
        }*/

        // 4. 投影的构造方式查询
        Query query = session.createQuery("select new Customer(cust_id, cust_name) from Customer");
        List<Customer> list = query.list();

        for (Customer customer :list) {
            System.out.println(customer);
        }


        tx.commit();
    }

    @Test
    /**
     * 1.2.5 query by criteria(QBC检索)
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo9() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        // 1. 创建criteria
        Criteria criteria = session.createCriteria(Customer.class);
        // 2. 设定查询条件
        Criterion criterion = Restrictions.eq("cust_id", 7L);
        // 3.添加查询条件
        criteria.add(criterion);
         // 4. 执行
        List<Customer> customerList = criteria.list();

        for (Customer customer :customerList) {
            System.out.println(customer);
        }

        tx.commit();
    }

    @Test
    /**
     * 1.2.5.2 query by criteria(QBC检索) 条件检索
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo10() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        // 1. 创建criteria
        Criteria criteria = session.createCriteria(Customer.class);
        // 2. 设定查询条件
        Criterion criterion = Restrictions.like("cust_name", "%小%");
        Criterion criterion1 = Restrictions.gt("cust_id", 2L);
        // 3.添加查询条件
        criteria.add(criterion);
        criteria.add(criterion1);
         // 4. 执行
        List<Customer> customerList = criteria.list();

        for (Customer customer :customerList) {
            System.out.println(customer);
        }

        tx.commit();
    }

    @Test
    /**
     * 1.2.5.2 query by criteria(QBC检索) 分页检索和排序检索
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo11() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        // 1. 创建criteria
        Criteria criteria = session.createCriteria(Customer.class);
        // 2. 设定查询条件
        // 3.添加查询条件
            // 排序
        criteria.addOrder(Order.desc("cust_id"));
            // 分页
        criteria.setFirstResult(5);
        criteria.setMaxResults(5);
         // 4. 执行
        List<Customer> customerList = criteria.list();

        for (Customer customer :customerList) {
            System.out.println(customer);
        }

        tx.commit();
    }

    @Test
    /**
     * 1.2.5.2 query by criteria(QBC检索) 统计查询
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo12() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        // 1. 创建criteria
        Criteria criteria = session.createCriteria(Customer.class);
        // 2. 设定查询条件
        // 3.添加查询条件
        criteria.setProjection(Projections.rowCount());
         // 4. 执行
        Long count = (Long) criteria.uniqueResult();

        System.out.println(count);

        tx.commit();
    }

    @Test
    /**
     * 1.2.5.6 query by criteria(QBC检索) 离线条件查询:DetachedCriteria (SSH整合经常使用)
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo13() {
        // 获得一个离线条件查询对象
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
        detachedCriteria.add(Restrictions.eq("cust_name", "小叶"));

        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        // 离线条件查询对象与session对象绑定
        List<Customer> list = detachedCriteria.getExecutableCriteria(session).list();

        for (Customer customer :list) {
            System.out.println(customer);
        }


        tx.commit();
    }

    @Test
    /**
     * 1.2.6 query by criteria(QBC检索) 本地SQL查询
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo14() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        SQLQuery sqlQuery = session.createSQLQuery("select cust_id,cust_name from cst_customer");

        List<Object[]> list = sqlQuery.list();

        for (Object[] objects :list) {
            System.out.println(Arrays.toString(objects));
        }

        tx.commit();
    }

    @Test
    /**
     * 1.2.7 hibernate 多表查询 内连接
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo15() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        Query query = session.createQuery("from Customer c inner join c.linkManset");

        List<Object[]> list = query.list();

        for (Object[] objects :list) {
            System.out.println(Arrays.toString(objects));
        }


        tx.commit();
    }

    @Test
    /**
     * 1.2.7 hibernate 多表查询 迫切内连接
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo16() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
//        List<Customer> list = session.createQuery("from Customer c inner join fetch c.linkManset").list();
        // distinct
        List<Customer> list = session.createQuery("select distinct c from Customer c inner join fetch c.linkManset").list();

        for (Customer customer :list) {
            System.out.println(customer);
        }

        tx.commit();
    }

    @Test
    /**
     * 1.2.7 hibernate 延迟加载策略
     * 查询12号客户的同时查询其联系人
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo17() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();

        // 1. 默认设置
        /*System.out.println("查询客户........");
        Customer cu = session.get(Customer.class, 12L);
        System.out.println("12号客户联系人数：" );
        System.out.println(cu.getLinkManset().size());*/

        // 2 <set>上配置 fetch = "select" lazy="true"
        /*System.out.println("查询客户........");
        Customer cu = session.get(Customer.class, 12L);
        System.out.println("12号客户联系人数：" );
        System.out.println(cu.getLinkManset().size());*/

        // 3 <set>上配置 fetch = "select" lazy="false"
        /*System.out.println("查询客户........");
        Customer cu = session.get(Customer.class, 12L);
        System.out.println("12号客户联系人数：" );
        System.out.println(cu.getLinkManset().size());*/

        // 4 <set>上配置 fetch = "select" lazy="extra"
        /*System.out.println("查询客户........");
        Customer cu = session.get(Customer.class, 12L);
        System.out.println("12号客户联系人数：" );
        System.out.println(cu.getLinkManset().size());*/

        // 4 <set>上配置 fetch = "join" lazy 失效
        /*System.out.println("查询客户........");
        Customer cu = session.get(Customer.class, 12L);
        System.out.println("12号客户联系人数：" );
        System.out.println(cu.getLinkManset().size());*/

        // 4 <set>上配置 fetch = "subselect" lazy= "true"
        System.out.println("查询客户........");
        List<Customer> customerList = session.createQuery("from Customer").list();
        System.out.println("12号客户联系人数：" );
        for (Customer customer :customerList) {
            System.out.println(customer.getLinkManset().size());
        }

        tx.commit();
    }

    @Test
    /**
     * 1.2.7 hibernate 延迟加载策略
     * 查询3号联系人的同时查询其客户
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo18() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        // 默认（懒加载，查询联系人后，使用到客户信息时再查询客户信息）
        /*System.out.println("查询联系人......................");
        LinkMan linkMan = session.get(LinkMan.class, 3L);
        System.out.println("查询客户.............................");
        System.out.println(linkMan.getCustomer().getCust_name());*/

        // <many-to-one fetch="select" lazy="proxy"/>
        // lazy="proxy" 是否延迟加载取决于一方的设置
        /*System.out.println("查询联系人......................");
        LinkMan linkMan = session.get(LinkMan.class, 3L);
        System.out.println("查询客户.............................");
        System.out.println(linkMan.getCustomer().getCust_name());*/

        // <many-to-one fetch="select" lazy="false"/>
        // lazy="false"
        /*System.out.println("查询联系人......................");
        LinkMan linkMan = session.get(LinkMan.class, 3L);
        System.out.println("查询客户.............................");
        System.out.println(linkMan.getCustomer().getCust_name());*/

        // <many-to-one fetch="join"/>
        // lazy="失效"
        System.out.println("查询联系人......................");
        // 发送一条迫切左外连接查询对象
        LinkMan linkMan = session.get(LinkMan.class, 3L);
        System.out.println("查询客户.............................");
        System.out.println(linkMan.getCustomer().getCust_name());

        tx.commit();
    }

    @Test
    /**
     * 1.2.7 hibernate 批量抓取
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/6/6 16:22
     */
    public void demo19() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        tx.begin();
        // 1. 查询客户时批量抓取联系人
       /* List<Customer> customerList = session.createQuery("from Customer").list();
        for (Customer customer :customerList) {
            System.out.println("客户：" + customer.getCust_name());

            for (LinkMan linkMan :customer.getLinkManset()) {
                System.out.println("客户对应的联系人：" + linkMan.getLkm_name());
            }

        }*/

        // 2. 查询联系人时批量抓取客户
        // <class name="Customer" table="cst_customer" batch-size="12">
        List<LinkMan> linkManList = session.createQuery("from LinkMan").list();
        for (LinkMan linkMan :linkManList) {
            System.out.println("联系人：" + linkMan.getLkm_name());

            if (null != linkMan.getCustomer()) {
                System.out.println("联系人对应的客户：" + linkMan.getCustomer().getCust_name());
            }

        }

        tx.commit();
    }
}
