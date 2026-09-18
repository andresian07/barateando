package barateando.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "viaje")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViajeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id_viaje")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "destino", nullable = false, length = 120)
    private String destino;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    // anotacion para saber que es una union de muchos a muchos
    @ManyToMany
    // anotacion para la unicion de tablas
    @JoinTable(
            // nombre de como se va llaamr la tabla intermedia
            name = "usuario_viaje",
            // nombre de la union de columna de esta direccion
            joinColumns = @JoinColumn(name = "id_viaje"),
            //nombre de columna a la cual se esta haciendo la union
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    // aqui se trae algo asi como la lista de usuarios que pertenecen a ese viaje

    private List<UsuarioEntity> participantes = new ArrayList<>();


}
