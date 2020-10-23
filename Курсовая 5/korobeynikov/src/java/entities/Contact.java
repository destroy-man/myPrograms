/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "contact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c"),
    @NamedQuery(name = "Contact.findByNumContact", query = "SELECT c FROM Contact c WHERE c.numContact = :numContact"),
    @NamedQuery(name = "Contact.findBySurname", query = "SELECT c FROM Contact c WHERE c.surname = :surname"),
    @NamedQuery(name = "Contact.findByName", query = "SELECT c FROM Contact c WHERE c.name = :name"),
    @NamedQuery(name = "Contact.findByPatronym", query = "SELECT c FROM Contact c WHERE c.patronym = :patronym"),
    @NamedQuery(name = "Contact.findByPhone", query = "SELECT c FROM Contact c WHERE c.phone = :phone")})
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_contact")
    private Integer numContact;
    @Size(max = 2147483647)
    @Column(name = "surname")
    private String surname;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "patronym")
    private String patronym;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Недопустимый формат номера телефона/факса (должен иметь формат xxx-xxx-xxxx)")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "phone")
    private String phone;
    @JoinColumn(name = "sale_software_num_sale", referencedColumnName = "num_sale")
    @ManyToOne(optional = false)
    private SaleSoftware saleSoftwareNumSale;
    @JoinColumn(name = "counteragents_num_conteragent", referencedColumnName = "num_conteragent")
    @ManyToOne(optional = false)
    private Counteragents counteragentsNumConteragent;

    public Contact() {
    }

    public Contact(Integer numContact) {
        this.numContact = numContact;
    }

    public Integer getNumContact() {
        return numContact;
    }

    public void setNumContact(Integer numContact) {
        this.numContact = numContact;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronym() {
        return patronym;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public SaleSoftware getSaleSoftwareNumSale() {
        return saleSoftwareNumSale;
    }

    public void setSaleSoftwareNumSale(SaleSoftware saleSoftwareNumSale) {
        this.saleSoftwareNumSale = saleSoftwareNumSale;
    }

    public Counteragents getCounteragentsNumConteragent() {
        return counteragentsNumConteragent;
    }

    public void setCounteragentsNumConteragent(Counteragents counteragentsNumConteragent) {
        this.counteragentsNumConteragent = counteragentsNumConteragent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numContact != null ? numContact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.numContact == null && other.numContact != null) || (this.numContact != null && !this.numContact.equals(other.numContact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Contact[ numContact=" + numContact + " ]";
    }
    
}
