package com.example.kotlinapp

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kotlinapp.ui.theme.KotlinAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigator()
                }
            }
        }
    }
}

const val  FIRST_ROUTE ="first_page"
const val  LOGIN_ROUTE ="login"
const val  REGISTER_ROUTE ="register"
const val  HOME_ROUTE ="home"
const val  MENU_ROUTE ="menu"
const val  NEWS_ROUTE= "news"

@Composable
fun Navigator() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = FIRST_ROUTE ){
        composable(route= FIRST_ROUTE){ FirstView(navController)}
        composable(route= LOGIN_ROUTE){ LogInView(navController)}
        composable(route= REGISTER_ROUTE){ RegisterView(navController)}
        composable(route= HOME_ROUTE){ MainScaffoldView()}
        composable(route= MENU_ROUTE){ MenuView()}
        composable(route= NEWS_ROUTE){ NewsView()}


    }

}



@Composable
fun FirstView(navController: NavHostController) {



    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedButton(onClick = { navController.navigate(LOGIN_ROUTE) }) {
            Text(text = "Login")
        }
        OutlinedButton(onClick = { navController.navigate(REGISTER_ROUTE) }) {
            Text(text = "Register")
        }
    }

}

@Composable
fun LogInView(navController: NavHostController) {

    var email by remember { mutableStateOf("")}
    var pw by remember { mutableStateOf("")}

    val userVM = viewModel<FireBaseSetUp>()
    if(userVM.username.value.isNotEmpty()){
        MainScaffoldView()
    }



    Column (

        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Log in with existing user")
        OutlinedTextField(
            value = email,
            onValueChange ={email =it},
            label ={ Text(text = "Email")
    })
        OutlinedTextField(
            value = pw,
            onValueChange ={pw =it},
            label ={ Text(text = "password") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedButton(onClick = { userVM.loginUser(email,pw) }) {
            Text(text = "Login")
        }
        OutlinedButton(onClick = { navController.navigate(FIRST_ROUTE) }) {
            Text(text = "Back")
        }
    }
}






@Composable
fun RegisterView(navController: NavHostController) {
    var email by remember { mutableStateOf("")}
    var pw by remember { mutableStateOf("")}
    val userVM = viewModel<FireBaseSetUp>()

    Column (

        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Create new user")
        OutlinedTextField(
            value = email,
            onValueChange ={email =it},
            label ={ Text(text = "Email")
            })
        OutlinedTextField(
            value = pw,
            onValueChange ={pw =it},
            label ={ Text(text = "password") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedButton(onClick = { userVM.CreateUser(email,pw);navController.navigate(LOGIN_ROUTE) }) {
            Text(text = "Create User")
        }
        OutlinedButton(onClick = { navController.navigate(FIRST_ROUTE) }) {
            Text(text = "Back")
        }
    }
}

@Composable
fun MainScaffoldView() {

    val navController = rememberNavController()



    Scaffold (
        topBar = { TopBarView()},
        bottomBar = { BottomBarView(navController)},
        content = { MainContentView(navController) }
    )
}
@Composable
fun TopBarView() {
    val userVM = viewModel<FireBaseSetUp>()

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFF318BCF))
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text =  userVM.username.value)
        OutlinedButton(onClick = { userVM.logOutUser() }) {
            Text(text = "log out")
        }
    }
}
@Composable
fun BottomBarView(navController: NavHostController) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFF318BCF)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "home",
            modifier = Modifier.clickable { navController.navigate(HOME_ROUTE) })
        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "menu",
            modifier = Modifier.clickable { navController.navigate(MENU_ROUTE) }
        )


    }
}
@Composable
fun MainContentView(navController: NavHostController) {



    NavHost(navController = navController, startDestination = HOME_ROUTE) {
        composable(route = HOME_ROUTE) { NewsView() }
        composable(route = MENU_ROUTE) {MenuView()}


    }
}

@Composable
fun NewsView() {

    Column (

        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFCF3190))
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "NEWS: Here you will see ads and discounts")
    }
}

@Composable
fun MenuView() {

    Column (

        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7DD5A))
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "MENU: here you will see all the available products")
    }
}













@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KotlinAppTheme {
        Greeting("Android")
    }
}