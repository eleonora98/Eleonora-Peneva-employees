import { Component } from '@angular/core';
import { EmployeeService } from './services/employee-service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html'
})
export class AppComponent {

	results: any[] = [];

	constructor(private service: EmployeeService) { }

	onFileChange(event: any) {

		const file = event.target.files[0];

		if (!file) {
			return;
		}

		this.service.upload(file)
			.subscribe(data => {
				this.results = data;
				event.target.value = '';

			});
	}
}