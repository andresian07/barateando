package barateando.web.controller;

import barateando.service.GastoService;
import barateando.web.dto.GastoDto;
import barateando.web.dto.GastoRequest;
import barateando.web.dto.GastoUpdate;
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

@WebMvcTest(GastoController.class)
public class GastoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GastoService gastoService;

    @Test
    void getDevuelveElGastoCuandoExiste() throws Exception {
        GastoDto dtoFalso = new GastoDto(1L, new BigDecimal("100"), "Cena", LocalDate.now(), "Ana");

        Mockito.when(gastoService.get(1L)).thenReturn(dtoFalso);

        mockMvc.perform(get("/barateando/gastos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(100));
    }

    @Test
    void getAllGastos() throws Exception {
        GastoDto dto1 = new GastoDto(1L, new BigDecimal("100"), "Cena", LocalDate.now(), "Ana");
        GastoDto dto2 = new GastoDto(2L, new BigDecimal("50"), "Taxi", LocalDate.now(), "Juan");

        Mockito.when(gastoService.getAll()).thenReturn(List.of(dto1,dto2));

        mockMvc.perform(get("/barateando/gastos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].monto").value(100))
                .andExpect(jsonPath("$[1].monto").value(50));

    }

    @Test
    void crearGasto() throws Exception {
      GastoRequest  requestFalso = new GastoRequest(new BigDecimal("200"), "Almuerzo", LocalDate.now(), 1L, 1L);
      GastoDto dtoCreado = new GastoDto(3L, new BigDecimal("200"), "Almuerzo", LocalDate.now(), "Ana");

        Mockito.when(gastoService.create(requestFalso)).thenReturn(dtoCreado);
      mockMvc.perform(post("/barateando/gastos")
              .contentType
                      (MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(requestFalso)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.monto").value(200));


    }

    @Test
    void deleteEliminaElGasto() throws Exception {
        mockMvc.perform(delete("/barateando/gastos/1"))
                .andExpect(status().isOk());

        Mockito.verify(gastoService).delete(1L);
    }


    @Test
    void updateActualizaElGasto() throws Exception{
        GastoUpdate gastoUpdate = new GastoUpdate(new BigDecimal(200),"voleto avion", LocalDate.now());
        GastoDto gastoDto = new GastoDto(1L,new BigDecimal(200),"voleto avion", LocalDate.now(), "andres");

        Mockito.when(gastoService.update(1L, gastoUpdate)).thenReturn(gastoDto);
        mockMvc.perform(put("/barateando/gastos/1")
                .contentType
                        (MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gastoUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monto").value(200));



    }

}
