import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GroupService, Group } from '../services/group.service';

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './groups.html',
  styleUrl: './groups.css',
})
export class Groups implements OnInit {
  
  groups: Group[] = [];
  loading = true;
  error: string | null = null;

  constructor(private groupService: GroupService) {}

  ngOnInit(): void {
    this.groupService.getGroups().subscribe(
      (data) => {
        this.groups = data;
        this.loading = false;
      },
      (error) => {
        this.error = 'Error al cargar los grupos';
        this.loading = false;
        console.error(error);
      }
    );
  }
}
