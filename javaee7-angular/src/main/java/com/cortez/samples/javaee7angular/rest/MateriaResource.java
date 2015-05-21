package com.cortez.samples.javaee7angular.rest;

import com.cortez.samples.javaee7angular.data.Materia;
import com.cortez.samples.javaee7angular.data.Person;
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
@Path("materias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MateriaResource extends Application {
    @PersistenceContext
    private EntityManager entityManager;

    private Integer countMaterias() {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Person p");
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
    public Person getMateria(@PathParam("id") Long id) {
        return entityManager.find(Person.class, id);
    }

    @POST
    public Person saveMateria(Person person) {
        if (person.getId() == null) {
            Person personToSave = new Person();
            personToSave.setName(person.getName());
            personToSave.setDescription(person.getDescription());
            personToSave.setImageUrl(person.getImageUrl());
            entityManager.persist(person);
        } else {
            Person personToUpdate = getMateria(person.getId());
            personToUpdate.setName(person.getName());
            personToUpdate.setDescription(person.getDescription());
            personToUpdate.setImageUrl(person.getImageUrl());
            person = entityManager.merge(personToUpdate);
        }

        return person;
    }

    @DELETE
    @Path("{id}")
    public void deletePerson(@PathParam("id") Long id) {
        entityManager.remove(getMateria(id));
    }
}
