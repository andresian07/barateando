package barateando.web.exception;

import barateando.service.GastoService;
import barateando.web.controller.GastoController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GastoController.class)
public class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GastoService gastoService;

    @Test
    void devuelve404CuandoElRecursoNoExiste() throws Exception {
        Mockito.when(gastoService.get(1L)).thenThrow(new RecursoNoEncontradoException("no se encontro el gasto"));
        mockMvc.perform(get("/barateando/gastos/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("no se encontro el gasto"));

    }
}
