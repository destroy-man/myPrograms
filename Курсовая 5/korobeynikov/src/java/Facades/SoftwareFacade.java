/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import entities.Software;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class SoftwareFacade extends AbstractFacade<Software> {
    @PersistenceContext(unitName = "korobeynikovPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SoftwareFacade() {
        super(Software.class);
    }
    
}
