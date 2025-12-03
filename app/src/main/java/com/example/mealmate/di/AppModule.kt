package com.example.mealmate.di

import android.content.Context
import androidx.room.Room
import com.example.mealmate.data.datastore.UserPreferences
import com.example.mealmate.data.local.AddRecipe.*
import com.example.mealmate.data.local.Ingredient.IngredientDao
import com.example.mealmate.data.local.Instruction.InstructionDao
import com.example.mealmate.data.local.MealPlan.MealPlanDao
import com.example.mealmate.data.local.RecipeSource.RecipeSourceDao
import com.example.mealmate.data.local.Auth.UserDao
import com.example.mealmate.data.local.UserDatabase
import com.example.mealmate.data.local.shoppinglist.ShoppingListDao
import com.example.mealmate.data.repository.*
import com.example.mealmate.domain.repository.*
import com.example.mealmate.domain.usecase.GetRecipesByUserUseCase
import com.example.mealmate.domain.usecase.IngredientUseCase.*
import com.example.mealmate.domain.usecase.InstructionUseCase.*
import com.example.mealmate.domain.usecase.LoginUserUseCase
import com.example.mealmate.domain.usecase.LogoutUserUseCase
import com.example.mealmate.domain.usecase.RecipeSource.*
import com.example.mealmate.domain.usecase.RecipeUsecase.*
import com.example.mealmate.domain.usecase.RegisterUserUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.DeleteAllShoppingListItem
import com.example.mealmate.domain.usecase.ShoppingListItem.DeleteShoppingListItemUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.GetAllShoppingListItemUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.UpdateShoppingListItemUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.ClearMealPlansUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.DeleteMealPlanUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.GetMealPlanForDayUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.GetMealPlansUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.InsertMealPlansUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.UpdateMealPlanUseCase
import com.example.mealmate.domain.usecase.ShoppingListItem.InsertShoppingListItemUseCase
import com.example.mealmate.domain.usecase.mealPlanUsecase.GetMealPlansGroupedByDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ─── Database ──────────────────────────────
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, "user_db")
            .fallbackToDestructiveMigration()
            .build()

    // ─── DAOs ──────────────────────────────────
    @Provides
    @Singleton
    fun provideUserDao(db: UserDatabase): UserDao = db.userDao()

    @Provides
    @Singleton
    fun provideRecipeDao(db: UserDatabase): RecipeDao = db.recipeDao()

    @Provides
    @Singleton
    fun provideIngredientDao(db: UserDatabase): IngredientDao = db.ingredientDao()

    @Provides
    @Singleton
    fun provideInstructionDao(db: UserDatabase): InstructionDao = db.instructionDao()

    @Provides
    @Singleton
    fun provideRecipeSourceDao(db: UserDatabase): RecipeSourceDao = db.recipeSourceDao()

    @Provides
    @Singleton
    fun provideMealPlanDao(db: UserDatabase): MealPlanDao = db.mealPlanDao()

    @Provides
    @Singleton
    fun provideShoppingListItems(db:UserDatabase):ShoppingListDao=db.shoppingListDao()

    // ─── Preferences ───────────────────────────
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences =
        UserPreferences(context)

    // ─── Repositories ─────────────────────────
    @Provides
    @Singleton
    fun provideLoginRepository(dao: UserDao, prefs: UserPreferences): LoginRepository =
        LoginRepositoryImpl(dao, prefs)

    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao, prefs: UserPreferences): UserRepository =
        UserRepositoryImpl(dao, prefs)

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDao: RecipeDao, ingredientDao: IngredientDao, instructionDao: InstructionDao, sourceDao: RecipeSourceDao): RecipeRepository =
        RecipeRepositoryImpl(recipeDao,ingredientDao,instructionDao,sourceDao)

    @Provides
    @Singleton
    fun provideIngredientRepository(dao: IngredientDao): IngredientRepository =
        IngredientRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideInstructionRepository(dao: InstructionDao): InstructionRepository =
        InstructionRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideRecipeSourceRepository(dao: RecipeSourceDao): RecipeSourceRepository =
        RecipeSourceRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideMealPlanRepository(dao: MealPlanDao): MealPlanRepository =
        MealPlanRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideShoppingListRepository(dao: ShoppingListDao):ShoppingListRepository=
        ShoppingListRepositoryImpl(dao)

    // ─── Use Cases ────────────────────────────
    // Login & Register
    @Provides
    @Singleton
    fun provideLoginUserUseCase(repository: LoginRepository): LoginUserUseCase =
        LoginUserUseCase(repository)

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(repository: UserRepository): RegisterUserUseCase =
        RegisterUserUseCase(repository)

    @Provides
    @Singleton
    fun provideLogoutUserUseCase(repository: LoginRepository): LogoutUserUseCase=
        LogoutUserUseCase(repository)
    // Recipe UseCases
    @Provides
    @Singleton
    fun provideInsertRecipeUseCase(repository: RecipeRepository): InsertRecipeUseCase =
        InsertRecipeUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllRecipesUseCase(repository: RecipeRepository): GetAllRecipesUseCase =
        GetAllRecipesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetRecipeDetailUseCase(repository: RecipeRepository): GetRecipeDetailUseCase =
        GetRecipeDetailUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteRecipeUseCase(repository: RecipeRepository): DeleteRecipeUseCase =
        DeleteRecipeUseCase(repository)

    @Provides
    @Singleton
    fun provideGetRecipesByUserUseCase(repository: RecipeRepository):GetRecipesByUserUseCase=
        GetRecipesByUserUseCase(repository)

    // Ingredient UseCases
    @Provides
    fun provideAddIngredientUseCase(repository: IngredientRepository): AddIngredientUseCase =
        AddIngredientUseCase(repository)

    @Provides
    fun provideDeleteIngredientUseCase(repository: IngredientRepository): DeleteIngredientUseCase =
        DeleteIngredientUseCase(repository)

    @Provides
    fun provideGetIngredientsForRecipeUseCase(repository: IngredientRepository): GetIngredientsForRecipeUseCase =
        GetIngredientsForRecipeUseCase(repository)

    // Instruction UseCases
    @Provides
    fun provideAddInstructionUseCase(repository: InstructionRepository): AddInstructionUseCase =
        AddInstructionUseCase(repository)

    @Provides
    fun provideDeleteInstructionUseCase(repository: InstructionRepository): DeleteInstructionUseCase =
        DeleteInstructionUseCase(repository)

    @Provides
    fun provideUpdateInstructionUseCase(repository: InstructionRepository): UpdateInstructionUseCase =
        UpdateInstructionUseCase(repository)

    @Provides
    fun provideGetInstructionsForRecipeUseCase(repository: InstructionRepository): GetInstructionsForRecipeUseCase =
        GetInstructionsForRecipeUseCase(repository)

    // RecipeSource UseCases


    @Provides
    fun provideAddRecipeSourceUseCase(repository: RecipeSourceRepository): AddRecipeSourceUseCase =
        AddRecipeSourceUseCase(repository)

    @Provides
    fun provideDeleteRecipeSourceUseCase(repository: RecipeSourceRepository): DeleteRecipeSourceUseCase =
        DeleteRecipeSourceUseCase(repository)

    @Provides
    fun provideGetRecipeSourcesUseCase(repository: RecipeSourceRepository): GetSourcesForRecipeUseCase =
        GetSourcesForRecipeUseCase(repository)


    // RecipeSource UseCases

    @Provides
    fun provideClearMealPlanUseCase(repository:MealPlanRepository):ClearMealPlansUseCase=
        ClearMealPlansUseCase(repository)


    @Provides
    fun provideGetMealForDayPlanUseCase(repository: MealPlanRepository):GetMealPlanForDayUseCase=
        GetMealPlanForDayUseCase(repository)

    @Provides
    @Singleton
    fun provideInsertMealPlansUseCase(repository: MealPlanRepository): InsertMealPlansUseCase =
        InsertMealPlansUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMealPlansUseCase(repository: MealPlanRepository): GetMealPlansUseCase =
        GetMealPlansUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteMealPlanUseCase(repository: MealPlanRepository): DeleteMealPlanUseCase =
        DeleteMealPlanUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateMealPlanUseCase(repository: MealPlanRepository):UpdateMealPlanUseCase=
        UpdateMealPlanUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMealPlansGroupedByDayUseCase(repository:MealPlanRepository): GetMealPlansGroupedByDayUseCase=
        GetMealPlansGroupedByDayUseCase(repository)

    // ShoppingListItem UseCases

    @Provides
    @Singleton
    fun provideInsertShoppingListItemUseCase(repository:ShoppingListRepository): InsertShoppingListItemUseCase =
        InsertShoppingListItemUseCase(repository)


    @Provides
    @Singleton
    fun provideUpdateShoppingListItemUseCase(repository:ShoppingListRepository): UpdateShoppingListItemUseCase =
        UpdateShoppingListItemUseCase(repository)

    @Provides
    @Singleton
    fun provideGetAllShoppingListItemUseCase(repository:ShoppingListRepository): GetAllShoppingListItemUseCase =
        GetAllShoppingListItemUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteShoppingListItemUseCase(repository:ShoppingListRepository): DeleteShoppingListItemUseCase =
        DeleteShoppingListItemUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteAllShoppingListItemUseCase(repository: ShoppingListRepository): DeleteAllShoppingListItem=
        DeleteAllShoppingListItem(repository)


}
