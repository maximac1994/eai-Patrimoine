/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jerom
 */
@Embeddable
public class SalleEquipementPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numeroSalle")
    private String numeroSalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEquipement")
    private int idEquipement;

    public SalleEquipementPK() {
    }

    public SalleEquipementPK(String numeroSalle, int idEquipement) {
        this.numeroSalle = numeroSalle;
        this.idEquipement = idEquipement;
    }

    public String getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public int getIdEquipement() {
        return idEquipement;
    }

    public void setIdEquipement(int idEquipement) {
        this.idEquipement = idEquipement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroSalle != null ? numeroSalle.hashCode() : 0);
        hash += (int) idEquipement;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalleEquipementPK)) {
            return false;
        }
        SalleEquipementPK other = (SalleEquipementPK) object;
        if ((this.numeroSalle == null && other.numeroSalle != null) || (this.numeroSalle != null && !this.numeroSalle.equals(other.numeroSalle))) {
            return false;
        }
        if (this.idEquipement != other.idEquipement) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SalleEquipementPK[ numeroSalle=" + numeroSalle + ", idEquipement=" + idEquipement + " ]";
    }
    
}
