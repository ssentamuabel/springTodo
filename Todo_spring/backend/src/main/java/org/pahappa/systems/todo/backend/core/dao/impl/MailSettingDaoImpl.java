package org.pahappa.systems.todo.backend.core.dao.impl;

import org.pahappa.systems.todo.backend.core.dao.MailSettingDao;
import org.pahappa.systems.todo.backend.models.MailSetting;
import org.sers.webutils.server.core.dao.impl.BaseDAOImpl;
import org.springframework.stereotype.Repository;

@Repository
public class MailSettingDaoImpl extends BaseDAOImpl<MailSetting> implements MailSettingDao{
    
}
