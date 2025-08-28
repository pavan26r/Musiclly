package com.example.musicuiapp.auth.authviewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel(){
private val  auth = FirebaseAuth.getInstance()


// advance function logic hai yeh ->
    fun login(email: String, password: String, onResult: (Boolean) -> Unit){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
           task -> if(task.isSuccessful){
               onResult(true)
           }
            else{
                Log.e("AuthViewModel", "Login failed", task.exception)
                onResult(false)
            }
        }

    }
    fun signup(email: String, password: String, onResult: (Boolean) -> Unit){
      auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
          task -> if(task.isSuccessful){
              onResult(true)
          }
          else{
              Log.e("AuthViewModel", "Signup failed", task.exception)
          onResult(false)
          }
      }

    }
}