package com.RSVP.rsvp.utils;


import com.RSVP.rsvp.dao.MessageDao;
import com.RSVP.rsvp.dao.UserDao;
import com.RSVP.rsvp.model.Message;
import com.RSVP.rsvp.model.Role;
import com.RSVP.rsvp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Making initial check...");
        log.info("Validating Roles");
        Role adminRole = userDao.getRole("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role("ROLE_ADMIN");
            adminRole = userDao.createRole(adminRole);
        }

        Role userRole = userDao.getRole("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            userDao.createRole(userRole);
        }

        log.info("Validating Users");
        User admin = userDao.get("admin@irsvped.com");
        if (admin == null) {
            admin = new User("admin@irsvped.com", "admin", "Admin", "User");
            admin.addRole(adminRole);

            userDao.create(admin);
        }

        log.info("Validating Messages");
        Message signup = messageDao.get(Constants.SIGN_UP);
        if (signup == null) {
            signup = new Message();
            signup.setName(Constants.SIGN_UP);
            signup.setSubject("Welcome to RSVP!");
            StringBuilder content = new StringBuilder();
            content.append("<p>Salut @@NAME@@,</p>");
            content.append("<p>We are excited to help you create your special event.</p>");
            content.append("<p>To access your event's reports and settings you'll require to provide your credentials:</p>");
            content.append("<dl>");
            content.append("<dt>Email</dt>");
            content.append("<dd>@@USERNAME@@</dd>");
            content.append("<dt>Password</dt>");
            content.append("<dd>@@PASSWORD@@</dd>");
            content.append("</dl>");
            content.append("<p>Thanks again for using our service.</p>");
            signup.setContent(content.toString());
            messageDao.create(signup);
        }

        Message code = messageDao.get(Constants.CODE);
        if (code == null) {
            code = new Message();
            code.setName(Constants.CODE);
            code.setSubject("Your RSVP code is available!");
            StringBuilder content = new StringBuilder();
            content.append("<p>Here is your RSVP code for @@EVENT@@, please provide this code to your special guests:</p>");
            content.append("<p>@@CODE@@</p>");
            content.append("<p>Enjoy!</p>");
            code.setContent(content.toString());
            messageDao.create(code);
        }

        Message thankyou = messageDao.get(Constants.THANKS);
        if (thankyou == null) {
            thankyou = new Message();
            thankyou.setName(Constants.THANKS);
            thankyou.setSubject("Thank you for RSVPing!");
            StringBuilder content = new StringBuilder();
            content.append("<p>Thanks for RSVPing to @@EVENT@@!</p>");
            content.append("<p>Enjoy!</p>");
            thankyou.setContent(content.toString());
            messageDao.create(thankyou);
        }

        log.info("Done. Application is running!");
    }

}
