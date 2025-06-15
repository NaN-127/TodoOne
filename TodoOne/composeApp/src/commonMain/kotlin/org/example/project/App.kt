 package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benasher44.uuid.uuid4
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import todoone.composeapp.generated.resources.Res
import todoone.composeapp.generated.resources.compose_multiplatform



data class TodoItem(
    val id: String = uuid4().toString(),
    val text: String
)

@Composable

fun App() {
   Box(
      modifier = Modifier.fillMaxSize().background(Color(0xFF2C2C2C))
   ){
      todoHome()
   }
}


 @Composable
 fun todoHome(){
     var todoText by remember { mutableStateOf("") }
     var todoItems by remember { mutableStateOf(listOf<TodoItem>()) }
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().padding(26.dp)
    ) {
        Text(
            text = "Your To Do",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )
        Spacer(Modifier.height(32.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = todoText,
            onValueChange = {
                todoText = it
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF4CAF50),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = Color(0xFFAAAAAA),
                unfocusedPlaceholderColor = Color(0xFFAAAAAA)
            ),
            singleLine = true,
            placeholder = {
                Text(
                    "Add a new todo item...",
                    color =  Color(0xFFAAAAAA),
                )
            },
            suffix = {
               IconButton(
                   onClick = {
                      if(todoText.isNotBlank()){
                         val newItem = TodoItem(text = todoText)
                          todoItems+= newItem
                          todoText = ""
                          
                      }
                   }
               ){
                   Icon(
                       imageVector = Icons.Default.Add,
                       contentDescription = "Add",
                       tint = Color(0xFF4CAF50)
                   )
               }

            }
        )
        Spacer(Modifier.height(24.dp))

        if(todoItems.isEmpty()) {
            Text(
                "No todos yet! Add some above.",
                color = Color(0xFFAAAAAA),
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else{
            LazyColumn {
                items(todoItems) { todo ->
                    TodoCard(todo = todo.text) {
                        todoItems = todoItems.filter { it.id != todo.id }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }

        }
        
        
        
    }
 }

 @Composable
 fun TodoCard(todo: String, onRemove: () -> Unit) {
     Card(
         modifier = Modifier.fillMaxWidth(),
         shape = RoundedCornerShape(12.dp),
         colors = CardDefaults.cardColors(containerColor = Color.White),
         elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
     ) {
         Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(16.dp),
             verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.SpaceBetween
         ) {
             Text(
                 text = todo,
                 color = Color.Black,
                 fontSize = 18.sp,
                 fontWeight = FontWeight.Medium,
                 modifier = Modifier.weight(1f)
             )
             IconButton(
                 onClick = onRemove,
                 modifier = Modifier.size(40.dp)
             ) {
                 Icon(
                     imageVector = Icons.Default.Close,
                     contentDescription = "Remove todo item",
                     tint = Color(0xFFE57373)
                 )
             }
         }
     }
 }