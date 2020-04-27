/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.conexcionmysql;

import com.mycompany.conexcionmysql.exceptions.IllegalOrphanException;
import com.mycompany.conexcionmysql.exceptions.NonexistentEntityException;
import com.mycompany.conexcionmysql.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tecio
 */
public class Terminos_ECJpaController implements Serializable {

    public Terminos_ECJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Terminos_EC terminos_EC) throws PreexistingEntityException, Exception {
        if (terminos_EC.getPosteosCollection() == null) {
            terminos_EC.setPosteosCollection(new ArrayList<Posteos_EC>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Posteos_EC> attachedPosteosCollection = new ArrayList<Posteos_EC>();
            for (Posteos_EC posteosCollectionPosteos_ECToAttach : terminos_EC.getPosteosCollection()) {
                posteosCollectionPosteos_ECToAttach = em.getReference(posteosCollectionPosteos_ECToAttach.getClass(), posteosCollectionPosteos_ECToAttach.getPosteosPK());
                attachedPosteosCollection.add(posteosCollectionPosteos_ECToAttach);
            }
            terminos_EC.setPosteosCollection(attachedPosteosCollection);
            em.persist(terminos_EC);
            for (Posteos_EC posteosCollectionPosteos_EC : terminos_EC.getPosteosCollection()) {
                Terminos_EC oldTerminosOfPosteosCollectionPosteos_EC = posteosCollectionPosteos_EC.getTerminos();
                posteosCollectionPosteos_EC.setTerminos(terminos_EC);
                posteosCollectionPosteos_EC = em.merge(posteosCollectionPosteos_EC);
                if (oldTerminosOfPosteosCollectionPosteos_EC != null) {
                    oldTerminosOfPosteosCollectionPosteos_EC.getPosteosCollection().remove(posteosCollectionPosteos_EC);
                    oldTerminosOfPosteosCollectionPosteos_EC = em.merge(oldTerminosOfPosteosCollectionPosteos_EC);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTerminos_EC(terminos_EC.getHashTermino()) != null) {
                throw new PreexistingEntityException("Terminos_EC " + terminos_EC + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Terminos_EC terminos_EC) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Terminos_EC persistentTerminos_EC = em.find(Terminos_EC.class, terminos_EC.getHashTermino());
            Collection<Posteos_EC> posteosCollectionOld = persistentTerminos_EC.getPosteosCollection();
            Collection<Posteos_EC> posteosCollectionNew = terminos_EC.getPosteosCollection();
            List<String> illegalOrphanMessages = null;
            for (Posteos_EC posteosCollectionOldPosteos_EC : posteosCollectionOld) {
                if (!posteosCollectionNew.contains(posteosCollectionOldPosteos_EC)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Posteos_EC " + posteosCollectionOldPosteos_EC + " since its terminos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Posteos_EC> attachedPosteosCollectionNew = new ArrayList<Posteos_EC>();
            for (Posteos_EC posteosCollectionNewPosteos_ECToAttach : posteosCollectionNew) {
                posteosCollectionNewPosteos_ECToAttach = em.getReference(posteosCollectionNewPosteos_ECToAttach.getClass(), posteosCollectionNewPosteos_ECToAttach.getPosteosPK());
                attachedPosteosCollectionNew.add(posteosCollectionNewPosteos_ECToAttach);
            }
            posteosCollectionNew = attachedPosteosCollectionNew;
            terminos_EC.setPosteosCollection(posteosCollectionNew);
            terminos_EC = em.merge(terminos_EC);
            for (Posteos_EC posteosCollectionNewPosteos_EC : posteosCollectionNew) {
                if (!posteosCollectionOld.contains(posteosCollectionNewPosteos_EC)) {
                    Terminos_EC oldTerminosOfPosteosCollectionNewPosteos_EC = posteosCollectionNewPosteos_EC.getTerminos();
                    posteosCollectionNewPosteos_EC.setTerminos(terminos_EC);
                    posteosCollectionNewPosteos_EC = em.merge(posteosCollectionNewPosteos_EC);
                    if (oldTerminosOfPosteosCollectionNewPosteos_EC != null && !oldTerminosOfPosteosCollectionNewPosteos_EC.equals(terminos_EC)) {
                        oldTerminosOfPosteosCollectionNewPosteos_EC.getPosteosCollection().remove(posteosCollectionNewPosteos_EC);
                        oldTerminosOfPosteosCollectionNewPosteos_EC = em.merge(oldTerminosOfPosteosCollectionNewPosteos_EC);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = terminos_EC.getHashTermino();
                if (findTerminos_EC(id) == null) {
                    throw new NonexistentEntityException("The terminos_EC with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Terminos_EC terminos_EC;
            try {
                terminos_EC = em.getReference(Terminos_EC.class, id);
                terminos_EC.getHashTermino();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The terminos_EC with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Posteos_EC> posteosCollectionOrphanCheck = terminos_EC.getPosteosCollection();
            for (Posteos_EC posteosCollectionOrphanCheckPosteos_EC : posteosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Terminos_EC (" + terminos_EC + ") cannot be destroyed since the Posteos_EC " + posteosCollectionOrphanCheckPosteos_EC + " in its posteosCollection field has a non-nullable terminos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(terminos_EC);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Terminos_EC> findTerminos_ECEntities() {
        return findTerminos_ECEntities(true, -1, -1);
    }

    public List<Terminos_EC> findTerminos_ECEntities(int maxResults, int firstResult) {
        return findTerminos_ECEntities(false, maxResults, firstResult);
    }

    private List<Terminos_EC> findTerminos_ECEntities(boolean all, int maxResults, int firstResult) {
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

    public Terminos_EC findTerminos_EC(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Terminos_EC.class, id);
        } finally {
            em.close();
        }
    }

    public int getTerminos_ECCount() {
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
