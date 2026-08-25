import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  constructor(private router: Router) {}

  email: string = '';
  password: string = '';
  showPassword: boolean = false;
 
  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }
 
  onLogin(): void {
    console.log('Login attempt', { email: this.email, password: this.password });
  }
 
  onForgotPassword(): void {
    console.log('Forgot password tapped');
  }
 
  onGoToSignUp(): void {
    this.router.navigate(['/SignUp']);
  }
}
