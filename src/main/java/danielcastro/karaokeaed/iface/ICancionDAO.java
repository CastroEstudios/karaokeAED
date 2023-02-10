/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package danielcastro.karaokeaed.iface;

import danielcastro.karaokeaed.model.Cancion;
import java.util.List;

/**
 *
 * @author 2dama
 */
public interface ICancionDAO extends IDAO<Cancion, Integer> {

    @Override
    Cancion findById(Integer id);

    @Override
    List<Cancion> findAll();

    @Override
    Cancion add(Cancion cancion);

    @Override
    Cancion update(Cancion cancion);
}
