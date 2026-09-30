package barateando.web.controller;

import barateando.service.ViajeService;
import barateando.web.dto.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViajeController.class)
public class ViajeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ViajeService viajeService;

    @Test
    void getDevuelveElViajeCuandoExiste() throws Exception {
        ViajeDto dtoFalso = new ViajeDto(1L, "Vacaciones", "Bariloche", LocalDate.now(), LocalDate.now().plusDays(5), List.of());

        Mockito.when(viajeService.get(1L)).thenReturn(dtoFalso);

        mockMvc.perform(get("/barateando/viajes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Vacaciones"));
    }

    @Test
    void getAllViajes() throws Exception {
        ViajeDto viaje1 = new ViajeDto(1L, "Vacaciones", "Bariloche", LocalDate.now(), LocalDate.now().plusDays(5), List.of());
        ViajeDto viaje2 = new ViajeDto(2L, "Trabajo", "Buenos Aires", LocalDate.now(), LocalDate.now().plusDays(2), List.of());

        Mockito.when(viajeService.getAll()).thenReturn(List.of(viaje1, viaje2));

        mockMvc.perform(get("/barateando/viajes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Vacaciones"))
                .andExpect(jsonPath("$[1].nombre").value("Trabajo"));
    }

    @Test
    void crearViaje() throws Exception {
        ViajeRequest requestFalso = new ViajeRequest("Vacaciones", "Bariloche", LocalDate.now(), LocalDate.now().plusDays(5));
        ViajeDto dtoCreado = new ViajeDto(1L, "Vacaciones", "Bariloche", LocalDate.now(), LocalDate.now().plusDays(5), List.of());

        Mockito.when(viajeService.crear(requestFalso)).thenReturn(dtoCreado);

        mockMvc.perform(post("/barateando/viajes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestFalso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Vacaciones"));
    }

    @Test
    void updateViaje() throws Exception {
        ViajeUpdate updateFalso = new ViajeUpdate("Vacaciones actualizadas", "Ushuaia", LocalDate.now(), LocalDate.now().plusDays(7));
        ViajeDto dtoActualizado = new ViajeDto(1L, "Vacaciones actualizadas", "Ushuaia", LocalDate.now(), LocalDate.now().plusDays(7), List.of());

        Mockito.when(viajeService.update(1L, updateFalso)).thenReturn(dtoActualizado);

        mockMvc.perform(put("/barateando/viajes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFalso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Vacaciones actualizadas"));
    }

    @Test
    void deleteViaje() throws Exception {
        mockMvc.perform(delete("/barateando/viajes/1"))
                .andExpect(status().isOk());

        Mockito.verify(viajeService).delete(1L);
    }

    @Test
    void agregarParticipanteAlViaje() throws Exception {
        UsuarioDto participante = new UsuarioDto(2L, "Marta", "marta@mail.com");
        ViajeDto dtoConParticipante = new ViajeDto(1L, "Vacaciones", "Bariloche", LocalDate.now(), LocalDate.now().plusDays(5), List.of(participante));
        Mockito.when(viajeService.agregarParticipante(1L,2L)).thenReturn(dtoConParticipante);
        mockMvc.perform(post("/barateando/viajes/1/participantes/2"))
                .andExpect(status().isOk());

    }

    @Test
    void getResumenViaje() throws Exception {
        ResumenViaje resumenFalso = new ResumenViaje(new BigDecimal("1000"), new BigDecimal("300"), new BigDecimal("700"));
        Mockito.when(viajeService.getResumen(2L)).thenReturn(resumenFalso);
        mockMvc.perform(get("/barateando/viajes/2/resumen"))
                .andExpect(status().isOk());
    }

    @Test
    void gastoPorViaje() throws Exception {
        GastoDto gasto1 = new GastoDto(1L, new BigDecimal("100"), "Cena", LocalDate.now(), "Ana");
        GastoDto gasto2 = new GastoDto(2L, new BigDecimal("50"), "Taxi", LocalDate.now(), "Juan");
        Mockito.when(viajeService.getGastosPorViaje(1L)).thenReturn(List.of(gasto1,gasto2));
        mockMvc.perform(get("/barateando/viajes/1/gastos"))
                .andExpect(status().isOk());
    }
}