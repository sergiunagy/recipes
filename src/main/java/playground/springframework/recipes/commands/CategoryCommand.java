package playground.springframework.recipes.commands;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;
}
