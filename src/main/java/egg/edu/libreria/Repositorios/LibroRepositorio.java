package egg.edu.libreria.Repositorios;

import egg.edu.libreria.Entidades.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {

    @Query("SELECT a FROM Libro a WHERE a.isbn = :isbn")
    public Libro buscarLibroPorIsbn(@Param("isbn") Long isbn);

    @Query("SELECT c FROM Libro c WHERE c.titulo = :titulo")
    public Libro buscarLibro(@Param("titulo") String titulo);

    @Query("SELECT b FROM Libro b WHERE b.activo = true ")
    public List<Libro> buscarActivos();

    public Libro getById(Long bn);

    public Optional<Libro> findById(Long bnLibro);

}
