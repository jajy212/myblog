import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { MemberCate } from './member-cate.model';
import { MemberCateService } from './member-cate.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-member-cate',
    templateUrl: './member-cate.component.html'
})
export class MemberCateComponent implements OnInit, OnDestroy {
memberCates: MemberCate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private memberCateService: MemberCateService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.memberCateService.query().subscribe(
            (res: Response) => {
                this.memberCates = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMemberCates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: MemberCate) {
        return item.id;
    }



    registerChangeInMemberCates() {
        this.eventSubscriber = this.eventManager.subscribe('memberCateListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
