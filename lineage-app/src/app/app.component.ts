import {Component} from '@angular/core';
import {RemoteService} from "./remote.service";
import {AppValue} from "./app-value";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  extractIds: number[];
  currentIndex: number = -1;
  appValue: AppValue;

  constructor(private remoteService: RemoteService) {
  }

  private save(): void {
    this.remoteService.save(this.appValue).subscribe(m => m);
  }

  private getModel(): void {
    if (this.currentIndex >= 0){
      this.remoteService.extract(this.extractIds[this.currentIndex]).subscribe(m => {
        this.appValue = m;
        console.log('Relation: ', this.appValue.extract.relation);
      })
    }
  }

  hi(text: string, sub: string){
    return text.replace(new RegExp(sub, "ig"), '<span class="hi">$&</span>'); // case insensitive
  }

  fetch(query: string): void {
    this.currentIndex = -1;
    this.remoteService.query(query).subscribe(extractIds => {
      this.extractIds = extractIds;
      this.appValue = undefined;
      if (extractIds.length > 0){
        this.currentIndex = 0;
        this.getModel();
      }
    });
  }

  next(): void {
    if (this.currentIndex >= 0){
      this.currentIndex = (this.currentIndex + 1) % this.extractIds.length;
      this.getModel();
    }
  }

  prev(): void {
    if (this.currentIndex >= 0){
      this.currentIndex = (this.currentIndex - 1 + this.extractIds.length) % this.extractIds.length;
      this.getModel();
    }
  }

  get relation(): number {
    return this.appValue.extract.relation;
  }

  set relation(r: number) {
    console.log('Setting relation to: ', r);
    this.appValue.extract.relation = r;
    this.save();
    this.next();
  }
}
