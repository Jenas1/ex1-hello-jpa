package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;
    @Column(name = "USERNAME")
    private String Username;
/*    @Column(name = "TEAM_ID")
    private Long teamId*/;

    @ManyToOne  // 1대 다
    @JoinColumn(name="TEAM_ID") // FK
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    //연관관계 편의 메소드 나자신의 세팅 양쪽으로
    //changeTeam
}