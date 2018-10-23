import {Component, Input, OnInit} from '@angular/core';
import {ExtractValue, Company, Hello, RemoteService} from "./remote.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  extractIds: number[];
  currentIndex: number = -1;
  appModel: ExtractValue;
  parentId: number = 0;
  childId: number = 0;

  hello: Hello = new class implements Hello {
    title: string = '';
  };

  constructor(private remoteService: RemoteService) {
  }

  private save(): void {
    this.remoteService.save(this.extractIds[this.currentIndex], this.parentId, this.childId).subscribe(m => m);
  }

  private getModel(): void {
    this.parentId = this.childId = 0;
    if (this.currentIndex >= 0){
      this.remoteService.extract(this.extractIds[this.currentIndex]).subscribe(m => this.appModel = m)
    }
  }

  fetch(query: string): void {
    this.currentIndex = -1;
    this.remoteService.query(query).subscribe(extractIds => {
      this.extractIds = extractIds;
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
    this.remoteService.hello().subscribe(h => this.hello = h)
  }
}
