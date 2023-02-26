package com.RSVP.rsvp.model;


import com.RSVP.rsvp.validation.PasswordsNotEmpty;
import com.RSVP.rsvp.validation.PasswordsNotEqual;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "findUserByUsername", query = "select u from User u where u.username = :username"),
        @NamedQuery(name = "findUserByOpenId", query = "select u from User u where u.openId = :openId")
})
@PasswordsNotEmpty(
        triggerFieldName = "signInProvider",
        passwordFieldName = "password",
        passwordVerificationFieldName = "passwordVerification"
)
@PasswordsNotEqual(
        passwordFieldName = "password",
        passwordVerificationFieldName = "passwordVerification"
)

public class User implements Serializable, UserDetails, SocialUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Integer version;
    @Column(nullable = true, unique = true)
    private String openId;
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Transient
    private String passwordVerification;
    private Boolean enabled = true;
    private Boolean accountExpired = false;
    private Boolean accountLocked = false;
    private Boolean credentialsExpired = false;
    private String firstName;
    private String lastName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {
            @JoinColumn(name = "user_id")}, inverseJoinColumns
            = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SocialMediaService signInProvider;

    // default constructor
    public User() {
    }

    // parameterized constructor
    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) { this.roles.add(role); }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.addAll(roles);
        return authorities;
    }
    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }
    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName);
        sb.append(" ");
        sb.append(lastName);
        return sb.toString();
    }
    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaService signInProvider) {
        this.signInProvider = signInProvider;
    }
    public boolean isNormalRegistration() {
        return signInProvider == null;
    }
    public boolean isSocialSignIn() {
        return signInProvider != null;
    }
    @Override
    public String getUserId() {
        return username;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.username);
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.username, other.username);
    }
    @Override
    public String toString() {
        return "User{username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + '}';
    }

}
