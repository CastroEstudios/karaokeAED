/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package danielcastro.karaokeaed.iface;

import java.util.List;

/**
 *
 * @author 2dama
 */
public interface IDAO<T, I> {

    T findById(I id);

    List<T> findAll();

    T add(T t);

    T update(T t);

    boolean delete(T t);
    
}
