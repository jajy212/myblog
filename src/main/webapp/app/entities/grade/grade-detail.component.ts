import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Grade } from './grade.model';
import { GradeService } from './grade.service';

@Component({
    selector: 'jhi-grade-detail',
    templateUrl: './grade-detail.component.html'
})
export class GradeDetailComponent implements OnInit, OnDestroy {

    grade: Grade;
    private subscription: any;

    constructor(
        private gradeService: GradeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.gradeService.find(id).subscribe(grade => {
            this.grade = grade;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
