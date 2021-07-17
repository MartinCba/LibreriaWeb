package egg.edu.libreria.Servicios;

import egg.edu.libreria.Entidades.Cliente;
import egg.edu.libreria.Errores.ErrorServicio;
import egg.edu.libreria.Repositorios.ClienteRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Cliente guardar(Long documento, String nombre, String apellido, String domicilio, String telefono) throws ErrorServicio {
        validar(documento, nombre, apellido, domicilio, telefono);

        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDomicilio(domicilio);
        cliente.setActivo(true);
        cliente.setCreado(new Date());

        return clienteRepositorio.save(cliente);
    }

    public Cliente alta(Long documento) {

        Cliente cliente = clienteRepositorio.getById(documento);

        cliente.setActivo(true);
        return clienteRepositorio.save(cliente);
    }

    public Cliente baja(Long documento) {

        Cliente cliente = clienteRepositorio.getById(documento);

        cliente.setActivo(false);
        return clienteRepositorio.save(cliente);
    }

    public List<Cliente> listarActivos() {
        return clienteRepositorio.buscarActivos();
    }

    public List<Cliente> listarTodos() {
        return clienteRepositorio.findAll();
    }

    public void validar(Long documento, String nombre, String apellido, String domicilio, String telefono) throws ErrorServicio {

        if (documento == null) {
            throw new ErrorServicio("El documento del cliente no puede ser nulo");
        }
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new ErrorServicio("El nombre del cliente no puede ser nulo");
        }
        if (apellido == null || apellido.isEmpty() || apellido.contains("  ")) {
            throw new ErrorServicio("El apellido del cliente no puede ser nulo");
        }
        if (domicilio == null || domicilio.isEmpty() || domicilio.contains("  ")) {
            throw new ErrorServicio("El domicilio del cliente no puede ser nulo");
        }
        if (telefono == null || telefono.isEmpty() || telefono.contains("  ")) {
            throw new ErrorServicio("El telefono del cliente no puede ser nulo");
        }

    }

}
