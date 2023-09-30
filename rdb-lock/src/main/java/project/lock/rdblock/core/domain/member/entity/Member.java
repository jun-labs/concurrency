package project.lock.rdblock.core.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.util.Objects;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "following_count")
    private Integer followingCount;

    @Column(name = "follower_count")
    private Integer followerCount;

    protected Member() {
    }

    public Member(String name) {
        this.name = name;
        this.followingCount = 0;
        this.followerCount = 0;
    }

    public void increaseFollowingCount() {
        this.followingCount += 1;
    }

    public void increaseFollowerCount() {
        this.followerCount += 1;
    }

    public void decreaseFollowerCount() {
        this.followerCount -= 1;
    }

    public void decreaseFollowingCount() {
        this.followingCount -= 1;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Member member)) {
            return false;
        }
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(
            "[id: %s, followingCount: %s, followerCount: %s]",
            id, followingCount, followerCount
        );
    }
}
