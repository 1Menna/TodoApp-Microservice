import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Login } from './components/login/login';
import { SignUp } from './components/sign-up/sign-up';

export const routes: Routes = [
    {path: "", redirectTo: "Home",pathMatch:"full"},
    {path: "Home", component: Home, title: "home"},
    {path: "Login", component: Login, title: "login"},
    {path: "SignUp", component: SignUp, title: "SignUp"},
];
