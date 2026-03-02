import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GroupService, Group } from '../services/group.service';

@Component({
  selector: 'app-groups',
  standalone: true,
  templateUrl: './groups.component.html',
  imports: [CommonModule],
})
export class GroupsComponent implements OnInit {

  private groupService = inject(GroupService); // usa inject() en standalone

  groups: Group[] = [];

  ngOnInit(): void {
    this.groupService.getGroups().subscribe((data: Group[]) => {
      this.groups = data;
    });
  }

}