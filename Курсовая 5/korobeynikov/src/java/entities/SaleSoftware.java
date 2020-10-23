/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "sale_software")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaleSoftware.findAll", query = "SELECT s FROM SaleSoftware s"),
    @NamedQuery(name = "SaleSoftware.findByNumSale", query = "SELECT s FROM SaleSoftware s WHERE s.numSale = :numSale"),
    @NamedQuery(name = "SaleSoftware.findByDateSale", query = "SELECT s FROM SaleSoftware s WHERE s.dateSale = :dateSale"),
    @NamedQuery(name = "SaleSoftware.findByPrice", query = "SELECT s FROM SaleSoftware s WHERE s.price = :price"),
    @NamedQuery(name = "SaleSoftware.findByCount", query = "SELECT s FROM SaleSoftware s WHERE s.count = :count")})
public class SaleSoftware implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_sale")
    private Integer numSale;
    @Column(name = "date_sale")
    @Temporal(TemporalType.DATE)
    private Date dateSale;
    @Column(name = "price")
    private Integer price;
    @Column(name = "count")
    private Integer count;
    @JoinColumn(name = "software_num_software", referencedColumnName = "num_software")
    @ManyToOne(optional = false)
    private Software softwareNumSoftware;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saleSoftwareNumSale")
    private List<Contact> contactList;

    public SaleSoftware() {
    }

    public SaleSoftware(Integer numSale) {
        this.numSale = numSale;
    }

    public Integer getNumSale() {
        return numSale;
    }

    public void setNumSale(Integer numSale) {
        this.numSale = numSale;
    }

    public Date getDateSale() {
        return dateSale;
    }

    public void setDateSale(Date dateSale) {
        this.dateSale = dateSale;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Software getSoftwareNumSoftware() {
        return softwareNumSoftware;
    }

    public void setSoftwareNumSoftware(Software softwareNumSoftware) {
        this.softwareNumSoftware = softwareNumSoftware;
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
        hash += (numSale != null ? numSale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaleSoftware)) {
            return false;
        }
        SaleSoftware other = (SaleSoftware) object;
        if ((this.numSale == null && other.numSale != null) || (this.numSale != null && !this.numSale.equals(other.numSale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SaleSoftware[ numSale=" + numSale + " ]";
    }
    
}
