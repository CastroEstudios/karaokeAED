/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package danielcastro.karaokeaed.dao;

import danielcastro.karaokeaed.iface.IUsuarioDAO;
import danielcastro.karaokeaed.model.Usuario;
import java.awt.Toolkit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

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
        try {
            Query query = entityManager.createQuery("FROM Usuario");
            return query.getResultList();
        }catch(Exception e) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Usuario add(Usuario usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error añadiendo usuario", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public Usuario update(Usuario usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error actualizando usuario", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean delete(Usuario usuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(usuario) ? usuario : entityManager.merge(usuario));
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error borrando usuario", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    public void updateOrDelete(Usuario usuario, boolean isInList) {
        if (isInList) {
            update(usuario);
        } else {
            delete(usuario);
        }
    }
    
}
