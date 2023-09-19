import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { BrandCreateFormComponent } from './brand/brand-create-form/brand-create-form.component';
import { BrandListComponent } from './brand/brand-list/brand-list.component';
import { BrandUpdateFormComponent } from './brand/brand-update-form/brand-update-form.component';
import { BrandComponent } from './brand/brand.component';
import { CategoryCreateFormComponent } from './category/category-create-form/category-create-form.component';
import { CategoryListComponent } from './category/category-list/category-list.component';
import { CategoryUpdateFormComponent } from './category/category-update-form/category-update-form.component';
import { CategoryComponent } from './category/category.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ProductFormComponent } from './product/product-form/product-form.component';
import { ProductListComponent } from './product/product-list/product-list.component';
import { ProductComponent } from './product/product.component';
import { UserFormComponent } from './user/user-form/user-form.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { UserUpdateFormComponent } from './user/user-update-form/user-update-form.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  { path: '', redirectTo: "/login", pathMatch: "full"},
  { path: 'home', component: HomepageComponent, canActivate: [AuthGuard], data: { roles: ["Admin", "Editor", "Salesperson", "Shipper", "Assistant"] } },
  { path: 'users', component: UserComponent, canActivate: [AuthGuard], data: {roles: ["Admin"]},
              children: [
                { path: '', component: UserListComponent },
                { path: 'new', component: UserFormComponent },
                { path: 'edit/:id', component: UserUpdateFormComponent }
              ] 
  },
  { path: 'categories', component: CategoryComponent, canActivate: [AuthGuard], data: {roles: ["Admin", "Editor"]},
              children: [
                { path: '',component: CategoryListComponent },
                { path: 'new', component: CategoryCreateFormComponent },
                { path: 'edit/:id', component: CategoryUpdateFormComponent }
              ]
  },
  { path: 'brands', component: BrandComponent, canActivate: [AuthGuard], data: {roles: ["Admin", "Editor"]},
              children: [
                { path: '',component: BrandListComponent },
                { path: 'new', component: BrandCreateFormComponent },
                { path: 'edit/:id', component: BrandUpdateFormComponent }
              ]
  },
  { path: 'products', component: ProductComponent, canActivate: [AuthGuard], data: {roles: ["Admin", "Editor", "Salesperson", "Shipper"]},
  children: [
    { path: '',component: ProductListComponent },
    { path: 'new', component: ProductFormComponent },
    { path: 'edit/:id', component: ProductFormComponent }
  ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'forbidden', component: ForbiddenComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
