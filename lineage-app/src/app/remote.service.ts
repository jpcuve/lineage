import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";


export interface Hello {
  title: string;
}

export interface Company {
  id: number;
  name: string;
}

export interface Extract {
  id: number;
  decisionId: number;
  lang: string;
  sentences: string;
}

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

  hello(): Observable<Hello> {
    return this.httpClient.get<Hello>(`${this.base}/hello`);
  }

  extract(id: number): Observable<ExtractValue> {
    return this.httpClient.get<ExtractValue>(`${this.base}/extract/${id}`);
  }

  query(query: string): Observable<number[]> {
    return this.httpClient.post<number[]>(`${this.base}/query`, query);
  }

  save(extractId: number, parentId: number, childId: number): Observable<SaveValue> {
    let body = new class implements SaveValue {
      childId: number = childId;
      extractId: number = extractId;
      parentId: number = parentId;
    };
    console.info('Posting data:', body);
    return this.httpClient.post<SaveValue>(`${this.base}/save`, body);
  }

}
