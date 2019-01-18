/**
 *    Copyright 2009-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.demo.test;

import org.apache.demo.dao.MailDao;
import org.apache.demo.dao.MailDaoImpl;
import org.apache.demo.pojo.Mail;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author: yangpengwei
 * @create: 2018-09-12 16:12
 * @description: 测试类
 **/
public class TestMbatis {
    private static MailDao mailDao;

    static {
        mailDao = new MailDaoImpl();
    }

    @Test
    public void testInsert() {
        Mail mail1 = new Mail(1, "123@sina.com", "个人使用");
        Mail mail2 = new Mail(2, "123@qq.com", "企业使用");
        Mail mail3 = new Mail(3, "123@sohu.com", "注册账号使用");
        System.out.println(mailDao.insertMail(mail1));
        System.out.println(mailDao.insertMail(mail2));
        System.out.println(mailDao.insertMail(mail3));
    }

    @Test
    public void testDelete() {
        System.out.println(mailDao.deleteMail(1));
    }

    @Test
    public void testUpdate() {
        Mail mail = new Mail(2, "123@qq.com", "个人使用");
        mail.setId(2);
        System.out.println(mailDao.updateMail(mail));
        System.out.println(mailDao.selectMailById(2));
    }

    @Test
    public void testSelectOne() {
        System.out.println(mailDao.selectMailById(2));
    }

    @Test
    public void testSelectList() {
        List<Mail> mailList = mailDao.selectMailList();
        if (mailList != null && mailList.size() != 0) {
            for (Mail mail : mailList) {
                System.out.println(mail);
            }
        }
    }
}
