import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from './item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private baseURL = "http://localhost:8080/api/v1/items"

  constructor( private httpClient: HttpClient ) { }

  getItemsList(): Observable<Item[]> {
    return this.httpClient.get<Item[]>(`${this.baseURL}`);
  }

  createItem(item: Item): Observable<Object> {
    return this.httpClient.post(`${this.baseURL}`, item);
  }
}
