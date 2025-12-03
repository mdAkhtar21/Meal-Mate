import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mealmate.R
import com.example.mealmate.presentation.common.AppBar
import com.example.mealmate.presentation.list.ListViewModel

@Composable
fun MessageScreen(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel()
) {

    var mobileNo by remember { mutableStateOf("") }
    val listData by viewModel.shoppingListItems.collectAsState()
    val context = LocalContext.current
    val checkedItems = listData.filter { it.isChecked }
    val groupedItems = checkedItems.groupBy { it.categoryName }

    Scaffold(
        topBar = {
            AppBar(
                topBarHeader = "Send SMS Message",
                showBackButton = true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            TextField(
                value = mobileNo,
                onValueChange = { mobileNo = it },
                placeholder = { Text(text = "Phone number", fontWeight = FontWeight.W600, color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, color = Color.Gray),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 10.dp, end = 10.dp)

                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(painter= painterResource(id=R.drawable.order), contentDescription = null,tint = Color.Unspecified, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Shopping List",
                            fontWeight = FontWeight.W400,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp, max = 400.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        groupedItems.forEach { (category, items) ->

                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(painter= painterResource(id= R.drawable.folder), contentDescription = null,tint = Color.Unspecified,modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = category,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }

                            itemsIndexed(items) { index, item ->
                                Text(
                                    text = "${index + 1}. ${item.ingredientName}",
                                    fontWeight = FontWeight.Normal,
                                    color = Color.DarkGray,
                                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (mobileNo.isNotBlank()) {
                        val messageText = buildString {
                            groupedItems.forEach { (category, items) ->
                                append("$category:\n")
                                items.forEachIndexed { index, item ->
                                    append("${index + 1}. ${item.ingredientName}\n")
                                }
                                append("\n")
                            }
                        }

                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("smsto:$mobileNo")
                            putExtra("sms_body", messageText)
                        }

                        context.startActivity(intent)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Send Message",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
