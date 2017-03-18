import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Grade } from './grade.model';
import { GradeService } from './grade.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-grade',
    templateUrl: './grade.component.html'
})
export class GradeComponent implements OnInit, OnDestroy {
grades: Grade[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private gradeService: GradeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.gradeService.query().subscribe(
            (res: Response) => {
                this.grades = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGrades();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: Grade) {
        return item.id;
    }



    registerChangeInGrades() {
        this.eventSubscriber = this.eventManager.subscribe('gradeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
