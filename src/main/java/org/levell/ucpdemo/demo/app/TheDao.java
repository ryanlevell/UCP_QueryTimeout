package org.levell.ucpdemo.demo.app;

import oracle.ucp.jdbc.PoolDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.naming.NamingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Repository
public class TheDao {

    PoolDataSource dataSource;
    SessionFactory sessionFactory;

    public TheDao(PoolDataSource dataSource, SessionFactory sessionFactory) throws InvocationTargetException, IllegalAccessException, NamingException {
        this.dataSource = dataSource;
        this.sessionFactory = sessionFactory;

        for(Method m : dataSource.getClass().getMethods()) {
            if(m.getName().startsWith("get") && m.getParameterCount() == 0) {
                System.out.println(m.getName() + "=" + m.invoke(dataSource));
            }
        }
    }

    public List<TheTable> list() {
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<TheTable> query = session.createNativeQuery("select * from tbl", TheTable.class);
        return query.list();
    }
}
