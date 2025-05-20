package com.example.todo_api.follow;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.todo_api.member.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class FollowRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Follow follow) {
    boolean exists = em.createQuery(
            "SELECT COUNT(f) FROM Follow f WHERE f.follower.id = :followerId AND f.following.id = :followingId",
            Long.class)
        .setParameter("followerId", follow.getFollower().getId())
        .setParameter("followingId", follow.getFollowing().getId())
        .getSingleResult() > 0;
        
        if (exists) {
            return;
        }
        em.persist(follow);
    }

    public Follow findById(Long id) {
        return em.find(Follow.class, id);
    }
    
    public List<Follow> findByFollower(Member member) {
        return em.createQuery("SELECT f FROM Follow f WHERE f.follower.id = :follower_id", Follow.class)
                .setParameter("follower_id", member.getId())
                .getResultList();
    }

    public List<Follow> findByFollowing(Member member) {
        return em.createQuery("SELECT f FROM Follow f WHERE f.following.id = :following_id", Follow.class)
                .setParameter("following_id", member.getId())
                .getResultList();
    }

    public void deleteById(Long id) {
        Follow follow = findById(id);
        em.remove(follow);
    }
    
    public void flushAndClear() {
        em.flush();
        em.clear();
    }
}
