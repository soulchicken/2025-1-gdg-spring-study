package com.example.todo_api.bean;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanTest {

    ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    public void getAllBeanTest() {
        // spring 컨테이너를 생성하고 (설정파일 정보를 사용해서)
        // 컨테이너 안에 있는 모든 빈을 조회하는 테스트
        for (String name: context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // 스프링 컨테이너 안에 MyBean이 있는지 검증하고 싶음
        // 컨텍스트 안에 MyBean이 있을까요??
        Assertions.assertThat(context.getBeanDefinitionNames()).contains("myBean");
    }

    @Test
    public void getOneBeanTest() {
        MyBean myBean1 = context.getBean(MyBean.class);
        MyBean myBean2 = context.getBean(MyBean.class);

        System.out.println(myBean1);
        System.out.println(myBean2);

        Assertions.assertThat(myBean1).isSameAs(myBean2);
    }

    @Test
    public void dependencyInjectionTest() {
        MyBean myBean = context.getBean(MyBean.class);
        MySubBean mySubBean = context.getBean(MySubBean.class);

        System.out.println(myBean.getMySubBean());
        System.out.println(mySubBean);

        Assertions.assertThat(myBean.getMySubBean()).isSameAs(mySubBean);
    }
}
