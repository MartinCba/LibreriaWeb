package egg.edu.libreria.Servicios;

import egg.edu.libreria.Entidades.Libro;
import egg.edu.libreria.Errores.ErrorServicio;
import egg.edu.libreria.Repositorios.LibroRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public Libro guardar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        validar(isbn, titulo, anio, ejemplares);

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setActivo(true);
        libro.setCreado(new Date());

        return libroRepositorio.save(libro);
    }

    @Transactional
    public Libro alta(Long isbn) {

        Libro libro = libroRepositorio.getById(isbn);

        libro.setActivo(true);
        return libroRepositorio.save(libro);
    }

    @Transactional
    public Libro baja(Long isbn) {

        Libro libro = libroRepositorio.getById(isbn);

        libro.setActivo(false);
        return libroRepositorio.save(libro);
    }

    public List<Libro> listarActivos() {
        return libroRepositorio.buscarActivos();
    }

    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        if (isbn == null) {
            throw new ErrorServicio("El isbn del libro no puede ser nulo");
        }
        if (titulo == null || titulo.isEmpty() || titulo.contains("  ")) {
            throw new ErrorServicio("El titulo del libro no puede ser nulo");
        }
        if (anio == null) {
            throw new ErrorServicio("El año del libro no puede ser nulo");
        }
        if (ejemplares == null) {
            throw new ErrorServicio("La cantidad de ejemplares del libro no pueden ser nula");
        }

    }

}
