import { AuthService } from './../services/auth.service';
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with basic auth credentials if available
        const currentUserToken = this.authenticationService.authToken;
        
        if (currentUserToken) {
            request = request.clone({
                setHeaders: { 
                    Authorization: `Bearer ${currentUserToken}`
                }
            });
        }

        return next.handle(request);
    }
}
