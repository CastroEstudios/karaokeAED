/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package danielcastro.karaokeaed.iface;

import danielcastro.karaokeaed.model.Usuario;
import java.util.List;

/**
 *
 * @author 2dama
 */
public interface IUsuarioDAO extends IDAO<Usuario, Integer> {

    @Override
    Usuario findById(Integer id);

    @Override
    List<Usuario> findAll();

    @Override
    Usuario add(Usuario usuario);

    @Override
    Usuario update(Usuario usuario);
}
