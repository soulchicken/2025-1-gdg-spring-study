package com.example.todo_api.todo;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.todo_api.member.Member;
import com.example.todo_api.member.MemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TodoRepositoryTest {
    
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional // 트랜잭션을 반드시 사용해서 테스트 해야한다.
    @Rollback(false) // 롤백을 하지 않도록 설정
    void todoSaveTest() {
        // 트랜잭션의 시작
        Todo todo = new Todo("todo content", false, null);
        todoRepository.save(todo);

        // 트랜잭션 종료 & 커밋
        // 에러가 발생하면 자동 롤백

        Assertions.assertThat(todo.getId()).isNotNull();
    }

    // 조회
    @Test
    @Transactional
    void todoFindByIdTest() {
        // given
        Todo todo = new Todo("todo content", false, null);
        todoRepository.save(todo);
        todoRepository.flushAndClear(); // 영속성 컨텍스트 초기화

        // when
        Todo findTodo = todoRepository.findById(todo.getId());

        // then
        Assertions.assertThat(findTodo.getContent()).isEqualTo("todo content");
    }

    // 다건 조회
    @Test
    @Transactional
    void todoFindAllTest() {
        // given
        Todo todo1 = new Todo("todo content1", false, null);
        Todo todo2 = new Todo("todo content2", false, null);
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        // when
        List<Todo> todos = todoRepository.findAll();
        System.out.println(todos);

        // then
        Assertions.assertThat(todos).hasSize(2);
    }

    // 조건 조회
    @Test
    @Transactional
    void todoFindAllByMemberTest() {

        // given
        Member member1 = new Member("member1@gdg.com", "password1");
        Member member2 = new Member("member2@gdg.com", "password2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Todo todo1 = new Todo("todo content1", false, member1);
        Todo todo2 = new Todo("todo content2", false, member1);
        Todo todo3 = new Todo("todo content3", false, member2);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        // when
        List<Todo> member1Todos = todoRepository.findAllByMember(member1);
        List<Todo> member2Todos = todoRepository.findAllByMember(member2);

        // then
        Assertions.assertThat(member1Todos).hasSize(2);
        Assertions.assertThat(member2Todos).hasSize(1);
    }

    // 데이터 수정
    @Test
    @Transactional
    @Rollback(false)
    void todoUpdateTest() {
        Todo todo = new Todo("todo content", false, null);
        todoRepository.save(todo);
        todoRepository.flushAndClear(); // 영속성 컨텍스트 초기화

        Todo findTodo = todoRepository.findById(todo.getId());
        findTodo.updateContent("new content");
    }

    // 데이터 삭제
    @Test
    @Transactional
    @Rollback(false)
    void todoDeleteTest() {
        Todo todo = new Todo("todo content", false, null);
        todoRepository.save(todo);
        todoRepository.flushAndClear(); // 영속성 컨텍스트 초기화

        todoRepository.deleteById(todo.getId());
        Todo findTodo = todoRepository.findById(todo.getId());

        Assertions.assertThat(findTodo).isNull();
    }

    @AfterAll
    public static void doNotFinish() {
        System.out.println("테스트 종료");
        while (true) {}
    }

}
