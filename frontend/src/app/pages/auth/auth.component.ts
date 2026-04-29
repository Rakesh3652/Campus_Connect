import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
 selector:'app-auth',
 standalone: true,
 imports: [CommonModule, FormsModule],
 templateUrl:'./auth.component.html',
 styleUrls:['./auth.component.css']
})
export class AuthComponent{

 isLogin=true;
 signupPassword = '';

 get passwordStrength(): number {
   let strength = 0;
   if (this.signupPassword.length > 5) strength++;
   if (this.signupPassword.match(/[a-z]+/)) strength++;
   if (this.signupPassword.match(/[A-Z]+/)) strength++;
   if (this.signupPassword.match(/[0-9]+/)) strength++;
   if (this.signupPassword.match(/[$@#&!]+/)) strength++;
   
   if (this.signupPassword.length === 0) return 0;
   if (strength < 3) return 1; // Weak
   if (strength < 5) return 2; // Medium
   return 3; // Strong
 }

 switchMode(){
   this.isLogin=!this.isLogin;
 }

 login(){
   alert("Login clicked");
 }

 signup(){
   alert("Signup clicked");
 }

}
