/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package danielcastro.karaokeaed.dao;

import danielcastro.karaokeaed.iface.ICancionUsuarioDAO;
import danielcastro.karaokeaed.model.CancionUsuario;
import java.awt.Toolkit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author Anima
 */
public class CancionUsuarioDAOImpl implements ICancionUsuarioDAO {
    
    private final EntityManager entityManager;
    
    public CancionUsuarioDAOImpl(EntityManager entityManager) {
            this.entityManager = entityManager;
    }
    
    @Override
    public CancionUsuario findById(Integer id) {
        try {
            return entityManager.find(CancionUsuario.class, id);
        }catch(Exception e) {
            Logger.getLogger(CancionUsuario.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<CancionUsuario> findAll() {
        try {
            Query query = entityManager.createQuery("FROM CancionUsuario");
            return query.getResultList();
        }catch(Exception e) {
            Logger.getLogger(CancionUsuario.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public CancionUsuario add(CancionUsuario cancionUsuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cancionUsuario);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return cancionUsuario;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(CancionUsuario.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error añadiendo el registro de la cancion", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public CancionUsuario update(CancionUsuario cancionUsuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cancionUsuario);
            entityManager.getTransaction().commit();
            return cancionUsuario;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(CancionUsuario.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error actualizando el registro de la cancion", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean delete(CancionUsuario cancionUsuario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(cancionUsuario) ? cancionUsuario : entityManager.merge(cancionUsuario));
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(CancionUsuario.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error borrando el registro de la cancion", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
    public void updateOrDelete(CancionUsuario cancionUsuario, boolean isInList) {
        if (isInList) {
            update(cancionUsuario);
        } else {
            //delete(cancionUsuario);
        }
    }
    
}
