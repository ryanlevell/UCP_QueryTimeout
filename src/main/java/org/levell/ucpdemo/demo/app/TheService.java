package org.levell.ucpdemo.demo.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.List;

@Service
@EnableTransactionManagement
public class TheService {

    @Autowired
    TheDao theDao;

    @Transactional(rollbackOn = RuntimeException.class)
    public List<TheTable> doStuff() {
        return theDao.list();
    }
}
