/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package danielcastro.karaokeaed.iface;

import danielcastro.karaokeaed.model.CancionUsuario;
import java.util.List;

/**
 *
 * @author 2dama
 */
public interface ICancionUsuarioDAO extends IDAO<CancionUsuario, Integer> {

    @Override
    CancionUsuario findById(Integer id);

    @Override
    List<CancionUsuario> findAll();

    @Override
    CancionUsuario add(CancionUsuario cancion);

    @Override
    CancionUsuario update(CancionUsuario cancion);
}
