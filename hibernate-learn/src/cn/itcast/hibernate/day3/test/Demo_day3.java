package cn.itcast.hibernate.day3.test;

import cn.itcast.hibernate.day1.entity.Customer;
import cn.itcast.hibernate.day1.entity.LinkMan;
import cn.itcast.hibernate.day1.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @Description:    Hibernate day3
 * @Author:         yesixian
 * @CreateDate:     2019/5/29 14:24
 * @UpdateUser:
 * @UpdateDate:     2019/5/29 14:24
 * @UpdateRemark:   修改内容
 */
public class Demo_day3 {

    @Test
    /**
     * 保存一个客户和两个联系人
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/29 14:25
     */
    public void demoSave () {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 1. 创建一个客户
        Customer customer = new Customer();

        customer.setCust_name("姜总");
        customer.setCust_phone("159000");

        // 2. 创建两个联系人
        LinkMan linkMan1 = new LinkMan();
        LinkMan linkMan2 = new LinkMan();

        linkMan1.setLkm_name("李秘书");
        linkMan2.setLkm_name("王助理");

        // 3. 建立关系
        customer.getLinkManset().add(linkMan1);
        customer.getLinkManset().add(linkMan2);

        linkMan1.setCustomer(customer);
        linkMan2.setCustomer(customer);

        // 4. save
        session.save(customer);
        session.save(linkMan1);
        session.save(linkMan2);

        tx.commit();
    }

    @Test
    /**
     * 保存操作只保存一方？
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/29 14:49
     */
    public void demoSave1 () {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 1. 创建一个客户
        Customer customer = new Customer();

        customer.setCust_name("刘总");
        customer.setCust_phone("159001");

        // 2. 创建两个联系人
        LinkMan linkMan1 = new LinkMan();
        LinkMan linkMan2 = new LinkMan();

        linkMan1.setLkm_name("李秘书1");
        linkMan2.setLkm_name("王助理2");

        // 3. 建立关系
        customer.getLinkManset().add(linkMan1);
        customer.getLinkManset().add(linkMan2);

        linkMan1.setCustomer(customer);
        linkMan2.setCustomer(customer);

        // 4. save 只保存一方保错。瞬时对象异常，一个持久对象关联了瞬时对象
        session.save(customer);
        /*session.save(linkMan1);
        session.save(linkMan2);*/

        tx.commit();
    }

    @Test
    /**
     * 级联保存：只保存一方
     * 级联是有方向的，保存客户的同时保存联系人
     * 在 Customer.hbm.xml 中的 <set> 标签中配置 cascade="save-update"
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/29 14:49
     */
    public void demoSave2 () {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 1. 创建一个客户
        Customer customer = new Customer();

        customer.setCust_name("王总");
        customer.setCust_phone("159002");

        // 2. 创建两个联系人
        LinkMan linkMan1 = new LinkMan();
        LinkMan linkMan2 = new LinkMan();

        linkMan1.setLkm_name("李秘书3");
        linkMan2.setLkm_name("王助理3");

        // 3. 建立关系
        customer.getLinkManset().add(linkMan1);
        customer.getLinkManset().add(linkMan2);

        linkMan1.setCustomer(customer);
        linkMan2.setCustomer(customer);

        // 4. save
        session.save(customer);

        tx.commit();
    }

    @Test
    /**
     * 级联保存：只保存一方
     * 级联是有方向的，保存联系人的同时保存联系人
     * 在 LinkMan.hbm.xml 中的 <many-to-one> 标签中配置 cascade="save-update"
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/29 14:49
     */
    public void demoSave3 () {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 1. 创建一个客户
        Customer customer = new Customer();

        customer.setCust_name("吴总");
        customer.setCust_phone("159003");

        // 2. 创建两个联系人
        LinkMan linkMan1 = new LinkMan();
        LinkMan linkMan2 = new LinkMan();

        linkMan1.setLkm_name("李秘书4");
        linkMan2.setLkm_name("王助理4");

        // 3. 建立关系
        customer.getLinkManset().add(linkMan1);
        customer.getLinkManset().add(linkMan2);

        linkMan1.setCustomer(customer);
        linkMan2.setCustomer(customer);

        // 4. save
        session.save(linkMan1);
        session.save(linkMan2);

        tx.commit();
    }

