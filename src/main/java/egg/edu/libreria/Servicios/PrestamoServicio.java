package egg.edu.libreria.Servicios;

import egg.edu.libreria.Entidades.Cliente;
import egg.edu.libreria.Entidades.Libro;
import egg.edu.libreria.Entidades.Prestamo;
import egg.edu.libreria.Errores.ErrorServicio;
import egg.edu.libreria.Repositorios.ClienteRepositorio;
import egg.edu.libreria.Repositorios.LibroRepositorio;
import egg.edu.libreria.Repositorios.PrestamoRepositorio;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional
    public Prestamo altaPrestamo(Long idCliente, Long isbnLibro) throws ErrorServicio {
        validar(idCliente);

        Prestamo prestamo = new Prestamo();
        Cliente cliente = clienteRepositorio.getById(idCliente);
        if (cliente.isActivo()) {
            Optional<Libro> respuesta = libroRepositorio.findById(isbnLibro);
            if (respuesta.isPresent()) {
                Libro libro = respuesta.get();
                prestamo.setFecha(new Date());
                prestamo.setDevolucion(sumarDiasFecha(new Date(), 7));
                prestamo.setLibro(libro);
                prestamo.setCliente(cliente);
                prestamo.setPrestamoActivo(true);
            } else {
                throw new ErrorServicio("No existe un libro vinculado a el Isbn ingresado");
            }
        } else {
            throw new ErrorServicio("No existe un cliente activo con ese id");
        }
        return prestamoRepositorio.save(prestamo);
    }

    @Transactional
    public void bajaPrestamo(String idPrestamo, Long isbnLibro) throws ErrorServicio {
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(idPrestamo);
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            if (prestamo.getLibro().getIsbn().equals(isbnLibro)) {                       //revisar metodo bajaPrestamo
                Date fechaEntrega = new Date();
                if (prestamo.getDevolucion().getTime() < fechaEntrega.getTime()) {
                    prestamo.setDevolucion(new Date());
                    prestamo.setMulta(500.0);
                    System.out.println("Su prestamo ya habia expirado, debe abonar una multa de : " + prestamo.getMulta());
                    prestamo.setPrestamoActivo(false);
                } else {
                    prestamo.setDevolucion(new Date());
                    prestamo.setPrestamoActivo(false);
                }
                prestamoRepositorio.save(prestamo);
            }
        } else {
            throw new ErrorServicio("No existe un prestamo con el id ingresado");
        }
    }

    @Transactional
    public Prestamo altaPrestamoTitulo(Long idCliente, String tituloLibro) throws ErrorServicio {
        validar(idCliente);

        Prestamo prestamo = new Prestamo();
        Cliente cliente = clienteRepositorio.getById(idCliente);
        if (cliente.isActivo()) {
            Libro libro = libroRepositorio.buscarLibro(tituloLibro);
            if (libro.getTitulo().equals(tituloLibro)) {
                prestamo.setFecha(new Date());
                prestamo.setDevolucion(sumarDiasFecha(new Date(), 7));
                prestamo.setCliente(cliente);
                prestamo.setLibro(libro);
                prestamo.setPrestamoActivo(true);
            } else {
                throw new ErrorServicio("No existe un libro con el nombre ingresado");
            }
        } else {
            throw new ErrorServicio("No existe un cliente activo con ese id");
        }
        return prestamoRepositorio.save(prestamo);
    }

    public Prestamo consultarPrestamo(Long idCliente) throws ErrorServicio {
        validar(idCliente);
        Prestamo prestamo = prestamoRepositorio.getById(idCliente);

        return prestamo;
    }

    public List<Prestamo> listarActivos() {
        return prestamoRepositorio.buscarActivos();
    }

    public List<Prestamo> listarTodos() {
        return prestamoRepositorio.findAll();
    }

    public Date sumarDiasFecha(Date fecha, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir

        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos		
    }

    public void validar(Long idCliente) throws ErrorServicio {

        if (idCliente == null) {
            throw new ErrorServicio("El documento del cliente no puede ser nulo");
        }
    }
}
