package com.cortez.samples.javaee7angular.rest;

import com.cortez.samples.javaee7angular.data.Materia;
import com.cortez.samples.javaee7angular.data.Materia;
import com.cortez.samples.javaee7angular.pagination.PaginatedListWrapper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import java.util.List;

/**
 * REST Service to expose the data to display in the UI grid.
 *
 * @author Roberto Cortez
 */
@Stateless
@ApplicationPath("/resources")
@Path("materia")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MateriaResource extends Application {
    @PersistenceContext
    private EntityManager entityManager;

    private Integer countMaterias() {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM MATERIA p");
        return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    private List<Materia> findMaterias(int startPosition, int maxResults, String sortFields, String sortDirections) {
        Query query =
                entityManager.createQuery("SELECT p FROM MATERIAS p ORDER BY p." + sortFields + " " + sortDirections);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    private PaginatedListWrapper findMaterias(PaginatedListWrapper wrapper) {
        wrapper.setTotalResults(countMaterias());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setListMaterias(findMaterias(start,
                                    wrapper.getPageSize(),
                                    wrapper.getSortFields(),
                                    wrapper.getSortDirections()));
        return wrapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedListWrapper listMaterias(@DefaultValue("1")
                                            @QueryParam("page")
                                            Integer page,
                                            @DefaultValue("id")
                                            @QueryParam("sortFields")
                                            String sortFields,
                                            @DefaultValue("asc")
                                            @QueryParam("sortDirections")
                                            String sortDirections) {
        PaginatedListWrapper paginatedListWrapper = new PaginatedListWrapper();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(10);
        return findMaterias(paginatedListWrapper);
    }

    @GET
    @Path("{id}")
    public Materia getMateria(@PathParam("id") Long id) {
        return entityManager.find(Materia.class, id);
    }

    @POST
    public Materia saveMateria(Materia materia) {
        if (materia.getId() == null) {
            Materia materiaToSave = new Materia();
            materiaToSave.setNome(materia.getNome());
            materiaToSave.setDescricao(materia.getDescricao());
                   entityManager.persist(materia);
        } else {
            Materia materiaToUpdate = getMateria(materia.getId());
            materiaToUpdate.setNome(materia.getNome());
            materiaToUpdate.setDescricao(materia.getDescricao());
            materia = entityManager.merge(materiaToUpdate);
        }

        return materia;
    }

    @DELETE
    @Path("{id}")
    public void deleteMateria(@PathParam("id") Long id) {
        entityManager.remove(getMateria(id));
    }
}
