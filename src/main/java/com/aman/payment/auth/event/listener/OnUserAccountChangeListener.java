/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aman.payment.auth.event.listener;

import com.aman.payment.auth.event.OnUserAccountChangeEvent;
import com.aman.payment.auth.exception.MailSendException;
import com.aman.payment.auth.model.User;
import com.aman.payment.auth.service.impl.MailService;

import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class OnUserAccountChangeListener implements ApplicationListener<OnUserAccountChangeEvent> {

    private static final Logger logger = Logger.getLogger(OnUserAccountChangeListener.class);
    private final MailService mailService;

    @Autowired
    public OnUserAccountChangeListener(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * As soon as a registration event is complete, invoke the email verification
     * asynchronously in an another thread pool
     */
    @Override
    @Async
    public void onApplicationEvent(OnUserAccountChangeEvent onUserAccountChangeEvent) {
    	if(onUserAccountChangeEvent.getAction().equals("Update Password") ||
    			onUserAccountChangeEvent.getAction().equals("Reset Password")) {
    		sendAccountChangePassword(onUserAccountChangeEvent);
    	}
    	
    }

    /**
     * Send email verification to the user and persist the token in the database.
     */
    private void sendAccountChangePassword(OnUserAccountChangeEvent event) {
        User user = event.getUser();
        String action = event.getAction();
        String actionStatus = event.getActionStatus();
        String recipientAddress = user.getEmail();

        try {
            mailService.sendAccountChangePassword(action, actionStatus, user, event.getPassword());
        } catch (IOException | TemplateException | MessagingException e) {
            logger.error(e);
            throw new MailSendException(recipientAddress, "Account Change Password");
        }
    }
}
