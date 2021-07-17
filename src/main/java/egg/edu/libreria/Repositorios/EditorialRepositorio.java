package egg.edu.libreria.Repositorios;

import egg.edu.libreria.Entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("SELECT c FROM Editorial c WHERE c.nombre = :nombre")
    public Editorial buscarEditorialPorNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Editorial a WHERE a.activo = true ")
    public List<Editorial> buscarActivos();

}
