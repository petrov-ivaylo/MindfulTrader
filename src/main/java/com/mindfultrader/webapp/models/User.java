package com.mindfultrader.webapp.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//import javax.persistence.CascadeType;

/*
 * JPA representation of customers table
 * 
 * Notes:
 *  - Name parameter inside @Column annotation must match DB attribute name
 *  - The name specified after Private <type> <name> is the field name we work with in the java code - ie can be set arbitrarily as suits our purpose
 *  - for id, email and password the name of the java object field matches the name of the db relation attribute, and so spring JPA figures out the db name 
 *     from that. 	
 *     
 *     Created: 5th Mar 2021
 *     Updated: 5th Apr 2021
 *     
 *     Author: Ivaylo
 *     Updated-by: Emma
 * 
 */

/*import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;*/

@Entity
@Table(name = "customers")
public class User {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column(nullable = false, unique = true, length = 64)
    private String email;
     
    @Column(nullable = false, length = 256)
    private String password;
     
    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;
     
    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Roles> roles = new HashSet<>();
    
    
    
	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}