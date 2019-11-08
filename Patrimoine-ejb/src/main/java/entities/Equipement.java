/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jerom
 */
@Entity
@Table(name = "Equipement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Equipement.findAll", query = "SELECT e FROM Equipement e")
    , @NamedQuery(name = "Equipement.findByIdEquipement", query = "SELECT e FROM Equipement e WHERE e.idEquipement = :idEquipement")
    , @NamedQuery(name = "Equipement.findByNomEquipement", query = "SELECT e FROM Equipement e WHERE e.nomEquipement = :nomEquipement")})
public class Equipement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idEquipement")
    private Integer idEquipement;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nomEquipement")
    private String nomEquipement;

    public Equipement() {
    }

    public Equipement(Integer idEquipement) {
        this.idEquipement = idEquipement;
    }

    public Equipement(Integer idEquipement, String nomEquipement) {
        this.idEquipement = idEquipement;
        this.nomEquipement = nomEquipement;
    }

    public Integer getIdEquipement() {
        return idEquipement;
    }

    public void setIdEquipement(Integer idEquipement) {
        this.idEquipement = idEquipement;
    }

    public String getNomEquipement() {
        return nomEquipement;
    }

    public void setNomEquipement(String nomEquipement) {
        this.nomEquipement = nomEquipement;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEquipement != null ? idEquipement.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Equipement)) {
            return false;
        }
        Equipement other = (Equipement) object;
        if ((this.idEquipement == null && other.idEquipement != null) || (this.idEquipement != null && !this.idEquipement.equals(other.idEquipement))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Equipement[ idEquipement=" + idEquipement + " ]";
    }
    
}
