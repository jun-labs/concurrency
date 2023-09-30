package project.lock.rdblock.core.domain.follow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;

@Entity
@Getter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "target_id")
    private Long targetId;

    @Version
    private Integer version;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    protected Follow() {
    }

    public Follow(
        Long sourceId,
        Long targetId
    ) {
        validateId(sourceId, targetId);
        this.sourceId = sourceId;
        this.targetId = targetId;
    }

    private void validateId(
        Long sourceId,
        Long targetId
    ) {
        if (sourceId == null || targetId == null) {
            throw new IllegalArgumentException();
        }
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
    }
}
