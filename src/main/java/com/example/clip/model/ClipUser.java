package com.example.clip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clip_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClipUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "clipUser")
    private List<Payload> payload;

    @OneToMany(mappedBy = "clipUser")
    private List<Disbursement> disbursement;
}
