package com.example.kotlinapp


import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class FireBaseSetUp:ViewModel() {
    var username = mutableStateOf("")

    data class UserInfo(val email:String, val pw:String)


fun loginUser(email: String , pw : String){
    Firebase.auth
        .signInWithEmailAndPassword(email,pw)
        .addOnSuccessListener {
            username.value = email
        }
}
    fun logOutUser(){
        Firebase.auth.signOut()
        username.value = ""
    }

fun CreateUser(email: String,pw: String){
    Firebase.auth
        .createUserWithEmailAndPassword(email,pw)
        .addOnSuccessListener { username.value=email }

}






}