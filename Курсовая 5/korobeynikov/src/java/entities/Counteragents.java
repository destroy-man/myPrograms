/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "counteragents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Counteragents.findAll", query = "SELECT c FROM Counteragents c"),
    @NamedQuery(name = "Counteragents.findByNumConteragent", query = "SELECT c FROM Counteragents c WHERE c.numConteragent = :numConteragent"),
    @NamedQuery(name = "Counteragents.findByName", query = "SELECT c FROM Counteragents c WHERE c.name = :name"),
    @NamedQuery(name = "Counteragents.findByPhoneCounteragent", query = "SELECT c FROM Counteragents c WHERE c.phoneCounteragent = :phoneCounteragent")})
public class Counteragents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_conteragent")
    private Integer numConteragent;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "phone_counteragent")
    private String phoneCounteragent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "counteragentsNumConteragent")
    private List<ActualAddress> actualAddressList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "counteragentsNumConteragent")
    private List<Contact> contactList;

    public Counteragents() {
    }

    public Counteragents(Integer numConteragent) {
        this.numConteragent = numConteragent;
    }

    public Integer getNumConteragent() {
        return numConteragent;
    }

    public void setNumConteragent(Integer numConteragent) {
        this.numConteragent = numConteragent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneCounteragent() {
        return phoneCounteragent;
    }

    public void setPhoneCounteragent(String phoneCounteragent) {
        this.phoneCounteragent = phoneCounteragent;
    }

    @XmlTransient
    public List<ActualAddress> getActualAddressList() {
        return actualAddressList;
    }

    public void setActualAddressList(List<ActualAddress> actualAddressList) {
        this.actualAddressList = actualAddressList;
    }

    @XmlTransient
    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numConteragent != null ? numConteragent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Counteragents)) {
            return false;
        }
        Counteragents other = (Counteragents) object;
        if ((this.numConteragent == null && other.numConteragent != null) || (this.numConteragent != null && !this.numConteragent.equals(other.numConteragent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Counteragents[ numConteragent=" + numConteragent + " ]";
    }
    
}
