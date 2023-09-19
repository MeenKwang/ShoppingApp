import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { HomepageComponent } from './homepage/homepage.component';
import { UserService } from './service/user/user.service';
import { AuthService } from './service/auth/auth.service';
import { CookieService } from 'ngx-cookie-service';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { UserComponent } from './user/user.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { UserFormComponent } from './user/user-form/user-form.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { AuthInterceptor } from './auth/auth.interceptor';
import { AuthGuard } from './auth/auth.guard';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NgbModalModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgSelectModule } from '@ng-select/ng-select';
import { UserUpdateFormComponent } from './user/user-update-form/user-update-form.component';
import { ConfirmModalComponent } from './modal/confirm-modal/confirm-modal.component';
import { CategoryComponent } from './category/category.component';
import { BrandComponent } from './brand/brand.component';
import { CategoryListComponent } from './category/category-list/category-list.component';
import { BrandListComponent } from './brand/brand-list/brand-list.component';
import { CategoryService } from './service/category/category.service';
import { CategoryCreateFormComponent } from './category/category-create-form/category-create-form.component';
import { CategoryUpdateFormComponent } from './category/category-update-form/category-update-form.component';
import { BrandCreateFormComponent } from './brand/brand-create-form/brand-create-form.component';
import { BrandUpdateFormComponent } from './brand/brand-update-form/brand-update-form.component';
import { NotifyModalComponent } from './modal/notify-modal/notify-modal.component';
import { ProductComponent } from './product/product.component';
import { ProductListComponent } from './product/product-list/product-list.component';
import { ProductFormComponent } from './product/product-form/product-form.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { DatePipe } from '@angular/common';
import { MaterialModule } from './material/material.module';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    HomepageComponent,
    ForbiddenComponent,
    UserComponent,
    UserListComponent,
    UserFormComponent,
    PageNotFoundComponent,
    UserUpdateFormComponent,
    ConfirmModalComponent,
    CategoryComponent,
    BrandComponent,
    CategoryListComponent,
    BrandListComponent,
    CategoryCreateFormComponent,
    CategoryUpdateFormComponent,
    BrandCreateFormComponent,
    BrandUpdateFormComponent,
    NotifyModalComponent,
    ProductComponent,
    ProductListComponent,
    ProductFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule,
    NgbModule,
    NgbModalModule,
    ReactiveFormsModule,
    NgSelectModule,
    BrowserAnimationsModule,
    CKEditorModule,
    MaterialModule,
  ],
  providers: [
    UserService,
    AuthService,
    CookieService,
    AuthGuard,
    CategoryService,
    DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
