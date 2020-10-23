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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "software")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Software.findAll", query = "SELECT s FROM Software s"),
    @NamedQuery(name = "Software.findByNumSoftware", query = "SELECT s FROM Software s WHERE s.numSoftware = :numSoftware"),
    @NamedQuery(name = "Software.findByName", query = "SELECT s FROM Software s WHERE s.name = :name"),
    @NamedQuery(name = "Software.findByTypeSoftware", query = "SELECT s FROM Software s WHERE s.typeSoftware = :typeSoftware")})
public class Software implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_software")
    private Integer numSoftware;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "type_software")
    private String typeSoftware;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "softwareNumSoftware")
    private List<SaleSoftware> saleSoftwareList;
    @JoinColumn(name = "software_num_vender", referencedColumnName = "num_vender")
    @ManyToOne
    private Vender softwareNumVender;

    public Software() {
    }

    public Software(Integer numSoftware) {
        this.numSoftware = numSoftware;
    }

    public Integer getNumSoftware() {
        return numSoftware;
    }

    public void setNumSoftware(Integer numSoftware) {
        this.numSoftware = numSoftware;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeSoftware() {
        return typeSoftware;
    }

    public void setTypeSoftware(String typeSoftware) {
        this.typeSoftware = typeSoftware;
    }

    @XmlTransient
    public List<SaleSoftware> getSaleSoftwareList() {
        return saleSoftwareList;
    }

    public void setSaleSoftwareList(List<SaleSoftware> saleSoftwareList) {
        this.saleSoftwareList = saleSoftwareList;
    }

    public Vender getSoftwareNumVender() {
        return softwareNumVender;
    }

    public void setSoftwareNumVender(Vender softwareNumVender) {
        this.softwareNumVender = softwareNumVender;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numSoftware != null ? numSoftware.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Software)) {
            return false;
        }
        Software other = (Software) object;
        if ((this.numSoftware == null && other.numSoftware != null) || (this.numSoftware != null && !this.numSoftware.equals(other.numSoftware))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Software[ numSoftware=" + numSoftware + " ]";
    }
    
}
