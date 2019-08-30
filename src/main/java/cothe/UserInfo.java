package cothe;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class UserInfo {
    @Id
    private Long id;
    private String name;
}
