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

		if (!file.name.endsWith('.csv')) {
			alert('Please upload CSV file');
			return;
		}

		this.service.upload(file)
			.subscribe(data => {
				this.results = data;
				event.target.value = '';

			});
	}
}