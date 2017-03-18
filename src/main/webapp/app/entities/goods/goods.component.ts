import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { Goods } from './goods.model';
import { GoodsService } from './goods.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-goods',
    templateUrl: './goods.component.html'
})
export class GoodsComponent implements OnInit, OnDestroy {
goods: Goods[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private goodsService: GoodsService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.goodsService.query().subscribe(
            (res: Response) => {
                this.goods = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGoods();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId (index: number, item: Goods) {
        return item.id;
    }



    registerChangeInGoods() {
        this.eventSubscriber = this.eventManager.subscribe('goodsListModification', (response) => this.loadAll());
    }


    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}
