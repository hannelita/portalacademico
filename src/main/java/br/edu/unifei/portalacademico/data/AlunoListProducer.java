package br.edu.unifei.portalacademico.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.unifei.portalacademico.model.Aluno;
import br.edu.unifei.portalacademico.model.Member;

@RequestScoped
public class AlunoListProducer {

	@PersistenceContext
	private EntityManager em;

	private List<Aluno> alunos;

	// @Named provides access the return value via the EL variable name "member"
	// in the UI (e.g., Facelets or JSP view)
	@Produces
	@Named
	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void onAlunoListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Aluno aluno) {
		retrieveAllAlunosOrderedByName();
	}

	@PostConstruct
	public void retrieveAllAlunosOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Aluno> criteria = cb.createQuery(Aluno.class);
		Root<Aluno> aluno = criteria.from(Aluno.class);
		
		criteria.select(aluno).orderBy(cb.asc(aluno.get("matricula")));
		alunos = em.createQuery(criteria).getResultList();
	}

}
