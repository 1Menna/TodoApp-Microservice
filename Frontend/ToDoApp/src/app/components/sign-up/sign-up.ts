import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-up',
  imports: [FormsModule],
  templateUrl: './sign-up.html',
  styleUrl: './sign-up.css',
})
export class SignUp {
  constructor(private router: Router) {}

  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  showPassword: boolean = false;
  showConfirmPassword: boolean = false;
 
  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }
 
  toggleConfirmPasswordVisibility(): void {
    this.showConfirmPassword = !this.showConfirmPassword;
  }
 
  onSignUp(): void {
    if (this.password !== this.confirmPassword) {
      console.log('Passwords do not match');
      return;
    }
    // Hook up registration logic here
    console.log('Sign up attempt', { email: this.email, password: this.password });
  }
 
  onGoToLogin(): void {
    // Hook up navigation to login page here
    this.router.navigate(['/Login']);
  }
}
