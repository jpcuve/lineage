import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";


export interface Hello {
  title: string;
}

@Injectable()
export class RemoteService {
  base: string;

  constructor(private httpClient: HttpClient){
    let w: Window = <Window> window;
    let cs: string[] = (parseInt(w.location.port) < 8080 ? ['http://', w.location.hostname, ':8080'] : [])
    this.base = cs.concat(['/api']).join('')
    console.info('base:', this.base)
  }

  public hello(): Observable<Hello> {
    return this.httpClient.get<Hello>(this.base + '/hello');
  }

}
