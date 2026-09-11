package barateando;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Ejercicio {

    static class Compra {
        BigDecimal precio;
        Compra(String precio) { this.precio = new BigDecimal(precio); }
        BigDecimal getPrecio() { return precio; }
    }

    public static void main(String[] args) {
        // Caso 1: usuario CON limite
        Optional<BigDecimal> limiteConfigurado = Optional.of(new BigDecimal("200"));
        List<Compra> compras1 = List.of(new Compra("50"), new Compra("30"));
        System.out.println("Disponible (con limite): " + disponible(limiteConfigurado, compras1));
        // Esperado: 120  (200 - 80)

        // Caso 2: usuario SIN limite
        Optional<BigDecimal> sinLimite = Optional.empty();
        List<Compra> compras2 = List.of(new Compra("50"), new Compra("30"));
        System.out.println("Disponible (sin limite): " + disponible(sinLimite, compras2));
        // Esperado: -80  (0 - 80)
    }

    static BigDecimal disponible(Optional<BigDecimal> limite, List<Compra> compras) {
        // TODO 1: si "limite" tiene algo adentro, usalo. Si esta vacio, usar BigDecimal.ZERO.
        //         (usa if/else con .isPresent() y .get(), como en el ejemplo de ViajeService)
        BigDecimal limiteFinal = limite.map(valor -> valor)
                .orElse(BigDecimal.ZERO);// <-- reemplazar

        // TODO 2: sumar el precio de todas las compras con un for, empezando en BigDecimal.ZERO
        BigDecimal totalComprado = BigDecimal.ZERO;
        for(Compra compra : compras){
           totalComprado = totalComprado.add(compra.getPrecio());
        }

        BigDecimal diferencia = limiteFinal.subtract(totalComprado);
        // <-- reemplazar

        // TODO 3: devolver limiteFinal - totalComprado (con el metodo que corresponde, no con "-")
        return diferencia;
    }
}