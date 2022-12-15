package org.pahappa.systems.todo.backend.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.pahappa.systems.todo.backend.constants.TokenStatus;
import org.sers.webutils.model.BaseEntity;
import org.sers.webutils.model.security.User;

/**
 * 
 * @author ttc
 * This model is responsible for holding API user tokens
 *
 */
@Entity
@Table(name = "user_tokens")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserToken extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	//the user for whom the token belongs
	private User user;
	
	//the automatically generated token
	private String token;
	
	//the status of the token. A token may be active of expired
	private TokenStatus tokenStatus;
	
	public UserToken() {
		this.tokenStatus = TokenStatus.ACTIVE;
	}
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
    @Column(name = "token_status")
    @Enumerated(value = EnumType.STRING)
    public TokenStatus getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(TokenStatus tokenStatus) {
		this.tokenStatus = tokenStatus;
	}
	
	@Column(name = "token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return this.user.toString();
	}

	@Override
	public int hashCode() {
		return super.getId() != null ? this.getClass().hashCode() + super.getId().hashCode() : super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UserToken && (super.getId() != null) ? super.getId().equals(((UserToken) obj).getId()) : (obj == this);
	}

}
