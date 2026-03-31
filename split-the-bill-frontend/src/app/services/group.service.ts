import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Group, GroupRequest } from '../models/group.model';
import { User, UserRequest } from '../models/user.model';
import { Expense, ExpenseRequest } from '../models/expense.model';

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

  addUserToGroup(groupId: number, userRequest: UserRequest): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${groupId}/users`, userRequest);
  }

  getExpensesByGroup(groupId: number): Observable<Expense[]> {
    return this.http.get<Expense[]>(`${this.apiUrl}/${groupId}/expenses`);
  }

  getUsersByGroup(groupId: number): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/${groupId}/users`);
  }

  addExpenseToGroup(groupId: number, expenseRequest: ExpenseRequest): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${groupId}/expenses`, expenseRequest);
  }

}
