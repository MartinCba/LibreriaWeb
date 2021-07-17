package egg.edu.libreria.Servicios;

import egg.edu.libreria.Entidades.Editorial;
import egg.edu.libreria.Errores.ErrorServicio;
import egg.edu.libreria.Repositorios.EditorialRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public Editorial guardar(String nombre) throws ErrorServicio {
        validar(nombre);

        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setActivo(true);
        editorial.setCreado(new Date());

        return editorialRepositorio.save(editorial);
    }

    @Transactional
    public Editorial alta(String id) {

        Editorial editorial = editorialRepositorio.getById(id);

        editorial.setActivo(true);
        return editorialRepositorio.save(editorial);
    }

    @Transactional
    public Editorial baja(String id) {

        Editorial editorial = editorialRepositorio.getById(id);

        editorial.setActivo(false);
        return editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarActivos() {
        return editorialRepositorio.buscarActivos();
    }

    public List<Editorial> listarTodos() {
        return editorialRepositorio.findAll();
    }

    public void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new ErrorServicio("El nombre de la editorial no puede ser nulo");
        }
    }

}
