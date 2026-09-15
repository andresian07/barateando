package barateando.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "presupuesto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresupuestoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_presupuesto")
    private Long id;

    @Column(name = "monto_presupuesto")
    @Positive
    private BigDecimal montoPresupuesto;

    @OneToOne
    @JoinColumn(name = "id_viaje", unique = true
        )
    private ViajeEntity viaje;

}
