import { Routes } from '@angular/router';
import { GroupList } from './components/group-list/group-list';
import { GroupDetail } from './components/group-detail/group-detail';

export const routes: Routes = [
    { path: '', component: GroupList }, 
    { path: 'groups/:id', component: GroupDetail } 
];