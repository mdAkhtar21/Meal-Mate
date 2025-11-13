package com.example.mealmate.presentation.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.local.AddRecipe.RecipeTableEntity
import com.example.mealmate.domain.model.MealPlan
import com.example.mealmate.domain.usecase.RecipeUsecase.GetRecipeByIdUsecase
import com.example.mealmate.domain.usecase.mealPlanUsecase.DeleteMealPlanUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.GetMealPlansUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.UpdateMealPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ShowMealPlan(
    val recipe: RecipeTableEntity?,
    val mealPlan: MealPlan,
)

@HiltViewModel
class PlanScreenViewModel @Inject constructor(
    private val getMealPlansUseCase: GetMealPlansUseCase,
    private val getRecipeByIdUsecase: GetRecipeByIdUsecase,
    private val deleteMealPlanUseCase : DeleteMealPlanUseCase,
    private val updateMealPlanUseCase: UpdateMealPlanUseCase
) : ViewModel() {

    private val _mealPlans = MutableStateFlow<List<ShowMealPlan>>(emptyList())
    val mealPlans: StateFlow<List<ShowMealPlan>> = _mealPlans

    init {
        showList()
    }

    fun showList() {
        viewModelScope.launch {
            val mealPlansList = getMealPlansUseCase()
            val mappedList = mealPlansList.map { mealPlan ->
                val recipe = try {
                    getRecipeByIdUsecase(mealPlan.recipeId)
                } catch (e: Exception) {
                    null
                }
                ShowMealPlan(
                    recipe = recipe,
                    mealPlan = mealPlan
                )
            }
            _mealPlans.value = mappedList
        }
    }

    fun UpdateMealPlan(id:Long,newDay:String,newMealType:String){
        viewModelScope.launch {
            val currentPlan=_mealPlans.value.find { it.mealPlan.id==id }
            if(currentPlan!=null){
                val updatePlan=currentPlan.mealPlan.copy(
                    day=newDay,
                    mealType = newMealType
                )
                updateMealPlanUseCase(updatePlan)
                showList()
            }
        }
    }



    fun deleteMealPlan(id: Long) {
        viewModelScope.launch {
            deleteMealPlanUseCase(id)
            showList()
        }
    }
}
