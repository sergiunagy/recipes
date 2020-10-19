package playground.springframework.recipes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import playground.springframework.recipes.commands.UnitOfMeasureCommand;
import playground.springframework.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import playground.springframework.recipes.domain.UnitOfMeasure;
import playground.springframework.recipes.domain.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository uomRepo;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    UnitOfMeasureService unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl(uomRepo, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUOMs() {
        //given
        Set<UnitOfMeasure> unitsOfMeasure = new HashSet<>();
        unitsOfMeasure.add(UnitOfMeasure.builder().id(1L).build());
        unitsOfMeasure.add(UnitOfMeasure.builder().id(2L).build());

        when(uomRepo.findAll()).thenReturn(unitsOfMeasure);

        //when
        Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUOMs();

        //then
        assertEquals(2, commands.size());
        verify(uomRepo, times(1)).findAll();


    }
}