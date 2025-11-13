package com.example.mealmate.presentation.detailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToMealPlan(
    recipeId:Long?,
    showMealAddPlanSheet: MutableState<Boolean>,
    sheetState: SheetState,
    scope: CoroutineScope,
    viewModel: AddToMealPlanViewModel = hiltViewModel()
) {

    var expanded by remember { mutableStateOf(false) }
    val mealtypeList = listOf("Breakfast", "Lunch", "Dinner", "Refreshment")
    var selectedItem by remember { mutableStateOf(mealtypeList[0]) }
    val daysList = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    val dayCheckedMap = remember { mutableStateMapOf<String, Boolean>() }
    daysList.forEach { dayCheckedMap.putIfAbsent(it, false) }

    if(showMealAddPlanSheet.value){
        ModalBottomSheet(
            onDismissRequest = { showMealAddPlanSheet.value = false },
            sheetState = sheetState,
        ) {
          Column(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 10.dp)) {
              Text(
                  text = "Add to Meal Plan",
                  style = MaterialTheme.typography.titleLarge,
                  color = Color.Black.copy(alpha = 0.8f),
                  fontWeight = FontWeight.Bold
              )

              Spacer(modifier = Modifier.height(12.dp))

              Row(
                  modifier = Modifier.fillMaxWidth(),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween
              ) {
                  Text(
                      text = "Meal Type",
                      style = MaterialTheme.typography.bodyMedium,
                      fontWeight = FontWeight.W600,
                      color = Color.Black.copy(alpha = 0.8f)
                  )

                  ExposedDropdownMenuBox(
                      expanded = expanded,
                      onExpandedChange = { expanded = !expanded }
                  ) {

                      Row(
                          modifier = Modifier
                              .menuAnchor()
                              .clickable { expanded = true }
                              .padding(horizontal = 12.dp, vertical = 8.dp),
                          verticalAlignment = Alignment.CenterVertically
                      ) {
                          Text(
                              text = selectedItem,
                              style = MaterialTheme.typography.bodyMedium,
                              fontWeight = FontWeight.W500,
                              color = Color.Black
                          )
                          Icon(
                              imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                              contentDescription = null
                          )
                      }

                      ExposedDropdownMenu(
                          expanded = expanded,
                          onDismissRequest = { expanded = false }
                      ) {
                          mealtypeList.forEach { option ->
                              DropdownMenuItem(
                                  text = { Text(option) },
                                  onClick = {
                                      selectedItem = option
                                      expanded = false
                                  }
                              )
                          }
                      }
                  }
              }


              Spacer(modifier = Modifier.height(18.dp))

              LazyColumn(
                  contentPadding = PaddingValues(6.dp)
              ) {
                  items(daysList) { day ->
                      DayListUi(
                          option = day,
                          checked = dayCheckedMap[day] ?: false,
                          onCheckedChange = { newValue ->
                              dayCheckedMap[day] = newValue
                          }
                      )
                  }
              }

              Button(onClick = {
                  val selectedDays = dayCheckedMap.filterValues { it }.keys.toList()

                  if (recipeId != null) {
                      viewModel.addMealPlans(
                          recipeId = recipeId,
                          mealType = selectedItem,
                          selectedDays = selectedDays
                      )
                  }
                  scope.launch { sheetState.hide() }
                  showMealAddPlanSheet.value=false
              },
                  modifier = Modifier.fillMaxWidth().height(45.dp)
              ) {
                  Text(
                      text = "Save",
                      fontWeight = FontWeight.Bold,
                      style = MaterialTheme.typography.titleLarge,
                      color = Color.White
                  )
              }

          }
        }
    }
}

@Composable
fun DayListUi(option: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = option,
            color = Color.Black.copy(alpha = 0.8f),
            fontWeight = FontWeight.W600,
            style = MaterialTheme.typography.bodyMedium
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}


