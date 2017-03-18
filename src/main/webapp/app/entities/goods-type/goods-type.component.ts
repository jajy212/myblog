import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { GoodsType } from './goods-type.model';
import { GoodsTypeService } from './goods-type.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-goods-type',
    templateUrl: './goods-type.component.html'
})
export class GoodsTypeComponent implements OnInit, OnDestroy {
goodsTypes: GoodsType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private goodsTypeService: GoodsTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.goodsTypeService.query().subscribe(
            (res: Response) => {
                this.goodsTypes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGoodsTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: GoodsType) {
        return item.id;
    }



    registerChangeInGoodsTypes() {
        this.eventSubscriber = this.eventManager.subscribe('goodsTypeListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
