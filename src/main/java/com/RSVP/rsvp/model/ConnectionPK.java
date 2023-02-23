package com.RSVP.rsvp.model;


import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConnectionPK implements Serializable {

    private String userid;
    private String providerId;
    private String providerUserId;

    // Default constructor
    public ConnectionPK() {
    }

    // parameterized constructor
    public ConnectionPK(String userid, String providerId, String providerUserId) {
        this.userid = userid;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
    }

    // Getters and Setters

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.userid);
        hash = 23 * hash + Objects.hashCode(this.providerId);
        hash = 23 * hash + Objects.hashCode(this.providerUserId);
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
        final ConnectionPK other = (ConnectionPK) obj;
        if(!Objects.equals(this.userid, other.userid)) {
            return false;
        }
        if(!Objects.equals(this.providerId, other.providerId)) {
            return false;
        }
        return Objects.equals(this.providerUserId, other.providerUserId);
    }

    @Override
    public String toString() {
        return "ConnectionPK{" + "userid=" + userid + ", providerId=" + providerId + ", providerUserId=" + providerUserId + '}';
    }
}
