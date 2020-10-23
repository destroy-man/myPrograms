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
@Table(name = "actual_address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActualAddress.findAll", query = "SELECT a FROM ActualAddress a"),
    @NamedQuery(name = "ActualAddress.findByNumActualAddress", query = "SELECT a FROM ActualAddress a WHERE a.numActualAddress = :numActualAddress"),
    @NamedQuery(name = "ActualAddress.findByAddress", query = "SELECT a FROM ActualAddress a WHERE a.address = :address")})
public class ActualAddress implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_actual_address")
    private Integer numActualAddress;
    @Size(max = 2147483647)
    @Column(name = "address")
    private String address;
    @JoinColumn(name = "counteragents_num_conteragent", referencedColumnName = "num_conteragent")
    @ManyToOne(optional = false)
    private Counteragents counteragentsNumConteragent;

    public ActualAddress() {
    }

    public ActualAddress(Integer numActualAddress) {
        this.numActualAddress = numActualAddress;
    }

    public Integer getNumActualAddress() {
        return numActualAddress;
    }

    public void setNumActualAddress(Integer numActualAddress) {
        this.numActualAddress = numActualAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        hash += (numActualAddress != null ? numActualAddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActualAddress)) {
            return false;
        }
        ActualAddress other = (ActualAddress) object;
        if ((this.numActualAddress == null && other.numActualAddress != null) || (this.numActualAddress != null && !this.numActualAddress.equals(other.numActualAddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ActualAddress[ numActualAddress=" + numActualAddress + " ]";
    }
    
}
