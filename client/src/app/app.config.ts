import {ApplicationConfig, forwardRef} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {provideHttpClient} from "@angular/common/http";
import {NG_VALUE_ACCESSOR} from "@angular/forms";
import {UserLoginComponent} from "./components/user-login/user-login.component";

//to use httpClient you should insert provideHttpClient() into the providers of the application
export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideHttpClient()]
};
