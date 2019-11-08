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
@Table(name = "Salle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salle.findAll", query = "SELECT s FROM Salle s")
    , @NamedQuery(name = "Salle.findByNumeroSalle", query = "SELECT s FROM Salle s WHERE s.numeroSalle = :numeroSalle")
    , @NamedQuery(name = "Salle.findByCapacite", query = "SELECT s FROM Salle s WHERE s.capacite = :capacite")})
public class Salle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "numeroSalle")
    private String numeroSalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacite")
    private int capacite;

    public Salle() {
    }

    public Salle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public Salle(String numeroSalle, int capacite) {
        this.numeroSalle = numeroSalle;
        this.capacite = capacite;
    }

    public String getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(String numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroSalle != null ? numeroSalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salle)) {
            return false;
        }
        Salle other = (Salle) object;
        if ((this.numeroSalle == null && other.numeroSalle != null) || (this.numeroSalle != null && !this.numeroSalle.equals(other.numeroSalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Salle[ numeroSalle=" + numeroSalle + " ]";
    }
    
}
