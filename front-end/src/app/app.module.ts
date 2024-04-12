import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbCarouselModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { DefaultInterceptor } from './Interceptors/default.interceptor';
import Swal from 'sweetalert2';
import { FooterComponent } from './components/footer/footer.component';
import { Page404NotFoundComponent } from './components/page404-not-found/page404-not-found.component';







@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    Page404NotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbCarouselModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: DefaultInterceptor,
      multi: true,
    },
    { provide: 'Swal', useValue: Swal }

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
