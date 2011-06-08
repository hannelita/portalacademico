package br.edu.unifei.portalacademico.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import br.edu.unifei.portalacademico.model.Aluno;
import br.edu.unifei.portalacademico.model.Curso;
import br.edu.unifei.portalacademico.model.Member;
import br.edu.unifei.portalacademico.model.StatusAluno;

@Model
public class AlunoRegistration {

	@Inject
	@Category("portalacademico")
	private Logger log;

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@Inject
	private Event<Aluno> alunoEventSrc;

	private Aluno aluno;

	@Produces
	@Named
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public void register() throws Exception {
		log.info("Registering " + aluno.getNome());
		// UserTransaction only needed when bean is not an EJB
		utx.begin();
		em.joinTransaction();
		em.persist(aluno);
		utx.commit();
		alunoEventSrc.fire(aluno);
		initAluno();
	}
	
	public void remove() throws Exception{
		log.info("Deleting " + aluno.getNome());
		utx.begin();
		em.joinTransaction();
		em.remove(aluno);
		utx.commit();
		alunoEventSrc.fire(aluno);
		initAluno();
	}

	@PostConstruct
	public void initAluno() {
		aluno = new Aluno();
	}

	public SelectItem[] getCursosValue() {
		SelectItem[] items = new SelectItem[Curso.values().length];
		int i = 0;
		for (Curso c : Curso.values()) {
			items[i++] = new SelectItem(c, c.name());
		}
		return items;
	}
	public SelectItem[] getStatusAlunoValue() {
		SelectItem[] items = new SelectItem[StatusAluno.values().length];
		int i = 0;
		for (StatusAluno st : StatusAluno.values()) {
			items[i++] = new SelectItem(st, st.name());
		}
		return items;
	}

}