    @Test
    /**
     * 测试对象导航 （维护双方的关系）
     * 客户和联系人都配置了 cascade = "save-update"
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception
     * @date         2019/5/29 15:53
     */
    public void demoSave4 () {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // 1. 创建一个客户
        Customer customer = new Customer();

        customer.setCust_name("胡总6");
        customer.setCust_phone("1590056");

        // 2. 创建两个联系人
        LinkMan linkMan1 = new LinkMan();
        LinkMan linkMan2 = new LinkMan();
        LinkMan linkMan3 = new LinkMan();

        linkMan1.setLkm_name("李秘书6");
        linkMan2.setLkm_name("王助理6");
        linkMan3.setLkm_name("王秘书6");

        // 3. 建立关系
        linkMan1.setCustomer(customer);
        customer.getLinkManset().add(linkMan2);
        customer.getLinkManset().add(linkMan3);

        // 4. save 条件是：双方都设置了 cascade = "save-update"
//        session.save(linkMan1); // insert 4条
//        session.save(customer); // insert 3条
        session.save(linkMan2); // insert 1条
        tx.commit();

    }

    @Test
    /**
     * 1.2.3.3 hibernate 的级联删除1
     * 一方和多方都不设置 cascade
     * @Author       yesixian
     * @param
     * @return       
     * @exception    
     * @date         2019/5/31 10:44
     */
    public void demo5() {
        Session session = HibernateUtils.openSession();

        Transaction tx = session.beginTransaction();

        // 若客户下面有联系人，默认情况下先将联系人的客户id置null,再删除客户
        Customer customer = session.get(Customer.class, 8L);
        session.delete(customer);

        tx.commit();
        session.close();
    }

    @Test
    /**
     * 1.2.3.3 hibernate 的级联删除2：级联删除有方向性
     * 删除客户的同时删除联系人
     * Customer.hbm.xml 中set标签配置 case = "delete"
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/31 10:44
     */
    public void demo6() {
        Session session = HibernateUtils.openSession();

        Transaction tx = session.beginTransaction();

        // 级联删除，必须先查询再删除
        // 因为查询到客户，客户中的联系人就查到了
        Customer customer = session.get(Customer.class, 18L);
        session.delete(customer);

        tx.commit();
        session.close();
    }

    @Test
    /**
     * 1.2.3.3 hibernate 的级联删除2：级联删除有方向性
     * 删除联系人的同时删除客户
     * LinkMan.hbm.xml 中<many-to-one />标签配置 case = "delete"
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/31 10:44
     */
    public void demo7() {
        Session session = HibernateUtils.openSession();

        Transaction tx = session.beginTransaction();

        // 级联删除，必须先查询再删除
        LinkMan linkMan = session.get(LinkMan.class, 20L);
        session.delete(linkMan);

        tx.commit();
        session.close();
    }

    @Test
    /**
     * 1.2.3.4 双向关联产生多余的sql
     * 问题：双方维护外键会产生多余的SQL
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/31 10:44
     */
    public void demo8() {
        Session session = HibernateUtils.openSession();

        Transaction tx = session.beginTransaction();

        // 将5号联系人关联给5号客户
        // 两个更新LinkMan 的sql
        LinkMan linkMan = session.get(LinkMan.class, 5L);
        Customer customer = session.get(Customer.class, 5L);


        linkMan.setCustomer(customer);
        customer.getLinkManset().add(linkMan);

        tx.commit();
        session.close();
    }

    @Test
    /**
     * 1.2.3.4 双向关联产生多余的sql
     * 问题：双方维护外键会产生多余的SQL
     * 解决：一方放弃维护外键的关系 Customer.hbm.xml <set />标签中配置inverse = ‘true'
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/31 10:44
     */
    public void demo9() {
        Session session = HibernateUtils.getCurrentSession();

        Transaction tx = session.beginTransaction();

        // 将5号联系人关联给5号客户
        LinkMan linkMan = session.get(LinkMan.class, 5L);
        Customer customer = session.get(Customer.class, 5L);


        linkMan.setCustomer(customer);
        customer.getLinkManset().add(linkMan);

        tx.commit();
        session.close();
    }

    @Test
    /**
     * 1.2.3.5 cascade和inverse的区别
     * cascade:操作一方的时候是否级联操作另一方
     * inverse:是否维护外键
     * @Author       yesixian
     * @param
     * @return
     * @exception
     * @date         2019/5/31 10:44
     */
    public void demo10() {
        Session session = HibernateUtils.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Customer customer = new Customer();
        LinkMan linkMan = new LinkMan();

        linkMan.setLkm_name("小叶");
        customer.setCust_name("叶总");
        customer.getLinkManset().add(linkMan);

        // 保存的联系人没有外键，因为Customer放弃了维护外键
        session.save(customer);

        tx.commit();
        session.close();
    }
}
