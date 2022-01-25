package com.example.composetest.login.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetest.login.data.local.model.DataStoreOperations
import com.example.composetest.login.data.local.repository.DataStoreOperationsImpl
import com.example.composetest.login.presentation.viewmodel.auth.AuthState
import com.example.composetest.login.presentation.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject



@Composable
fun HomeScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val dataStoreOperations: DataStoreOperations = DataStoreOperationsImpl(context)

    val authState = remember { mutableStateOf("User not authorized")}
    val jwt:MutableState<String?> = remember { mutableStateOf(null)}

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true, block =  {
        jwt.value = dataStoreOperations.readToken().stateIn(coroutineScope).value
    })

    authViewModel.authState.addObserver { result ->
        when(result){
            is AuthState.GetUser -> {
                authState.value = "Authorized, id: ${result.user?.userId}"
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = { authViewModel.login("72222222222", "Qazxsw21") }) {
            Text(text = "Login")
        }
        Text(text = jwt.value?:"null")

        Spacer(modifier = Modifier.height(20.dp))
        Column() {
            Text(text = authState.value)
            Button(onClick = { authViewModel.getUser() }) {
                Text(text = "Get User")
            }
        }

    }
}