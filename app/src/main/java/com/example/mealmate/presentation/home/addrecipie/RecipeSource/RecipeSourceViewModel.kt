package com.example.mealmate.presentation.home.addrecipie.RecipeSource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.data.Mapper.toDomain
import com.example.mealmate.data.local.RecipeSource.RecipeSourceEntity
import com.example.mealmate.domain.model.RecipeSource
import com.example.mealmate.domain.usecase.RecipeSource.AddRecipeSourceUseCase
import com.example.mealmate.domain.usecase.RecipeSource.DeleteRecipeSourceUseCase
import com.example.mealmate.domain.usecase.RecipeSource.GetSourcesForRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeSourceViewModel @Inject constructor(
    private val addSourceUseCase: AddRecipeSourceUseCase,
    private val deleteSourceUseCase: DeleteRecipeSourceUseCase,
    private val getSourcesForRecipeUseCase: GetSourcesForRecipeUseCase
) : ViewModel() {

    private val _sources = MutableStateFlow<List<RecipeSource>>(emptyList())
    val sources: StateFlow<List<RecipeSource>> = _sources

    fun loadSources(tempKey: Long) {
        viewModelScope.launch {
            _sources.value = getSourcesForRecipeUseCase.getByTempKey(tempKey)
        }
    }

    fun addSource(tempKey: Long, name: String, url: String) {
        viewModelScope.launch {
            val source = RecipeSourceEntity(
                sourceId = 0L,
                recipeId =null ,
                tempKey = tempKey,
                name = name,
                url = url
            )
            addSourceUseCase(source.toDomain())
            loadSources(tempKey)
        }
    }
    fun deleteSource(tempKey: Long, sourceId: Long) {
        viewModelScope.launch {
            deleteSourceUseCase(tempKey,sourceId)
            loadSources(tempKey)
        }
    }
}