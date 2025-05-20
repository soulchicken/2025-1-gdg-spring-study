package com.example.todo_api.todo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.todo_api.member.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TodoRepository {

    @PersistenceContext
    private EntityManager em;

    // 데이터 생성
    public void save(Todo todo) {
        em.persist(todo);
    }
    
    // 데이터 조회
    // 단건 조회 (1개 데이터)
    public Todo findById(Long id) {
        return em.find(Todo.class, id);
    }

    // 다건 조회
    public List<Todo> findAll() {
        // JPQL 사용
        return em.createQuery("SELECT t FROM Todo t", Todo.class)
                .getResultList();
    }

    // 조건 조회 (WHERE 절을 사용)
    public List<Todo> findAllByMember(Member member) {
        return em.createQuery("SELECT t FROM Todo t WHERE t.member = :todo_member", Todo.class)
                .setParameter("todo_member", member)
                .getResultList();

    }

    // 데이터 수정 : 엔티티 클래스의 필드를 수정하면 된다.

    // 데이터 삭제
    public void deleteById(Long id) {
        Todo todo = findById(id);
        em.remove(todo);
    }


    // TEST 용도로만 사용하기
    public void flushAndClear() {
        em.flush();
        em.clear();
    }
}
