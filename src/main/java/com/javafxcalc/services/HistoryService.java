package com.javafxcalc.services;

import com.javafxcalc.entities.HistoryEntity;
import com.javafxcalc.repos.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class HistoryService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private HistoryRepository repository;

    public void add(String text) {
        repository.save(new HistoryEntity(text));
    }

    public List<String> getHistory() {
        TypedQuery<HistoryEntity> typedQuery = em.createQuery("SELECT h FROM HistoryEntity h ORDER BY h.id DESC", HistoryEntity.class);
        typedQuery.setMaxResults(10);
        return typedQuery.getResultList().stream().map(e -> e.getText()).collect(Collectors.toList());
    }
    public String getHistoryAsText() {
        return getHistory().stream().collect(Collectors.joining("\n"));
    }

}
