import {HttpInterceptorFn} from "@angular/common/http";
//interceptor which runs in for every request - clones the previous one and adds token to the header
export const authInterceptor: HttpInterceptorFn=(request,next)=>{
  const token  = localStorage.getItem('token')?? '';
  request=request.clone({
    setHeaders:{
      Authorization: token? `Bearer ${token}` : '',
    }
  });
  return next(request);
}
