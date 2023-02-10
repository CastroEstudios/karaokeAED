package danielcastro.karaokeaed.dao;

import java.io.Serializable;
import java.util.List;
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
    @Column(name = "Id")
    private int id;
    @Column(name = "Nombre", nullable = false)
    private String nombre;
    @Column(name = "Autor", nullable = false)
    private String autor;
    @Column(name = "Repeticiones", nullable = false)
    private int repeticiones;
    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL)
    List<Cancion_Usuario> cantadas;

    public Cancion() {
    }

    public Cancion(int id, String nombre, String autor, int repeticiones) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.repeticiones = repeticiones;
    }

    public Cancion(int id, String nombre, String autor, int repeticiones, List<Cancion_Usuario> cantadas) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.repeticiones = repeticiones;
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

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public List<Cancion_Usuario> getCantadas() {
        return cantadas;
    }

    public void setCantadas(List<Cancion_Usuario> cantadas) {
        this.cantadas = cantadas;
    }

    @Override
    public String toString() {
        return "" + id;
    }
    
}
