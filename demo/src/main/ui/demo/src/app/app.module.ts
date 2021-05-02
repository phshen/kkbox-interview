import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { VendorInfoService } from './service/vendor-info.service';
import { HttpClientModule } from '@angular/common/http';
// import { VendorFormComponent, ContactDialogComponent } from './vendor-form/vendor-form.component';
import { VendorListComponent, ContactDialogComponent, VendorDialogComponent } from './vendor-list/vendor-list.component';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    // VendorFormComponent,
    VendorListComponent,
    ContactDialogComponent,
    VendorDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatFormFieldModule,
    MatButtonModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatTableModule,
    MatDialogModule
  ],
  providers: [VendorInfoService],
  bootstrap: [AppComponent]
})
export class AppModule { }
