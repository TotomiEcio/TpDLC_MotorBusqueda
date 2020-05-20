/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import com.mycompany.conexcionmysql.exceptions.exceptions.NonexistentEntityException;
import com.mycompany.conexcionmysql.exceptions.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.Posteo_EC;
import persistencia.PosteoPK;

/**
 *
 * @author tecio
 */
public class PosteoJpaController implements Serializable {

    public PosteoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Posteo_EC posteo) throws PreexistingEntityException, Exception {
        if (posteo.getPosteoPK() == null) {
            posteo.setPosteoPK(new PosteoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(posteo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPosteo(posteo.getPosteoPK()) != null) {
                throw new PreexistingEntityException("Posteo " + posteo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Posteo_EC posteo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            posteo = em.merge(posteo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PosteoPK id = posteo.getPosteoPK();
                if (findPosteo(id) == null) {
                    throw new NonexistentEntityException("The posteo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PosteoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Posteo_EC posteo;
            try {
                posteo = em.getReference(Posteo_EC.class, id);
                posteo.getPosteoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The posteo with id " + id + " no longer exists.", enfe);
            }
            em.remove(posteo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Posteo_EC> findPosteoEntities() {
        return findPosteoEntities(true, -1, -1);
    }

    public List<Posteo_EC> findPosteoEntities(int maxResults, int firstResult) {
        return findPosteoEntities(false, maxResults, firstResult);
    }

    private List<Posteo_EC> findPosteoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Posteo_EC.class));
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

    public Posteo_EC findPosteo(PosteoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Posteo_EC.class, id);
        } finally {
            em.close();
        }
    }

    public int getPosteoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Posteo_EC> rt = cq.from(Posteo_EC.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
