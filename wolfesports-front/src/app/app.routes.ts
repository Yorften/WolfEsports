import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TournamentComponent } from './pages/tournament/tournament.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'tournament/:id',
        component: TournamentComponent
    }
];
