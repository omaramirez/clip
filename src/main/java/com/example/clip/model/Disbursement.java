package com.example.clip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "disbursement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long disbursementId;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_clip_user_id", nullable = false)
    private ClipUser clipUser;

    @Basic(optional = false)
    @Column(name = "date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

}