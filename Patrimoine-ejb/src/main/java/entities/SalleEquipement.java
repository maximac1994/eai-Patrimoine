/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jerom
 */
@Entity
@Table(name = "SalleEquipement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SalleEquipement.findAll", query = "SELECT s FROM SalleEquipement s")
    , @NamedQuery(name = "SalleEquipement.findByNumeroSalle", query = "SELECT s FROM SalleEquipement s WHERE s.salleEquipementPK.numeroSalle = :numeroSalle")
    , @NamedQuery(name = "SalleEquipement.findByIdEquipement", query = "SELECT s FROM SalleEquipement s WHERE s.salleEquipementPK.idEquipement = :idEquipement")})
public class SalleEquipement implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SalleEquipementPK salleEquipementPK;

    public SalleEquipement() {
    }

    public SalleEquipement(SalleEquipementPK salleEquipementPK) {
        this.salleEquipementPK = salleEquipementPK;
    }

    public SalleEquipement(String numeroSalle, int idEquipement) {
        this.salleEquipementPK = new SalleEquipementPK(numeroSalle, idEquipement);
    }

    public SalleEquipementPK getSalleEquipementPK() {
        return salleEquipementPK;
    }

    public void setSalleEquipementPK(SalleEquipementPK salleEquipementPK) {
        this.salleEquipementPK = salleEquipementPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salleEquipementPK != null ? salleEquipementPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalleEquipement)) {
            return false;
        }
        SalleEquipement other = (SalleEquipement) object;
        if ((this.salleEquipementPK == null && other.salleEquipementPK != null) || (this.salleEquipementPK != null && !this.salleEquipementPK.equals(other.salleEquipementPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SalleEquipement[ salleEquipementPK=" + salleEquipementPK + " ]";
    }
    
}
