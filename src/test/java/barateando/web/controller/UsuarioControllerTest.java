package barateando.web.controller;

import barateando.service.UsuarioService;
import barateando.web.dto.UsuarioDto;
import barateando.web.dto.UsuarioRequest;
import barateando.web.dto.UsuarioUpdate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioService usuarioService;

    @Test
    void getDevuelveUsuarioCuandoExiste() throws Exception{
        UsuarioDto dtoFalso = new UsuarioDto(1L, "Andrés", "andres@mail.com");
        Mockito.when(usuarioService.buscarPorId(1l)).thenReturn(dtoFalso);
        mockMvc.perform(get("/barateando/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Andrés"));
    }

    @Test
    void getAllUsuarios() throws Exception{
        UsuarioDto usuario1 = new UsuarioDto(1L, "Andrés", "andres@mail.com");
        UsuarioDto usuario2 = new UsuarioDto(2L, "Marta", "marta@mail.com");

        Mockito.when(usuarioService.listar()).thenReturn(List.of(usuario1,usuario2));
        mockMvc.perform(get("/barateando/usuarios"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].nombre").value("Andrés"))
               .andExpect(jsonPath("$[1].nombre").value("Marta"));

    }

    @Test
    void crearUsuario() throws Exception{
        UsuarioRequest requestFalso = new UsuarioRequest("Andrés", "andres@mail.com");
        UsuarioDto dtoCreado = new UsuarioDto(1L, "Andrés", "andres@mail.com");
        Mockito.when(usuarioService.crear(requestFalso)).thenReturn(dtoCreado);
        mockMvc.perform(post("/barateando/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestFalso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Andrés"));
    }

    @Test
    void updateUsuario() throws Exception{
        UsuarioUpdate updateFalso = new UsuarioUpdate("Andrés Zuluaga", "andres.nuevo@mail.com");
        UsuarioDto dtoActualizado = new UsuarioDto(1L, "Andrés Zuluaga", "andres.nuevo@mail.com");
        Mockito.when(usuarioService.update(1L,updateFalso)).thenReturn(dtoActualizado);
        mockMvc.perform(put("/barateando/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFalso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Andrés Zuluaga"));

    }

    @Test
    void deleteUsuario() throws Exception{
        mockMvc.perform(delete("/barateando/usuarios/1"))
                .andExpect(status().isOk());
        Mockito.verify(usuarioService).eliminar(1L);

    }

}
