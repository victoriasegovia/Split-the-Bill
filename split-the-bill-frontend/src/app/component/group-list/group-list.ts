import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GroupCard } from '../group-card/group-card';
import { Group } from '../../models/group';
import { GroupService } from '../../services/group.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-group-list',
  standalone: true,
  imports: [CommonModule, GroupCard],
  templateUrl: './group-list.html',
  styleUrls: ['./group-list.css']
})
export class GroupList implements OnInit {

  groups$!: Observable<Group[]>; // observable directo

  constructor(private groupService: GroupService) { }

  ngOnInit(): void {
    this.groups$ = this.groupService.getAll(); // no subscribe aquí
  }
}
