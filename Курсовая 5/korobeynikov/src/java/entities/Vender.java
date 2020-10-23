/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "vender")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vender.findAll", query = "SELECT v FROM Vender v"),
    @NamedQuery(name = "Vender.findByNumVender", query = "SELECT v FROM Vender v WHERE v.numVender = :numVender"),
    @NamedQuery(name = "Vender.findByName", query = "SELECT v FROM Vender v WHERE v.name = :name"),
    @NamedQuery(name = "Vender.findByCountry", query = "SELECT v FROM Vender v WHERE v.country = :country"),
    @NamedQuery(name = "Vender.findBySite", query = "SELECT v FROM Vender v WHERE v.site = :site"),
    @NamedQuery(name = "Vender.findByMail", query = "SELECT v FROM Vender v WHERE v.mail = :mail")})
public class Vender implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_vender")
    private Integer numVender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "country")
    private String country;
    @Size(max = 2147483647)
    @Column(name = "site")
    private String site;
    @Size(max = 2147483647)
    @Column(name = "mail")
    private String mail;
    @OneToMany(mappedBy = "softwareNumVender")
    private List<Software> softwareList;

    public Vender() {
    }

    public Vender(Integer numVender) {
        this.numVender = numVender;
    }

    public Vender(Integer numVender, String name) {
        this.numVender = numVender;
        this.name = name;
    }

    public Integer getNumVender() {
        return numVender;
    }

    public void setNumVender(Integer numVender) {
        this.numVender = numVender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @XmlTransient
    public List<Software> getSoftwareList() {
        return softwareList;
    }

    public void setSoftwareList(List<Software> softwareList) {
        this.softwareList = softwareList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numVender != null ? numVender.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vender)) {
            return false;
        }
        Vender other = (Vender) object;
        if ((this.numVender == null && other.numVender != null) || (this.numVender != null && !this.numVender.equals(other.numVender))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Vender[ numVender=" + numVender + " ]";
    }
    
}
