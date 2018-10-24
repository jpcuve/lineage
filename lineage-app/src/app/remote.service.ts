import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Company, Extract} from "./domain";

export interface ExtractValue {
  extract: Extract;
  companies: Company[];
}

export interface SaveValue {
  extractId: number;
  parentId: number;
  childId: number;
}

@Injectable()
export class RemoteService {
  base: string;

  constructor(private httpClient: HttpClient){
    let w: Window = <Window> window;
    this.base = '/api';
    if (parseInt(w.location.port) < 8080){
      this.base = `http://${w.location.hostname}:8080${this.base}`;
    }
  }

  extract(id: number): Observable<ExtractValue> {
    return this.httpClient.get<ExtractValue>(`${this.base}/extract/${id}`);
  }

  query(query: string): Observable<number[]> {
    return this.httpClient.post<number[]>(`${this.base}/query`, query);
  }

  save(extractId: number, parentId: number, childId: number): Observable<SaveValue> {
    return this.httpClient.post<SaveValue>(`${this.base}/save`, new class implements SaveValue {
      childId: number = childId;
      extractId: number = extractId;
      parentId: number = parentId;
    });
  }

}
