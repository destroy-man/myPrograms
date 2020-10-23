/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Facades;

import entities.SaleSoftware;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class SaleSoftwareFacade extends AbstractFacade<SaleSoftware> {
    @PersistenceContext(unitName = "korobeynikovPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SaleSoftwareFacade() {
        super(SaleSoftware.class);
    }
    
    public List<SaleSoftware> findRangePrice(int[] price) {
        int price0=0;
        int price1=0;
        price[0]=price1;
        price[1]=price0;
        //System.out.println(birth0);
        //System.out.println(birth1);
        return em.createQuery(
        "SELECT price FROM SaleSoftware WHERE price BETWEEN :bd0 AND :bd1")
        .setParameter("bd0", price0)
        .setParameter("bd1", price1)
        .getResultList();
    }
}
