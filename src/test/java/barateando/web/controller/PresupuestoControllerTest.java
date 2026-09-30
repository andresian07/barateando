package barateando.web.controller;

import barateando.service.PresupuestoService;
import barateando.web.dto.PresupuestoDto;
import barateando.web.dto.PresupuestoRequest;
import barateando.web.dto.PresupuestoUpdate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PresupuestoController.class)
public class PresupuestoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PresupuestoService presupuestoService;

    @Test
    void getDevuelveElPresupuestoCuandoExiste() throws Exception {
        PresupuestoDto dtoFalso = new PresupuestoDto(1L, new BigDecimal("1000"), 1L);

        Mockito.when(presupuestoService.get(1L)).thenReturn(dtoFalso);

        mockMvc.perform(get("/barateando/presupuestos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoPresupuesto").value(1000));
    }

    @Test
    void getAllPresupuestos() throws Exception {
        PresupuestoDto presupuesto1 = new PresupuestoDto(1L, new BigDecimal("1000"), 1L);
        PresupuestoDto presupuesto2 = new PresupuestoDto(2L, new BigDecimal("500"), 2L);

        Mockito.when(presupuestoService.getAll()).thenReturn(List.of(presupuesto1, presupuesto2));

        mockMvc.perform(get("/barateando/presupuestos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].montoPresupuesto").value(1000))
                .andExpect(jsonPath("$[1].montoPresupuesto").value(500));
    }

    @Test
    void crearPresupuesto() throws Exception {
        PresupuestoRequest requestFalso = new PresupuestoRequest(new BigDecimal("1000"), 1L);
        PresupuestoDto dtoCreado = new PresupuestoDto(1L, new BigDecimal("1000"), 1L);

        Mockito.when(presupuestoService.crear(requestFalso)).thenReturn(dtoCreado);

        mockMvc.perform(post("/barateando/presupuestos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestFalso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoPresupuesto").value(1000));
    }

    @Test
    void updatePresupuesto() throws Exception {
        PresupuestoUpdate updateFalso = new PresupuestoUpdate(new BigDecimal("1500"));
        PresupuestoDto dtoActualizado = new PresupuestoDto(1L, new BigDecimal("1500"), 1L);

        Mockito.when(presupuestoService.update(1L, updateFalso)).thenReturn(dtoActualizado);

        mockMvc.perform(put("/barateando/presupuestos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFalso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoPresupuesto").value(1500));
    }

    @Test
    void deletePresupuesto() throws Exception {
        mockMvc.perform(delete("/barateando/presupuestos/1"))
                .andExpect(status().isOk());

        Mockito.verify(presupuestoService).delete(1L);
    }
}