package com.aman.payment.auth.model;

import com.aman.payment.core.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subServicePriceTier")
public class SubServicePriceTier  extends DateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    private String name;

    @NotNull
    private Double fees;

    private String description;

    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "subServiceFk", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private SubService subServiceFk;

}
