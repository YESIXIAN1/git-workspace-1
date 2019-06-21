package cn.itcast.hibernate.day4.test;

import cn.itcast.hibernate.day1.entity.Role;
import cn.itcast.hibernate.day1.entity.User;
import cn.itcast.hibernate.day1.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * @Description:    hibernate day4
 * @Author:         yesixian
 * @CreateDate:     2019/6/4 16:51
 * @UpdateUser:     
 * @UpdateDate:     2019/6/4 16:51
 * @UpdateRemark:   修改内容
 */
public class Demo_day4 {
    @Test
    /**
     * hibernate 多对多关联测试
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception    
     * @date         2019/6/4 16:52
     */
    public void demo1() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        // 1. 用户
        User user1 = new User();
        User user2 = new User();

        user1.setUser_name("张三");
        user2.setUser_name("李四");

        // 2. 角色
        Role role3 = new Role();
        Role role1 = new Role();
        Role role2 = new Role();

        role3.setRole_name("行政");
        role1.setRole_name("保洁");
        role2.setRole_name("前台");

        // 3. 保存 （多对多关联时，一定要一方放弃维护外键权）
        user1.getRoles().add(role1);
        user1.getRoles().add(role3);
        user2.getRoles().add(role2);
        user2.getRoles().add(role3);

        role1.getUsers().add(user1);
        // *** user1 不设置role2的角色。（因为role2没有关联级联操作）
        role2.getUsers().add(user1);
        role2.getUsers().add(user2);
        role3.getUsers().add(user2);

        session.save(user1);
        session.save(user2);
        session.save(role1);
        session.save(role2);
        session.save(role3);

        tx.commit();
    }

    @Test
    /**
     * hibernate 多对多关联级联保存和更新：保存用户级联角色
     * User.hbm.xml <set></set> cascade=‘save-update’
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception
     * @date         2019/6/4 16:52
     */
    public void demo10() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        // 1. 用户
        User user1 = new User();
        User user2 = new User();

        user1.setUser_name("张三2");
        user2.setUser_name("李四2");

        // 2. 角色
        Role role3 = new Role();
        Role role1 = new Role();
        Role role2 = new Role();

        role1.setRole_name("保洁2");
        role2.setRole_name("前台3");
        role3.setRole_name("行政4");

        // 3. 保存 （多对多关联时，一定要一方放弃维护外键权）
        user1.getRoles().add(role1);
        user1.getRoles().add(role3);
        user2.getRoles().add(role2);
        user2.getRoles().add(role3);

        // 可以省略
        /*role1.getUsers().add(user1);
        role2.getUsers().add(user2);
        role3.getUsers().add(user2);
        role3.getUsers().add(user1);*/

        // 4. user设置了级联更新和保存
        session.save(user1);
        session.save(user2);
        // 省略
        /*session.save(role1);
        session.save(role2);
        session.save(role3);*/

        tx.commit();
    }

    @Test
    /**
     * hibernate 多对多关联级联保存和更新：保存角色级联用户
     * User 映射中配置级联操作
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception
     * @date         2019/6/4 16:52
     */
    public void demo11() {

    }

    @Test
    /**
     * hibernate 多对多级联删除（了解，实际开发中非常少，都是假删除）
     * Role.hbm.xml <set></set> cascade='delete'
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception
     * @date         2019/6/4 16:52
     */
    public void demo13() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        User user = session.get(User.class, 2L);
        // 删除user，同时删除sys_user_role, role 的关联信息
        session.delete(user);

        tx.commit();
    }

    @Test
    /**
     * hibernate 多对多级联删除（了解，实际开发中非常少，都是假删除）
     * Role.hbm.xml <set></set> cascade='delete'
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception
     * @date         2019/6/4 16:52
     */
    public void demo12() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        Role role = session.get(Role.class, 4L);
        // 删除user，同时删除sys_user_role，user 的关联信息
        session.delete(role);

        tx.commit();
    }

    @Test
    /**
     * hibernate 多对多其他操作：删除某个用户的角色
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception
     * @date         2019/6/4 16:52
     */
    public void demo14() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        User user = session.get(User.class, 5L);
        Role role = session.get(Role.class, 7L);

        user.getRoles().remove(role);

        tx.commit();
    }

    @Test
    /**
     * hibernate 多对多其他操作：某个用户角色的改选
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception
     * @date         2019/6/4 16:52
     */
    public void demo15() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        User user = session.get(User.class, 5L);
        Role role1 = session.get(Role.class, 8L);
        Role role2 = session.get(Role.class, 5L);

        user.getRoles().remove(role1);
        user.getRoles().add(role2);

        tx.commit();
    }

    @Test
    /**
     * hibernate 多对多其他操作：给某个用户添加角色
     * 给6号用户添加角色6
     * @Author       yesixian
     * @param        []
     * @return       void
     * @exception
     * @date         2019/6/4 16:52
     */
    public void demo16() {
        Session session = HibernateUtils.openSession();
        Transaction tx = session.beginTransaction();

        User user = session.get(User.class, 6L);
        Role role1 = session.get(Role.class, 6L);

        user.getRoles().add(role1);

        tx.commit();
    }


}
