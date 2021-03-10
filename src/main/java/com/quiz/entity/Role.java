package com.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "role")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Role extends BaseEntity implements GrantedAuthority {
    @Column(name = "name")
    private String name;

    public Role(Integer id, String name) {
        super(id);
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return name;
    }
}
