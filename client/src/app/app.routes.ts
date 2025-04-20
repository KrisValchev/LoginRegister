import { Routes } from '@angular/router';
import {UserLoginComponent} from "./components/user-login/user-login.component";
import {UserRegisterComponent} from "./components/user-register/user-register.component";
//Route typescript class for routing through components
export const routes: Routes = [
  {path:'login',component:UserLoginComponent},
  {path:'',redirectTo:'/login',pathMatch:'full'},//when url path is empty redirect to login component
  {path:'register',component:UserRegisterComponent},
];
