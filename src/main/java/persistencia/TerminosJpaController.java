/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import Funciones.exceptions.NonexistentEntityException;
import Funciones.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author tecio
 */
public class TerminosJpaController implements Serializable {

    public TerminosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Terminos_EC terminos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(terminos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTerminos(terminos.getHashTermino()) != null) {
                throw new PreexistingEntityException("Terminos " + terminos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Terminos_EC terminos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            terminos = em.merge(terminos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = terminos.getHashTermino();
                if (findTerminos(id) == null) {
                    throw new NonexistentEntityException("The terminos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Terminos_EC terminos;
            try {
                terminos = em.getReference(Terminos_EC.class, id);
                terminos.getHashTermino();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The terminos with id " + id + " no longer exists.", enfe);
            }
            em.remove(terminos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Terminos_EC> findTerminosEntities() {
        return findTerminosEntities(true, -1, -1);
    }

    public List<Terminos_EC> findTerminosEntities(int maxResults, int firstResult) {
        return findTerminosEntities(false, maxResults, firstResult);
    }

    private List<Terminos_EC> findTerminosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Terminos_EC.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Terminos_EC findTerminos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Terminos_EC.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerminosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Terminos_EC> rt = cq.from(Terminos_EC.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
