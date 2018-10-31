import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {RemoteService} from "./remote.service";
import {FormsModule} from "@angular/forms";
import {ButtonModule, InputTextModule} from "primeng/primeng";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    InputTextModule,
    ButtonModule,
  ],
  providers: [
    RemoteService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
