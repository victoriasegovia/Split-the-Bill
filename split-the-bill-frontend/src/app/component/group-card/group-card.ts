import { Component, Input } from '@angular/core';
import { Group } from '../../models/group';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-group-card',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './group-card.html',
  styleUrls: ['./group-card.css']
})
export class GroupCard {
  @Input() group!: Group;
}
