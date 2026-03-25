import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Group, GroupRequest } from '../models/group.model';

@Injectable({
  providedIn: 'root',
})
export class GroupService {

  private apiUrl = 'http://localhost:8080/api/groups';

  constructor(private http: HttpClient) { }

  getGroups(): Observable<Group[]> {
    return this.http.get<Group[]>(this.apiUrl);
  }

  addGroup(request: GroupRequest): Observable<Group> {
    return this.http.post<Group>(this.apiUrl, request);
  }

  getGroupById(id: number): Observable<Group> {
    return this.http.get<Group>(`${this.apiUrl}/${id}`);
  }

}
