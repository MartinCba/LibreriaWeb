package egg.edu.libreria.Repositorios;

import egg.edu.libreria.Entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {

    @Query("SELECT d FROM Cliente d WHERE d.documento = :documento")
    public Cliente buscarClientePorDocumento(@Param("documento") Long documento);

    @Query("SELECT c FROM Cliente c WHERE c.nombre = :nombre")
    public Cliente buscarClientePorNombre(@Param("nombre") String nombre);

    @Query("SELECT b FROM Cliente b WHERE b.apellido = :apellido")
    public Cliente buscarClientePorApellido(@Param("apellido") String apellido);

    @Query("SELECT a FROM Cliente a WHERE a.activo = true ")
    public List<Cliente> buscarActivos();

    public Cliente getById(Long documento);

}
