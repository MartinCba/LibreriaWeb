package egg.edu.libreria.Repositorios;

import egg.edu.libreria.Entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {

    @Query("SELECT c FROM Prestamo c WHERE c.Cliente.id = :id")
    public Prestamo buscarPrestamoPorIdCliente(@Param("id") String id);

    @Query("SELECT b FROM Prestamo b WHERE b.Cliente.nombre = :nombre")
    public Prestamo buscarPrestamoPorNombreCliente(@Param("nombre") String nombre);

    @Query("SELECT a FROM Prestamo a WHERE a.activo = true ")
    public List<Prestamo> buscarActivos();

    public Prestamo getById(Long idCliente);

}
