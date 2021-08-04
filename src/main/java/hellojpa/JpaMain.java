package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    private EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //영속
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L,"B");

            em.persist(member1);
            em.persist(member2);
            System.out.println("================================");
            //선을 긋고 나간다. 버퍼링이라는 기능을 쓸 수 가 있는것이다. 최적화 여지자체가 없다. 커밋하기 직전에 인서트 하면된다.


            //디비 쿼리가 날라가는시기
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        // 엔티티 메니저를 꼭 닫아주어야 한다 .. 사실은 이런 코드가 없어질 것이다 스프링이 다 관리해주어서


        //code 코드를 항상 닫아 주어야 한다. 실제로 디비에 저장하는 트랜젝션은 ?? 디비 컬렉션을 얻어서 일관적인 엔티티 메니저를
        // 꼭 만들어 줘야한다.
        emf.close();


    }
}
