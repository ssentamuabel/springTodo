package org.pahappa.systems.todo.frontend.client.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.sers.webutils.server.core.utils.DateUtils;

@ManagedBean(name = "genericController", eager = true)
@ApplicationScoped
public class GenericController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currentYear;
	private Date currentDate;
	private String currentDateAsString;
	private String systemName = "Payment Gateway";
	private String searchPlaceHolder = "Enter text to search";
	private String dataEmptyMessage = "No records found";
	private String contactNumbers;
	private String contactEmail;
	private String landingPageLabel = "";

	@PostConstruct
	public void init() {
		this.currentDate = new Date();
		this.currentYear = DateUtils.currentYear();
		this.currentDateAsString = new SimpleDateFormat("EEE, dd MMM, yyyy HH:mm:ss").format(currentDate);
	}

	/**
	 * @return the dataEmptyMessage
	 */
	public String getDataEmptyMessage() {
		return dataEmptyMessage;
	}

	/**
	 * @return the searchPlaceHolder
	 */
	public String getSearchPlaceHolder() {
		return searchPlaceHolder;
	}

	/**
	 * @return the landingPageLabel
	 */
	public String getLandingPageLabel() {
		return landingPageLabel;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the contactNumbers
	 */
	public String getContactNumbers() {
		return contactNumbers;
	}

	/**
	 * @return the contactEmail
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * @return the currentYear
	 */
	public int getCurrentYear() {
		return currentYear;
	}

	/**
	 * @return the currentDate
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * @return the currentDateAsString
	 */
	public String getCurrentDateAsString() {
		return currentDateAsString;
	}

	/**
	 * @return the systemName
	 */
	public String getSystemName() {
		return systemName;
	}
}
