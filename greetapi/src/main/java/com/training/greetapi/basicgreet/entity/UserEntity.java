package com.training.greetapi.basicgreet.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "greeting_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String userName;
    @Column(name = "dob")
    private int age;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)

    /*
    * Use @JsonManagedReference and @JsonBackReference:

Annotate the UserEntity's messages field with @JsonManagedReference.
Annotate the MessageEntity's user field with @JsonBackReference.
This tells Jackson to serialize the forward part of the
* relationship (UserEntity to MessageEntity)
* but to ignore the backward part (MessageEntity to UserEntity).
    *
    * */

    @JsonManagedReference
    private Set<MessageEntity> messages = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return age == that.age && Objects.equals(id, that.id) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, age);
    }


}
