package br.com.rent.tools.api.dto;

import br.com.rent.tools.api.model.Available;
import br.com.rent.tools.api.model.Category;
import br.com.rent.tools.api.model.Condition;
import br.com.rent.tools.api.model.Tool;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ToolDto(Long id, String name, Double price, Category category, Integer minimumRentalDays, Available available, Condition condition) {

    public ToolDto(Tool tool) {

        this(tool.getId(), tool.getName(), tool.getPrice(), tool.getCategory(), tool.getMinimumRentalDays(), tool.getAvailable(), tool.getCondition());

    }

}
