/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.conexcionmysql;

import com.mycompany.conexcionmysql.exceptions.NonexistentEntityException;
import com.mycompany.conexcionmysql.exceptions.PreexistingEntityException;
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
public class Posteos_ECJpaController implements Serializable {

    public Posteos_ECJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Posteos_EC posteos_EC) throws PreexistingEntityException, Exception {
        if (posteos_EC.getPosteosPK() == null) {
            posteos_EC.setPosteosPK(new PosteosPK_EC());
        }
        posteos_EC.getPosteosPK().setHashTer(posteos_EC.getTerminos().getHashTermino());
        posteos_EC.getPosteosPK().setHashDoc(posteos_EC.getDocumentos().getHashDocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Documentos_EC documentos = posteos_EC.getDocumentos();
            if (documentos != null) {
                documentos = em.getReference(documentos.getClass(), documentos.getHashDocumento());
                posteos_EC.setDocumentos(documentos);
            }
            Terminos_EC terminos = posteos_EC.getTerminos();
            if (terminos != null) {
                terminos = em.getReference(terminos.getClass(), terminos.getHashTermino());
                posteos_EC.setTerminos(terminos);
            }
            em.persist(posteos_EC);
            if (documentos != null) {
                documentos.getPosteosCollection().add(posteos_EC);
                documentos = em.merge(documentos);
            }
            if (terminos != null) {
                terminos.getPosteosCollection().add(posteos_EC);
                terminos = em.merge(terminos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPosteos_EC(posteos_EC.getPosteosPK()) != null) {
                throw new PreexistingEntityException("Posteos_EC " + posteos_EC + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Posteos_EC posteos_EC) throws NonexistentEntityException, Exception {
        posteos_EC.getPosteosPK().setHashTer(posteos_EC.getTerminos().getHashTermino());
        posteos_EC.getPosteosPK().setHashDoc(posteos_EC.getDocumentos().getHashDocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Posteos_EC persistentPosteos_EC = em.find(Posteos_EC.class, posteos_EC.getPosteosPK());
            Documentos_EC documentosOld = persistentPosteos_EC.getDocumentos();
            Documentos_EC documentosNew = posteos_EC.getDocumentos();
            Terminos_EC terminosOld = persistentPosteos_EC.getTerminos();
            Terminos_EC terminosNew = posteos_EC.getTerminos();
            if (documentosNew != null) {
                documentosNew = em.getReference(documentosNew.getClass(), documentosNew.getHashDocumento());
                posteos_EC.setDocumentos(documentosNew);
            }
            if (terminosNew != null) {
                terminosNew = em.getReference(terminosNew.getClass(), terminosNew.getHashTermino());
                posteos_EC.setTerminos(terminosNew);
            }
            posteos_EC = em.merge(posteos_EC);
            if (documentosOld != null && !documentosOld.equals(documentosNew)) {
                documentosOld.getPosteosCollection().remove(posteos_EC);
                documentosOld = em.merge(documentosOld);
            }
            if (documentosNew != null && !documentosNew.equals(documentosOld)) {
                documentosNew.getPosteosCollection().add(posteos_EC);
                documentosNew = em.merge(documentosNew);
            }
            if (terminosOld != null && !terminosOld.equals(terminosNew)) {
                terminosOld.getPosteosCollection().remove(posteos_EC);
                terminosOld = em.merge(terminosOld);
            }
            if (terminosNew != null && !terminosNew.equals(terminosOld)) {
                terminosNew.getPosteosCollection().add(posteos_EC);
                terminosNew = em.merge(terminosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PosteosPK_EC id = posteos_EC.getPosteosPK();
                if (findPosteos_EC(id) == null) {
                    throw new NonexistentEntityException("The posteos_EC with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PosteosPK_EC id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Posteos_EC posteos_EC;
            try {
                posteos_EC = em.getReference(Posteos_EC.class, id);
                posteos_EC.getPosteosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The posteos_EC with id " + id + " no longer exists.", enfe);
            }
            Documentos_EC documentos = posteos_EC.getDocumentos();
            if (documentos != null) {
                documentos.getPosteosCollection().remove(posteos_EC);
                documentos = em.merge(documentos);
            }
            Terminos_EC terminos = posteos_EC.getTerminos();
            if (terminos != null) {
                terminos.getPosteosCollection().remove(posteos_EC);
                terminos = em.merge(terminos);
            }
            em.remove(posteos_EC);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Posteos_EC> findPosteos_ECEntities() {
        return findPosteos_ECEntities(true, -1, -1);
    }

    public List<Posteos_EC> findPosteos_ECEntities(int maxResults, int firstResult) {
        return findPosteos_ECEntities(false, maxResults, firstResult);
    }

    private List<Posteos_EC> findPosteos_ECEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Posteos_EC.class));
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

    public Posteos_EC findPosteos_EC(PosteosPK_EC id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Posteos_EC.class, id);
        } finally {
            em.close();
        }
    }

    public int getPosteos_ECCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Posteos_EC> rt = cq.from(Posteos_EC.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
