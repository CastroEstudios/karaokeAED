/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package danielcastro.karaokeaed.dao;

import danielcastro.karaokeaed.iface.IUsuarioDAO;
import danielcastro.karaokeaed.model.Usuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author 2dama
 */
public class UsuarioDAOImpl implements IUsuarioDAO {

    private final EntityManager entityManager;

    public UsuarioDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Usuario findById(Integer id) {
        try {
            return entityManager.find(Usuario.class, id);
        }catch(Exception e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Usuario> findAll() {
        Query query = entityManager.createQuery("FROM Usuario");
        return query.getResultList();
    }

    @Override
    public Usuario add(Usuario usuario) {
        entityManager.persist(usuario);
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        entityManager.merge(usuario);
        return usuario;
    }

    @Override
    public boolean delete(Usuario usuario) {
        entityManager.remove(usuario);
        return true;
    }
    
}
