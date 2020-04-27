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
public class Documentos_ECJpaController implements Serializable {

    public Documentos_ECJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documentos_EC documentos_EC) throws PreexistingEntityException, Exception {
        if (documentos_EC.getPosteosCollection() == null) {
            documentos_EC.setPosteosCollection(new ArrayList<Posteos_EC>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Posteos_EC> attachedPosteosCollection = new ArrayList<Posteos_EC>();
            for (Posteos_EC posteosCollectionPosteos_ECToAttach : documentos_EC.getPosteosCollection()) {
                posteosCollectionPosteos_ECToAttach = em.getReference(posteosCollectionPosteos_ECToAttach.getClass(), posteosCollectionPosteos_ECToAttach.getPosteosPK());
                attachedPosteosCollection.add(posteosCollectionPosteos_ECToAttach);
            }
            documentos_EC.setPosteosCollection(attachedPosteosCollection);
            em.persist(documentos_EC);
            for (Posteos_EC posteosCollectionPosteos_EC : documentos_EC.getPosteosCollection()) {
                Documentos_EC oldDocumentosOfPosteosCollectionPosteos_EC = posteosCollectionPosteos_EC.getDocumentos();
                posteosCollectionPosteos_EC.setDocumentos(documentos_EC);
                posteosCollectionPosteos_EC = em.merge(posteosCollectionPosteos_EC);
                if (oldDocumentosOfPosteosCollectionPosteos_EC != null) {
                    oldDocumentosOfPosteosCollectionPosteos_EC.getPosteosCollection().remove(posteosCollectionPosteos_EC);
                    oldDocumentosOfPosteosCollectionPosteos_EC = em.merge(oldDocumentosOfPosteosCollectionPosteos_EC);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDocumentos_EC(documentos_EC.getHashDocumento()) != null) {
                throw new PreexistingEntityException("Documentos_EC " + documentos_EC + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documentos_EC documentos_EC) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentos_EC persistentDocumentos_EC = em.find(Documentos_EC.class, documentos_EC.getHashDocumento());
            Collection<Posteos_EC> posteosCollectionOld = persistentDocumentos_EC.getPosteosCollection();
            Collection<Posteos_EC> posteosCollectionNew = documentos_EC.getPosteosCollection();
            List<String> illegalOrphanMessages = null;
            for (Posteos_EC posteosCollectionOldPosteos_EC : posteosCollectionOld) {
                if (!posteosCollectionNew.contains(posteosCollectionOldPosteos_EC)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Posteos_EC " + posteosCollectionOldPosteos_EC + " since its documentos field is not nullable.");
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
            documentos_EC.setPosteosCollection(posteosCollectionNew);
            documentos_EC = em.merge(documentos_EC);
            for (Posteos_EC posteosCollectionNewPosteos_EC : posteosCollectionNew) {
                if (!posteosCollectionOld.contains(posteosCollectionNewPosteos_EC)) {
                    Documentos_EC oldDocumentosOfPosteosCollectionNewPosteos_EC = posteosCollectionNewPosteos_EC.getDocumentos();
                    posteosCollectionNewPosteos_EC.setDocumentos(documentos_EC);
                    posteosCollectionNewPosteos_EC = em.merge(posteosCollectionNewPosteos_EC);
                    if (oldDocumentosOfPosteosCollectionNewPosteos_EC != null && !oldDocumentosOfPosteosCollectionNewPosteos_EC.equals(documentos_EC)) {
                        oldDocumentosOfPosteosCollectionNewPosteos_EC.getPosteosCollection().remove(posteosCollectionNewPosteos_EC);
                        oldDocumentosOfPosteosCollectionNewPosteos_EC = em.merge(oldDocumentosOfPosteosCollectionNewPosteos_EC);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = documentos_EC.getHashDocumento();
                if (findDocumentos_EC(id) == null) {
                    throw new NonexistentEntityException("The documentos_EC with id " + id + " no longer exists.");
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
            Documentos_EC documentos_EC;
            try {
                documentos_EC = em.getReference(Documentos_EC.class, id);
                documentos_EC.getHashDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documentos_EC with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Posteos_EC> posteosCollectionOrphanCheck = documentos_EC.getPosteosCollection();
            for (Posteos_EC posteosCollectionOrphanCheckPosteos_EC : posteosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Documentos_EC (" + documentos_EC + ") cannot be destroyed since the Posteos_EC " + posteosCollectionOrphanCheckPosteos_EC + " in its posteosCollection field has a non-nullable documentos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(documentos_EC);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Documentos_EC> findDocumentos_ECEntities() {
        return findDocumentos_ECEntities(true, -1, -1);
    }

    public List<Documentos_EC> findDocumentos_ECEntities(int maxResults, int firstResult) {
        return findDocumentos_ECEntities(false, maxResults, firstResult);
    }

    private List<Documentos_EC> findDocumentos_ECEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documentos_EC.class));
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

    public Documentos_EC findDocumentos_EC(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documentos_EC.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentos_ECCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documentos_EC> rt = cq.from(Documentos_EC.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
