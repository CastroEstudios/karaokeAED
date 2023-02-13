/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package danielcastro.karaokeaed.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Anima
 */
@Entity
@Table(name = "Cancion_Usuario")
public class CancionUsuario implements Serializable {
    
    @Id
    private int id;
    @ManyToOne()
    @JoinColumn(name = "cancion_id")
    private Cancion cancion;
    @ManyToOne()
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Column (name = "Fecha")
    private LocalDate fecha;

    public CancionUsuario() {
    }

    public CancionUsuario(int id, Cancion cancion, Usuario usuario) {
        this.id = id;
        this.fecha = LocalDate.now();
        this.usuario = usuario;
        this.cancion = cancion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public int getIdUsuario() {
        return usuario.getId();
    }

    public void setIdUsuario(int idUsuario) {
        this.usuario.setId(idUsuario);
    }
    
    public int getIdCancion() {
        return cancion.getId();
    }

    public void setIdCancion(int idCancion) {
        this.cancion.setId(idCancion);
    }

    @Override
    public String toString() {
        return "Cancion_Usuario{" + "id=" + id + ", fecha=" + fecha + ", cancion=" + cancion + ", usuario=" + usuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.cancion);
        hash = 97 * hash + Objects.hashCode(this.usuario);
        hash = 97 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CancionUsuario other = (CancionUsuario) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.cancion, other.cancion)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return Objects.equals(this.fecha, other.fecha);
    }

    
    
}
