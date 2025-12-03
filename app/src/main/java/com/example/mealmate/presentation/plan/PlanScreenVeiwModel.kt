package com.example.mealmate.presentation.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.Mapper.toDomain
import com.example.mealmate.data.Mapper.toEntity
import com.example.mealmate.domain.model.MealPlan
import com.example.mealmate.domain.model.RecipeTable
import com.example.mealmate.domain.usecase.LoginUserUseCase
import com.example.mealmate.domain.usecase.RecipeUsecase.GetRecipeByIdUsecase
import com.example.mealmate.domain.usecase.mealPlanUsecase.ClearMealPlansUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.DeleteMealPlanUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.GetMealPlansGroupedByDayUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.UpdateMealPlanUseCase
import com.example.mealmate.presentation.home.foryouscreen.StaticRecipeStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShowMealPlan(
    val recipe: RecipeTable?,
    val mealPlan: MealPlan
)
@HiltViewModel
class PlanScreenViewModel @Inject constructor(
    private val getRecipeByIdUsecase: GetRecipeByIdUsecase,
    private val deleteMealPlanUseCase: DeleteMealPlanUseCase,
    private val updateMealPlanUseCase: UpdateMealPlanUseCase,
    private val getMealPlansGroupedByDayUseCase: GetMealPlansGroupedByDayUseCase,
    private val clearAllMealPlansUseCase:ClearMealPlansUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _mealPlans = MutableStateFlow<Map<String, List<ShowMealPlan>>>(emptyMap())
    val mealPlans: StateFlow<Map<String, List<ShowMealPlan>>> = _mealPlans

    init {
        loadMealPlans()
    }

    fun loadMealPlans() {
        viewModelScope.launch {

            val userId = loginUserUseCase.getLoggedInUser().first().first

            getMealPlansGroupedByDayUseCase().collect { grouped ->

                val filteredByUser = grouped.mapValues { entry ->
                    entry.value.filter { it.userId == userId }
                }.filter { it.value.isNotEmpty() }

                val finalMapped = filteredByUser.mapValues { entry ->
                    entry.value.mapNotNull { mealPlan ->

                        var recipe = try {
                            getRecipeByIdUsecase(mealPlan.recipeId)?.toEntity()
                        } catch (e: Exception) { null }
                        if (recipe == null) {
                            val staticRecipe = StaticRecipeStore.staticRecipes
                                .find { it.recipe.recipeId == mealPlan.recipeId && it.recipe.userId == userId }
                            recipe = staticRecipe?.recipe?.toEntity()
                        }
                        if (recipe == null || recipe.userId != userId) return@mapNotNull null

                        ShowMealPlan(
                            recipe = recipe.toDomain(),
                            mealPlan = mealPlan
                        )
                    }
                }.filter { it.value.isNotEmpty() }

                _mealPlans.value = finalMapped
            }
        }
    }



    fun updateMealPlan(id: Long, newDay: String, newMealType: String) {
        viewModelScope.launch {
            val allPlans = _mealPlans.value.values.flatten()

            val currentPlan = allPlans.find { it.mealPlan.id == id } ?: return@launch

            val updated = currentPlan.mealPlan.copy(
                day = newDay,
                mealType = newMealType
            )
            updateMealPlanUseCase(updated)
        }
    }

    fun deleteMealPlan(id: Long) {
        viewModelScope.launch {
            deleteMealPlanUseCase(id)
        }
    }

    fun clearAllMealPlan(){
        viewModelScope.launch {
            clearAllMealPlansUseCase()
        }
    }
}




