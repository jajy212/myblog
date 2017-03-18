import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Goods } from './goods.model';
import { GoodsService } from './goods.service';

@Component({
    selector: 'jhi-goods-detail',
    templateUrl: './goods-detail.component.html'
})
export class GoodsDetailComponent implements OnInit, OnDestroy {

    goods: Goods;
    private subscription: any;

    constructor(
        private goodsService: GoodsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.goodsService.find(id).subscribe(goods => {
            this.goods = goods;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
