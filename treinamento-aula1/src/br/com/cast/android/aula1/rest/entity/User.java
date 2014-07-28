package br.com.cast.android.aula1.rest.entity;

import java.io.Serializable;

import br.com.cast.android.aula1.rest.LoginRestClient;

/**
 * Classe que representa a entidade esperada no reponse do serviço REST {@link LoginRestClient#authenticate(User)}.
 *
 * @author venilton.junior
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private String email;
	private String password;
	private long dateRegistration;
	private long dateLastLogin;

	public User() {
		super();
	}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getDateRegistration() {
		return dateRegistration;
	}

	public void setDateRegistration(long dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	public long getDateLastLogin() {
		return dateLastLogin;
	}

	public void setDateLastLogin(long dateLastLogin) {
		this.dateLastLogin = dateLastLogin;
	}

}
