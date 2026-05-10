import { Component } from '@angular/core';
import { EmployeeService } from './services/employee-service';
import { PairResult } from './model/pair-result';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html'
})
export class AppComponent {

	results: PairResult[] = [];
	constructor(private service: EmployeeService) { }

	onFileChange(event: any) {
		const file = event.target.files[0];

		if (!file) {
			return;
		}

		this.service.upload(file)
			.subscribe({
				next: (data) => {
					this.results = data;
					event.target.value = '';
				},
				error: (err) => {
					alert(err.error);
					event.target.value = '';
				}
				
			});
	}
}