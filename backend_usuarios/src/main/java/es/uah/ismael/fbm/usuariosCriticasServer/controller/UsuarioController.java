package es.uah.ismael.fbm.usuariosCriticasServer.controller;

import es.uah.ismael.fbm.usuariosCriticasServer.model.Usuario;
import es.uah.ismael.fbm.usuariosCriticasServer.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @Value("${server.version}")
    private String serverVersion;

    @GetMapping("/usuarios")
    public List<Usuario> buscarTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodos();

        if ("v2".equals(serverVersion)) {
            // Seleccionar la mitad de los usuarios de manera aleatoria
            Collections.shuffle(usuarios);
            int mitad = usuarios.size() / 2;
            usuarios = usuarios.subList(0, mitad);
            usuarios.forEach(u -> {
                u.setNombre(u.getNombre().toUpperCase());
                u.setCorreo(u.getCorreo().toUpperCase());
            });
        }

        return usuarios;
    }

    @GetMapping("/usuarios/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable("id") Integer idUsuario) {
        return usuarioService.buscarUsuarioPorId(idUsuario);
    }

    @GetMapping("/usuarios/nombre/{nombre}")
    public Usuario buscarUsuarioPorNombre(@PathVariable("nombre") String nombre) {
        return usuarioService.buscarUsuarioPorNombre(nombre);
    }

    @GetMapping("/usuarios/correo/{correo}")
    public Usuario buscarUsuarioPorCorreo(@PathVariable("correo") String correo) {
        return usuarioService.buscarUsuarioPorCorreo(correo);
    }

    @GetMapping("/usuarios/login/nombre/{nombre}/{clave}")
    public Usuario buscarUsuarioPorNombreYClave(@PathVariable("nombre") String nombre, @PathVariable("clave") String clave) {
        return usuarioService.buscarUsuarioPorNombreYClave(nombre, clave);
    }

    @GetMapping("/usuarios/login/correo/{correo}/{clave}")
    public Usuario buscarUsuarioPorCorreoYClave(@PathVariable("correo") String correo, @PathVariable("clave") String clave) {
        return usuarioService.buscarUsuarioPorCorreoYClave(correo, clave);
    }

    @PostMapping("/usuarios")
    public void guardarUsuario(@RequestBody Usuario usuario) {
        if(usuario!=null && usuario.getIdUsuario() < 1) {
            usuario.setIdUsuario(null);
        }
        usuarioService.guardarUsuario(usuario);
    }

    @PutMapping("/usuarios")
    public void actualizarUsuario(@RequestBody Usuario usuario) {
        usuarioService.actualizarUsuario(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public void eliminarUsuario(@PathVariable("id") Integer idUsuario) {
        usuarioService.eliminarUsuario(idUsuario);
    }

}
