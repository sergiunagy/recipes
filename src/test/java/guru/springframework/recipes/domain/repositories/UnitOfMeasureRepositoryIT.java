package guru.springframework.recipes.domain.repositories;

import guru.springframework.recipes.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() throws Exception{
    }

    @Test
    @DirtiesContext
    void findByDescription() throws Exception{
        Optional<UnitOfMeasure> uom= unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", uom.get().getDescription());
    }
    @Test
    void findByDescriptionTbsp() throws Exception{
        Optional<UnitOfMeasure> uom= unitOfMeasureRepository.findByDescription("Tablespoon");

        assertEquals("Tablespoon", uom.get().getDescription());
    }
}