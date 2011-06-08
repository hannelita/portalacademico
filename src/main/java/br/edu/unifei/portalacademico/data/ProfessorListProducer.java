package br.edu.unifei.portalacademico.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.unifei.portalacademico.model.Professor;

@RequestScoped
public class ProfessorListProducer {
	

	@PersistenceContext
	private EntityManager em;

	private List<Professor> professores;

	// @Named provides access the return value via the EL variable name "member"
	// in the UI (e.g., Facelets or JSP view)
	@Produces
	@Named
	public List<Professor> getProfessors() {
		return professores;
	}

	public void onProfessorListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Professor professor) {
		retrieveAllProfessoresOrderedByName();
	}

	@PostConstruct
	public void retrieveAllProfessoresOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Professor> criteria = cb.createQuery(Professor.class);
		Root<Professor> professor = criteria.from(Professor.class);
		
		criteria.select(professor).orderBy(cb.asc(professor.get("nome")));
		professores = em.createQuery(criteria).getResultList();
	}


}
