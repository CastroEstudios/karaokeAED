package danielcastro.karaokeaed.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "Usuario")
public class Usuario implements Serializable {

    @Id
    @Column(name = "Id")
    private int id;
    @Column(name = "Nombre", nullable = false)
    private String nombre;
    @Column(name = "Apellidos", nullable = false)
    private String apellidos;
    @Column(name = "Canta_Mal", nullable = false)
    private boolean canta_mal;
    @OneToMany(mappedBy = "cancion", cascade = CascadeType.ALL)
    List<CancionUsuario> cantadas;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String apellidos, boolean canta_mal) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.canta_mal = canta_mal;
    }

    public Usuario(int id, String nombre, String apellidos, boolean canta_mal, List<CancionUsuario> cantadas) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.canta_mal = canta_mal;
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isCanta_mal() {
        return canta_mal;
    }

    public void setCanta_mal(boolean canta_mal) {
        this.canta_mal = canta_mal;
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

}
