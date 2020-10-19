package playground.springframework.recipes.services;

import playground.springframework.recipes.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUOMs();
}
