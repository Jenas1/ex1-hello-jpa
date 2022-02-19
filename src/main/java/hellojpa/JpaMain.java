package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {
    private EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            //jPAL
//            List<Member> result = em.createQuery(
//                    "select m From Member m where m.username like '%kim%'",
//                    Member.class
//            ).getResultList();
//            // m 은 맴버자체를 가져오는것 !
//            for (Member member: result) {
//                System.out.println("member = "+ member);
//            }

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            //flush > commit, query날라갈때는 동작한다.
            //dbconn.excuteQuery>> 요시점엔는 결과0 이다. 이런경우는 강제로 플러쉬를 해줘야 한다
            em.createNativeQuery("select Member_ID, city, street, zipcode, USERNAME from MEMBER")
                            .getResultList();


            tx.commit();
        } catch (Exception e) {

            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();


    }
}
