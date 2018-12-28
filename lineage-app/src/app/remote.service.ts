import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AppValue} from "./app-value";

@Injectable()
export class RemoteService {
  base: string;

  constructor(private httpClient: HttpClient){
    this.base = '/api';
    if (parseInt(window.location.port) < 8080){
      this.base = `http://${window.location.hostname}:8080${this.base}`;
    }
  }

  extract(id: number): Observable<AppValue> {
    return this.httpClient.get<AppValue>(`${this.base}/extract/${id}`);
  }

  query(query: string): Observable<number[]> {
    return this.httpClient.post<number[]>(`${this.base}/query`, query);
  }

  save(extractValue: AppValue): Observable<AppValue> {
    return this.httpClient.post<AppValue>(`${this.base}/save`, extractValue);
  }

}
