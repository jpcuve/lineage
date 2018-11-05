import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ExtractValue, RemoteService} from "./remote.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  extractIds: number[];
  currentIndex: number = -1;
  extractValue: ExtractValue;

  constructor(private remoteService: RemoteService) {
  }

  private save(): void {
    this.remoteService.save(this.extractValue).subscribe(m => m);
  }

  private getModel(): void {
    if (this.currentIndex >= 0){
      this.remoteService.extract(this.extractIds[this.currentIndex]).subscribe(m => {
        this.extractValue = m;
        console.log('Relation: ', this.extractValue.extract.relation);
      })
    }
  }

  fetch(query: string): void {
    this.currentIndex = -1;
    this.remoteService.query(query).subscribe(extractIds => {
      this.extractIds = extractIds;
      this.extractValue = undefined;
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
    return this.extractValue.extract.relation;
  }

  set relation(r: number) {
    console.log('Setting relation to: ', r);
    this.extractValue.extract.relation = r;
    this.save();
    this.next();
  }
}
