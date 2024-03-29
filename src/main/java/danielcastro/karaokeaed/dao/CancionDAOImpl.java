/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package danielcastro.karaokeaed.dao;

import danielcastro.karaokeaed.iface.ICancionDAO;
import danielcastro.karaokeaed.model.Cancion;
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
public class CancionDAOImpl implements ICancionDAO {
    
    private final EntityManager entityManager;

    public CancionDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Cancion findById(Integer id) {
        try {
            return entityManager.find(Cancion.class, id);
        }catch(Exception e) {
            Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public List<Cancion> findAll() {
        try {
            Query query = entityManager.createQuery("FROM Cancion");
            return query.getResultList();
        }catch(Exception e) {
            Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public Cancion add(Cancion cancion) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cancion);
            entityManager.flush();
            entityManager.getTransaction().commit();
            return cancion;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error añadiendo cancion", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public Cancion update(Cancion cancion) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cancion);
            entityManager.getTransaction().commit();
            return cancion;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error actualizando cancion", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    public boolean delete(Cancion cancion) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(cancion) ? cancion : entityManager.merge(cancion));
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            Logger.getLogger(Cancion.class.getName()).log(Level.SEVERE
                    , null, e);
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null
                    , "Error borrando cancion", "Error"
                    , JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    
}
