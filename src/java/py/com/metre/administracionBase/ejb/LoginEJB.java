/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.metre.administracionBase.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.metre.administracionBase.jpa.Usuario;


/**
 *
 * @author 
 */
@Stateless
public class LoginEJB {

  @PersistenceContext
  EntityManager em;

  public Usuario login(String codigo)
  {
      try{
          String query = "select * from usuario where nombre = '" + codigo + "'";
          // and id_usuario IN (select id_usuario from usuario_rol where id_rol = 4)
          Query q = em.createNativeQuery(query, Usuario.class);
          return (Usuario) q.getSingleResult();
          //return (BswUsuarios) em.createQuery("SELECT u FROM BswUsuarios u WHERE u.codUsuario ='" +codigo+ "'").getSingleResult();
      }catch(NullPointerException ex){
          return null;
      }
  }
}

