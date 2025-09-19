package com.example.catfoodv1.service.product;

import com.example.catfoodv1.model.dto.bussines.CommonDto;
import com.example.catfoodv1.model.entity.product.Ingredient;
import com.example.catfoodv1.repo.product.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public CommonDto save(CommonDto dto) {
        Ingredient ingredient = ingredientRepository.save(new Ingredient(null, dto.getCode(), dto.getName()));
        return new CommonDto(ingredient.getId(),ingredient.getIngredientCode(), ingredient.getIngredientName());
    }

}
