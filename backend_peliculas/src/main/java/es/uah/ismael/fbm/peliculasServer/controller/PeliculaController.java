package es.uah.ismael.fbm.peliculasServer.controller;

import es.uah.ismael.fbm.peliculasServer.model.Actor;
import es.uah.ismael.fbm.peliculasServer.model.Pelicula;
import es.uah.ismael.fbm.peliculasServer.service.IActorService;
import es.uah.ismael.fbm.peliculasServer.service.IPeliculaService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class PeliculaController {

    Logger log = org.slf4j.LoggerFactory.getLogger(PeliculaController.class);

    @Autowired
    private IPeliculaService peliculaService;

    @Autowired
    private IActorService actorService;

    @Value("${server.version}")
    private String serverVersion;

    @GetMapping("/peliculas")
    public List<Pelicula> buscarTodos() {
        List<Pelicula> peliculas = peliculaService.buscarTodas();
        if ("v2".equals(serverVersion)) {
            // Seleccionar la mitad de las películas de manera aleatoria
            Collections.shuffle(peliculas);
            int mitad = peliculas.size() / 2;
            peliculas = peliculas.subList(0, mitad);
            peliculas.forEach(p -> p.setTitulo(p.getTitulo().toUpperCase()));
        }
        return peliculas;
    }

    @GetMapping("/peliculas/{id}")
    public Pelicula buscarPeliculaPorId(@PathVariable("id") Integer id) {
        return peliculaService.buscarPeliculaPorId(id);
    }

    @GetMapping("/peliculas/tituloCompleto/{titulo}")
    public Pelicula buscarPeliculasPorTituloCompleto(@PathVariable("titulo") String titulo) {
        return peliculaService.buscarPeliculaPorTituloCompleto(titulo);
    }

    @GetMapping("/peliculas/titulo/{titulo}")
    public List<Pelicula> buscarPeliculasPorTitulo(@PathVariable("titulo") String titulo) {
        return peliculaService.buscarPeliculasPorTitulo(titulo);
    }

    @GetMapping("/peliculas/genero/{genero}")
    public List<Pelicula> buscarPeliculasPorGenero(@PathVariable("genero") String genero) {
        return peliculaService.buscarPeliculasPorGenero(genero);
    }

    @GetMapping("/peliculas/direccion/{direccion}")
    public List<Pelicula> buscarPeliculasPorDireccion(@PathVariable("direccion") String direccion) {
        return peliculaService.buscarPeliculasPorDireccion(direccion);
    }

    @GetMapping("/peliculas/anio/{anio1}/{anio2}")
    public List<Pelicula> buscarPeliculasPorAnio(@PathVariable("anio1") Integer anio1, @PathVariable("anio2") Integer anio2) {
        return peliculaService.buscarPeliculasPorAnio(anio1, anio2);
    }

    @GetMapping("/peliculas/actor/{idActor}")
    public List<Pelicula> buscarPeliculasPorActor(@PathVariable("idActor") Integer idActor) {
        return peliculaService.buscarPeliculasPorActor(idActor);
    }

    @GetMapping("/peliculas/titulobyid/{id}")
    public String buscarTituloPeliculaPorId(@PathVariable("id") Integer id) {
        return peliculaService.buscarTituloPeliculaPorId(id);
    }

    @PostMapping("/peliculas")
    public void guardarPelicula(@RequestBody Pelicula pelicula) {
        Pelicula saved = peliculaService.guardarPelicula(pelicula);
        if(pelicula.getActores() != null){
            pelicula.getActores().forEach(actor -> {
                actorService.asignarPelicula(actor.getIdActor(), saved);
            });
        }
    }

    @PutMapping("/peliculas")
    public void actualizarPelicula(@RequestBody Pelicula pelicula) {
        Pelicula peliculaBefore = peliculaService.buscarPeliculaPorId(pelicula.getIdPelicula());
        for (Actor actor : peliculaBefore.getActores()) {
            if (!pelicula.getActores().contains(actor)) {
                actorService.desasignarPelicula(actor.getIdActor(), peliculaBefore);
            }
        }
        for (Actor actor : pelicula.getActores()) {
            if (!peliculaBefore.getActores().contains(actor)) {
                actorService.asignarPelicula(actor.getIdActor(), peliculaBefore);
            }
        }
        peliculaService.actualizarPelicula(pelicula);
    }

    @DeleteMapping("/peliculas/{id}")
    public void eliminarPelicula(@PathVariable("id") Integer id) {
        peliculaService.eliminarPelicula(id);
    }

}
