package org.Linh0512.module.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    public enum RoleName {
        ADMIN,
        USER
    }
}
