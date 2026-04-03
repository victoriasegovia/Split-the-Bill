import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Group, GroupRequest } from '../../models/group.model';
import { GroupService } from '../../services/group.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-group-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './group-list.html',
  styleUrl: './group-list.css',
})
export class GroupList implements OnInit {

  groups: Group[] = [];
  newGroupName: string = '';

  constructor(
    private groupService: GroupService,
    private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.refreshGroups();
  }

  refreshGroups() {
    this.groupService.getGroups().subscribe(data => {
      this.groups = data;
      this.cdr.detectChanges();
    });
  }

  createGroup() {
    if (this.newGroupName.trim()) {
      const request: GroupRequest = {
        groupId: null,
        name: this.newGroupName
      };

      this.groupService.addGroup(request).subscribe(() => {
        this.newGroupName = '';
        this.refreshGroups();
      });
    }
  }
}