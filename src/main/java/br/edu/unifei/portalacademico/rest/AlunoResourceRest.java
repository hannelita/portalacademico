package br.edu.unifei.portalacademico.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import br.edu.unifei.portalacademico.data.MemberRepository;
import br.edu.unifei.portalacademico.model.Aluno;
import br.edu.unifei.portalacademico.model.Member;

@Path("/alunos")
@RequestScoped
public class AlunoResourceRest {
	
	@PersistenceContext
    private EntityManager em;

    @GET
    public List<Aluno> listAllAlunos() {
        // Use @SupressWarnings to force IDE to ignore warnings about "genericizing" the results of this query
        @SuppressWarnings("unchecked")
        // We recommend centralizing inline queries such as this one into @NamedQuery annotations on the @Entity class
        // as described in the named query blueprint: https://blueprints.dev.java.net/bpcatalog/ee5/persistence/namedquery.html
        final List<Aluno> results = em.createQuery("select a from Aluno a order by a.matricula").getResultList();
        return results;
    }

    @GET
    @Path("/{id:[1-9][0-9]*}")
    public Aluno lookupAlunoById(@PathParam("id") long id) {
        return em.find(Aluno.class, id);
    }

}
