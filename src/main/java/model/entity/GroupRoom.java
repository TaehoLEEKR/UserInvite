package model.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GroupRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grId;
    private Integer grCount;
    private String grName;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

}
