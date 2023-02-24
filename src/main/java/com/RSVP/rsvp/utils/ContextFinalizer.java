package com.RSVP.rsvp.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;

@Component
public class ContextFinalizer implements ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ContextFinalizer.class);

    @Override
    public void onApplicationEvent(ContextClosedEvent e) {
        log.info("Stopping connections");
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while(drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
                log.warn(String.format("Driver %s deregistered", d));
            } catch (SQLException ex) {
                log.warn(String.format("Error deregistered driver %s", d), ex);
            }
        }
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for(Thread t : threadArray) {
            if(t.getName().contains("Abandoned connection cleanup thread")) {
                synchronized (t) {
                    t.stop(); //don't complain, it works
                }
            }
        }
        log.info("Finished stopping connections");
    }
}
