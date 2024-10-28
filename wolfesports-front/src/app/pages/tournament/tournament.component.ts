import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-tournament',
  standalone: true,
  imports: [],
  templateUrl: './tournament.component.html',
  styleUrl: './tournament.component.css'
})
export class TournamentComponent {
  tournamentId: string | null = null;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      this.tournamentId = params.get('id');
      console.log('Tournament ID:', this.tournamentId);

    });
  }
}
