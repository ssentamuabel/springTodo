package org.pahappa.systems.todo.backend.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.sers.webutils.model.BaseEntity;

@Entity
@Table(name = "mail_settings")
@Inheritance(strategy = InheritanceType.JOINED)
public class MailSetting extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String senderAddress = "";
    private String senderPassword = "";
    private String senderSmtpHost = "";
    private String senderSmtpPort = "";
    

    /**
	 * @return the senderAddress
	 */
    @Column(name = "sender_address", nullable = false, length = 100)
	public String getSenderAddress() {
		return senderAddress;
	}

	/**
	 * @param senderAddress the senderAddress to set
	 */
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * @return the senderPassword
	 */
    @Column(name = "sender_password", nullable = false, length = 100)
	public String getSenderPassword() {
		return senderPassword;
	}

	/**
	 * @param senderPassword the senderPassword to set
	 */
	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}

	/**
	 * @return the senderSmtpHost
	 */
    @Column(name = "sender_host", nullable = false, length = 100)
	public String getSenderSmtpHost() {
		return senderSmtpHost;
	}

	/**
	 * @param senderSmtpHost the senderSmtpHost to set
	 */
	public void setSenderSmtpHost(String senderSmtpHost) {
		this.senderSmtpHost = senderSmtpHost;
	}

	/**
	 * @return the senderSmtpPort
	 */
    @Column(name = "sender_port", nullable = false, length = 100)
	public String getSenderSmtpPort() {
		return senderSmtpPort;
	}

	/**
	 * @param senderSmtpPort the senderSmtpPort to set
	 */
	public void setSenderSmtpPort(String senderSmtpPort) {
		this.senderSmtpPort = senderSmtpPort;
	}

	@Override
    public boolean equals(Object object) {
        return object instanceof MailSetting && (super.getId() != null) ? super.getId().equals(((MailSetting) object).getId())
                : (object == this);
    }

    @Override
    public int hashCode() {
        return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
    }
}
