package danielcastro.karaokeaed.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "Canciones")
public class Cancion implements Serializable {

    @Id
    @Column(name = "Id", unique = true)
    private int id;
    @Column(name = "Nombre", nullable = false)
    private String nombre;
    @Column(name = "Autor", nullable = false)
    private String autor;
    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL)
    List<CancionUsuario> cantadas;

    public Cancion() {
    }

    public Cancion(int id, String nombre, String autor) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
    }

    public Cancion(int id, String nombre, String autor, List<CancionUsuario> cantadas) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.cantadas = cantadas;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<CancionUsuario> getCantadas() {
        return cantadas;
    }

    public void setCantadas(List<CancionUsuario> cantadas) {
        this.cantadas = cantadas;
    }

    @Override
    public String toString() {
        return "" + id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.id;
        hash = 43 * hash + Objects.hashCode(this.nombre);
        hash = 43 * hash + Objects.hashCode(this.autor);
        hash = 43 * hash + Objects.hashCode(this.cantadas);
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
        final Cancion other = (Cancion) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.autor, other.autor)) {
            return false;
        }
        return Objects.equals(this.cantadas, other.cantadas);
    }

}
