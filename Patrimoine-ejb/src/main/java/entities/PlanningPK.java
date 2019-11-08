/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jerom
 */
@Embeddable
public class PlanningPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numeroSalle")
    private String numeroSalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateJ")
    @Temporal(TemporalType.DATE)
    private Date dateJ;

    public PlanningPK() {
    }

    public PlanningPK(String numeroSalle, Date dateJ) {
        this.numeroSalle = numeroSalle;
        this.dateJ = dateJ;
    }

    public String getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public Date getDateJ() {
        return dateJ;
    }

    public void setDateJ(Date dateJ) {
        this.dateJ = dateJ;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroSalle != null ? numeroSalle.hashCode() : 0);
        hash += (dateJ != null ? dateJ.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanningPK)) {
            return false;
        }
        PlanningPK other = (PlanningPK) object;
        if ((this.numeroSalle == null && other.numeroSalle != null) || (this.numeroSalle != null && !this.numeroSalle.equals(other.numeroSalle))) {
            return false;
        }
        if ((this.dateJ == null && other.dateJ != null) || (this.dateJ != null && !this.dateJ.equals(other.dateJ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PlanningPK[ numeroSalle=" + numeroSalle + ", dateJ=" + dateJ + " ]";
    }
    
}
