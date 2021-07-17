package egg.edu.libreria.Servicios;

import egg.edu.libreria.Entidades.Autor;
import egg.edu.libreria.Errores.ErrorServicio;
import egg.edu.libreria.Repositorios.AutorRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public Autor guardar(String nombre) throws ErrorServicio {
        validar(nombre);

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setActivo(true);
        autor.setCreado(new Date());

        return autorRepositorio.save(autor);
    }

    @Transactional
    public Autor alta(String id) {

        Autor autor = autorRepositorio.getById(id);

        autor.setActivo(true);
        return autorRepositorio.save(autor);
    }

    @Transactional
    public Autor baja(String id) {

        Autor autor = autorRepositorio.getById(id);

        autor.setActivo(false);
        return autorRepositorio.save(autor);
    }

    public List<Autor> listarActivos() {
        return autorRepositorio.buscarActivos();
    }

    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }

    public void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new ErrorServicio("El nombre del autor no puede ser nulo");
        }
    }

}
