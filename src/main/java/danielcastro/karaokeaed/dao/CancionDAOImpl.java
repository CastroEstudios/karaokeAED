/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package danielcastro.karaokeaed.dao;

import danielcastro.karaokeaed.iface.ICancionDAO;
import danielcastro.karaokeaed.model.Cancion;
import danielcastro.karaokeaed.model.CancionUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author 2dama
 */
public class CancionDAOImpl implements ICancionDAO {
    
    private final EntityManager entityManager;

    public CancionDAOImpl(EntityManager eM) {
        this.entityManager = eM;
    }

    @Override
    public Cancion findById(Integer id) {
        return entityManager.find(Cancion.class, id);
    }

    @Override
    public List<Cancion> findAll() {
        Query query = entityManager.createQuery("FROM Cancion");
        return query.getResultList();
    }

    @Override
    public Cancion add(Cancion cancion) {
        entityManager.persist(cancion);
        return cancion;
    }

    @Override
    public Cancion update(Cancion cancion) {
        entityManager.merge(cancion);
        return cancion;
    }

    @Override
    public boolean delete(Cancion cancion) {
        entityManager.remove(cancion);
        return true;
    }

}
