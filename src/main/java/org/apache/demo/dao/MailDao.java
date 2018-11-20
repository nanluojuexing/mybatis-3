package org.apache.demo.dao;

import org.apache.demo.pojo.Mail;

import java.util.Date;
import java.util.List;

/**
 * @author: yangpengwei
 * @create: 2018-09-12 16:05
 * @description: dao
 **/
public interface MailDao {

    /**
     * 插入一条邮箱信息
     */
    public long insertMail(Mail mail);

    /**
     * 删除一条邮箱信息
     */
    public int deleteMail(long id);

    /**
     * 更新一条邮箱信息
     */
    public int updateMail(Mail mail);

    /**
     * 查询邮箱列表
     */
    public List<Mail> selectMailList();

    /**
     * 根据主键id查询一条邮箱信息
     */
    public Mail selectMailById(long id);
}
