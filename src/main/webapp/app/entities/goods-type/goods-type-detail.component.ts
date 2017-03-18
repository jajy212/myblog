import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GoodsType } from './goods-type.model';
import { GoodsTypeService } from './goods-type.service';

@Component({
    selector: 'jhi-goods-type-detail',
    templateUrl: './goods-type-detail.component.html'
})
export class GoodsTypeDetailComponent implements OnInit, OnDestroy {

    goodsType: GoodsType;
    private subscription: any;

    constructor(
        private goodsTypeService: GoodsTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.goodsTypeService.find(id).subscribe(goodsType => {
            this.goodsType = goodsType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
