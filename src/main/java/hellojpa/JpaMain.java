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

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "1000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street", "1000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "1000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("============= START =============");
            Member findMember = em.find(Member.class, member.getId());

            // 지연로딩인 것이다.
//            List<Address> addressesHistory = findMember.getAddressHistory();
//            for (Address address: addressesHistory) {
//                System.out.println("address =: "+ address.getCity());
//            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("address =: "+ favoriteFood);
            }


            // city 를 바꾸고 싶으면 통으로 갈아 껴주어야 한다. 값타입은
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            //값타입 업데이트
            // 치킨 > 한식  통으로 갈아끼워야한다. 지우고
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //주소를 바꾸는거 기본적으로 컬렉션은 리무브가 이퀄스를 찾는다. 제대로 넣어야함
            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "1000"));
            findMember.getAddressHistory().add(new AddressEntity("newCity2", "street", "1000"));



            //member.getHomeAddress().setCity("newCity");설정안해주면이렇게 변경해줘야함!

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
