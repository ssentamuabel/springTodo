package org.pahappa.systems.todo.backend.core.services;

import org.pahappa.systems.todo.backend.models.MailSetting;
import org.sers.webutils.model.exception.ValidationFailedException;

/**
 * Responsible for CRUD operations on {@link MailSetting}
 * 
 * @author mmc
 *
 */
public interface MailSettingService {
	/**
	 * Adds a {@link MailSetting} to the database.
	 * 
	 * @param mailSetting
	 * @throws ValidationFailedException if the following attributes are blank:
	 *               senderAddress, senderPassword, senderSmtpHost, senderSmtpPort
	 */
	MailSetting saveMailSetting(MailSetting mailSetting) throws ValidationFailedException;

	/**
	 * Gets mail settings
	 * 
	 * @return
	 */
	MailSetting getMailSetting();
}
