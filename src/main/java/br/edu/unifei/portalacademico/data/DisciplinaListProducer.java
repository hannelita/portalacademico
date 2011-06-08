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

import br.edu.unifei.portalacademico.model.Disciplina;

@RequestScoped
public class DisciplinaListProducer {
	
	@PersistenceContext
	private EntityManager em;

	private List<Disciplina> disciplinas;

	// @Named provides access the return value via the EL variable name "member"
	// in the UI (e.g., Facelets or JSP view)
	@Produces
	@Named
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void onDisciplinaListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Disciplina disciplina) {
		retrieveAllDisciplinasOrderedByName();
	}

	@PostConstruct
	public void retrieveAllDisciplinasOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Disciplina> criteria = cb.createQuery(Disciplina.class);
		Root<Disciplina> disciplina = criteria.from(Disciplina.class);
		
		criteria.select(disciplina).orderBy(cb.asc(disciplina.get("sigla")));
		disciplinas = em.createQuery(criteria).getResultList();
	}

}
