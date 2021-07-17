package egg.edu.libreria.Repositorios;

import egg.edu.libreria.Entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    @Query("SELECT c FROM Autor c WHERE c.nombre = :nombre")
    public Autor buscarAutorPorNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a WHERE a.activo = true ")
    public List<Autor> buscarActivos();

}
