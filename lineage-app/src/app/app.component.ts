import {Component, OnInit} from '@angular/core';
import {Hello, RemoteService} from "./remote.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  hello: Hello = new class implements Hello {
    title: string = '';
  };

  constructor(private remoteService: RemoteService) {
  }

  ngOnInit(): void {
    this.remoteService.hello().subscribe(h => this.hello = h)
  }
}
