import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { GroupService } from '../../services/group.service';
import { Group } from '../../models/group.model';
import { FormsModule } from '@angular/forms';
import { UserRequest, MemberWithExpenses, User } from '../../models/user.model';
import { Expense, ExpenseRequest } from '../../models/expense.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-group-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './group-detail.html',
  styleUrl: './group-detail.css',
})
export class GroupDetail implements OnInit {

  group: Group | undefined;
  public newExpense = {
    expenseId: null,
    title: '',
    payerId: null,
    groupId: null,
    totalAmount: null
  };

  newMemberName: string = '';

  users: User[] = [];
  expenses: Expense[] = [];
  public membersWithData: MemberWithExpenses[] = [];

  constructor(
    private route: ActivatedRoute,
    private groupService: GroupService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (id) {
      this.loadAllData(id);
    }
  }

  loadAllData(id: number): void {
    forkJoin({
      group: this.groupService.getGroupById(id),
      expenses: this.groupService.getExpensesByGroup(id),
      users: this.groupService.getUsersByGroup(id)
    }).subscribe({
      next: ({ group, expenses, users }) => {
        this.group = group;
        this.expenses = expenses;
        this.users = users;
        this.mapMembersWithExpenses();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error cargando datos del grupo', err)
    });

  }

  addMember(): void {
    if (this.newMemberName.trim() && this.group) {
      const userRequest: UserRequest = { userId: null, name: this.newMemberName };

      this.groupService.addUserToGroup(this.group.groupId, userRequest).subscribe({
        next: () => {
          this.newMemberName = '';
          this.loadAllData(this.group!.groupId);
        },
        error: (err) => console.error('Error adding user', err)
      });
    }
  }

  addExpense(): void {
    if (this.isExpenseValid()) {
      const expenseRequest: ExpenseRequest = {
        expenseId: null,
        title: this.newExpense.title,
        payerId: this.newExpense.payerId!,
        groupId: this.group!.groupId,
        totalAmount: this.newExpense.totalAmount!
      };

      this.groupService.addExpenseToGroup(this.group!.groupId, expenseRequest).subscribe({
        next: () => {
          this.resetForm();
          this.loadAllData(this.group!.groupId);
        },
        error: (err) => {
          console.error('Error adding expense', err);
        }
      });
    }
  }

  private mapMembersWithExpenses(): void {
    if (!this.group) return;

    this.membersWithData = this.users.map(user => {
      const userExpenses = this.expenses.filter(e => e.payerId === user.userId);
      const totalSpent = userExpenses.reduce((acc, exp) => acc + exp.totalAmount, 0);

      return {
        ...user,
        userExpenses: userExpenses,
        totalSpent: totalSpent
      };
    });

    this.cdr.detectChanges();
  }

  private resetForm(): void {
    this.newExpense = {
      expenseId: null,
      title: '',
      payerId: null,
      groupId: null,
      totalAmount: null
    };
  }

  isExpenseValid(): boolean {
    return !!(
      this.newExpense.title &&
      this.newExpense.title.trim().length > 0 &&
      this.newExpense.totalAmount &&
      this.newExpense.totalAmount > 0 &&
      this.newExpense.payerId
    );
  }
}
