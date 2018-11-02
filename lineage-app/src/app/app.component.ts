import {Component, OnInit} from '@angular/core';
import {ExtractValue, RemoteService} from "./remote.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  extractIds: number[];
  currentIndex: number = -1;
  extractValue: ExtractValue;
  relation: number = 0;

  constructor(private remoteService: RemoteService) {
  }

  private save(): void {
    this.remoteService.save(this.extractIds[this.currentIndex], this.relation).subscribe(m => m);
  }

  private getModel(): void {
    this.relation = 0;
    if (this.currentIndex >= 0){
      this.remoteService.extract(this.extractIds[this.currentIndex]).subscribe(m => this.extractValue = m)
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
      this.save();
      this.currentIndex = (this.currentIndex + 1) % this.extractIds.length;
      this.getModel();
    }
  }

  prev(): void {
    if (this.currentIndex >= 0){
      this.save();
      this.currentIndex = (this.currentIndex - 1 + this.extractIds.length) % this.extractIds.length;
      this.getModel();
    }
  }

  ngOnInit(): void {
  }
}
