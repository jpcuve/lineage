import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {RemoteService} from "./remote.service";
import {FormsModule} from "@angular/forms";
import {ButtonModule, InputTextModule, RadioButtonModule} from "primeng/primeng";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {from, Observable, of} from "rxjs";
import {fromPromise} from "rxjs/internal-compatibility";
import {delay, last, scan, take} from "rxjs/operators";

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
    RadioButtonModule,
  ],
  providers: [
    RemoteService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor() {
    const obs1: Observable<string> = of('foo', 'bar', 'zoo');
    const obs2: Observable<number> = from([1, 2, 3]);
    const obs3: Observable<number> = fromPromise(new Promise((resolve, reject) => { resolve(123)}));
    const obs4: Observable<any> = Observable.create(observer => {
      observer.next('foo');
      setTimeout(() => observer.next('foo finished'), 2000);
      observer.complete();
    });
    const fn = v => console.log(v);
    obs1.pipe(delay(5000), take(2)).subscribe(fn);
    obs2.pipe(scan((acc, cur) => acc + cur, 0), last()).subscribe(fn);
    obs3.subscribe(fn);
    obs4.subscribe(fn);
  }
}
